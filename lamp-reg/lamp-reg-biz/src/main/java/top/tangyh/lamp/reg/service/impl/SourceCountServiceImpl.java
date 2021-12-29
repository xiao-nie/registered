package top.tangyh.lamp.reg.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.tangyh.basic.base.service.SuperCacheServiceImpl;
import top.tangyh.basic.cache.model.CacheKeyBuilder;
import top.tangyh.lamp.authority.entity.auth.User;
import top.tangyh.lamp.authority.entity.core.Org;
import top.tangyh.lamp.authority.service.auth.UserService;
import top.tangyh.lamp.authority.service.core.OrgService;
import top.tangyh.lamp.reg.dto.RegDoctorDTO;
import top.tangyh.lamp.reg.dto.RegOrgDTO;
import top.tangyh.lamp.reg.entity.SourceCount;
import top.tangyh.lamp.reg.mapper.SourceCountMapper;
import top.tangyh.lamp.reg.service.SourceCountService;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.*;

/**
 * @author nie
 * @date 2021/12/20 20:18
 **/
@Slf4j
@Service

@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SourceCountServiceImpl extends SuperCacheServiceImpl<SourceCountMapper, SourceCount> implements SourceCountService {

    @Resource
    private UserService userService;

    @Resource
    private OrgService orgService;

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

    @Override
    public List<RegOrgDTO> getOrgList() {
        List<RegOrgDTO> list = new ArrayList<>();
        orgService.findForeign().forEach(org -> {
            List<RegDoctorDTO> dtoList = new ArrayList<>();
            ArrayList<Long> ids = new ArrayList<>();
            ids.add(org.getId());
            orgService.findChildren(ids).forEach(orgSon -> {
                userService.getUserByOrg(orgSon.getId()).forEach(user -> {
                    dtoList.add(this.userConvertDoctor(user, orgSon));
                });
            });
            list.add(this.orgConvertDto(org, dtoList));
        });
        return list;
    }

    private RegOrgDTO orgConvertDto(Org org, List<RegDoctorDTO> list) {
        RegOrgDTO.RegOrgDTOBuilder builder = RegOrgDTO.builder();
        if (org == null) {
            return builder.build();
        }
        return builder
                .orgId(org.getId())
                .doctors(list)
                .label(org.getLabel())
                .build();
    }

    private RegDoctorDTO userConvertDoctor(User user, Org org) {
        RegDoctorDTO.RegDoctorDTOBuilder builder = RegDoctorDTO.builder();
        if (user == null || org == null) {
            return builder.build();
        }
        String theTitle = "";
        switch (user.getEducation()) {
            case "01":
                theTitle = "住院医师";
                break;
            case "02":
                theTitle = "主治医师";
                break;
            case "03":
                theTitle = "副主任医师";
                break;
            case "04":
                theTitle = "主任医师";
                break;
            case "05":
                theTitle = "其他";
                break;
        }

        return builder
                .doctor_id(user.getId())
                .name(user.getName() + "(" + theTitle + ")")
                .theTitle(theTitle)
                .context(user.getWorkDescribe())
                .sonOrgName(org.getLabel())
                .avatar(user.getAvatar())
                .sex(user.getSex().getDesc())
                .sourceCount(baseMapper.selectOne(new LambdaQueryWrapper<SourceCount>().eq(SourceCount::getDoctorId, user.getId())).getSourceCount())
                .build();
    }

}
