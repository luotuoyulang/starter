package cn.luotuoyulang.myspringbootstarter.controller;

import cn.luotuoyulang.myspringbootstarter.annotation.Security;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description TODO
 * @Classname TestController
 * @Date 2021/2/9 17:00
 * @Author by liuyuhu
 * @Contact 17600520726@163.com 微信 aa249890950-5
 */
@Slf4j
@RestController
public class TestController {

    @Security
    @PostMapping("/a")
    public TestParam test (@RequestBody TestParam param) {
        log.info("入参为 name [{}] " , param);
        return param;
    }
}