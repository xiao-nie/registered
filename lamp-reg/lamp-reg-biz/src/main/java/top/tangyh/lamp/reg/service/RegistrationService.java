package top.tangyh.lamp.reg.service;

import top.tangyh.basic.base.R;
import top.tangyh.basic.base.service.SuperCacheService;
import top.tangyh.basic.model.LoadService;
import top.tangyh.lamp.reg.dto.RegCredentialsDTO;
import top.tangyh.lamp.reg.entity.Registration;

/**
 * <p>
 *
 * </p>
 *
 * @author nie
 * @date: 2021-12-28
 **/
public interface RegistrationService extends SuperCacheService<Registration>, LoadService {

    /**
     * 检查是否已经挂号
     *
     * @param doctorId
     * @param userId
     * @return
     */
    Boolean checkReg(Long doctorId, Long userId);

    /**
     * 挂号
     *
     * @param doctorId
     * @param userId
     * @return
     */
    R<RegCredentialsDTO> saveReg(Long doctorId, Long userId);

}
