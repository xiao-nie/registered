package top.tangyh.lamp.reg.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import top.tangyh.basic.base.mapper.SuperMapper;
import top.tangyh.lamp.reg.entity.Registration;

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
}
