package top.tangyh.lamp.reg.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import top.tangyh.basic.context.ContextUtil;
import top.tangyh.lamp.tenant.entity.Tenant;
import top.tangyh.lamp.tenant.service.TenantService;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author nie
 * @date 2021/12/19 18:53
 **/
@Configuration      //1.主要用于标记配置类，兼备Component的效果。
@EnableScheduling   // 2.开启定时任务
public class TestService {

    @Autowired
    private TenantService tenantService;

    //3.添加定时任务
    @Scheduled(cron = "0/10 * * * * ?")
    //或直接指定时间间隔，例如：5秒
    //@Scheduled(fixedRate=5000)
    private void configureTasks() {
        List<Tenant> tenants = tenantService.find();
        for (Tenant tenant : tenants) {
            if (!"0000".equals(tenant.getCode())) {
                System.out.println("tenant = " + tenant);
                ContextUtil.setTenant(tenant.getCode());

            }
        }
        System.err.println("执行静态定时任务时间: " + LocalDateTime.now());
    }
}
