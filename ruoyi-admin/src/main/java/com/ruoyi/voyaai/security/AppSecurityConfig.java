package com.ruoyi.voyaai.security;

import java.util.List;
import java.io.IOException;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import org.springframework.context.annotation.*;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import com.ruoyi.voyaai.service.app.AppSessionService;

/** Isolated chain: mini-program tokens never become RuoYi administrator principals. */
@Configuration
public class AppSecurityConfig {
    @Bean
    @Order(0)
    public SecurityFilterChain appSecurity(HttpSecurity http, AppSessionService sessions) throws Exception {
        return http.securityMatcher("/app/voyaai/**")
            .csrf(c -> c.disable()).requestCache(c -> c.disable())
            .sessionManagement(c -> c.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .exceptionHandling(c -> c
                .authenticationEntryPoint((req, res, ex) -> error(res, 401, "请先登录或重新登录"))
                .accessDeniedHandler((req, res, ex) -> error(res, 403, "无权执行此操作")))
            .authorizeHttpRequests(c -> c
                .requestMatchers(HttpMethod.POST, "/app/voyaai/auth/login").permitAll()
                .requestMatchers(HttpMethod.POST, "/app/voyaai/view-logs").permitAll()
                .requestMatchers(HttpMethod.GET, "/app/voyaai/cities", "/app/voyaai/cities/*/attractions", "/app/voyaai/attractions/*", "/app/voyaai/guides", "/app/voyaai/guides/*", "/app/voyaai/tags", "/app/voyaai/comments").permitAll()
                .anyRequest().authenticated())
            // Construct here rather than as a servlet Filter bean: it runs only in this chain.
            .addFilterBefore(new OncePerRequestFilter() {
                @Override
                protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws ServletException, IOException {
                    String token = AppSessionService.bearer(req.getHeader("Authorization"));
                    Long id;
                    try { id = sessions.resolve(token); }
                    catch (Exception e) { error(res, 503, "登录服务暂不可用，请稍后重试"); return; }
                    if (id != null) {
                        var context = SecurityContextHolder.createEmptyContext();
                        context.setAuthentication(new UsernamePasswordAuthenticationToken(new AppPrincipal(id), null, List.of()));
                        SecurityContextHolder.setContext(context);
                    }
                    chain.doFilter(req, res);
                }
            }, UsernamePasswordAuthenticationFilter.class).build();
    }
    private static void error(HttpServletResponse res, int code, String message) throws IOException {
        res.setStatus(code); res.setContentType("application/json;charset=UTF-8");
        res.getWriter().write("{\"code\":" + code + ",\"msg\":\"" + message + "\"}");
    }
}
