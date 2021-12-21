package top.tangyh.lamp.reg.config;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import top.tangyh.basic.context.ContextUtil;
import top.tangyh.basic.database.mybatis.conditions.query.QueryWrap;
import top.tangyh.lamp.authority.entity.auth.User;
import top.tangyh.lamp.authority.service.auth.UserService;
import top.tangyh.lamp.reg.constant.Constant;
import top.tangyh.lamp.reg.entity.SourceCount;
import top.tangyh.lamp.reg.service.SourceCountService;
import top.tangyh.lamp.reg.service.impl.SourceCountServiceImpl;
import top.tangyh.lamp.tenant.entity.Tenant;
import top.tangyh.lamp.tenant.service.TenantService;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author nie
 * @date 2021/12/19 18:53
 **/
@Slf4j
@Configuration
@EnableScheduling
public class TestService {

    @Autowired
    private UserService userService;

    @Autowired
    private TenantService tenantService;

    @Autowired
    private SourceCountService sourceCountService;

    @Scheduled(cron = "0/30 * * * * ? ")
    private void configureTasks() {

        log.info("每天凌晨执行静态定时任务时间: {}", LocalDateTime.now());
        //获取所有可用租户
        List<Tenant> tenants = tenantService.find();
        for (Tenant tenant : tenants) {
            //排除0000
            if (!"0000".equals(tenant.getCode())) {
                //设置租户访问数据库
                ContextUtil.setTenant(tenant.getCode());
                //获取改租户下面所有医生
                BaseMapper<User> baseMapper = userService.getBaseMapper();
                List<User> list = baseMapper.selectList(new LambdaQueryWrapper<User>().eq(User::getStationId, Constant.DOCTOR_STATION_ID));
                //清空号源表
                sourceCountService.remove(new QueryWrap<>());
                for (User user : list) {
                    log.info("--------------{}: {}", user.getName(), user.getSourceCount());
                    SourceCount sourceCount = SourceCount.builder()
                            .doctorId(user.getId())
                            .sourceCount(user.getSourceCount())
                            .build();
                    log.info("sourceCount: {}", sourceCount);
                    sourceCountService.save(sourceCount);
                }
            }
        }
    }
}
