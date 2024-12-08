package priv.menguer.velocity.service.impl;

import priv.menguer.commons.core.constant.BaseConstant;
import priv.menguer.commons.core.util.StrUtils;
import priv.menguer.velocity.config.GenConfig;
import priv.menguer.velocity.dao.MysqlMapper;
import priv.menguer.velocity.entity.ColumnInfo;
import priv.menguer.velocity.entity.TableInfo;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * @author menguer@126.com
 * @version 1.0.0
 * @date 2019年11月8日 下午3:07:49
 * @description
 */
public class MysqlServiceImpl {
    /**
     * 主键约束
     */
    private static final String PRIMARY_KEY = "PRI";
    /**
     * 唯一键约束
     */
    private static final String UNIQUE = "U";

    private final MysqlMapper baseMapper = MysqlMapper.getInstance();

    /**
     * 获取表信息
     *
     * @return
     * @throws Exception
     * @author menguer@126.com
     * @time 2024/12/8 17:14
     */
    public List<TableInfo> getTableInfos() throws Exception {
        ResultSet resultSet = baseMapper.getAllTabComments(GenConfig.schemaName, GenConfig.tableType, GenConfig.tableNames);
        // 6. 遍历结果集中的每一行数据
        List<TableInfo> tableInfos = new ArrayList<>();
        while (resultSet.next()) {
            String tableSchema = resultSet.getString("TABLE_SCHEMA");
            String tableName = resultSet.getString("TABLE_NAME");
            String tableComment = resultSet.getString("TABLE_COMMENT");
            System.out.println(tableSchema + "." + tableName + "\t" + tableComment);
            // 获取该表所有字段
            ResultSet columnResultSet = baseMapper.getAllColumns(tableSchema, tableName);

            List<ColumnInfo> columns = new ArrayList<>();
            while (columnResultSet.next()) {
                String columnName = columnResultSet.getString("COLUMN_NAME");
                String columnType = columnResultSet.getString("DATA_TYPE");
                String columnComment = columnResultSet.getString("COLUMN_COMMENT");
                String columnKey = columnResultSet.getString("COLUMN_KEY");
                columns.add(new ColumnInfo(
                        columnName
                        , getJavaType(columnType)
                        , StrUtils.underlineToCamel(columnName.toLowerCase(), true)
                        , columnComment
                        , columnKey != null && columnKey.contains(PRIMARY_KEY) ? BaseConstant.YES : BaseConstant.NO
                        , columnKey != null && columnKey.contains(UNIQUE) ? BaseConstant.YES : BaseConstant.NO));
            }
            tableInfos.add(new TableInfo(tableSchema, tableName, tableComment, columns));
        }
        return tableInfos;
    }

    private String getJavaType(String columnType) {
        if (columnType.contains("int")) {
            return "Integer";
        }
        if ("double".equals(columnType) || "number".equals(columnType) || "numeric".equals(columnType)) {
            return "BigDecimal";
        }
        if (columnType.contains("date") || columnType.contains("time")) {
            return "Date";
        }
        return "String";
    }
}