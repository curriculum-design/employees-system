package org.cdteam.employee.base.dto;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import org.cdteam.employee.converter.LocalDateTimeConverter;
import org.cdteam.employee.converter.LocalDateTimeDeserializer;

import java.time.LocalDateTime;

@Data
public class TrainRecordDTO extends EmployeeDTO{

    /**
     * ID
     */
    private Long id;

    /**
     * 人员ID
     */
    private Long employeeId;

    /**
     * 课程名称
     */
    private String courseName;

    /**
     * 培训形式
     */
    private String trainStyle;

    /**
     * 开设部门/机构
     */
    private String makeCourse;

    /**
     * 开课时间
     */
    @JsonSerialize(using = LocalDateTimeConverter.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime beginTime;

    /**
     * 完成时间
     */
    @JsonSerialize(using = LocalDateTimeConverter.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime endTime;

    /**
     * 课时
     */
    private Integer classHour;

    /**
     * 讲师ID
     */
    private Long teacherId;

    /**
     * 讲师
     */
    private String teacherName;

    /**
     * 课程满意度
     */
    private String rate;

    /**
     * 考核成绩
     */
    private String score;

    /**
     * 备注
     */
    private String remark;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    @JsonSerialize(using = LocalDateTimeConverter.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime createTime;

    /**
     * 修改时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    @JsonSerialize(using = LocalDateTimeConverter.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime updateTime;
}
