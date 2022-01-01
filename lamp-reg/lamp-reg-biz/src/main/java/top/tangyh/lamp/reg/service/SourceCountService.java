package top.tangyh.lamp.reg.service;

import top.tangyh.basic.base.service.SuperCacheService;
import top.tangyh.basic.model.LoadService;
import top.tangyh.lamp.reg.dto.RegOrgDTO;
import top.tangyh.lamp.reg.entity.SourceCount;

import java.util.List;

/**
 * @author nie
 * @date 2021/12/20 20:17
 **/
public interface SourceCountService extends SuperCacheService<SourceCount>, LoadService {

    /**
     * 挂号列表
     *
     * @return
     */
    List<RegOrgDTO> getOrgList();

    /**
     * 号源+1
     *
     * @param doctorId
     * @return
     */
    Integer add(Long doctorId);

    /**
     * 号源-1
     *
     * @param doctorId
     * @return
     */
    Integer decrease(Long doctorId);

}
