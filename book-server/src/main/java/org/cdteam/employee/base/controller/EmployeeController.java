package org.cdteam.employee.base.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.cdteam.spring.cloud.starter.context.bean.Pagination;
import org.cdteam.employee.base.dto.EmployeeDTO;
import org.cdteam.employee.base.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Api(value = "人员管理", tags = "[基础模块]人员管理")
@RestController
@RequestMapping("/v1/base-employee")
public class EmployeeController {

    @Autowired
    EmployeeService employeeService;

    @ApiOperation("获取人员列表")
    @GetMapping("/list")
    public Pagination<EmployeeDTO> info(Integer pageSize, @RequestParam(defaultValue = "1") Integer pageNum
            , Long typeId, Long publisherId, String code, String name) {
        return employeeService.page(pageSize, pageNum, typeId, publisherId, code, name);
    }

    @ApiOperation("新增人员")
    @PostMapping("/save")
    public Integer save(@RequestBody EmployeeDTO employeeDTO) {
        return employeeService.save(employeeDTO);
    }

    @ApiOperation("修改人员")
    @PutMapping("/update")
    public Integer update(@RequestBody EmployeeDTO employeeDTO) {
        return save(employeeDTO);
    }

    @ApiOperation("删除人员")
    @DeleteMapping("/{id}")
    public Integer delete(@PathVariable Long id) {
        return employeeService.delete(id);
    }

    @ApiOperation("获取所有人员")
    @GetMapping("/all")
    public List<EmployeeDTO> list() {
        return employeeService.list();
    }

    @ApiOperation("获取人员信息")
    @GetMapping("/{id}")
    public EmployeeDTO get(@PathVariable Long id) {
        return employeeService.getById(id);
    }
}
