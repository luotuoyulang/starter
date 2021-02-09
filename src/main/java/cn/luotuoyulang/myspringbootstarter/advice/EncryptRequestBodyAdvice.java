package cn.luotuoyulang.myspringbootstarter.advice;

import cn.luotuoyulang.myspringbootstarter.annotation.Security;
import cn.luotuoyulang.myspringbootstarter.config.SensitiveConfigProperties;
import cn.luotuoyulang.myspringbootstarter.handler.SecurityHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdvice;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Type;

/**
 * RequestBodyAdvice一个全局的解决方案 , 免去了我们在Controller处理的繁琐
 * RequestBodyAdvice仅对使用了@RqestBody注解的生效 , 因为它原理上还是AOP , 所以GET方法是不会操作的
 * @author yangge
 * @version 1.0.0
 * @date 2020/8/26 17:37
 */
@Slf4j
@ControllerAdvice
public class EncryptRequestBodyAdvice extends AbstractRequestResponseBodyAdvice implements RequestBodyAdvice {

    public EncryptRequestBodyAdvice(SensitiveConfigProperties properties) {
        STANDARD_CLASS.addAll(properties.getClassPackage());
        DEFAULT_CLEAN_DEPTH = properties.getMaxDeep();
    }

    /***
     * @Description 此处如果返回false , 则不执行当前Advice的业务
     * @param methodParameter
     * @param targetType
     * @param converterType
     * @return: boolean
     * @throws
     * @author liuyuhu
     * @date 2021/2/9 16:07
     */
    @Override
    public boolean supports(MethodParameter methodParameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {
//        return methodParameter.hasParameterAnnotation(Security.class);
        return true;
    }
    /**
     * @Description 读取参数前执行
     *              在此做些编码 / 解密 / 封装参数为对象的操作
     * @param inputMessage
     * @param parameter
     * @param targetType
     * @param converterType
     * @return: org.springframework.http.HttpInputMessage
     * @throws
     * @author liuyuhu
     * @date 2021/2/9 16:09
     */
    @Override
    public HttpInputMessage beforeBodyRead(HttpInputMessage inputMessage, MethodParameter parameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) throws IOException {
        return inputMessage;
    }

    /**
     * @Description 读取参数后执行
     * @param body
     * @param inputMessage
     * @param parameter
     * @param targetType
     * @param converterType
     * @return: java.lang.Object
     * @throws
     * @author liuyuhu
     * @date 2021/2/9 16:10
     */
    @Override
    public Object afterBodyRead(Object body, HttpInputMessage inputMessage, MethodParameter parameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {
        log.debug("request body {}", body);
        try {
            return decrypt(body);
        } catch (Exception e) {
            log.error("decrypt failed ", e);
        }
        return null;
    }

    @Override
    public Object handleEmptyBody(Object body, HttpInputMessage inputMessage, MethodParameter parameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {
        return body;
    }

    @Override
    public String handleSecurity(Field field, String value) {
        // 处理器分优先级，优先级高的处理器优先处理，只要找到一个支持的处理器，后续的处理器不再处理
        for (SecurityHandler securityHandler : securityHandlers) {
            if (field != null && securityHandler.support(field)) {
                Annotation annotation = securityHandler.acquire(field);
                return securityHandler.handleDecrypt(value, annotation);
            }
        }
        return value;
    }

    private Object decrypt(Object body) throws Exception {
        return handleObject(0, DEFAULT_CLEAN_DEPTH, null, body);
    }

}
