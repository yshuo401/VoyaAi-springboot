package com.ruoyi.voyaai.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import com.ruoyi.voyaai.domain.entity.VoyaAiFeedback;
import com.ruoyi.voyaai.domain.vo.FeedbackVO;

public interface VoyaAiFeedbackMapper {

    int insert(VoyaAiFeedback entity);

    List<FeedbackVO> selectAdminList(@Param("status") String status, @Param("keyword") String keyword,
            @Param("contact") String contact,
            @Param("beginCreateTime") String beginCreateTime, @Param("endCreateTime") String endCreateTime);

    FeedbackVO selectById(@Param("id") Long id);

    int handle(@Param("id") Long id, @Param("handleRemark") String handleRemark,
            @Param("handleBy") String handleBy);
}
