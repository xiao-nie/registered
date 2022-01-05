package top.tangyh.lamp.reg.service.impl;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.LocalDateTimeUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.tangyh.basic.base.R;
import top.tangyh.basic.base.service.SuperCacheServiceImpl;
import top.tangyh.basic.cache.model.CacheKeyBuilder;
import top.tangyh.basic.context.ContextUtil;
import top.tangyh.lamp.authority.dao.auth.UserMapper;
import top.tangyh.lamp.authority.dao.core.OrgMapper;
import top.tangyh.lamp.authority.entity.auth.User;
import top.tangyh.lamp.reg.dto.HistoryDTO;
import top.tangyh.lamp.reg.dto.MinDTO;
import top.tangyh.lamp.reg.dto.RegCredentialsDTO;
import top.tangyh.lamp.reg.dto.RegUserInfo;
import top.tangyh.lamp.reg.entity.Registration;
import top.tangyh.lamp.reg.entity.SourceCount;
import top.tangyh.lamp.reg.mapper.RegistrationMapper;
import top.tangyh.lamp.reg.mapper.SourceCountMapper;
import top.tangyh.lamp.reg.service.RegistrationService;
import top.tangyh.lamp.reg.service.SourceCountService;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 *
 * </p>
 *
 * @author nie
 * @date: 2021-12-28
 **/
@Transactional
@Service
public class RegistrationServiceImpl extends SuperCacheServiceImpl<RegistrationMapper, Registration> implements RegistrationService {

    @Autowired
    private SourceCountService sourceCountService;

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
            sourceCountService.decrease(doctorId);
            return R.success(regCredentialsDTO);
        }
        return R.fail("挂号失败");
    }

    @Override
    public R<List<RegCredentialsDTO>> getRegList() {
        List<Registration> list = baseMapper.selectList(new LambdaQueryWrapper<Registration>().eq(Registration::getUserId, ContextUtil.getUserId()));
        List<RegCredentialsDTO> credentialsDTOList = new ArrayList<>();
        list.forEach(registration -> credentialsDTOList.add(entityToRegCredentialsDTO(registration)));
        return R.success(credentialsDTOList);
    }

    @Override
    public R delReg(Long id) {
        Registration registration = baseMapper.selectById(id);
        if (registration == null) {
            return R.fail("找不到该挂号订单");
        }
        if (registration.getState() == 1) {
            return R.fail("就诊中无法取消");
        }
        if (registration.getState() == 2) {
            return R.fail("已就诊无法取消");
        }
        Long doctorId = baseMapper.selectById(id).getDoctorId();
        return baseMapper.deleteById(id) > 0 && sourceCountService.add(doctorId) > 0 ? R.success("取消成功") : R.fail("取消失败");
    }

    @Override
    public R getMin() {
        List<Registration> today = baseMapper.getToday(ContextUtil.getUserId(), DateUtil.beginOfDay(new Date()), DateUtil.endOfDay(new Date()));
        if (today.size() < 1) {
            return R.fail("您今天暂无患者就诊！");
        }
        //过滤出待就诊和就诊中的挂号记录
        List<Registration> state = today.stream().filter(registration -> registration.getState() < 2).collect(Collectors.toList());
        //获取需要叫号的患者信息，根据排号顺序
        Registration min = state.stream().min(Comparator.comparing(Registration::getNumber)).get();
        User user = userMapper.selectById(min.getUserId());
        RegUserInfo regUserInfo = userTORegUserDTO(user, min);

        List<Registration> registrations = baseMapper.selectList(new LambdaQueryWrapper<Registration>()
                .eq(Registration::getUserId, min.getUserId())
                .eq(Registration::getDoctorId, min.getDoctorId())
                .ne(Registration::getId, min.getId()));
        ArrayList<HistoryDTO> historyDTOS = new ArrayList<>();
        registrations.forEach(r -> historyDTOS.add(HistoryDTO.builder().id(r.getId()).date(LocalDateTimeUtil.format(r.getCreateTime(), DatePattern.CHINESE_DATE_PATTERN)).info(r.getCaseHistory()).build()));
        return R.success(MinDTO.builder().registration(min).regUserInfo(regUserInfo).hisList(historyDTOS).build());
    }

    public RegCredentialsDTO entityToRegCredentialsDTO(Registration entity) {
        if (entity == null) {
            return RegCredentialsDTO.builder().build();
        }
        String state = "";
        switch (entity.getState()) {
            case 0:
                state = "待就诊";
                break;
            case 1:
                state = "就诊中";
                break;
            case 2:
                state = "已就诊";
                break;
        }
        return RegCredentialsDTO.builder()
                .id(entity.getId())
                .createTime(entity.getCreateTime())
                .userName(userMapper.selectById(entity.getUserId()).getName())
                .doctorName(userMapper.selectById(entity.getDoctorId()).getName())
                .number(entity.getNumber())
                .treatmentDepartment(orgMapper.selectById(userMapper.selectById(entity.getDoctorId()).getOrgId()).getLabel())
                .userId(entity.getUserId())
                .state(state)
                .caseHistory(entity.getCaseHistory())
                .build();

    }

    private RegUserInfo userTORegUserDTO(User user, Registration registration) {
        if (user == null) {
            return null;
        }
        String state = "";
        switch (registration.getState()) {
            case 0:
                state = "待就诊";
                break;
            case 1:
                state = "就诊中";
                break;
            case 2:
                state = "已就诊";
                break;
        }
        return RegUserInfo.builder()
                .id(user.getId())
                .idCard(user.getAccount())
                .name(user.getName())
                .sex(user.getSex().getDesc())
                .age(DateUtil.year(new Date()) - Integer.parseInt(user.getAccount().substring(6, 10)))
                .state(state)
                .build();

    }
}
