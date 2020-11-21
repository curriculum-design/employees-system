package org.cdteam.employee.base.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import org.cdteam.employee.converter.LocalDateTimeConverter;
import org.cdteam.employee.converter.LocalDateTimeDeserializer;


@Data
public class EmployeeUploadDTO {

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
    private String joinTime;
}
