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
public class DaMengMapper extends AbstractMapper {
    private DaMengMapper() {
    }

    private static enum Singleton {
        INSTANCE;

        private final DaMengMapper mapper;

        Singleton() {
            mapper = new DaMengMapper();
        }

        public DaMengMapper getInstance() {
            return mapper;
        }
    }

    public static DaMengMapper getInstance() {
        return Singleton.INSTANCE.getInstance();
    }

    /**
     * 查询表信息
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
        sql.append("SELECT * FROM ALL_TAB_COMMENTS WHERE 1=1 ");
        if (schemaName != null) {
            sql.append("AND OWNER ='").append(schemaName).append("'");
        }
        if (tableType != null) {
            sql.append("AND TABLE_TYPE ='").append(tableType).append("'");
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
     * 联查ALL_TAB_COLUMNS、ALL_COL_COMMENTS
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

    public ResultSet getAllColumns(String schemaName, Set<String> tableNames) {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT ");
        sql.append("t1.*,t2.COMMENTS ");
        sql.append("FROM ");
        sql.append("(");
        sql.append("SELECT * FROM ALL_TAB_COLUMNS WHERE 1=1 ");
        if (schemaName != null) {
            sql.append("AND OWNER ='").append(schemaName).append("'");
        }
        if (tableNames != null && !tableNames.isEmpty()) {
            sql.append("AND TABLE_NAME IN('");
            for (String tableName : tableNames) {
                sql.append(tableName).append("','");
            }
            sql.append("') ");
        }
        sql.append(")t1 ");
        sql.append("LEFT JOIN ");
        sql.append("(");
        sql.append("SELECT * FROM ALL_COL_COMMENTS WHERE 1=1 ");
        if (schemaName != null) {
            sql.append("AND SCHEMA_NAME ='").append(schemaName).append("'");
        }
        if (tableNames != null && !tableNames.isEmpty()) {
            sql.append("AND TABLE_NAME IN('");
            for (String tableName : tableNames) {
                sql.append(tableName).append("','");
            }
            sql.append("') ");
        }
        sql.append(")t2 ");
        sql.append("ON t1.OWNER=t2.SCHEMA_NAME ");
        sql.append("AND t1.TABLE_NAME=t2.TABLE_NAME ");
        sql.append("AND t1.COLUMN_NAME=t2.COLUMN_NAME ");
        // System.out.println(sql);
        return getResultSet(sql.toString());
    }

    /**
     * 查询ALL_CONS_COLUMNS中CONSTRAINT_TYPE='U'的唯一键字段
     *
     * @param schemaName
     * @param tableName
     * @return
     * @author ZhangMingHan
     * @time 2024年1月15日 11:02:27
     */
    public ResultSet getAllConsColumns(String schemaName, String tableName) {
        Set<String> tableNames = new HashSet<String>();
        tableNames.add(tableName);
        return getAllConsColumns(schemaName, tableNames);
    }

    /**
     * 查询ALL_CONS_COLUMNS
     *
     * @param schemaName
     * @param tableNames
     * @return
     * @author ZhangMingHan
     * @time 2024年1月15日 10:54:14
     */
    public ResultSet getAllConsColumns(String schemaName, Set<String> tableNames) {
        return getAllConsColumns(schemaName, tableNames, null);
    }

    /**
     * @param schemaName
     * @param tableNames
     * @param constraintType 'P'：表示主键（Primary Key）约束。
     *                       'U'：表示唯一性（Unique）约束。
     *                       'R'：表示外键（Referential Integrity）约束。
     *                       'C'：表示检查（Check）约束。
     * @return
     * @author ZhangMingHan
     * @time 2024年1月19日 10:29:17
     */
    public ResultSet getAllConsColumns(String schemaName, Set<String> tableNames, String constraintType) {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT t1.*,t2.CONSTRAINT_TYPE FROM");
        sql.append("(");
        // 查询ALL_CONS_COLUMNS
        sql.append("SELECT * FROM ALL_CONS_COLUMNS WHERE 1=1 ");
        if (schemaName != null) {
            sql.append("AND OWNER ='").append(schemaName).append("' ");
        }
        if (tableNames != null && !tableNames.isEmpty()) {
            sql.append("AND TABLE_NAME IN('");
            for (String tableName : tableNames) {
                sql.append(tableName).append("','");
            }
            sql.append("') ");
        }
        sql.append(") t1 ");
        sql.append("LEFT JOIN ");
        sql.append("(");
        // 查询ALL_CONSTRAINTS
        sql.append("SELECT * FROM ALL_CONSTRAINTS WHERE 1=1 ");
        sql.append("AND STATUS = 'ENABLED' ");
        if (schemaName != null) {
            sql.append("AND OWNER ='").append(schemaName).append("' ");
        }
        if (tableNames != null && !tableNames.isEmpty()) {
            sql.append("AND TABLE_NAME IN('");
            for (String tableName : tableNames) {
                sql.append(tableName).append("','");
            }
            sql.append("') ");
        }
        if (constraintType != null && !constraintType.isEmpty()) {
            sql.append(String.format("AND CONSTRAINT_TYPE IN('%s')", constraintType.replace(",", "','")));
        }
        sql.append(") t2 ");
        sql.append("ON t1.CONSTRAINT_NAME=t2.CONSTRAINT_NAME");
        return getResultSet(sql.toString());
    }
}
