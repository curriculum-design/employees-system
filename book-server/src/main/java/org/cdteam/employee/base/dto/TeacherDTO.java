package org.cdteam.employee.base.dto;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import org.cdteam.employee.converter.LocalDateTimeConverter;
import org.cdteam.employee.converter.LocalDateTimeDeserializer;

import java.time.LocalDateTime;

@Data
public class TeacherDTO {

    /**
     * ID
     */
    private Long id;

    /**
     * 中文名称
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

    /**
     * 新增时间
     */
    @TableField(fill = FieldFill.INSERT)
    @JsonSerialize(using = LocalDateTimeConverter.class)
    private LocalDateTime createTime;

    /**
     * 修改时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    @JsonSerialize(using = LocalDateTimeConverter.class)
    private LocalDateTime updateTime;

}
