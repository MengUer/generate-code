package priv.menguer.velocity.base;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.alibaba.fastjson.JSONArray;

/**
 * 数据基础方法接口
 */
public interface BasicMapper<T> {

	/**
	 * 单条新增
	 * 
	 * @author ZhangMingHan
	 * @time 2023年12月26日 14:35:24
	 * @param entity
	 * @return
	 */
	int insert(T entity);

	/**
	 * 多条新增
	 * 
	 * @author ZhangMingHan
	 * @time 2023年12月26日 14:35:34
	 * @param list
	 * @return
	 */
	int insertMany(List<T> list);

	/**
	 * 单条删除
	 * 
	 * @author ZhangMingHan
	 * @time 2023年12月26日 14:35:48
	 * @param gid
	 * @return
	 */
	int deleteById(Long id);

	/**
	 * 多条删除
	 * 
	 * @author ZhangMingHan
	 * @time 2023年12月26日 14:35:53
	 * @param gids
	 * @return
	 */
	int deleteByIds(Long[] ids);

	/**
	 * 条件删除
	 * 
	 * @author ZhangMingHan
	 * @time 2023年12月26日 14:35:42
	 * @param entity
	 * @return
	 */
	int delete(T entity);

	/**
	 * 单条修改
	 * 
	 * @author ZhangMingHan
	 * @time 2023年12月26日 14:35:58
	 * @param entity
	 * @return
	 */
	int update(T entity);

	/**
	 * 多条修改
	 * 
	 * @author ZhangMingHan
	 * @time 2023年12月26日 14:36:03
	 * @param list
	 * @return
	 */
	int updateMany(List<T> list);

	/**
	 * 有则更新，无则新增
	 * 
	 * @author ZhangMingHan
	 * @time 2024年1月21日 17:37:21
	 * @param list
	 * @return
	 */
	int merge(T entity);

	/**
	 * 条件查询
	 * 
	 * @author ZhangMingHan
	 * @time 2023年12月26日 14:36:09
	 * @param entity
	 * @return
	 */
	List<T> list(T entity);

	/**
	 * 查询详情
	 * 
	 * @author ZhangMingHan
	 * @time 2024年1月4日 13:39:34
	 * @param id
	 * @return
	 */
	T getInfo(Long id);

	/**
	 * 分组查询
	 * 
	 * @author ZhangMingHan
	 * @time 2024年1月26日 12:06:41
	 * @param entity
	 * @param groupBy
	 * @return
	 */
	JSONArray getInfoGroupBy(@Param("entity") T entity, @Param("groupBy") String[] groupBy);
}
