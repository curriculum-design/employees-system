package org.cdteam.employee.base.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.Version;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 培训课程
 * </p>
 *
 * @author lesl
 * @since 2021-01-20
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("base_course")
public class CourseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 课程编号
     */
    private String courseNo;

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
    private LocalDateTime beginTime;

    /**
     * 完成时间
     */
    private LocalDateTime endTime;

    /**
     * 课时
     */
    private Float classHour;

    /**
     * 讲师ID
     */
    private Long teacherId;

    /**
     * 讲师
     */
    private String teacherName;

    /**
     * 课程满意度
     */
    private String rate;

    /**
     * 新增时间
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    /**
     * 修改时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    /**
     * 是否删除
     */
    @TableLogic
    private Boolean isDel;


}
