package priv.menguer.velocity.service.impl;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import priv.menguer.commons.core.constant.BaseConstant;
import priv.menguer.commons.core.util.FileUtils;
import priv.menguer.commons.core.util.StrUtils;
import priv.menguer.velocity.config.GenConfig;
import priv.menguer.velocity.constant.TemplateEnum;
import priv.menguer.velocity.dao.MysqlMapper;
import priv.menguer.velocity.entity.ColumnInfo;
import priv.menguer.velocity.entity.TableInfo;
import priv.menguer.velocity.service.GenerateCodeService;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

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
    private static final String DB_TYPE = "mysql/";
    private static final String PRIMARY_KEY = "PRI";
    /**
     * 唯一键约束
     */
    private static final String UNIQUE = "U";
    private static VelocityEngine velocityEngine;

    private final MysqlMapper baseMapper = MysqlMapper.getInstance();

    public void execute() throws Exception {
        List<TableInfo> tableInfos = getTableInfos();
        for (TableInfo tableInfo : tableInfos) {
            if (GenConfig.generateEntity) {
                generateFile(tableInfo, TemplateEnum.ENTITY);// 生成实体类
            }
            if (GenConfig.generateDAO) {
                generateFile(tableInfo, TemplateEnum.DAO);// 生成dao层接口
            }
            if (GenConfig.generateMapper) {
                generateFile(tableInfo, TemplateEnum.MAPPER);// 生成mybatis映射文件
            }
            if (GenConfig.generateService) {
                generateFile(tableInfo, TemplateEnum.SERVICE);// 生成service接口
            }
            if (GenConfig.generateServiceImpl) {
                generateFile(tableInfo, TemplateEnum.SERVICEIMPL);// 生成service实现类
            }
            if (GenConfig.generateController) {
                generateFile(tableInfo, TemplateEnum.CONTROLLER);// 生成controller
            }
        }
        System.out.println("执行完毕。");
    }

    /**
     * 获取表信息
     *
     * @return
     * @throws Exception
     * @author menguer@126.com
     * @time 2024/12/8 17:14
     */
    private List<TableInfo> getTableInfos() throws Exception {
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

    /**
     * 生成mybatis的实体映射文件
     *
     * @param tableInfo
     * @throws IOException
     * @author menguer@126.com
     * @time 2019年11月26日 下午7:42:03
     */
    private void generateFile(TableInfo tableInfo, TemplateEnum templateType) throws IOException {
        String tableName = tableInfo.getTableName();
        String className = StrUtils.underlineToCamel(tableName, false);
        String subproject = tableInfo.getTableSubproject();
        // 设置模版中的必要参数
        VelocityContext context = new VelocityContext();
        context.put("tableName", tableName);
        context.put("className", className);
        context.put("tableSubproject", subproject);
        context.put("tableComment", tableInfo.getTableComment());
        context.put("projectName", subproject == null ? GenConfig.projectName : GenConfig.projectName + "." + subproject);
        context.put("list", tableInfo.getColumns());
        // 获取生成文件的路径
        String filePath = getGeneratePath(subproject, templateType, className);
        // 生成文件,如果文件已经存在,会生成新文件覆盖旧文件
        Writer writer = new PrintWriter(filePath);
        getTemplateByType(templateType).merge(context, writer);
        writer.close();
        System.out.println("生成文件：" + filePath);
    }

    /**
     * 获取模版路径
     *
     * @param templateType
     * @return
     * @author menguer@126.com
     * @time 2020-7-24 23:54:45
     */
    private Template getTemplateByType(TemplateEnum templateType) {
        if (velocityEngine == null) {
            velocityEngine = new VelocityEngine();
            Properties properties = new Properties();
            properties.setProperty("resource.loader", "class");
            properties.setProperty("class.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
            velocityEngine.init(properties);
        }
        return velocityEngine.getTemplate("/template/" + DB_TYPE + templateType.getValue() + ".vm", "utf-8");
    }

    /**
     * 根据不同模版获取生成文件的文件夹路径
     *
     * @param subproject   子工程名称
     * @param templateType
     * @param className    类名
     * @return
     * @author menguer@126.com
     * @time 2019年11月27日 下午1:34:30
     */
    private String getGeneratePath(final String subproject, TemplateEnum templateType, final String className) {
        StringBuilder generatePath = new StringBuilder(GenConfig.saveFilePath);
        if (subproject != null) {
            generatePath.append("\\").append(subproject);
        }
        generatePath.append("\\src\\com");
        if (TemplateEnum.ENTITY.equals(templateType)) {
            generatePath.append("\\entity");
            FileUtils.createFolder(generatePath.toString());
            return generatePath.append("\\").append(className).append(".java").toString();
        }
        if (TemplateEnum.DAO.equals(templateType)) {
            generatePath.append("\\dao");
            FileUtils.createFolder(generatePath.toString());
            return generatePath.append("\\").append(className).append("Mapper.java").toString();
        }
        if (TemplateEnum.SERVICE.equals(templateType)) {
            generatePath.append("\\service");
            FileUtils.createFolder(generatePath.toString());
            return generatePath.append("\\").append(className).append("Service.java").toString();
        }
        if (TemplateEnum.SERVICEIMPL.equals(templateType)) {
            generatePath.append("\\service\\impl");
            FileUtils.createFolder(generatePath.toString());
            return generatePath.append("\\").append(className).append("ServiceImpl.java").toString();
        }
        if (TemplateEnum.CONTROLLER.equals(templateType)) {
            generatePath.append("\\controller");
            FileUtils.createFolder(generatePath.toString());
            return generatePath.append("\\").append(className).append("Controller.java").toString();
        }
        if (TemplateEnum.MAPPER.equals(templateType)) {
            generatePath.append("\\dao");
            FileUtils.createFolder(generatePath.toString());
            return generatePath.append("\\").append(className).append("Mapper.xml").toString();
        }
        return generatePath.append("\\").append(className).append(".txt").toString();
    }
}