package top.tangyh.lamp.reg.service;

import top.tangyh.basic.base.R;
import top.tangyh.basic.base.service.SuperCacheService;
import top.tangyh.basic.model.LoadService;
import top.tangyh.lamp.reg.dto.RegCredentialsDTO;
import top.tangyh.lamp.reg.entity.Registration;

import java.util.List;

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

    /**
     * 获取当前用户挂号列表
     *
     * @return
     */
    R<List<RegCredentialsDTO>> getRegList();

    /**
     * 取消挂号订单
     *
     * @param id
     * @return
     */
    R delReg(Long id);

    /**
     * 医生获取当前需要就真的病患者信息
     *
     * @return
     */
    R getMin();

}
