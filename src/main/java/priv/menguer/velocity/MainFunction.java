package priv.menguer.velocity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import priv.menguer.velocity.config.GenConfig;
import priv.menguer.velocity.service.GenerateCodeService;
import priv.menguer.velocity.service.impl.GenerateCodeServiceImpl;

/**
 * @author menguer@126.com
 * @package priv.menguer.velocity
 * @file MainFunction.java
 * @description
 * @date 2020年7月23日 下午8:56:58
 * @verifier
 * @check
 * @update
 * @remark
 */
@SpringBootApplication
public class MainFunction implements CommandLineRunner {
    /**
     * @param args
     * @author menguer@126.com
     * @time 2020年7月23日 下午8:56:58
     */
    public static void main(String[] args) {
        SpringApplication.run(MainFunction.class, args);
    }

    @Autowired
    private GenerateCodeService generateCodeService;

    @Override
    public void run(String... args) throws Exception {
        GenConfig.init();
        try {
            generateCodeService.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}