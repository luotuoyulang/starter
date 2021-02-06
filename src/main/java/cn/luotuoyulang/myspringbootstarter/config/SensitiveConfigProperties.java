package cn.luotuoyulang.myspringbootstarter.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.List;

@ConfigurationProperties(prefix = "spring.sensitive")
@Data
public class SensitiveConfigProperties {

    private String type;

    private SecurityProperties security;

    private int maxDeep = 10;

    private List<String> classPackage = new ArrayList<>();
}
