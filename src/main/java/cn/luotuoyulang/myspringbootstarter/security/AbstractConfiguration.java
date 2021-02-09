package cn.luotuoyulang.myspringbootstarter.security;

import cn.luotuoyulang.myspringbootstarter.advice.EncryptRequestBodyAdvice;
import cn.luotuoyulang.myspringbootstarter.advice.EncryptResponseBodyAdvice;
import cn.luotuoyulang.myspringbootstarter.annotation.EnableSensitive;
import cn.luotuoyulang.myspringbootstarter.config.SensitiveConfigProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ImportAware;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.type.AnnotationMetadata;

/**
 * @author yangge
 * @version 1.0.0
 * @date 2020/8/31 9:16
 */
public abstract class AbstractConfiguration implements ImportAware {

    protected SensitiveConfigProperties configProperties;

    public AbstractConfiguration(SensitiveConfigProperties configProperties) {
        this.configProperties = configProperties;
    }

    @Override
    public void setImportMetadata(AnnotationMetadata importMetadata) {
        AnnotationAttributes enableSensitive = AnnotationAttributes.fromMap(
                importMetadata.getAnnotationAttributes(EnableSensitive.class.getName(), false));
        if (enableSensitive == null) {
            throw new IllegalArgumentException(
                    "@EnableSensitive is not present on importing class " + importMetadata.getClassName());
        }
        String[] packages = enableSensitive.getStringArray("packages");
        for (int i = 0; i < packages.length; i++) {
            if (!configProperties.getClassPackage().contains(packages[i])) {
                this.configProperties.getClassPackage().add(packages[i]);
            }
        }
    }

    @Bean
    @ConditionalOnMissingBean
    public EncryptRequestBodyAdvice encryptRequestBodyAdvice() {
        EncryptRequestBodyAdvice requestBodyAdvice = new EncryptRequestBodyAdvice(configProperties);
        return requestBodyAdvice;
    }

    @Bean
    @ConditionalOnMissingBean
    public EncryptResponseBodyAdvice encryptResponseBodyAdvice() {
        EncryptResponseBodyAdvice responseBodyAdvice = new EncryptResponseBodyAdvice(configProperties);
        return responseBodyAdvice;
    }
}
