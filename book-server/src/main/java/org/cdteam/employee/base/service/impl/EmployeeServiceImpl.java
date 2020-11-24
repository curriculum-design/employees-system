package org.cdteam.employee.base.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.commons.lang3.StringUtils;
import org.cdteam.employee.base.domain.EmployeeEntity;
import org.cdteam.employee.base.dto.EmployeeCreateDTO;
import org.cdteam.employee.base.dto.EmployeeDTO;
import org.cdteam.employee.base.dto.EmployeeUploadDTO;
import org.cdteam.spring.cloud.starter.common.utils.BeanCopyUtils;
import org.cdteam.spring.cloud.starter.common.utils.ListUtils;
import org.cdteam.spring.cloud.starter.context.bean.Pagination;
import org.cdteam.spring.cloud.starter.context.constant.ResponseCodeEnum;
import org.cdteam.spring.cloud.starter.context.exception.AppException;
import org.cdteam.employee.base.mapper.EmployeeMapper;
import org.cdteam.employee.base.service.EmployeeService;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
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
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeMapper mapper;

    @Override
    public Pagination<EmployeeDTO> page(Integer pageSize, Integer pageNum, String realName, String onJob, String workType, String org, String dept, String jobName, LocalDateTime beginTime, LocalDateTime endTime) {
        Page<EmployeeEntity> page = new Page<>();
        page.setCurrent(pageNum);
        page.setSize(pageSize);
        LambdaQueryWrapper<EmployeeEntity> queryWrapper = Wrappers.lambdaQuery();
        if (StringUtils.isNotBlank(realName)) {
            queryWrapper.like(EmployeeEntity::getRealName, realName);
        }
        if (StringUtils.isNotBlank(onJob)) {
            queryWrapper.like(EmployeeEntity::getOnJob, onJob);
        }
        if (StringUtils.isNotBlank(workType)) {
            queryWrapper.like(EmployeeEntity::getWorkType, workType);
        }
        if (StringUtils.isNotBlank(org)) {
            queryWrapper.like(EmployeeEntity::getOrg, org);
        }
        if (StringUtils.isNotBlank(dept)) {
            queryWrapper.like(EmployeeEntity::getDept, dept);
        }
        if (StringUtils.isNotBlank(jobName)) {
            queryWrapper.like(EmployeeEntity::getJobName, jobName);
        }
        if (beginTime != null) {
            queryWrapper.ge(EmployeeEntity::getJoinTime, beginTime);
        }
        if (endTime != null) {
            queryWrapper.le(EmployeeEntity::getJoinTime, endTime);
        }
        IPage<EmployeeEntity> employeeEntityIPage = mapper.selectPage(page, queryWrapper);
        List<EmployeeEntity> records = employeeEntityIPage.getRecords();
        List<EmployeeDTO> employeeDTOS = ListUtils.transferList(records, EmployeeDTO.class);
        return new Pagination<>(pageNum, pageSize, employeeEntityIPage.getTotal(), employeeDTOS);
    }

    @Override
    public Integer save(EmployeeCreateDTO employeeDTO) {
        if (isExistByName(employeeDTO.getRealName(), employeeDTO.getId())) {
            throw new AppException(ResponseCodeEnum.COMMON_EXCEPTION, "姓名已存在");
        }
        if (isExistByCode(employeeDTO.getEmployeeCode(), employeeDTO.getId())) {
            throw new AppException(ResponseCodeEnum.COMMON_EXCEPTION, "编号已存在");
        }
        EmployeeEntity employeeEntity = BeanCopyUtils.transferBean(employeeDTO, EmployeeEntity.class);
        if (employeeDTO.getId() == null) {
            return mapper.insert(employeeEntity);
        }
        return mapper.updateById(employeeEntity);
    }

    @Override
    public Integer uploadSave(EmployeeCreateDTO employeeDTO) {
        EmployeeEntity entity = mapper.selectOne(Wrappers.<EmployeeEntity>lambdaQuery()
                .eq(EmployeeEntity::getEmployeeCode, employeeDTO.getEmployeeCode()));
        if (entity != null) {
            employeeDTO.setId(entity.getId());
        }
        return save(employeeDTO);
    }

    @Override
    public List<EmployeeDTO> all() {
        List<EmployeeEntity> employeeEntities = mapper.selectList(Wrappers.emptyWrapper());
        return ListUtils.transferList(employeeEntities, EmployeeDTO.class);
    }

    @Override
    public EmployeeDTO getByCode(String employeeCode) {
        EmployeeEntity employeeEntity = mapper.selectOne(Wrappers.<EmployeeEntity>lambdaQuery().eq(EmployeeEntity::getEmployeeCode, employeeCode));
        return BeanCopyUtils.transferBean(employeeEntity, EmployeeDTO.class);
    }

    @Override
    public Integer delete(Long id) {
        return mapper.deleteById(id);
    }

    @Override
    public List<EmployeeDTO> list() {
        List<EmployeeEntity> records = mapper.selectList(Wrappers.emptyWrapper());
        return ListUtils.transferList(records, EmployeeDTO.class);
    }

    @Override
    public EmployeeDTO getById(Long id) {
        EmployeeEntity entity = mapper.selectById(id);
        return BeanCopyUtils.transferBean(entity, EmployeeDTO.class);
    }


    private boolean isExistByName(String name, Long id) {
        LambdaQueryWrapper<EmployeeEntity> queryWrapper = Wrappers.<EmployeeEntity>lambdaQuery().eq(EmployeeEntity::getRealName, name);
        if (Objects.nonNull(id)) {
            queryWrapper.ne(EmployeeEntity::getId, id);
        }
        return mapper.selectCount(queryWrapper) > 0;
    }

    private boolean isExistByCode(String code, Long id) {
        LambdaQueryWrapper<EmployeeEntity> queryWrapper = Wrappers.<EmployeeEntity>lambdaQuery().eq(EmployeeEntity::getRealName, code);
        if (Objects.nonNull(id)) {
            queryWrapper.ne(EmployeeEntity::getId, id);
        }
        return mapper.selectCount(queryWrapper) > 0;
    }
}
