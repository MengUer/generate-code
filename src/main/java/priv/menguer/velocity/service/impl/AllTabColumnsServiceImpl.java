package priv.menguer.velocity.service.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import priv.menguer.velocity.dao.AllTabColumnsMapper;
import priv.menguer.velocity.entity.AllTabColumns;
import priv.menguer.velocity.service.AbstracService;
import priv.menguer.velocity.service.AllTabColumnsService;

/**
 * @description
 * @author menguer@126.com
 * @date 2020-8-30 11:09:34
 * @verifier
 * @check
 * @update
 * @remark
 */
@Deprecated
public class AllTabColumnsServiceImpl extends AbstracService implements AllTabColumnsService {

	private AllTabColumnsMapper mapper;

	public AllTabColumnsServiceImpl() {
		if (mapper == null) {
			mapper = getSqlSession().getMapper(AllTabColumnsMapper.class);
		}
	}

	@Override
	public List<AllTabColumns> queryByParam(String owner, String tableName) {
		Set<String> set = new HashSet<>();
		if (tableName != null) {
			String[] tableNames = tableName.split(",");
			for (int i = 0; i < tableNames.length; i++) {
				set.add(tableNames[i]);
			}
		}
		List<AllTabColumns> list = mapper.queryByParam(owner, set);
		closeSqlSession();
		return list;
	}

}
