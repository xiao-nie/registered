package top.tangyh.lamp.reg.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import top.tangyh.basic.base.mapper.SuperMapper;
import top.tangyh.lamp.reg.entity.Registration;

import java.util.Date;
import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author nie
 * @date: 2021-12-28
 **/
@Repository
public interface RegistrationMapper extends SuperMapper<Registration> {

    /**
     * 检查是否已经挂号
     *
     * @param userId
     * @param doctorId
     * @param begin
     * @param after
     * @return
     */
    Integer checkReg(@Param("userId") Long userId, @Param("doctorId") Long doctorId, @Param("begin") Date begin, @Param("after") Date after);

    /**
     * 查询该医生今天的号
     *
     * @param doctorId
     * @param begin
     * @param after
     * @return
     */
    List<Registration> getToday(@Param("doctorId") Long doctorId, @Param("begin") Date begin, @Param("after") Date after);

}
