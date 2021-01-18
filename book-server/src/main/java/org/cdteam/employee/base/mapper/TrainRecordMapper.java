package org.cdteam.employee.base.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.cdteam.employee.base.dobject.TrainRecordUnionDO;
import org.cdteam.employee.base.domain.TrainRecordEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author lesl
 * @since 2020-11-22
 */
@Repository
public interface TrainRecordMapper extends BaseMapper<TrainRecordEntity> {

    IPage<TrainRecordUnionDO> selectUnionPage(Page<TrainRecordUnionDO> page
            , String realName, String org, String dept, String jobName, String courseNo, String courseName, String makeCourse
            , LocalDateTime beginTime, LocalDateTime endTime, String teacherName);

    IPage<TrainRecordUnionDO> selectUnionPageGroupTeacher(Page<TrainRecordUnionDO> page, String teacherName);
}
