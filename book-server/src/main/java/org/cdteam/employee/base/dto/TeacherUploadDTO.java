package org.cdteam.employee.base.dto;

import lombok.Data;


@Data
public class TeacherUploadDTO {

    /**
     * 人员编码
     */
    private String employeeCode;
    /**
     * 讲师名称
     */
    private String teacherName;

    /**
     * 类型
     */
    private String workType;
    /**
     * 主讲课程
     */
    private String speakCourse;

    /**
     * 认证/合作时间
     */
    private String teamWorkTime;
}
