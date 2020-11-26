package org.cdteam.employee.base.dto;

import lombok.Data;


@Data
public class TrainPlanUploadDTO {

    /**
     * 工号
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
     * 开设课程
     */
    private String makeCourse;

    /**
     * 预计开课时间
     */
    private String beginTime;

    /**
     * 课时
     */
    private Float classHour;

    /**
     * 备注
     */
    private String remark;
}
