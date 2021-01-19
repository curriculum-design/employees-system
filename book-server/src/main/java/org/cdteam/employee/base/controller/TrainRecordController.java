package org.cdteam.employee.base.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.cdteam.employee.base.dto.*;
import org.cdteam.employee.base.service.EmployeeService;
import org.cdteam.employee.base.service.TeacherService;
import org.cdteam.employee.base.service.TrainRecordService;
import org.cdteam.spring.cloud.starter.common.utils.BeanCopyUtils;
import org.cdteam.spring.cloud.starter.context.bean.Pagination;
import org.cdteam.spring.cloud.starter.context.constant.ResponseCodeEnum;
import org.cdteam.spring.cloud.starter.context.exception.AppException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

@Slf4j
@Api(value = "培训计划管理", tags = "[基础模块]培训计划管理")
@RestController
@RequestMapping("/v1/base-train-record")
public class TrainRecordController {

    @Autowired
    TrainRecordService trainRecordService;

    @Autowired
    TeacherService teacherService;

    @Autowired
    EmployeeService employeeService;

    @ApiOperation("获取培训计划列表")
    @GetMapping("/list")
    public Pagination<TrainRecordDTO> info(Integer pageSize, @RequestParam(defaultValue = "1") Integer pageNum
            , String realName, String org, String dept, String jobName, String courseNo, String courseName, String makeCourse
            , LocalDateTime beginTime, LocalDateTime endTime, String teacherName, Boolean byTeacher) {
        return trainRecordService.page(pageSize, pageNum, realName, org, dept, jobName
                , courseNo, courseName, makeCourse, beginTime, endTime, teacherName, byTeacher);
    }

    @ApiOperation("新增培训计划")
    @PostMapping("/upload-save")
    public Integer uploadSave(@RequestBody TrainRecordUploadDTO trainRecordDTO) {
        String beginTime = trainRecordDTO.getBeginTime();
        String endTime = trainRecordDTO.getEndTime();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.getDefault());

        LocalDate beginTimeDate;
        LocalDate endTimeDate;
        try {
            beginTimeDate = LocalDate.parse(beginTime, formatter);
            endTimeDate = LocalDate.parse(endTime, formatter);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw e;
        }

        EmployeeDTO employeeDTO = employeeService.getByCode(trainRecordDTO.getEmployeeCode());
        if (employeeDTO == null) {
            throw new AppException(ResponseCodeEnum.COMMON_EXCEPTION, "编码不存在");
        }
        // 判断讲师是否存在
        TeacherDTO teacherDTO = teacherService.getByName(trainRecordDTO.getTeacherName());
        if (teacherDTO == null) {
            throw new AppException(ResponseCodeEnum.COMMON_EXCEPTION, "讲师不存在");
        }

        TrainRecordCreateDTO trainRecordCreateDTO = BeanCopyUtils.transferBean(trainRecordDTO, TrainRecordCreateDTO.class);
        trainRecordCreateDTO.setBeginTime(LocalDateTime.of(beginTimeDate, LocalTime.MIN));
        trainRecordCreateDTO.setEndTime(LocalDateTime.of(endTimeDate, LocalTime.MIN));
        trainRecordCreateDTO.setEmployeeId(employeeDTO.getId());
        return trainRecordService.uploadSave(trainRecordCreateDTO);
    }

    @ApiOperation("新增培训计划")
    @GetMapping("/exist-course-no")
    public Boolean checkCourseNo(@RequestParam String courseNo) {
        return trainRecordService.existCourseNo(courseNo);
    }

    @ApiOperation("新增培训计划")
    @PostMapping("/save")
    public Integer save(@RequestBody TrainRecordCreateDTO trainRecordDTO) {
        if (!CollectionUtils.isEmpty(trainRecordDTO.getRefEmployeeAssemblyList())) {

            String courseNo = trainRecordDTO.getCourseNo();
            trainRecordService.deleteByCourseNo(courseNo);

            trainRecordDTO.getRefEmployeeAssemblyList().forEach(d -> {
                TrainRecordCreateDTO trainRecordCreateDTO = BeanCopyUtils.transferBean(trainRecordDTO, TrainRecordCreateDTO.class);
                trainRecordCreateDTO.setEmployeeId(d.getId());
                trainRecordCreateDTO.setId(null);
                trainRecordService.save(trainRecordCreateDTO);
            });
            return trainRecordDTO.getRefEmployeeAssemblyList().size();
        }
        return trainRecordService.save(trainRecordDTO);
    }

    @ApiOperation("查找关联课程编号的所有用户")
    @PostMapping("/search-employees-by-course-no")
    public List<EmployeeDTO> searchEmployeesByCourseNo(String courseNo) {
        if (StringUtils.isBlank(courseNo)) {
            return Collections.emptyList();
        }
        return trainRecordService.searchEmployeesByCourseNo(courseNo);
    }

    @ApiOperation("修改培训计划")
    @PutMapping("/update")
    public Integer update(@RequestBody TrainRecordCreateDTO trainRecordDTO) {
        return save(trainRecordDTO);
    }

    @ApiOperation("删除培训计划")
    @DeleteMapping("/{id}")
    public Integer delete(@PathVariable Long id) {
        return trainRecordService.delete(id);
    }

    @ApiOperation("获取所有培训计划")
    @GetMapping("/all")
    public List<TrainRecordDTO> list() {
        return trainRecordService.list();
    }

    @ApiOperation("获取培训计划信息")
    @GetMapping("/{id}")
    public TrainRecordDTO get(@PathVariable Long id) {
        return trainRecordService.getById(id);
    }
}
