package priv.menguer.velocity.dao;

import java.sql.ResultSet;
import java.util.HashSet;
import java.util.Set;

/**
 * @author menguer@126.com
 * @description
 * @date 2023年12月26日 下午9:58:49
 * @verifier
 * @check
 * @update
 * @remark
 */
public class MysqlMapper extends AbstractMapper {
    private MysqlMapper() {
    }

    private static enum Singleton {
        INSTANCE;

        private final MysqlMapper mapper;

        Singleton() {
            mapper = new MysqlMapper();
        }

        public MysqlMapper getInstance() {
            return mapper;
        }
    }

    public static MysqlMapper getInstance() {
        return Singleton.INSTANCE.getInstance();
    }

    /**
     * 查询表信息
     * TABLE_CATALOG
     * TABLE_SCHEMA
     * TABLE_NAME
     * TABLE_TYPE
     * ENGINE
     * VERSION
     * ROW_FORMAT
     * TABLE_ROWS
     * AVG_ROW_LENGTH
     * DATA_LENGTH
     * MAX_DATA_LENGTH
     * INDEX_LENGTH
     * DATA_FREE
     * AUTO_INCREMENT
     * CREATE_TIME
     * UPDATE_TIME
     * CHECK_TIME
     * TABLE_COLLATION
     * CHECKSUM
     * CREATE_OPTIONS
     * TABLE_COMMENT
     *
     * @param schemaName
     * @param tableType
     * @param tableNames
     * @return
     * @author ZhangMingHan
     * @time 2024年1月2日 15:45:57
     */
    public ResultSet getAllTabComments(String schemaName, String tableType, Set<String> tableNames) {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT * FROM information_schema.tables WHERE 1=1 ");
        if (schemaName != null) {
            sql.append(" AND TABLE_SCHEMA ='").append(schemaName).append("'");
        }
        if (tableType != null) {
            sql.append(" AND TABLE_TYPE ='").append(tableType).append("'");
        }
        if (tableNames != null && !tableNames.isEmpty()) {
            sql.append("AND TABLE_NAME IN('");
            for (String tableName : tableNames) {
                sql.append(tableName).append("','");
            }
            sql.append("') ");
        }

        return getResultSet(sql.toString());
    }

    /**
     * 查询字段信息
     * TABLE_CATALOG
     * TABLE_SCHEMA
     * TABLE_NAME
     * COLUMN_NAME
     * ORDINAL_POSITION
     * COLUMN_DEFAULT
     * IS_NULLABLE
     * DATA_TYPE
     * CHARACTER_MAXIMUM_LENGTH
     * CHARACTER_OCTET_LENGTH
     * NUMERIC_PRECISION
     * NUMERIC_SCALE
     * DATETIME_PRECISION
     * CHARACTER_SET_NAME
     * COLLATION_NAME
     * COLUMN_TYPE
     * COLUMN_KEY
     * EXTRA
     * PRIVILEGES
     * COLUMN_COMMENT
     * GENERATION_EXPRESSION
     *
     * @param schemaName
     * @param tableName
     * @return
     * @author ZhangMingHan
     * @time 2023年12月25日 18:25:30
     */
    public ResultSet getAllColumns(String schemaName, String tableName) {
        Set<String> tableNames = new HashSet<String>();
        tableNames.add(tableName);
        return getAllColumns(schemaName, tableNames);
    }

    /**
     * 查询字段信息
     * TABLE_CATALOG
     * TABLE_SCHEMA
     * TABLE_NAME
     * COLUMN_NAME
     * ORDINAL_POSITION
     * COLUMN_DEFAULT
     * IS_NULLABLE
     * DATA_TYPE
     * CHARACTER_MAXIMUM_LENGTH
     * CHARACTER_OCTET_LENGTH
     * NUMERIC_PRECISION
     * NUMERIC_SCALE
     * DATETIME_PRECISION
     * CHARACTER_SET_NAME
     * COLLATION_NAME
     * COLUMN_TYPE
     * COLUMN_KEY
     * EXTRA
     * PRIVILEGES
     * COLUMN_COMMENT
     * GENERATION_EXPRESSION
     *
     * @param schemaName
     * @param tableNames
     * @return
     * @author menguer@126.com
     * @time 2024/12/8 17:18
     */
    public ResultSet getAllColumns(String schemaName, Set<String> tableNames) {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT * from information_schema.columns where 1=1 ");
        if (schemaName != null) {
            sql.append(" AND TABLE_SCHEMA ='").append(schemaName).append("'");
        }
        if (tableNames != null && !tableNames.isEmpty()) {
            sql.append(" AND TABLE_NAME IN('");
            for (String tableName : tableNames) {
                sql.append(tableName).append("','");
            }
            sql.append("') ");
        }
        // System.out.println(sql);
        return getResultSet(sql.toString());
    }
}
