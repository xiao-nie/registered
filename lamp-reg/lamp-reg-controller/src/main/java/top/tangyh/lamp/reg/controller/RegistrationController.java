package top.tangyh.lamp.reg.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.tangyh.basic.base.R;
import top.tangyh.basic.base.controller.SuperCacheController;
import top.tangyh.basic.context.ContextUtil;
import top.tangyh.lamp.authority.dto.core.OrgPageQuery;
import top.tangyh.lamp.authority.dto.core.OrgSaveDTO;
import top.tangyh.lamp.authority.dto.core.OrgUpdateDTO;
import top.tangyh.lamp.reg.entity.Registration;
import top.tangyh.lamp.reg.service.RegistrationService;

import javax.annotation.Resource;

/**
 * @author nie
 * @date 2021/12/29 18:52
 **/
@Slf4j
@Validated
@RestController
@RequestMapping("/reg")
public class RegistrationController extends SuperCacheController<RegistrationService, Long, Registration, OrgPageQuery, OrgSaveDTO, OrgUpdateDTO> {

    @Autowired
    private RegistrationService registrationService;

    @PostMapping("/registered/{doctor_id}")
    public R registered(@PathVariable("doctor_id") String doctorId) {
        log.info("doctor: {}", doctorId);
        log.info("userId: {}",ContextUtil.getUserId());
        return success();
    }

}
