package priv.menguer.velocity.service.impl;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;

import priv.menguer.commons.core.constant.BaseConstant;
import priv.menguer.commons.core.util.FileUtils;
import priv.menguer.commons.core.util.StrUtils;
import priv.menguer.velocity.config.GenConfig;
import priv.menguer.velocity.constant.TemplateEnum;
import priv.menguer.velocity.entity.ColumnInfo;
import priv.menguer.velocity.entity.TableInfo;
import priv.menguer.velocity.mapper.BaseMapper;

/**
 * @author menguer@126.com
 * @date 2019年11月8日 下午3:07:49
 * @version 1.0.0
 * @description
 */
public class GenerateCodeServiceImpl {
	/**
	 * 主键约束
	 */
	private static final String PRIMARY_KEY = "P";
	/**
	 * 唯一键约束
	 */
	private static final String UNIQUE = "U";
	/**
	 * 外键约束
	 */
	private static final String REFERENTIAL_INTEGRITY = "R";
	/**
	 * 检查约束
	 */
	private static final String CHECK = "C";
	private static VelocityEngine velocityEngine;

	private BaseMapper baseMapper = BaseMapper.getInstance();

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

	private List<TableInfo> getTableInfos() throws Exception {
		ResultSet resultSet = baseMapper.getAllTabComments(GenConfig.schemaName, GenConfig.tableType, GenConfig.tableNames);
		// 4. 获取结果集的元数据
		// ResultSetMetaData metaData = resultSet.getMetaData();
		// 5. 获取列数（字段数）
		// int columnCount = metaData.getColumnCount();
		// 6. 遍历结果集中的每一行数据
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

			// 7. 遍历每一行的每一个字段
			// for (int i = 1; i <= columnCount; i++) {
			// // 8. 使用元数据获取字段的名称和值
			// String columnName = metaData.getColumnName(i);
			// Object columnValue = resultSet.getObject(i);
			//
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
			String string = map.get(columnName);
			if (string == null) {
				map.put(columnName, constraintType);
			} else {
				map.put(columnName, string.concat(constraintType));
			}
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

	/**
	 * 生成mybatis的实体映射文件
	 * 
	 * @author menguer@126.com
	 * @time 2019年11月26日 下午7:42:03
	 * @param tableInfo
	 * @throws IOException
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
	 * @author menguer@126.com
	 * @time 2020-7-24 23:54:45
	 * @param templateType
	 * @return
	 */
	private Template getTemplateByType(TemplateEnum templateType) {
		if (velocityEngine == null) {
			velocityEngine = new VelocityEngine();
			Properties properties = new Properties();
			properties.setProperty("resource.loader", "class");
			properties.setProperty("class.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
			velocityEngine.init(properties);
		}
		return velocityEngine.getTemplate("/template/" + templateType.getValue() + ".vm", "utf-8");
	}

	/**
	 * 根据不同模版获取生成文件的文件夹路径
	 * 
	 * @author menguer@126.com
	 * @time 2019年11月27日 下午1:34:30
	 * @param subproject
	 *            子工程名称
	 * @param templateName
	 * @param className
	 *            类名
	 * @return
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
