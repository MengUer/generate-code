package priv.menguer.velocity.service;

import java.util.List;

import priv.menguer.velocity.entity.AllTabColumns;

/**
 * @description
 * @author menguer@126.com
 * @date 2020-8-30 11:10:50
 * @verifier
 * @check
 * @update
 * @remark
 */
public interface AllTabColumnsService {

	/**
	 * @author menguer@126.com
	 * @time 2020-8-30 11:11:16
	 * @param owner
	 * @param tableName
	 * @return
	 */
	List<AllTabColumns> queryByParam(String owner, String tableName);
}
