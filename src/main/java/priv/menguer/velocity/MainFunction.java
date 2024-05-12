package priv.menguer.velocity;

import priv.menguer.velocity.config.GenConfig;
import priv.menguer.velocity.service.impl.GenerateCodeServiceImpl;

/**
 * @package priv.menguer.velocity
 * @file MainFunction.java
 * @description
 * @author menguer@126.com
 * @date 2020年7月23日 下午8:56:58
 * @verifier
 * @check
 * @update
 * @remark
 */
public class MainFunction {

	/**
	 * @author menguer@126.com
	 * @time 2020年7月23日 下午8:56:58
	 * @param args
	 */
	public static void main(String[] args) {
		GenConfig.init();
		// FileUtils.createFolder(GenConfig.saveFilePath);
		try {
			new GenerateCodeServiceImpl().execute();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
