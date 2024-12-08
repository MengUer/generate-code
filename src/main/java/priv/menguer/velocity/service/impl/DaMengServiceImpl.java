package priv.menguer.velocity.service.impl;

import priv.menguer.commons.core.constant.BaseConstant;
import priv.menguer.commons.core.util.StrUtils;
import priv.menguer.velocity.config.GenConfig;
import priv.menguer.velocity.dao.DaMengMapper;
import priv.menguer.velocity.entity.ColumnInfo;
import priv.menguer.velocity.entity.TableInfo;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author menguer@126.com
 * @version 1.0.0
 * @date 2019年11月8日 下午3:07:49
 * @description
 */
public class DaMengServiceImpl {
    /**
     * 主键约束
     */
    public static final String PRIMARY_KEY = "P";
    /**
     * 唯一键约束
     */
    public static final String UNIQUE = "U";
    /**
     * 外键约束
     */
    public static final String REFERENTIAL_INTEGRITY = "R";
    /**
     * 检查约束
     */
    public static final String CHECK = "C";

    private final DaMengMapper baseMapper = DaMengMapper.getInstance();

    public List<TableInfo> getTableInfos() throws Exception {
        ResultSet resultSet = baseMapper.getAllTabComments(GenConfig.schemaName, GenConfig.tableType, GenConfig.tableNames);
        List<TableInfo> tableInfos = new ArrayList<>();
        while (resultSet.next()) {
            String owner = resultSet.getString("OWNER");
            String tableName = resultSet.getString("TABLE_NAME");
            String tableComment = resultSet.getString("COMMENTS");
            System.out.println(owner + "." + tableName + "\t" + tableComment);
            // 获取该表所有字段
            ResultSet resultSet01 = baseMapper.getAllColumns(owner, tableName);
            // 获取该表所有约束字段
            Map<String, String> allConsColumnsMap = getAllConsColumnsMap(owner, tableName);

            List<ColumnInfo> columns = new ArrayList<>();
            while (resultSet01.next()) {
                String columnName = resultSet01.getString("COLUMN_NAME");
                String columnType = resultSet01.getString("DATA_TYPE");
                String columnComment = resultSet01.getString("COMMENTS");
                String constraintType = allConsColumnsMap.get(columnName);
                columns.add(new ColumnInfo(columnName, getJavaType(columnType), StrUtils.underlineToCamel(columnName.toLowerCase(), true),
                        columnComment, constraintType != null && constraintType.contains(PRIMARY_KEY) ? BaseConstant.YES : BaseConstant.NO,
                        constraintType != null && constraintType.contains(UNIQUE) ? BaseConstant.YES : BaseConstant.NO));
            }
            tableInfos.add(new TableInfo(owner, tableName, tableComment, columns));
        }
        return tableInfos;
    }

    private Map<String, String> getAllConsColumnsMap(String owner, String tableName) throws SQLException {
        Map<String, String> map = new HashMap<String, String>();
        ResultSet allConsColumns = baseMapper.getAllConsColumns(owner, tableName);
        if (allConsColumns == null) {
            return map;
        }
        while (allConsColumns.next()) {
            String columnName = allConsColumns.getString("COLUMN_NAME");
            String constraintType = allConsColumns.getString("CONSTRAINT_TYPE");
            map.merge(columnName, constraintType, String::concat);
        }
        return map;
    }

    private String getJavaType(String columnType) {
        if (columnType.contains("INT")) {
            return "Integer";
        }
        if ("DOUBLE".equals(columnType) || "NUMBER".equals(columnType) || "NUMERIC".equals(columnType)) {
            return "BigDecimal";
        }
        if (columnType.contains("DATE") || columnType.contains("TIME")) {
            return "Date";
        }
        return "String";
    }
}