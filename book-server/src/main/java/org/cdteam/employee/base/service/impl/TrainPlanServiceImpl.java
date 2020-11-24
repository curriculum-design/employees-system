package org.cdteam.employee.base.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.commons.lang3.StringUtils;
import org.cdteam.employee.base.dobject.TrainPlanUnionDO;
import org.cdteam.employee.base.domain.TrainPlanEntity;
import org.cdteam.employee.base.dto.TrainPlanCreateDTO;
import org.cdteam.employee.base.dto.TrainPlanDTO;
import org.cdteam.employee.base.mapper.TrainPlanMapper;
import org.cdteam.employee.base.service.TrainPlanService;
import org.cdteam.spring.cloud.starter.common.utils.BeanCopyUtils;
import org.cdteam.spring.cloud.starter.common.utils.ListUtils;
import org.cdteam.spring.cloud.starter.context.bean.Pagination;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author lesl
 * @since 2020-11-21
 */
@Service
public class TrainPlanServiceImpl implements TrainPlanService {

    @Autowired
    private TrainPlanMapper mapper;

    @Override
    public Pagination<TrainPlanDTO> page(Integer pageSize, Integer pageNum
            , String realName, String org, String dept, String jobName, String courseName, String trainStyle, String makeCourse
            , LocalDateTime beginTime, LocalDateTime endTime) {
        Page<TrainPlanUnionDO> page = new Page<>();
        page.setCurrent(pageNum);
        page.setSize(pageSize);
        IPage<TrainPlanUnionDO> trainPlanEntityIPage = mapper
                .selectUnionPage(page, realName, org, dept, jobName, courseName, trainStyle, makeCourse, beginTime, endTime);
        List<TrainPlanUnionDO> records = trainPlanEntityIPage.getRecords();
        List<TrainPlanDTO> trainPlanDTOS = ListUtils.transferList(records, TrainPlanDTO.class);
        return new Pagination<>(pageNum, pageSize, trainPlanEntityIPage.getTotal(), trainPlanDTOS);
    }

    @Override
    public Integer save(TrainPlanCreateDTO trainPlanDTO) {
        TrainPlanEntity trainPlanEntity = BeanCopyUtils.transferBean(trainPlanDTO, TrainPlanEntity.class);
        if (trainPlanDTO.getId() == null) {
            return mapper.insert(trainPlanEntity);
        }
        return mapper.updateById(trainPlanEntity);
    }

    @Override
    public Integer uploadSave(TrainPlanCreateDTO trainPlanDTO) {
        TrainPlanEntity entity = mapper.selectOne(Wrappers.<TrainPlanEntity>lambdaQuery()
                .eq(TrainPlanEntity::getEmployeeId, trainPlanDTO.getEmployeeId())
                .eq(TrainPlanEntity::getCourseName, trainPlanDTO.getCourseName()));
        if(entity != null){
            trainPlanDTO.setId(entity.getId());
        }
        return save(trainPlanDTO);
    }

    @Override
    public Integer delete(Long id) {
        return mapper.deleteById(id);
    }

    @Override
    public List<TrainPlanDTO> list() {
        List<TrainPlanEntity> records = mapper.selectList(Wrappers.emptyWrapper());
        return ListUtils.transferList(records, TrainPlanDTO.class);
    }

    @Override
    public TrainPlanDTO getById(Long id) {
        TrainPlanEntity entity = mapper.selectById(id);
        return BeanCopyUtils.transferBean(entity, TrainPlanDTO.class);
    }
}
