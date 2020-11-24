package org.cdteam.employee.base.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.cdteam.employee.base.dto.EmployeeDTO;
import org.cdteam.employee.base.dto.TrainPlanCreateDTO;
import org.cdteam.employee.base.dto.TrainPlanDTO;
import org.cdteam.employee.base.dto.TrainPlanUploadDTO;
import org.cdteam.employee.base.service.EmployeeService;
import org.cdteam.employee.base.service.TrainPlanService;
import org.cdteam.spring.cloud.starter.common.utils.BeanCopyUtils;
import org.cdteam.spring.cloud.starter.context.bean.Pagination;
import org.cdteam.spring.cloud.starter.context.constant.ResponseCodeEnum;
import org.cdteam.spring.cloud.starter.context.exception.AppException;
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
@Api(value = "培训计划管理", tags = "[基础模块]培训计划管理")
@RestController
@RequestMapping("/v1/base-train-plan")
public class TrainPlanController {

    @Autowired
    TrainPlanService trainPlanService;

    @Autowired
    EmployeeService employeeService;

    @ApiOperation("获取培训计划列表")
    @GetMapping("/list")
    public Pagination<TrainPlanDTO> info(Integer pageSize, @RequestParam(defaultValue = "1") Integer pageNum
            , String realName, String org, String dept, String jobName, String courseName, String trainStyle, String makeCourse
            , Timestamp beginTime, Timestamp endTime) {
        return trainPlanService.page(pageSize, pageNum, realName, org, dept, jobName, courseName, trainStyle, makeCourse
                , Optional.ofNullable(beginTime).map(Timestamp::toLocalDateTime).orElse(null)
                , Optional.ofNullable(endTime).map(Timestamp::toLocalDateTime).orElse(null));
    }

    @ApiOperation("新增培训计划")
    @PostMapping("/upload-save")
    public Integer uploadSave(@RequestBody TrainPlanUploadDTO trainPlanDTO) {
        String joinTime = trainPlanDTO.getBeginTime();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.getDefault());

        LocalDate localDate;
        try {
            localDate = LocalDate.parse(joinTime + "-01", formatter);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw e;
        }

        EmployeeDTO employeeDTO = employeeService.getByCode(trainPlanDTO.getEmployeeCode());
        if (employeeDTO == null) {
            throw new AppException(ResponseCodeEnum.COMMON_EXCEPTION, "编码不存在");
        }

        TrainPlanCreateDTO trainPlanCreateDTO = BeanCopyUtils.transferBean(trainPlanDTO, TrainPlanCreateDTO.class);
        trainPlanCreateDTO.setBeginTime(LocalDateTime.of(localDate, LocalTime.MIN));
        trainPlanCreateDTO.setEmployeeId(employeeDTO.getId());
        return trainPlanService.uploadSave(trainPlanCreateDTO);
    }

    @ApiOperation("新增培训计划")
    @PostMapping("/save")
    public Integer save(@RequestBody TrainPlanCreateDTO trainPlanDTO) {
        return trainPlanService.save(trainPlanDTO);
    }

    @ApiOperation("修改培训计划")
    @PutMapping("/update")
    public Integer update(@RequestBody TrainPlanCreateDTO trainPlanDTO) {
        return save(trainPlanDTO);
    }

    @ApiOperation("删除培训计划")
    @DeleteMapping("/{id}")
    public Integer delete(@PathVariable Long id) {
        return trainPlanService.delete(id);
    }

    @ApiOperation("获取所有培训计划")
    @GetMapping("/all")
    public List<TrainPlanDTO> list() {
        return trainPlanService.list();
    }

    @ApiOperation("获取培训计划信息")
    @GetMapping("/{id}")
    public TrainPlanDTO get(@PathVariable Long id) {
        return trainPlanService.getById(id);
    }
}
