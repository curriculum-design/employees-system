package org.cdteam.employee.base.dto;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import org.cdteam.employee.converter.LocalDateTimeConverter;
import org.cdteam.employee.converter.LocalDateTimeDeserializer;

import java.time.LocalDateTime;

@Data
public class EmployeeDTO {

    /**
     * ID
     */
    private Long id;

    /**
     * 人员编码
     */
    private String employeeCode;

    /**
     * 中文名称
     */
    private String realName;

    /**
     * 是否在职
     */
    private String onJob;

    /**
     * 工种
     */
    private String workType;

    /**
     * 机构
     */
    private String org;

    /**
     * 部门
     */
    private String dept;

    /**
     * 岗位
     */
    private String jobName;

    /**
     * 入职时间
     */
    @JsonSerialize(using = LocalDateTimeConverter.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime joinTime;

    /**
     * 新增时间
     */
    @TableField(fill = FieldFill.INSERT)
    @JsonSerialize(using = LocalDateTimeConverter.class)
    private LocalDateTime createTime;

    /**
     * 修改时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    @JsonSerialize(using = LocalDateTimeConverter.class)
    private LocalDateTime updateTime;

}
