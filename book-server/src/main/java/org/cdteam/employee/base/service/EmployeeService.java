package org.cdteam.employee.base.service;

import org.cdteam.employee.base.dto.EmployeeDTO;
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
public interface EmployeeService {

    Pagination<EmployeeDTO> page(Integer pageSize, Integer pageNum, Long typeId, Long publisherId, String code, String name);

    Integer save(EmployeeDTO employeeDTO);

    Integer delete(Long id);

    List<EmployeeDTO> list();

    EmployeeDTO getById(Long id);
}
