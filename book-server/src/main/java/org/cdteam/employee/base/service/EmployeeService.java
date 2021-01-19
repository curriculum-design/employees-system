package org.cdteam.employee.base.service;

import org.cdteam.employee.base.dto.EmployeeCreateDTO;
import org.cdteam.employee.base.dto.EmployeeDTO;
import org.cdteam.employee.base.dto.EmployeeUploadDTO;
import org.cdteam.spring.cloud.starter.context.bean.Pagination;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author lesl
 * @since 2020-11-11
 */
public interface EmployeeService {

    Pagination<EmployeeDTO> page(Integer pageSize, Integer pageNum, String realName, String onJob, String workType, String org, String dept, String jobName, LocalDateTime beginTime, LocalDateTime endTime);

    Integer save(EmployeeCreateDTO employeeDTO);

    Integer delete(Long id);

    List<EmployeeDTO> list();

    EmployeeDTO getById(Long id);

    Integer uploadSave(EmployeeCreateDTO employeeDTO);

    List<EmployeeDTO> all();

    EmployeeDTO getByCode(String employeeCode);

    List<EmployeeDTO> selectByIds(Set<Long> ids);

    List<EmployeeDTO> search(String q);
}
