package org.cdteam.employee.base.service;

import org.cdteam.employee.base.dto.*;
import org.cdteam.spring.cloud.starter.context.bean.Pagination;

import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 培训课程 服务类
 * </p>
 *
 * @author lesl
 * @since 2021-01-19
 */
public interface CourseService {

    Pagination<CourseDTO> page(Integer pageSize, Integer pageNum, String courseNo, String courseName, String trainStyle, String makeCourse
            , LocalDateTime beginTime, LocalDateTime endTime, String teacherName);

    Integer save(CourseCreateDTO employeeDTO);

    Integer delete(Long id);

    List<CourseDTO> list();

    CourseDTO getById(Long id);

    Integer uploadSave(CourseCreateDTO employeeDTO);

    List<CourseDTO> all();

    CourseDTO getByName(String courseName);
}
