package org.cdteam.employee.base.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import org.cdteam.employee.converter.LocalDateTimeConverter;
import org.cdteam.employee.converter.LocalDateTimeDeserializer;

import java.time.LocalDateTime;

@Data
public class TeacherCreateDTO {

    /**
     * ID
     */
    private Long id;

    /**
     * 讲师名称
     */
    private String teacherName;

    /**
     * 类型
     */
    private String workType;

}
