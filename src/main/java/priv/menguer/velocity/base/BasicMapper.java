package priv.menguer.velocity.base;

import java.util.Collection;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import com.alibaba.fastjson.JSONArray;

/**
 * 数据基础方法接口
 */
public interface BasicMapper<T> {
    /**
     * 有则更新，无则新增
     *
     * @param entity
     * @return
     * @author ZhangMingHan
     * @time 2024年1月21日 17:37:21
     */
    int upsert(T entity);

    /**
     * 多条新增
     *
     * @param list
     * @return
     * @author ZhangMingHan
     * @time 2023年12月26日 14:35:34
     */
    int insertBatch(List<T> list);

    /**
     * 条件删除
     *
     * @param entity
     * @return
     * @author ZhangMingHan
     * @time 2023年12月26日 14:35:42
     */
    int delete(T entity);

    /**
     * 多条删除
     *
     * @param ids
     * @return
     * @author ZhangMingHan
     * @time 2023年12月26日 14:35:53
     */
    int deleteByIds(@Param("ids") Collection<String> ids);

    /**
     * 单条修改
     *
     * @param entity
     * @return
     * @author ZhangMingHan
     * @time 2023年12月26日 14:35:58
     */
    int update(T entity);

    /**
     * 多条修改
     *
     * @param list
     * @return
     * @author ZhangMingHan
     * @time 2023年12月26日 14:36:03
     */
    int updateBatch(List<T> list);

    /**
     * 条件查询
     *
     * @param entity
     * @return
     * @author ZhangMingHan
     * @time 2023年12月26日 14:36:09
     */
    List<T> list(T entity);

    /**
     * 多个id查询
     *
     * @param ids
     * @return
     * @author menguer@126.com
     * @time 2025/1/4 17:34
     */
    List<T> listByIds(@Param("ids") Collection<String> ids);

    /**
     * 分组查询
     *
     * @param entity
     * @param groupBy
     * @return
     * @author ZhangMingHan
     * @time 2024年1月26日 12:06:41
     */
    JSONArray getInfoGroupBy(@Param("entity") T entity, @Param("groupBy") Collection<String> groupBy);
}
