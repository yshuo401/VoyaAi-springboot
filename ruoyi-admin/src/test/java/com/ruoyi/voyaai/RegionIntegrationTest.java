//package com.ruoyi.voyaai;
//
//import java.io.InputStream;
//import java.math.BigDecimal;
//import java.time.LocalDate;
//import java.util.Map;
//import java.util.Properties;
//import javax.sql.DataSource;
//import jakarta.validation.Validator;
//import org.apache.ibatis.session.SqlSessionFactory;
//import org.h2.jdbcx.JdbcDataSource;
//import org.junit.jupiter.api.*;
//import org.mybatis.spring.annotation.MapperScan;
//import org.springframework.context.annotation.*;
//import org.springframework.core.env.MapPropertySource;
//import org.springframework.jdbc.core.JdbcTemplate;
//import org.springframework.jdbc.datasource.DataSourceTransactionManager;
//import org.springframework.transaction.annotation.EnableTransactionManagement;
//import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
//import com.github.pagehelper.PageHelper;
//import com.github.pagehelper.PageInfo;
//import com.github.pagehelper.PageInterceptor;
//import com.ruoyi.common.exception.ServiceException;
//import com.ruoyi.framework.config.MyBatisConfig;
//import com.ruoyi.voyaai.controller.admin.CityController;
//import com.ruoyi.voyaai.domain.dto.*;
//import com.ruoyi.voyaai.domain.vo.CityVO;
//import com.ruoyi.voyaai.service.*;
//import com.ruoyi.voyaai.service.impl.*;
//
//import static org.junit.jupiter.api.Assertions.*;
//
///** Real XML mappings and transactional services against an isolated MySQL-mode H2 database. */
//class RegionIntegrationTest {
//    static AnnotationConfigApplicationContext context;
//    static JdbcTemplate jdbc;
//    static IVoyaAiCountryService countries;
//    static IVoyaAiProvinceService provinces;
//    static IVoyaAiCityService cities;
//
//    @Configuration
//    @EnableTransactionManagement
//    @MapperScan("com.ruoyi.voyaai.mapper")
//    @Import({MyBatisConfig.class, VoyaAiCountryServiceImpl.class, VoyaAiProvinceServiceImpl.class,
//            VoyaAiCityServiceImpl.class})
//    static class Config {
//        @Bean DataSource dataSource() {
//            JdbcDataSource source = new JdbcDataSource();
//            source.setURL("jdbc:h2:mem:regions;MODE=MySQL;DB_CLOSE_DELAY=-1");
//            return source;
//        }
//        @Bean DataSourceTransactionManager transactionManager(DataSource source) {
//            return new DataSourceTransactionManager(source);
//        }
//        @Bean LocalValidatorFactoryBean validator() { return new LocalValidatorFactoryBean(); }
//    }
//
//    @BeforeAll static void start() {
//        context = new AnnotationConfigApplicationContext();
//        context.getEnvironment().getPropertySources().addFirst(new MapPropertySource("test", Map.of(
//            "mybatis.typeAliasesPackage", "com.ruoyi.**.domain",
//            "mybatis.mapperLocations", "classpath*:mapper/**/*Mapper.xml",
//            "mybatis.configLocation", "classpath:mybatis/mybatis-config.xml")));
//        context.register(Config.class);
//        context.refresh();
//        PageInterceptor pagination = new PageInterceptor();
//        Properties properties = new Properties();
//        properties.setProperty("helperDialect", "mysql");
//        pagination.setProperties(properties);
//        context.getBean(SqlSessionFactory.class).getConfiguration().addInterceptor(pagination);
//        jdbc = new JdbcTemplate(context.getBean(DataSource.class));
//        countries = context.getBean(IVoyaAiCountryService.class);
//        provinces = context.getBean(IVoyaAiProvinceService.class);
//        cities = context.getBean(IVoyaAiCityService.class);
//    }
//
//    @AfterAll static void stop() { if (context != null) context.close(); }
//
//    @BeforeEach void reset() throws Exception {
//        PageHelper.clearPage();
//        try (InputStream input = getClass().getResourceAsStream("/region-schema.sql")) {
//            assertNotNull(input);
//            for (String statement : new String(input.readAllBytes(), java.nio.charset.StandardCharsets.UTF_8).split(";")) {
//                if (!statement.isBlank()) jdbc.execute(statement);
//            }
//        }
//        CountryDTO country = new CountryDTO();
//        country.setName("中国"); country.setCode("CN");
//        countries.create(country, "admin");
//        ProvinceDTO province = new ProvinceDTO();
//        province.setCountryId(1L); province.setName("四川");
//        provinces.create(province, "admin");
//    }
//
//    private CityDTO city(String name) {
//        CityDTO dto = new CityDTO();
//        dto.setProvinceId(1L); dto.setName(name);
//        dto.setLatitude(new BigDecimal("30.5728000"));
//        dto.setLongitude(new BigDecimal("104.0668000"));
//        return dto;
//    }
//
//    @Test void pageMetadataSurvivesVoProjectionAndController() {
//        for (int i = 0; i < 12; i++) cities.create(city("城市" + i), "admin");
//        CityQueryDTO query = new CityQueryDTO();
//        query.setPageNum(2); query.setPageSize(10);
//        var response = new CityController(cities).list(query);
//        assertEquals(12, response.getTotal());
//        assertEquals(2, response.getRows().size());
//        CityVO first = (CityVO) response.getRows().get(0);
//        assertEquals("四川", first.getProvinceName());
//        assertEquals("中国", first.getCountryName());
//        assertEquals(1L, first.getCountryId());
//        assertNull(PageHelper.getLocalPage());
//        assertEquals(12, cities.list(new CityQueryDTO()).size()); // export is unpaged
//    }
//
//    @Test void filteringIncludesEndDateAndUsesCountryJoin() {
//        cities.create(city("成都"), "admin");
//        jdbc.update("update voya_ai_city set create_time = '2026-09-20 23:59:59'");
//        CityQueryDTO query = new CityQueryDTO();
//        query.setCountryId(1L); query.setName("成");
//        query.setBeginCreateTime(LocalDate.of(2026,9,20));
//        query.setEndCreateTime(LocalDate.of(2026,9,20));
//        assertEquals(1, cities.list(query).size());
//        query.setCountryId(999L);
//        assertEquals(0, cities.list(query).size());
//    }
//
//    @Test void editPersistsStatusAndKeepsServerCountersAndAudit() {
//        cities.create(city("成都"), "creator");
//        jdbc.update("update voya_ai_city set view_count=123");
//        CityDTO dto = new CityDTO();
//        dto.setId(1L); dto.setProvinceId(1L); dto.setName("成都新名称"); dto.setStatus("1");
//        dto.setCoverImage("/profile/upload/test.jpg");
//        cities.update(dto, "editor");
//        CityVO result = cities.detail(1L);
//        assertEquals("1", result.getStatus());
//        assertEquals("creator", result.getCreateBy());
//        assertEquals("editor", result.getUpdateBy());
//        assertEquals(123L, result.getViewCount());
//        assertEquals("/profile/upload/test.jpg", result.getCoverImage());
//    }
//
//    @Test void duplicateIncludesDeletedNamesAndBlankCodesAreNull() {
//        cities.create(city("成都"), "admin");
//        assertEquals(409, assertThrows(ServiceException.class, () -> cities.create(city(" 成都 "), "admin")).getCode());
//        cities.delete(new Long[]{1L}, "admin");
//        assertEquals(409, assertThrows(ServiceException.class, () -> cities.create(city("成都"), "admin")).getCode());
//        for (String name : new String[]{"日本", "韩国"}) {
//            CountryDTO country = new CountryDTO(); country.setName(name); country.setCode(" ");
//            countries.create(country, "admin");
//        }
//        assertEquals(3, countries.options(null, true).size());
//    }
//
//    @Test void invalidParentStatusAndCoordinatesAreRejected() {
//        CityDTO dto = city("成都"); dto.setLatitude(new BigDecimal("91"));
//        assertEquals(400, assertThrows(ServiceException.class, () -> cities.create(dto, "admin")).getCode());
//        dto.setLatitude(null); dto.setProvinceId(999L);
//        assertEquals(409, assertThrows(ServiceException.class, () -> cities.create(dto, "admin")).getCode());
//        dto.setProvinceId(1L);
//        jdbc.update("update voya_ai_country set status='1'");
//        assertEquals(409, assertThrows(ServiceException.class, () -> cities.create(dto, "admin")).getCode());
//        assertTrue(provinces.options(1L, true).isEmpty());
//        assertEquals(1, provinces.options(1L, false).size());
//    }
//
//    @Test void disabledProvincesAreExcludedAndCannotBeSelected() {
//        RegionStatusDTO status = new RegionStatusDTO(); status.setId(1L); status.setStatus("1");
//        provinces.changeStatus(status, "editor");
//        assertTrue(provinces.options(1L, true).isEmpty());
//        assertEquals(409, assertThrows(ServiceException.class, () -> cities.create(city("成都"), "admin")).getCode());
//    }
//
//    @Test void linkedCountryAndProvinceCannotBeDeleted() {
//        assertEquals(409, assertThrows(ServiceException.class, () -> countries.delete(new Long[]{1L}, "admin")).getCode());
//        cities.create(city("成都"), "admin");
//        assertEquals(409, assertThrows(ServiceException.class, () -> provinces.delete(new Long[]{1L}, "admin")).getCode());
//    }
//
//    @Test void linkedCityBatchIsAtomic() {
//        cities.create(city("成都"), "admin"); cities.create(city("绵阳"), "admin");
//        jdbc.update("insert into voya_ai_attraction (city_id,del_flag) values (2,'0')");
//        assertEquals(409, assertThrows(ServiceException.class, () -> cities.delete(new Long[]{1L,2L}, "admin")).getCode());
//        assertEquals(2, cities.list(new CityQueryDTO()).size());
//        jdbc.update("update voya_ai_attraction set del_flag='1'");
//        assertEquals(2, cities.delete(new Long[]{1L,2L,2L}, "admin"));
//        assertTrue(cities.list(new CityQueryDTO()).isEmpty());
//        assertEquals(2, jdbc.queryForObject("select count(*) from voya_ai_city where del_flag='1' and update_by='admin'", Integer.class));
//    }
//
//    @Test void guidesAndTripsAlsoBlockCityDeletion() {
//        cities.create(city("成都"), "admin");
//        for (String table : new String[]{"voya_ai_guide", "voya_ai_trip"}) {
//            jdbc.update("insert into " + table + " (city_id,del_flag) values (1,'0')");
//            assertEquals(409, assertThrows(ServiceException.class, () -> cities.delete(new Long[]{1L}, "admin")).getCode());
//            jdbc.update("delete from " + table);
//        }
//    }
//
//    @Test void missingRecordAndInvalidBatchFailBeforeAnyDeletion() {
//        cities.create(city("成都"), "admin");
//        assertEquals(404, assertThrows(ServiceException.class, () -> cities.delete(new Long[]{1L,999L}, "admin")).getCode());
//        assertNotNull(cities.detail(1L));
//        assertEquals(400, assertThrows(ServiceException.class, () -> cities.delete(new Long[]{}, "admin")).getCode());
//        assertEquals(404, assertThrows(ServiceException.class, () -> cities.detail(999L)).getCode());
//    }
//
//    @Test void dtoBoundaryAndStatusValidation() throws Exception {
//        // Merged DTO has setId and audit fields are not exposed
//        RegionStatusDTO status = new RegionStatusDTO(); status.setId(1L); status.setStatus("2");
//        assertEquals(400, assertThrows(ServiceException.class, () -> cities.changeStatus(status, "admin")).getCode());
//        CityQueryDTO query = new CityQueryDTO(); query.setPageSize(1000);
//        assertEquals(400, assertThrows(ServiceException.class, () -> cities.list(query)).getCode());
//        query.setPageSize(10); query.setBeginCreateTime(LocalDate.of(2026,9,21)); query.setEndCreateTime(LocalDate.of(2026,9,20));
//        assertEquals(400, assertThrows(ServiceException.class, () -> cities.list(query)).getCode());
//    }
//
//    @Test void countryAndProvinceCrudAndPagination() {
//        CountryDTO country = new CountryDTO();
//        country.setId(1L); country.setName("中国更新"); country.setCode("CN");
//        countries.update(country,"editor");
//        ProvinceDTO province = new ProvinceDTO();
//        province.setId(1L); province.setCountryId(1L); province.setName("四川更新");
//        provinces.update(province,"editor");
//        assertEquals("中国更新", provinces.detail(1L).getCountryName());
//        assertEquals("四川更新", provinces.detail(1L).getName());
//        PageHelper.startPage(1,10);
//        assertEquals(1, new PageInfo<>(countries.list(new CountryQueryDTO())).getTotal());
//        assertEquals(1, provinces.delete(new Long[]{1L},"admin"));
//        assertEquals(1, countries.delete(new Long[]{1L},"admin"));
//        assertTrue(countries.options(null,true).isEmpty());
//    }
//}