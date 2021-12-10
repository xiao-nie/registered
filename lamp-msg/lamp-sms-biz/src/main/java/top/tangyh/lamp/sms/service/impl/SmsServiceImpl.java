package top.tangyh.lamp.sms.service.impl;

import org.springframework.stereotype.Service;
import top.tangyh.lamp.sms.nie.SendSms;
import top.tangyh.lamp.sms.nie.Sms;
import top.tangyh.lamp.sms.service.SmsService;

import javax.annotation.Resource;

/**
 * <p>
 *
 * </p>
 *
 * @author nie
 * @date: 2021-12-09
 **/
@Service
public class SmsServiceImpl implements SmsService {

    @Resource
    private SendSms sendSms;

    @Resource
    private Sms sms;

    @Override
    public void send(String mobile, String[] args) {

        String[] str = {mobile};
        sms.setMobile(str);
        sms.setParams(args);
        System.out.println("sms = " + sms);
        sendSms.sendPhoneTextMsg(sms);
    }
}
