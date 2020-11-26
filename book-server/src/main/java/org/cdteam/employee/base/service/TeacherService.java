package org.cdteam.employee.base.service;

import org.cdteam.employee.base.dto.TeacherCreateDTO;
import org.cdteam.employee.base.dto.TeacherDTO;
import org.cdteam.employee.base.dto.TeacherUploadDTO;
import org.cdteam.spring.cloud.starter.context.bean.Pagination;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author lesl
 * @since 2020-11-11
 */
public interface TeacherService {

    Pagination<TeacherDTO> page(Integer pageSize, Integer pageNum, String teacherName, String workType, String dept, String org, String jobName);

    Integer save(TeacherCreateDTO employeeDTO);

    Integer delete(Long id);

    List<TeacherDTO> list();

    TeacherDTO getById(Long id);

    Integer uploadSave(TeacherCreateDTO employeeDTO);

    List<TeacherDTO> all();

    TeacherDTO getByName(String teacherName);
}
