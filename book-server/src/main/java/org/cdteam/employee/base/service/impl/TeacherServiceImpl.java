package org.cdteam.employee.base.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.commons.lang3.StringUtils;
import org.cdteam.employee.base.domain.EmployeeEntity;
import org.cdteam.employee.base.domain.TeacherEntity;
import org.cdteam.employee.base.dto.EmployeeDTO;
import org.cdteam.employee.base.dto.TeacherCreateDTO;
import org.cdteam.employee.base.dto.TeacherDTO;
import org.cdteam.employee.base.dto.TeacherUploadDTO;
import org.cdteam.spring.cloud.starter.common.utils.BeanCopyUtils;
import org.cdteam.spring.cloud.starter.common.utils.ListUtils;
import org.cdteam.spring.cloud.starter.context.bean.Pagination;
import org.cdteam.spring.cloud.starter.context.constant.ResponseCodeEnum;
import org.cdteam.spring.cloud.starter.context.exception.AppException;
import org.cdteam.employee.base.mapper.TeacherMapper;
import org.cdteam.employee.base.service.TeacherService;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Objects;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author lesl
 * @since 2020-11-11
 */
@Service
public class TeacherServiceImpl implements TeacherService {

    @Autowired
    private TeacherMapper mapper;

    @Override
    public Pagination<TeacherDTO> page(Integer pageSize, Integer pageNum, String teacherName, String workType, String org) {
        Page<TeacherEntity> page = new Page<>();
        page.setCurrent(pageNum);
        page.setSize(pageSize);
        LambdaQueryWrapper<TeacherEntity> queryWrapper = Wrappers.lambdaQuery();
        if (StringUtils.isNotBlank(teacherName)) {
            queryWrapper.like(TeacherEntity::getTeacherName, teacherName);
        }
        if (StringUtils.isNotBlank(workType)) {
            queryWrapper.eq(TeacherEntity::getWorkType, workType);
        }
        IPage<TeacherEntity> employeeEntityIPage = mapper.selectPage(page, queryWrapper);
        List<TeacherEntity> records = employeeEntityIPage.getRecords();
        List<TeacherDTO> employeeDTOS = ListUtils.transferList(records, TeacherDTO.class);
        return new Pagination<>(pageNum, pageSize, employeeEntityIPage.getTotal(), employeeDTOS);
    }

    @Override
    public Integer save(TeacherCreateDTO employeeDTO) {
        if (isExistByName(employeeDTO.getTeacherName(), employeeDTO.getId())) {
            throw new AppException(ResponseCodeEnum.COMMON_EXCEPTION, "姓名已存在");
        }
        TeacherEntity employeeEntity = BeanCopyUtils.transferBean(employeeDTO, TeacherEntity.class);
        if (employeeDTO.getId() == null) {
            return mapper.insert(employeeEntity);
        }
        return mapper.updateById(employeeEntity);
    }

    @Override
    public Integer uploadSave(TeacherCreateDTO employeeDTO) {
        TeacherEntity entity = mapper.selectOne(Wrappers.<TeacherEntity>lambdaQuery()
                .eq(TeacherEntity::getTeacherName, employeeDTO.getTeacherName()));
        if(entity != null){
            employeeDTO.setId(entity.getId());
        }
        return save(employeeDTO);
    }

    @Override
    public List<TeacherDTO> all() {
        List<TeacherEntity> employeeEntities = mapper.selectList(Wrappers.emptyWrapper());
        return ListUtils.transferList(employeeEntities, TeacherDTO.class);
    }

    @Override
    public TeacherDTO getByName(String teacherName) {
        TeacherEntity teacherEntity = mapper.selectOne(Wrappers.<TeacherEntity>lambdaQuery().eq(TeacherEntity::getTeacherName, teacherName));
        return BeanCopyUtils.transferBean(teacherEntity, TeacherDTO.class);
    }

    @Override
    public Integer delete(Long id) {
        return mapper.deleteById(id);
    }

    @Override
    public List<TeacherDTO> list() {
        List<TeacherEntity> records = mapper.selectList(Wrappers.emptyWrapper());
        return ListUtils.transferList(records, TeacherDTO.class);
    }

    @Override
    public TeacherDTO getById(Long id) {
        TeacherEntity entity = mapper.selectById(id);
        return BeanCopyUtils.transferBean(entity, TeacherDTO.class);
    }


    private boolean isExistByName(String name, Long id) {
        LambdaQueryWrapper<TeacherEntity> queryWrapper = Wrappers.<TeacherEntity>lambdaQuery().eq(TeacherEntity::getTeacherName, name);
        if (Objects.nonNull(id)) {
            queryWrapper.ne(TeacherEntity::getId, id);
        }
        return mapper.selectCount(queryWrapper) > 0;
    }
}
