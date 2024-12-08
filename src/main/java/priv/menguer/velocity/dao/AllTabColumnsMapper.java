package priv.menguer.velocity.dao;

import java.util.List;
import java.util.Set;

import org.apache.ibatis.annotations.Param;

import priv.menguer.velocity.entity.AllTabColumns;

/**
 * @description
 * @author menguer@126.com
 * @date 2020-8-22 10:48:45
 * @verifier
 * @check
 * @update
 * @remark
 */
public interface AllTabColumnsMapper {

	/**
	 * @author menguer@126.com
	 * @time 2020-8-30 11:08:09
	 * @param owner
	 * @param tableNames
	 * @return
	 */
	List<AllTabColumns> queryByParam(@Param("owner") String owner, @Param("tableNames") Set<String> tableNames);
}
