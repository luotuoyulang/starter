package cn.luotuoyulang.myspringbootstarter.config;

import lombok.Data;
import org.springframework.util.StopWatch;

@Data
public class SecurityProperties {

    /**
     * @See SecurityMode
     */
    private String mode = "BASE64";

    private String type = "AES";

    private String charset = "UTF-8";

    private String secret = "+6cuvzvyrFZpRG9pf3r7eQ==";

    private String privateKey;

    private String publicKey;

    public static void main(String[] args) throws InterruptedException {
        StopWatch sw = new StopWatch("test");
        sw.start("task1");
        Thread.sleep(1000);
        sw.stop();
        System.out.println("sw.prettyPrint() = " + sw.prettyPrint());
    }
}
