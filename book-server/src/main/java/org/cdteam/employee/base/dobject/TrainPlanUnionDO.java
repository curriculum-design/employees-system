package org.cdteam.employee.base.dobject;

import lombok.Getter;
import lombok.Setter;
import org.cdteam.employee.base.domain.TrainPlanEntity;

import java.time.LocalDateTime;

@Getter
@Setter
public class TrainPlanUnionDO extends TrainPlanEntity {

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
