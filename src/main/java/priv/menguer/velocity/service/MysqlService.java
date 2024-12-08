package priv.menguer.velocity.service;

import priv.menguer.velocity.entity.TableInfo;

import java.util.List;

public interface MysqlService {
    List<TableInfo> getTableInfos() throws Exception;
}
