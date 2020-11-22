package org.cdteam.employee.base.dto;

import lombok.Data;


@Data
public class TeacherUploadDTO {
    /**
     * 讲师名称
     */
    private String teacherName;

    /**
     * 类型
     */
    private String workType;

    /**
     * 机构
     */
    private String org;
}
