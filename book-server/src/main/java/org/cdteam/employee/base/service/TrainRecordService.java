package org.cdteam.employee.base.service;

import org.cdteam.employee.base.dto.TrainRecordCreateDTO;
import org.cdteam.employee.base.dto.TrainRecordDTO;
import org.cdteam.spring.cloud.starter.context.bean.Pagination;

import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author lesl
 * @since 2020-11-22
 */
public interface TrainRecordService {

    Pagination<TrainRecordDTO> page(Integer pageSize, Integer pageNum
            , String realName, String org, String dept, String jobName, String courseName, String makeCourse
            , LocalDateTime beginTime, LocalDateTime endTime, String teacherName);

    Integer save(TrainRecordCreateDTO employeeDTO);

    Integer delete(Long id);

    List<TrainRecordDTO> list();

    TrainRecordDTO getById(Long id);

    Integer uploadSave(TrainRecordCreateDTO employeeDTO);
}
