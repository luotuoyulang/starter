package cn.luotuoyulang.myspringbootstarter.annotation;

import cn.luotuoyulang.myspringbootstarter.config.SensitivePropertiesAutoConfiguration;
import cn.luotuoyulang.myspringbootstarter.selector.SensitiveConfigurationSelector;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import({SensitivePropertiesAutoConfiguration.class, SensitiveConfigurationSelector.class})
public @interface EnableSensitive {

    boolean security() default true;

    boolean sensitive() default true;

    String[] packages() default {};
}
