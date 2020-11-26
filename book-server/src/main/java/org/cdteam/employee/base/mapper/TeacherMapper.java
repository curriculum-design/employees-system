package org.cdteam.employee.base.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.cdteam.employee.base.dobject.TeacherUnionDO;
import org.cdteam.employee.base.domain.TeacherEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author lesl
 * @since 2020-11-26
 */
@Repository
public interface TeacherMapper extends BaseMapper<TeacherEntity> {

    IPage<TeacherUnionDO> selectUnionPage(Page<TeacherUnionDO> page, String teacherName, String workType, String dept, String org, String jobName);
}
