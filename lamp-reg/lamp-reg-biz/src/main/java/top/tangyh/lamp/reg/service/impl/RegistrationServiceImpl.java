package top.tangyh.lamp.reg.service.impl;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.tangyh.basic.base.R;
import top.tangyh.basic.base.service.SuperCacheServiceImpl;
import top.tangyh.basic.cache.model.CacheKeyBuilder;
import top.tangyh.lamp.authority.dao.auth.UserMapper;
import top.tangyh.lamp.authority.dao.core.OrgMapper;
import top.tangyh.lamp.reg.dto.RegCredentialsDTO;
import top.tangyh.lamp.reg.entity.Registration;
import top.tangyh.lamp.reg.entity.SourceCount;
import top.tangyh.lamp.reg.mapper.RegistrationMapper;
import top.tangyh.lamp.reg.mapper.SourceCountMapper;
import top.tangyh.lamp.reg.service.RegistrationService;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
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
@Service
public class RegistrationServiceImpl extends SuperCacheServiceImpl<RegistrationMapper, Registration> implements RegistrationService {

    @Autowired
    private SourceCountMapper sourceCountMapper;

    @Autowired
    private OrgMapper orgMapper;

    @Autowired
    private UserMapper userMapper;

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
        return baseMapper.checkReg(userId, doctorId, DateUtil.beginOfDay(new Date()), DateUtil.endOfDay(new Date())) > 0;
    }

    @Override
    public R<RegCredentialsDTO> saveReg(Long doctorId, Long userId) {
        if (checkReg(doctorId, userId)) {
            return R.fail("您今天已经挂过该医生的号了，请勿重复挂号！");
        }
        SourceCount sourceCount = sourceCountMapper.selectOne(new LambdaQueryWrapper<SourceCount>().eq(SourceCount::getDoctorId, doctorId));
        if (sourceCount.getSourceCount() < 1) {
            return R.fail("无余号！");
        }
        List<Registration> list = baseMapper.getToday(doctorId, DateUtil.beginOfDay(new Date()), DateUtil.endOfDay(new Date()));
        Integer max = 0;
        if (list.size() > 0) {
            for (Registration reg : list) {
                max = reg.getNumber() > max ? reg.getNumber() : max;
            }
        }
        Registration registration = Registration.builder()
                .doctorId(doctorId)
                .userId(userId)
                .clinicalTime(new Date())
                .number(max + 1)
                .state(0)
                .caseHistory("")
                .build();
        int insert = baseMapper.insert(registration);
        if (insert > 0) {
            RegCredentialsDTO regCredentialsDTO = entityToRegCredentialsDTO(baseMapper.selectById(registration.getId()));
            return R.success(regCredentialsDTO);
        }
        return R.fail("挂号失败");
    }

    public RegCredentialsDTO entityToRegCredentialsDTO(Registration entity) {
        if (entity == null) {
            return RegCredentialsDTO.builder().build();
        }
        return RegCredentialsDTO.builder()
                .id(entity.getId())
                .createTime(entity.getCreateTime())
                .userName(userMapper.selectById(entity.getUserId()).getName())
                .doctorName(userMapper.selectById(entity.getDoctorId()).getName())
                .number(entity.getNumber())
                .treatmentDepartment(orgMapper.selectById(userMapper.selectById(entity.getDoctorId()).getOrgId()).getLabel())
                .userId(entity.getUserId())
                .build();

    }
}
