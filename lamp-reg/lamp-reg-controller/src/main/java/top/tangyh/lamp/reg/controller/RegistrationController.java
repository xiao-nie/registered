package top.tangyh.lamp.reg.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import top.tangyh.basic.base.R;
import top.tangyh.basic.base.controller.SuperCacheController;
import top.tangyh.basic.context.ContextUtil;
import top.tangyh.lamp.authority.dto.core.OrgPageQuery;
import top.tangyh.lamp.authority.dto.core.OrgSaveDTO;
import top.tangyh.lamp.authority.dto.core.OrgUpdateDTO;
import top.tangyh.lamp.reg.dto.RegCredentialsDTO;
import top.tangyh.lamp.reg.entity.Registration;
import top.tangyh.lamp.reg.service.RegistrationService;

import javax.annotation.Resource;
import java.util.List;

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

    /**
     * 挂号
     *
     * @param doctorId
     * @return
     */
    @PostMapping("/registered/{doctor_id}")
    public R registered(@PathVariable("doctor_id") Long doctorId) {
        log.info("doctor: {}", doctorId);
        log.info("userId: {}", ContextUtil.getUserId());
        return registrationService.saveReg(doctorId, ContextUtil.getUserId());
    }

    /**
     * 挂号记录
     *
     * @return
     */
    @GetMapping("/registered/page")
    public R<List<RegCredentialsDTO>> getRegList() {
        return registrationService.getRegList();
    }

    /**
     * 取消挂号
     *
     * @param id
     * @return
     */
    @PostMapping("/registered/del/{id}")
    public R delReg(@PathVariable("id") Long id) {
        return registrationService.delReg(id);
    }

    /**
     * 医生获取当前第一位需要就诊的患者
     *
     * @return
     */
    @GetMapping("/registered/doctor/getMin")
    public R getMin() {
        return registrationService.getMin();
    }

    /**
     * 就诊状态更新
     *
     * @param id
     * @return
     */
    @PostMapping("/registered/doctor/addState/{id}")
    public R addState(@PathVariable("id") Long id) {
        return registrationService.addState(id);
    }


}
