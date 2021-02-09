package cn.luotuoyulang.myspringbootstarter.controller;

import cn.luotuoyulang.myspringbootstarter.annotation.Security;
import lombok.Data;
import lombok.ToString;

/**
 * @Description TODO
 * @Classname TestParam
 * @Date 2021/2/9 17:06
 * @Author by liuyuhu
 * @Contact 17600520726@163.com 微信 aa249890950-5
 */
@ToString
@Data
public class TestParam {
    @Security
    private String name;
}