package org.cdteam.employee.base.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.cdteam.employee.base.dto.EmployeeCreateDTO;
import org.cdteam.employee.base.dto.EmployeeUploadDTO;
import org.cdteam.spring.cloud.starter.common.utils.BeanCopyUtils;
import org.cdteam.spring.cloud.starter.context.bean.Pagination;
import org.cdteam.employee.base.dto.EmployeeDTO;
import org.cdteam.employee.base.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

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
            , String realName, String onJob, String workType, String org, String dept, String jobName
            , Timestamp beginTime, Timestamp endTime) {
        return employeeService.page(pageSize, pageNum, realName, onJob, workType, org, dept, jobName
                , Optional.ofNullable(beginTime).map(Timestamp::toLocalDateTime).orElse(null)
                , Optional.ofNullable(endTime).map(Timestamp::toLocalDateTime).orElse(null));
    }

    @ApiOperation("新增人员")
    @PostMapping("/upload-save")
    public Integer uploadSave(@RequestBody EmployeeUploadDTO employeeDTO) {
        String joinTime = employeeDTO.getJoinTime();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.getDefault());

        LocalDate localDate;
        try {
            localDate = LocalDate.parse(joinTime, formatter);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw e;
        }

        EmployeeCreateDTO employeeCreateDTO = BeanCopyUtils.transferBean(employeeDTO, EmployeeCreateDTO.class);
        employeeCreateDTO.setJoinTime(LocalDateTime.of(localDate, LocalTime.MIN));
        return employeeService.uploadSave(employeeCreateDTO);
    }

    @ApiOperation("新增人员")
    @PostMapping("/save")
    public Integer save(@RequestBody EmployeeCreateDTO employeeDTO) {
        return employeeService.save(employeeDTO);
    }

    @ApiOperation("修改人员")
    @PutMapping("/update")
    public Integer update(@RequestBody EmployeeCreateDTO employeeDTO) {
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
