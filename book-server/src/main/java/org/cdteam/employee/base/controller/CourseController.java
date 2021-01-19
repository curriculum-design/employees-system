package org.cdteam.employee.base.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.cdteam.employee.base.dto.*;
import org.cdteam.employee.base.service.CourseService;
import org.cdteam.employee.base.service.EmployeeService;
import org.cdteam.employee.base.service.TeacherService;
import org.cdteam.spring.cloud.starter.common.utils.BeanCopyUtils;
import org.cdteam.spring.cloud.starter.context.bean.Pagination;
import org.cdteam.spring.cloud.starter.context.constant.ResponseCodeEnum;
import org.cdteam.spring.cloud.starter.context.exception.AppException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

@Slf4j
@Api(value = "课程管理", tags = "[基础模块]课程管理")
@RestController
@RequestMapping("/v1/base-course")
public class CourseController {

    @Autowired
    TeacherService teacherService;

    @Autowired
    CourseService courseService;

    @ApiOperation("获取课程列表")
    @GetMapping("/list")
    public Pagination<CourseDTO> info(Integer pageSize, @RequestParam(defaultValue = "1") Integer pageNum
            , String courseNo, String courseName, String trainStyle, String makeCourse
            , LocalDateTime beginTime, LocalDateTime endTime, String teacherName) {
        return courseService.page(pageSize, pageNum, courseNo, courseName, trainStyle, makeCourse, beginTime, endTime, teacherName);
    }

    @ApiOperation("新增课程")
    @PostMapping("/upload-save")
    public Integer uploadSave(@RequestBody CourseUploadDTO courseDTO) {
        String beginTime = courseDTO.getBeginTime();
        String endTime = courseDTO.getEndTime();

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

        // 判断讲师是否存在
        TeacherDTO teacherDTO = teacherService.getByName(courseDTO.getTeacherName());
        if (teacherDTO == null) {
            throw new AppException(ResponseCodeEnum.COMMON_EXCEPTION, "讲师不存在");
        }

        CourseCreateDTO courseCreateDTO = BeanCopyUtils.transferBean(courseDTO, CourseCreateDTO.class);
        courseCreateDTO.setBeginTime(LocalDateTime.of(beginTimeDate, LocalTime.MIN));
        courseCreateDTO.setEndTime(LocalDateTime.of(endTimeDate, LocalTime.MIN));
        courseCreateDTO.setTeacherId(teacherDTO.getId());
        return courseService.uploadSave(courseCreateDTO);
    }

    @ApiOperation("新增课程")
    @PostMapping("/save")
    public Integer save(@RequestBody CourseCreateDTO courseDTO) {
        return courseService.save(courseDTO);
    }

    @ApiOperation("修改课程")
    @PutMapping("/update")
    public Integer update(@RequestBody CourseCreateDTO courseDTO) {
        return save(courseDTO);
    }

    @ApiOperation("删除课程")
    @DeleteMapping("/{id}")
    public Integer delete(@PathVariable Long id) {
        return courseService.delete(id);
    }

    @ApiOperation("获取所有课程")
    @GetMapping("/all")
    public List<CourseDTO> list() {
        return courseService.list();
    }

    @ApiOperation("获取课程信息")
    @GetMapping("/{id}")
    public CourseDTO get(@PathVariable Long id) {
        return courseService.getById(id);
    }
}
