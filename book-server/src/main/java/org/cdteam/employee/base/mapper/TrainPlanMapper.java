package org.cdteam.employee.base.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Select;
import org.cdteam.employee.base.dobject.TrainPlanUnionDO;
import org.cdteam.employee.base.domain.TrainPlanEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author lesl
 * @since 2020-11-21
 */
@Repository
public interface TrainPlanMapper extends BaseMapper<TrainPlanEntity> {

    IPage<TrainPlanUnionDO> selectUnionPage(Page<TrainPlanUnionDO> page, String realName
            , String org, String dept, String jobName, String courseName, String trainStyle, String makeCourse, LocalDateTime beginTime, LocalDateTime endTime);
}
