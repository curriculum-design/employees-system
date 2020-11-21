package org.cdteam.employee.sys.service.impl;

import org.cdteam.employee.sys.dto.AdminDTO;
import org.cdteam.employee.sys.mapper.AdminMapper;
import org.cdteam.employee.sys.service.AdminService;
import org.cdteam.spring.cloud.starter.common.utils.BeanCopyUtils;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * <p>
 * 管理员信息 服务实现类
 * </p>
 *
 * @author lesl
 * @since 2020-11-11
 */
@Service
public class AdminServiceImpl implements AdminService {

	@Autowired
    private AdminMapper mapper;

    @Override
    public AdminDTO getById(Long adminId) {
        return BeanCopyUtils.transferBean(mapper.selectById(adminId), AdminDTO.class);
    }
}
