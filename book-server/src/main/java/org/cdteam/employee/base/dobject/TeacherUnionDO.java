package org.cdteam.employee.base.dobject;

import lombok.Data;
import org.cdteam.employee.base.domain.TeacherEntity;

import java.time.LocalDateTime;

@Data
public class TeacherUnionDO extends TeacherEntity {
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
    private LocalDateTime joinTime;
}
