package com.ruoyi.voyaai.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import com.ruoyi.voyaai.domain.entity.VoyaAiSearchHistory;
import com.ruoyi.voyaai.domain.vo.SearchHistoryVO;

public interface VoyaAiSearchHistoryMapper {

    int insert(VoyaAiSearchHistory entity);

    int deleteByUserAndKeyword(@Param("userId") Long userId, @Param("keyword") String keyword);

    int deleteOldestBeyond(@Param("userId") Long userId, @Param("keep") int keep);

    int deleteByIdAndUser(@Param("id") Long id, @Param("userId") Long userId);

    int deleteByUser(@Param("userId") Long userId);

    List<SearchHistoryVO> selectByUser(@Param("userId") Long userId);

    List<SearchHistoryVO> selectAdminList(@Param("keyword") String keyword, @Param("nickname") String nickname,
            @Param("beginCreateTime") String beginCreateTime, @Param("endCreateTime") String endCreateTime);

    SearchHistoryVO selectById(@Param("id") Long id);

    int removeById(@Param("id") Long id);
}
