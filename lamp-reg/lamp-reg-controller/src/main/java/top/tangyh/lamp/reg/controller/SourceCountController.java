package top.tangyh.lamp.reg.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.tangyh.basic.base.R;
import top.tangyh.basic.base.controller.SuperCacheController;
import top.tangyh.lamp.authority.dto.core.OrgPageQuery;
import top.tangyh.lamp.authority.dto.core.OrgSaveDTO;
import top.tangyh.lamp.authority.dto.core.OrgUpdateDTO;
import top.tangyh.lamp.authority.entity.core.Org;
import top.tangyh.lamp.authority.service.core.OrgService;
import top.tangyh.lamp.reg.dto.RegDoctorDTO;
import top.tangyh.lamp.reg.dto.RegOrgDTO;
import top.tangyh.lamp.reg.entity.SourceCount;
import top.tangyh.lamp.reg.service.SourceCountService;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author nie
 * @date 2021/12/19 19:01
 **/
@Slf4j
@Validated
@RestController
@RequestMapping("/SourceCount")
public class SourceCountController extends SuperCacheController<SourceCountService, Long, SourceCount, OrgPageQuery, OrgSaveDTO, OrgUpdateDTO> {

    @Resource
    private SourceCountService sourceCountService;

    @GetMapping("/getOrgList")
    public R getOrgList() {
        List<RegOrgDTO> orgList = sourceCountService.getOrgList();
        return R.success(orgList);
    }

    @GetMapping("/getDoctor/{doctor_id}")
    public R<RegDoctorDTO> getDoctor(@PathVariable("doctor_id") Long doctorId) {
        return sourceCountService.getDoctor(doctorId);
    }

}
