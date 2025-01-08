package priv.menguer.velocity.base;

//import com.github.pagehelper.PageInfo;

import java.util.Collection;
import java.util.List;

import com.alibaba.fastjson.JSONArray;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public interface BasicService<T> {
    Logger logger = LogManager.getLogger(LogManager.ROOT_LOGGER_NAME);
    int BATCH_SIZE = 1000;

    /**
     * 单条有则更新无则新增
     *
     * @param entity
     * @return
     * @author menguer@126.com
     * @time 2025/1/4 23:56
     */
    int upsert(T entity);

    /**
     * 批量有则更新无则新增
     *
     * @param list
     * @return
     * @author menguer@126.com
     * @time 2025/1/4 23:57
     */
    int upsertBatch(List<T> list);

    /**
     * 单条新增
     *
     * @param entity
     * @return
     * @throws Exception
     * @author menguer@126.com
     * @time 2025/1/4 21:40
     */
    int insert(T entity);

    /**
     * 批量新增
     *
     * @param list
     * @return
     * @throws Exception
     * @author menguer@126.com
     * @time 2025/1/4 21:40
     */
    int insertBatch(List<T> list);

    /**
     * 按条件删除
     *
     * @param entity
     * @return
     * @throws Exception
     * @author menguer@126.com
     * @time 2025/1/4 21:40
     */
    int delete(T entity);

    /**
     * 按单个id删除
     *
     * @param id
     * @return
     * @throws Exception
     * @author menguer@126.com
     * @time 2025/1/4 21:41
     */
    int deleteById(String id);

    /**
     * 按多个id删除
     *
     * @param ids
     * @return
     * @throws Exception
     * @author menguer@126.com
     * @time 2025/1/4 21:41
     */
    int deleteByIds(Collection<String> ids);

    /**
     * 单条更新
     *
     * @param entity
     * @return
     * @throws Exception
     * @author menguer@126.com
     * @time 2025/1/4 21:41
     */
    int update(T entity);

    /**
     * 批量更新
     *
     * @param list
     * @return
     * @throws Exception
     * @author menguer@126.com
     * @time 2025/1/4 21:41
     */
    int updateBatch(List<T> list);

    /**
     * 条件查询
     *
     * @param entity
     * @return
     * @throws Exception
     * @author menguer@126.com
     * @time 2025/1/4 21:41
     */
    List<T> list(T entity);

    /**
     * 按单个id查询
     *
     * @param id
     * @return
     * @throws Exception
     * @author menguer@126.com
     * @time 2025/1/4 21:41
     */
    T getById(String id);

    /**
     * 按多个id查询
     *
     * @param ids
     * @return
     * @throws Exception
     * @author menguer@126.com
     * @time 2025/1/4 21:41
     */
    List<T> listByIds(Collection<String> ids);

    /**
     * 分组查询
     *
     * @param entity
     * @param groupBy
     * @return
     * @throws Exception
     * @author menguer@126.com
     * @time 2025/1/4 21:42
     */
    JSONArray getInfoGroupBy(T entity, Collection<String> groupBy);

    // PageInfo<T> queryByPage(Integer page, Integer pageSize, T entity);
}