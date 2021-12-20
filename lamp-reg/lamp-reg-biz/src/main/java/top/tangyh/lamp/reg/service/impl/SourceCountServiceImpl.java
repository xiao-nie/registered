package top.tangyh.lamp.reg.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.tangyh.basic.base.service.SuperCacheServiceImpl;
import top.tangyh.basic.cache.model.CacheKeyBuilder;
import top.tangyh.lamp.reg.entity.SourceCount;
import top.tangyh.lamp.reg.mapper.SourceCountMapper;
import top.tangyh.lamp.reg.service.SourceCountService;

import java.io.Serializable;
import java.util.Map;
import java.util.Set;

/**
 * @author nie
 * @date 2021/12/20 20:18
 **/
@Slf4j
@Service

@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SourceCountServiceImpl extends SuperCacheServiceImpl<SourceCountMapper, SourceCount> implements SourceCountService {

    @Override
    public boolean save(SourceCount model) {
        return baseMapper.insert(model) > 0;
    }

    @Override
    public Map<Serializable, Object> findByIds(Set<Serializable> ids) {

        return null;
    }

    @Override
    protected CacheKeyBuilder cacheKeyBuilder() {
        return null;
    }
}
