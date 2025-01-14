package priv.menguer.velocity.base;

import com.alibaba.fastjson.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

public class BasicServiceImpl<M extends BasicMapper<T>, T> implements BasicService<T> {
    @Autowired
    protected M baseMapper;

    @Override
    public int upsert(T entity) {
        if (entity == null) {
            return -1;
        }
        List<T> list = new ArrayList<>();
        list.add(entity);
        return upsertBatch(list);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int upsertBatch(List<T> list) {
        if (list == null || list.isEmpty()) {
            return -1;
        }
        int size = list.size();
        for (int i = 0; i < size; i += BATCH_SIZE) {
            int end = Math.min(i + BATCH_SIZE, size);
            baseMapper.upsertBatch(list.subList(i, end));
        }
        return size;
    }

    @Override
    public int insert(T entity) {
        if (entity == null) {
            return -1;
        }
        List<T> list = new ArrayList<>();
        list.add(entity);
        return insertBatch(list);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int insertBatch(List<T> list) {
        if (list == null || list.isEmpty()) {
            return -1;
        }
        int size = list.size();
        for (int i = 0; i < size; i += BATCH_SIZE) {
            int end = Math.min(i + BATCH_SIZE, size);
            baseMapper.insertBatch(list.subList(i, end));
        }
        return size;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int delete(T entity) {
        if (entity == null) {
            return -1;
        }
        return baseMapper.delete(entity);
    }

    @Override
    public int deleteById(String id) {
        if (id == null || id.isEmpty()) {
            return -1;
        }
        Set<String> ids = new HashSet<>();
        ids.add(id);
        return deleteByIds(ids);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteByIds(Collection<String> ids) {
        if (ids == null || ids.isEmpty()) {
            return -1;
        }
        List<String> idList = new ArrayList<>(ids);
        int size = idList.size();
        for (int i = 0; i < size; i += BATCH_SIZE) {
            int end = Math.min(i + BATCH_SIZE, size);
            baseMapper.deleteByIds(idList.subList(i, end));
        }
        return size;
    }

    @Override
    public int update(T entity) {
        if (entity == null) {
            return -1;
        }
        List<T> list = new ArrayList<>();
        list.add(entity);
        return updateBatch(list);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateBatch(List<T> list) {
        if (list == null || list.isEmpty()) {
            return -1;
        }
        int size = list.size();
        for (int i = 0; i < size; i += BATCH_SIZE) {
            int end = Math.min(i + BATCH_SIZE, size);
            baseMapper.updateBatch(list.subList(i, end));
        }
        return size;
    }

    @Override
    public List<T> list(T entity) {
        if (entity == null) {
            return new ArrayList<>();
        }
        return baseMapper.list(entity);
    }

    @Override
    public T getById(String id) {
        if (id == null || id.isEmpty()) {
            return null;
        }
        Set<String> ids = new HashSet<>();
        ids.add(id);
        List<T> list = listByIds(ids);
        if (list == null || list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    @Override
    public List<T> listByIds(Collection<String> ids) {
        if (ids == null || ids.isEmpty()) {
            return new ArrayList<>();
        }
        List<String> idList = new ArrayList<>(ids);
        int size = idList.size();

        List<T> list = new ArrayList<>();
        for (int i = 0; i < size; i += BATCH_SIZE) {
            int end = Math.min(i + BATCH_SIZE, size);
            list.addAll(baseMapper.listByIds(idList.subList(i, end)));
        }
        return list;
    }

    @Override
    public JSONArray getInfoGroupBy(T entity, Collection<String> groupBy) {
        return baseMapper.getInfoGroupBy(entity, groupBy);
    }
}
