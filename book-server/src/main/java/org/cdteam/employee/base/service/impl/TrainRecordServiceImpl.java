package org.cdteam.employee.base.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.commons.lang3.StringUtils;
import org.cdteam.employee.base.dobject.TrainRecordUnionDO;
import org.cdteam.employee.base.domain.TrainRecordEntity;
import org.cdteam.employee.base.dto.TrainRecordCreateDTO;
import org.cdteam.employee.base.dto.TrainRecordDTO;
import org.cdteam.employee.base.mapper.TrainRecordMapper;
import org.cdteam.employee.base.service.TrainRecordService;
import org.cdteam.spring.cloud.starter.common.utils.BeanCopyUtils;
import org.cdteam.spring.cloud.starter.common.utils.ListUtils;
import org.cdteam.spring.cloud.starter.context.bean.Pagination;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author lesl
 * @since 2020-11-21
 */
@Service
public class TrainRecordServiceImpl implements TrainRecordService {

    @Autowired
    private TrainRecordMapper mapper;

    @Override
    public Pagination<TrainRecordDTO> page(Integer pageSize, Integer pageNum
            , String realName, String org, String dept, String jobName, String courseNo, String courseName, String makeCourse
            , LocalDateTime beginTime, LocalDateTime endTime, String teacherName, Boolean byTeacher) {
        Page<TrainRecordUnionDO> page = new Page<>();
        page.setCurrent(pageNum);
        page.setSize(pageSize);
        IPage<TrainRecordUnionDO> trainRecordEntityIPage = Optional.ofNullable(byTeacher)
                .map(d -> mapper.selectUnionPageGroupTeacher(page, teacherName))
                .orElseGet(() -> mapper.selectUnionPage(page, realName, org, dept, jobName, courseNo, courseName, makeCourse, beginTime, endTime, teacherName));
        List<TrainRecordUnionDO> records = trainRecordEntityIPage.getRecords();
        List<TrainRecordDTO> trainRecordDTOS = ListUtils.transferList(records, TrainRecordDTO.class);
        return new Pagination<>(pageNum, pageSize, trainRecordEntityIPage.getTotal(), trainRecordDTOS);
    }

    @Override
    public Integer save(TrainRecordCreateDTO trainRecordDTO) {
        if (StringUtils.isEmpty(trainRecordDTO.getScore())) {
            trainRecordDTO.setScore("合格");
        }
        TrainRecordEntity trainRecordEntity = BeanCopyUtils.transferBean(trainRecordDTO, TrainRecordEntity.class);
        if (trainRecordDTO.getId() == null) {
            return mapper.insert(trainRecordEntity);
        }
        return mapper.updateById(trainRecordEntity);
    }

    @Override
    public Integer uploadSave(TrainRecordCreateDTO trainRecordDTO) {
        TrainRecordEntity entity = mapper.selectOne(Wrappers.<TrainRecordEntity>lambdaQuery()
                .eq(TrainRecordEntity::getEmployeeId, trainRecordDTO.getEmployeeId())
                .eq(TrainRecordEntity::getCourseNo, trainRecordDTO.getCourseNo()));
        if (entity != null) {
            trainRecordDTO.setId(entity.getId());
        }
        return save(trainRecordDTO);
    }

    @Override
    public Boolean existCourseNo(String courseNo) {
        LambdaQueryWrapper<TrainRecordEntity> queryWrapper = Wrappers.<TrainRecordEntity>lambdaQuery().eq(TrainRecordEntity::getCourseNo, courseNo);
        return mapper.selectCount(queryWrapper) > 0;
    }

    @Override
    public void deleteByCourseNo(String courseNo) {
        mapper.delete(Wrappers.<TrainRecordEntity>lambdaQuery().eq(TrainRecordEntity::getCourseNo, courseNo));
    }

    @Override
    public Integer delete(Long id) {
        return mapper.deleteById(id);
    }

    @Override
    public List<TrainRecordDTO> list() {
        List<TrainRecordEntity> records = mapper.selectList(Wrappers.emptyWrapper());
        return ListUtils.transferList(records, TrainRecordDTO.class);
    }

    @Override
    public TrainRecordDTO getById(Long id) {
        TrainRecordEntity entity = mapper.selectById(id);
        return BeanCopyUtils.transferBean(entity, TrainRecordDTO.class);
    }
}
