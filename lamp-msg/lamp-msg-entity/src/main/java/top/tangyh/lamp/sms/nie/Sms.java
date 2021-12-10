package top.tangyh.lamp.sms.nie;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * <p>
 *
 * </p>
 *
 * @author nie
 * @date: 2021-12-09
 **/
@Component
@Data
@Accessors(chain = true)
public class Sms {

    /**
     * 签名
     */
    @Value("${tencentcloud.sms.sign}")
    private String sign ;
    /**
     * 模板
     */
    @Value("${tencentcloud.sms.templateId}")
    private String templateId;
    /**
     * 手机号
     */
    private String[] mobile;
    /**
     * 模板参数 {}
     */
    private String[] params;

}

