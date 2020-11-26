package org.cdteam.employee.base.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import org.cdteam.employee.converter.LocalDateTimeConverter;
import org.cdteam.employee.converter.LocalDateTimeDeserializer;

import java.time.LocalDateTime;


@Data
public class TrainRecordUploadDTO {

    /**
     * 人员编码
     */
    private String employeeCode;

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
    private String beginTime;

    /**
     * 完成时间
     */
    private String endTime;

    /**
     * 课时
     */
    private Float classHour;

    /**
     * 讲师ID
     */
    private String teacherCode;

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
}
