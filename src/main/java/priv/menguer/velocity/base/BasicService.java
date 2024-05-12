package priv.menguer.velocity.base;

//import com.github.pagehelper.PageInfo;

import java.util.List;

import com.alibaba.fastjson.JSONArray;

public interface BasicService<T> {
	int insert(T entity) throws Exception;

	int insertMany(List<T> list) throws Exception;

	int deleteById(Long id) throws Exception;

	int deleteByIds(Long[] ids) throws Exception;

	int delete(T entity) throws Exception;

	int update(T entity) throws Exception;

	int updateMany(List<T> list) throws Exception;

	int merge(T entity) throws Exception;

	List<T> list(T entity) throws Exception;

	T getInfo(Long id) throws Exception;

	JSONArray getInfoGroupBy(T entity, String groupBy) throws Exception;

	// PageInfo<T> queryByPage(Integer page, Integer pageSize, T entity) throws Exception;
}
