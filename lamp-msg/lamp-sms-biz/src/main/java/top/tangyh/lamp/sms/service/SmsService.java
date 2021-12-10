package top.tangyh.lamp.sms.service;

/**
 * <p>
 *
 * </p>
 *
 * @author nie
 * @date: 2021-12-09
 **/
public interface SmsService {

    /**
     * 发送短信
     * @param mobile
     * @param args
     */
    void send(String mobile, String[] args);

}
