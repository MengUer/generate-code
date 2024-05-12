package priv.menguer.velocity.service;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;

import priv.menguer.velocity.config.GenConfig;
//import priv.menguer.velocity.constant.Config;
import priv.menguer.velocity.constant.TemplateEnum;
import priv.menguer.velocity.constant.TypeConstant;
import priv.menguer.velocity.entity.ColumnInfo;
import priv.menguer.velocity.entity.TableInfo;

/**
 * @author menguer@126.com
 * @date 2019年11月8日 下午3:07:49
 * @version 1.0.0
 * @description
 */
public class GenerateCode {

	/**
	 * excel中首页的sheet名称
	 */
	private static final String EXCEL_HOME_PAGE = "HOME";

	/*
	 * 判断值
	 */
	private static final String BOOLEAN_YES = "YES";
	private static final String BOOLEAN_NO = "NO";

	private static String projectAbbName;// 工程英文简称
	private static String projectName;// 工程中文名称
	private static String dbName;// 数据库名称
	private static String dbcoding;// 数据库编码
	private static String dbType;// 数据库类型
	private static String dbUrl;// 数据库地址
	private static String dbUserName;// 数据库用户名
	private static String dbPassword;// 数据库密码
	private static List<TableInfo> tableInfos = new ArrayList<TableInfo>();// excel表的详情

	private VelocityEngine velocityEngine;

	public void execute() throws EncryptedDocumentException, IOException {
//		Config.initConfig();
		createFolder(GenConfig.saveFilePath);
		// 1.根据excel的绝对路径读取表中信息
		readExcel(GenConfig.databaseInfoFilePath);
		if (tableInfos.isEmpty()) {
			return;
		}
		for (int i = 0; i < tableInfos.size(); i++) {
			TableInfo tableInfo = tableInfos.get(i);
			if (BOOLEAN_YES.equalsIgnoreCase(tableInfo.getTableIsGenerateCode())) {
				generateFile(tableInfo, TemplateEnum.ENTITY);// 生成实体类
				generateFile(tableInfo, TemplateEnum.DAO);// 生成dao层接口
				generateFile(tableInfo, TemplateEnum.MAPPER);// 生成mybatis映射文件
				generateFile(tableInfo, TemplateEnum.SERVICE);// 生成service接口
				generateFile(tableInfo, TemplateEnum.SERVICEIMPL);// 生成service实现类
				generateFile(tableInfo, TemplateEnum.CONTROLLER);// 生成controller
			}
		}
		System.out.println("执行完毕。");
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
		String className = underlineToCamel(tableName, false);
		String subproject = tableInfo.getTableSubproject();
		// 设置模版中的必要参数
		VelocityContext context = new VelocityContext();
		context.put("tableName", tableName);
		context.put("className", className);
		context.put("tableSubproject", subproject);
		context.put("tableComment", tableInfo.getTableComment());
		context.put("projectName", subproject == null ? projectAbbName : projectAbbName + "." + subproject);
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
		StringBuilder generatePath = new StringBuilder(GenConfig.saveFilePath).append("\\").append(projectAbbName);
		if (subproject != null) {
			generatePath.append("\\").append(subproject);
		}
		generatePath.append("\\src\\com");
		if (TemplateEnum.ENTITY.equals(templateType)) {
			generatePath.append("\\entity");
			createFolder(generatePath.toString());
			return generatePath.append("\\").append(className).append(".java").toString();
		}
		if (TemplateEnum.DAO.equals(templateType)) {
			generatePath.append("\\dao");
			createFolder(generatePath.toString());
			return generatePath.append("\\").append(className).append("Mapper.java").toString();
		}
		if (TemplateEnum.SERVICE.equals(templateType)) {
			generatePath.append("\\service");
			createFolder(generatePath.toString());
			return generatePath.append("\\").append(className).append("Service.java").toString();
		}
		if (TemplateEnum.SERVICEIMPL.equals(templateType)) {
			generatePath.append("\\service\\impl");
			createFolder(generatePath.toString());
			return generatePath.append("\\").append(className).append("ServiceImpl.java").toString();
		}
		if (TemplateEnum.CONTROLLER.equals(templateType)) {
			generatePath.append("\\controller");
			createFolder(generatePath.toString());
			return generatePath.append("\\").append(className).append("Controller.java").toString();
		}
		if (TemplateEnum.MAPPER.equals(templateType)) {
			generatePath.append("\\dao");
			createFolder(generatePath.toString());
			return generatePath.append("\\").append(className).append("Mapper.xml").toString();
		}
		return generatePath.append("\\").append(className).append(".txt").toString();
	}

	/**
	 * 读取excel,存入tableinfo的list中
	 * 
	 * @author menguer@126.com
	 * @time 2019年11月9日 下午2:22:15
	 * @param path
	 *            文件的绝对路径
	 * @throws IOException
	 * @throws EncryptedDocumentException
	 */
	private void readExcel(String path) throws EncryptedDocumentException, IOException {
		Workbook workbook = WorkbookFactory.create(new File(path));
		if (workbook.getNumberOfSheets() == 0) {
			return;
		}
		for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
			Sheet sheet = workbook.getSheetAt(i);
			if (sheet == null) {
				continue;
			}
			if (EXCEL_HOME_PAGE.equalsIgnoreCase(sheet.getSheetName())) {
				readHomeSheet(sheet);
			} else {
				setTableInfo(sheet);
			}
		}
	}

	/**
	 * 读取首页信息
	 * 
	 * @author menguer@126.com
	 * @time 2019年11月26日 下午7:25:36
	 * @param sheet
	 */
	private void readHomeSheet(Sheet sheet) {
		projectAbbName = getCellValue(sheet.getRow(1).getCell(0));
		projectName = getCellValue(sheet.getRow(1).getCell(1));
		dbName = getCellValue(sheet.getRow(1).getCell(2));
		dbcoding = getCellValue(sheet.getRow(1).getCell(3));
		if (dbcoding == null) {
			dbcoding = "UTF-8";
		}
		dbType = getCellValue(sheet.getRow(3).getCell(0));
		dbUrl = getCellValue(sheet.getRow(3).getCell(1));
		dbUserName = getCellValue(sheet.getRow(3).getCell(2));
		dbPassword = getCellValue(sheet.getRow(3).getCell(3));
	}

	/**
	 * 读取sheet页中的数据，存入List<TableInfo>中
	 * 
	 * @author menguer@126.com
	 * @time 2019年11月10日 下午3:38:44
	 * @param sheet
	 */
	private void setTableInfo(Sheet sheet) {
		List<ColumnInfo> columns = new ArrayList<ColumnInfo>();

		// 第四行开始,注意是小于等于
		for (int i = 3; i <= sheet.getLastRowNum(); i++) {
			Row row = sheet.getRow(i);
			if (row == null) {
				continue;
			}
			String columnJavaName = getCellValue(row.getCell(1));// java中字段名称
			String columnName = getCellValue(row.getCell(2));// 字段名称
			String columnType = getCellValue(row.getCell(3));// 字段类型
			String myBatisType = null;// 对应MYBATIS的JDBC类型
			switch (columnType) {
			case TypeConstant.JAVA_BOOLEAN:
				myBatisType = TypeConstant.MYBATIS_BOOLEAN;
				break;
			case TypeConstant.JAVA_BYTE:
				myBatisType = TypeConstant.MYBATIS_NUMERIC;
				break;
			case TypeConstant.JAVA_CHARACTER:
				myBatisType = TypeConstant.MYBATIS_VARCHAR;
				break;
			case TypeConstant.JAVA_DATE:
				myBatisType = TypeConstant.MYBATIS_TIMESTAMP;
				break;
			case TypeConstant.JAVA_DOUBLE:
				myBatisType = TypeConstant.MYBATIS_NUMERIC;
				break;
			case TypeConstant.JAVA_FLOAT:
				myBatisType = TypeConstant.MYBATIS_NUMERIC;
				break;
			case TypeConstant.JAVA_INTEGER:
				myBatisType = TypeConstant.MYBATIS_NUMERIC;
				break;
			case TypeConstant.JAVA_LONG:
				myBatisType = TypeConstant.MYBATIS_NUMERIC;
				break;
			case TypeConstant.JAVA_SHORT:
				myBatisType = TypeConstant.MYBATIS_NUMERIC;
				break;
			case TypeConstant.JAVA_STRING:
				myBatisType = TypeConstant.MYBATIS_VARCHAR;
				break;
			default:
				break;
			}

			ColumnInfo columnInfo = new ColumnInfo();
			columnInfo.setColumnNumber(getCellValue(row.getCell(0)));
			columnInfo.setColumnJavaName(columnJavaName == null ? underlineToCamel(columnName, true) : columnJavaName);
			columnInfo.setColumnName(columnName);
			columnInfo.setColumnType(columnType);
			columnInfo.setColumnLength(getCellValue(row.getCell(4)));
			columnInfo.setColumnPrecision(getCellValue(row.getCell(5)));
			columnInfo.setColumnIsNull(getCellValue(row.getCell(6)));
			columnInfo.setColumnIsPrimaryKey(getCellValue(row.getCell(7)));
			columnInfo.setColumnComment(getCellValue(row.getCell(8)));
			columnInfo.setColumnDefault(getCellValue(row.getCell(9)));
			columnInfo.setColumnIsLikeQuery(getCellValue(row.getCell(10)));
			columnInfo.setColumnIsWebShow(getCellValue(row.getCell(11)));
			columnInfo.setMyBatisType(myBatisType);
			columns.add(columnInfo);
		}
		Row secRow = sheet.getRow(1);// 第二行
		TableInfo tableInfo = new TableInfo();
		tableInfo.setTableName(getCellValue(secRow.getCell(0)));// 表名
		tableInfo.setTableEngine(getCellValue(secRow.getCell(1)));// 表引擎（MySQL）
		tableInfo.setTableSubproject(getCellValue(secRow.getCell(2)));// 子工程名称
		tableInfo.setTableComment(getCellValue(secRow.getCell(3)));// 表注释
		tableInfo.setTableIsGenerateDB(getCellValue(secRow.getCell(4)));
		tableInfo.setTableIsGenerateCode(getCellValue(secRow.getCell(5)));
		tableInfo.setTableIsGenerateWebCode(getCellValue(secRow.getCell(6)));
		tableInfo.setTableIsAutoIncrement(getCellValue(secRow.getCell(7)));
		tableInfo.setColumns(columns);
		tableInfos.add(tableInfo);
	}

	/**
	 * 带下划线的字符串转驼峰式，smallCamel为true则转为小驼峰，否则为大驼峰
	 * 
	 * @author menguer@126.com
	 * @time 2019年11月26日 下午7:53:27
	 * @param line
	 * @param smallCamel
	 * @return
	 */
	private String underlineToCamel(String line, boolean smallCamel) {
		if (line == null || "".equals(line)) {
			return "";
		}
		StringBuffer sb = new StringBuffer();
		Pattern pattern = Pattern.compile("([A-Za-z\\d]+)(_)?");
		Matcher matcher = pattern.matcher(line);
		while (matcher.find()) {
			String word = matcher.group();
			sb.append(smallCamel && matcher.start() == 0 ? Character.toLowerCase(word.charAt(0)) : Character.toUpperCase(word.charAt(0)));
			int index = word.lastIndexOf('_');
			if (index > 0) {
				sb.append(word.substring(1, index).toLowerCase());
			} else {
				sb.append(word.substring(1).toLowerCase());
			}
		}
		return sb.toString();
	}

	/**
	 * 根据Cell的类型处理值，统一返回String类型
	 * 
	 * @author menguer@126.com
	 * @time 2019年11月27日 下午12:27:02
	 * @param cell
	 * @return
	 */
	private String getCellValue(Cell cell) {
		if (cell == null) {
			return null;
		}
		switch (cell.getCellType()) {
		case _NONE:
			return null;
		case NUMERIC:
			return new DecimalFormat().format(cell.getNumericCellValue()).replace(",", "");
		case STRING:
			return cell.getStringCellValue();
		case FORMULA:
			return null;
		case BLANK:
			return null;
		case BOOLEAN:
			return String.valueOf(cell.getBooleanCellValue());
		case ERROR:
			return null;
		default:
			return null;
		}
	}

	/**
	 * 判断文件夹是否存在，无则新建
	 * 
	 * @author menguer@126.com
	 * @time 2019年11月27日 上午9:16:47
	 * @param folderPath
	 */
	private void createFolder(String folderPath) {
		File folder = new File(folderPath);
		if (!folder.exists()) {
			folder.mkdirs();
			System.out.println("创建文件夹：" + folderPath);
		}
	}

}
