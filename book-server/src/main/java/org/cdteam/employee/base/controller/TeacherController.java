package org.cdteam.employee.base.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.cdteam.employee.base.dto.TeacherCreateDTO;
import org.cdteam.employee.base.dto.TeacherDTO;
import org.cdteam.employee.base.dto.TeacherUploadDTO;
import org.cdteam.employee.base.service.TeacherService;
import org.cdteam.spring.cloud.starter.common.utils.BeanCopyUtils;
import org.cdteam.spring.cloud.starter.context.bean.Pagination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

@Slf4j
@Api(value = "讲师管理", tags = "[基础模块]讲师管理")
@RestController
@RequestMapping("/v1/base-teacher")
public class TeacherController {

    @Autowired
    TeacherService teacherService;

    @ApiOperation("获取讲师列表")
    @GetMapping("/list")
    public Pagination<TeacherDTO> info(Integer pageSize, @RequestParam(defaultValue = "1") Integer pageNum
            , String teacherName, String workType, String org) {
        return teacherService.page(pageSize, pageNum, teacherName, workType, org);
    }

    @ApiOperation("新增讲师")
    @PostMapping("/upload-save")
    public Integer uploadSave(@RequestBody TeacherUploadDTO teacherDTO) {
        TeacherCreateDTO teacherCreateDTO = BeanCopyUtils.transferBean(teacherDTO, TeacherCreateDTO.class);
        return teacherService.uploadSave(teacherCreateDTO);
    }

    @ApiOperation("新增讲师")
    @PostMapping("/save")
    public Integer save(@RequestBody TeacherCreateDTO teacherDTO) {
        return teacherService.save(teacherDTO);
    }

    @ApiOperation("修改讲师")
    @PutMapping("/update")
    public Integer update(@RequestBody TeacherCreateDTO teacherDTO) {
        return save(teacherDTO);
    }

    @ApiOperation("删除讲师")
    @DeleteMapping("/{id}")
    public Integer delete(@PathVariable Long id) {
        return teacherService.delete(id);
    }

    @ApiOperation("获取所有讲师")
    @GetMapping("/all")
    public List<TeacherDTO> list() {
        return teacherService.list();
    }

    @ApiOperation("获取讲师信息")
    @GetMapping("/{id}")
    public TeacherDTO get(@PathVariable Long id) {
        return teacherService.getById(id);
    }
}
