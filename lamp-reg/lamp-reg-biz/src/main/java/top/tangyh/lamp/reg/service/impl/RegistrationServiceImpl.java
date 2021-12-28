package top.tangyh.lamp.reg.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import top.tangyh.basic.base.service.SuperCacheServiceImpl;
import top.tangyh.basic.cache.model.CacheKeyBuilder;
import top.tangyh.lamp.reg.entity.Registration;
import top.tangyh.lamp.reg.mapper.RegistrationMapper;
import top.tangyh.lamp.reg.service.RegistrationService;

import java.io.Serializable;
import java.util.Map;
import java.util.Set;

/**
 * <p>
 *
 * </p>
 *
 * @author nie
 * @date: 2021-12-28
 **/
public class RegistrationServiceImpl extends SuperCacheServiceImpl<RegistrationMapper, Registration> implements RegistrationService {

    @Override
    public Map<Serializable, Object> findByIds(Set<Serializable> ids) {
        return null;
    }

    @Override
    protected CacheKeyBuilder cacheKeyBuilder() {
        return null;
    }

    @Override
    public Boolean checkReg(Long doctorId, Long userId) {
        return null;
    }

    @Override
    public Registration saveReg(Long doctorId, Long userId) {
        return null;
    }
}
