package org.cdteam.employee.base.service;

import org.cdteam.employee.base.dto.TrainPlanCreateDTO;
import org.cdteam.employee.base.dto.TrainPlanDTO;
import org.cdteam.spring.cloud.starter.context.bean.Pagination;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author lesl
 * @since 2020-11-21
 */
public interface TrainPlanService {
    
    Pagination<TrainPlanDTO> page(Integer pageSize, Integer pageNum
            , String realName, String org, String dept, String jobName, String courseName, String trainStyle, String makeCourse
            , LocalDateTime beginTime, LocalDateTime endTime);

    Integer save(TrainPlanCreateDTO employeeDTO);

    Integer delete(Long id);

    List<TrainPlanDTO> list();

    TrainPlanDTO getById(Long id);

    Integer uploadSave(TrainPlanCreateDTO employeeDTO);
}
