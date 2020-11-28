package org.cdteam.employee.base.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import org.cdteam.employee.converter.LocalDateTimeConverter;
import org.cdteam.employee.converter.LocalDateTimeDeserializer;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class TrainPlanCreateDTO {
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
     * 开设课程
     */
    private String makeCourse;

    /**
     * 预计开课时间
     */
    @JsonSerialize(using = LocalDateTimeConverter.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime beginTime;

    /**
     * 课时
     */
    private Float classHour;

    /**
     * 备注
     */
    private String remark;

    /**
     * 批量选择人员时候会有
     */
    private List<EmployeeCreateDTO> refEmployeeAssemblyList;
}
