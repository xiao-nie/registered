package top.tangyh.lamp.reg.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.tangyh.basic.base.R;

/**
 * @author nie
 * @date 2021/12/19 19:01
 **/
@Slf4j
@Validated
@RestController
@RequestMapping("/tenant")
public class TestController {


    @GetMapping("/test")
    public R test() {
        System.out.println("true = " + true);
        return R.success("test");
    }

}
