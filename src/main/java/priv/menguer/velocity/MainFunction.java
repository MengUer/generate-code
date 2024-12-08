package priv.menguer.velocity;

import priv.menguer.velocity.config.GenConfig;
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
public class MainFunction {
    /**
     * @param args
     * @author menguer@126.com
     * @time 2020年7月23日 下午8:56:58
     */
    public static void main(String[] args) {
        GenConfig.init();
        try {
            new GenerateCodeServiceImpl().execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}