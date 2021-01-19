package org.cdteam.employee.base.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import org.cdteam.employee.converter.LocalDateTimeConverter;
import org.cdteam.employee.converter.LocalDateTimeDeserializer;

import java.time.LocalDateTime;

@Data
public class CourseCreateDTO {
    /**
     * 课程ID
     */
    private Long id;

    /**
     * 课程编号
     */
    private String courseNo;

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
    private Float classHour;

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

}
