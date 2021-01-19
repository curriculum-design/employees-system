package org.cdteam.employee.base.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.commons.lang3.StringUtils;
import org.cdteam.employee.base.domain.CourseEntity;
import org.cdteam.employee.base.dto.CourseCreateDTO;
import org.cdteam.employee.base.dto.CourseDTO;
import org.cdteam.employee.base.mapper.CourseMapper;
import org.cdteam.employee.base.service.CourseService;
import org.cdteam.spring.cloud.starter.common.utils.BeanCopyUtils;
import org.cdteam.spring.cloud.starter.common.utils.ListUtils;
import org.cdteam.spring.cloud.starter.context.bean.Pagination;
import org.cdteam.spring.cloud.starter.context.constant.ResponseCodeEnum;
import org.cdteam.spring.cloud.starter.context.exception.AppException;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

/**
 * <p>
 * 培训课程 服务实现类
 * </p>
 *
 * @author lesl
 * @since 2021-01-19
 */
@Service
public class CourseServiceImpl implements CourseService {

    @Autowired
    private CourseMapper mapper;

    @Override
    public Pagination<CourseDTO> page(Integer pageSize, Integer pageNum, String courseNo, String courseName, String trainStyle
            , String makeCourse, LocalDateTime beginTime, LocalDateTime endTime, String teacherName) {
        Page<CourseEntity> page = new Page<>();
        page.setCurrent(pageNum);
        page.setSize(pageSize);

        LambdaQueryWrapper<CourseEntity> queryWrapper = Wrappers.<CourseEntity>lambdaQuery();

        if (StringUtils.isNoneBlank(courseNo)) {
            queryWrapper.like(CourseEntity::getCourseNo, courseNo);
        }
        if (StringUtils.isNoneBlank(courseName)) {
            queryWrapper.like(CourseEntity::getCourseName, courseName);
        }
        if (StringUtils.isNoneBlank(trainStyle)) {
            queryWrapper.like(CourseEntity::getTrainStyle, trainStyle);
        }
        if (StringUtils.isNoneBlank(makeCourse)) {
            queryWrapper.like(CourseEntity::getMakeCourse, makeCourse);
        }
        if(beginTime != null){
            queryWrapper.ge(CourseEntity::getBeginTime, beginTime);
        }
        if(beginTime != null){
            queryWrapper.le(CourseEntity::getEndTime, endTime);
        }
        if (StringUtils.isNoneBlank(teacherName)) {
            queryWrapper.like(CourseEntity::getTeacherName, teacherName);
        }

        IPage<CourseEntity> courseEntityIPage = mapper.selectPage(page, queryWrapper);
        List<CourseEntity> records = courseEntityIPage.getRecords();
        List<CourseDTO> courseDTOS = ListUtils.transferList(records, CourseDTO.class);
        return new Pagination<>(pageNum, pageSize, courseEntityIPage.getTotal(), courseDTOS);
    }

    @Override
    public Integer save(CourseCreateDTO courseDTO) {
        if (isExistByNo(courseDTO.getCourseNo(), courseDTO.getId())) {
            throw new AppException(ResponseCodeEnum.COMMON_EXCEPTION, "姓名已存在");
        }
        CourseEntity courseEntity = BeanCopyUtils.transferBean(courseDTO, CourseEntity.class);
        if (courseDTO.getId() == null) {
            return mapper.insert(courseEntity);
        }
        return mapper.updateById(courseEntity);
    }

    @Override
    public Integer uploadSave(CourseCreateDTO courseDTO) {
        CourseEntity entity = mapper.selectOne(Wrappers.<CourseEntity>lambdaQuery()
                .eq(CourseEntity::getCourseName, courseDTO.getCourseName()));
        if (entity != null) {
            courseDTO.setId(entity.getId());
        }
        return save(courseDTO);
    }

    @Override
    public List<CourseDTO> all() {
        List<CourseEntity> courseEntities = mapper.selectList(Wrappers.emptyWrapper());
        return ListUtils.transferList(courseEntities, CourseDTO.class);
    }

    @Override
    public CourseDTO getByName(String courseName) {
        CourseEntity courseEntity = mapper.selectOne(Wrappers.<CourseEntity>lambdaQuery().eq(CourseEntity::getCourseName, courseName));
        return BeanCopyUtils.transferBean(courseEntity, CourseDTO.class);
    }

    @Override
    public Integer delete(Long id) {
        return mapper.deleteById(id);
    }

    @Override
    public List<CourseDTO> list() {
        List<CourseEntity> records = mapper.selectList(Wrappers.emptyWrapper());
        return ListUtils.transferList(records, CourseDTO.class);
    }

    @Override
    public CourseDTO getById(Long id) {
        CourseEntity entity = mapper.selectById(id);
        return BeanCopyUtils.transferBean(entity, CourseDTO.class);
    }


    private boolean isExistByNo(String courseNo, Long id) {
        LambdaQueryWrapper<CourseEntity> queryWrapper = Wrappers.<CourseEntity>lambdaQuery().eq(CourseEntity::getCourseNo, courseNo);
        if (Objects.nonNull(id)) {
            queryWrapper.ne(CourseEntity::getId, id);
        }
        return mapper.selectCount(queryWrapper) > 0;
    }
}
