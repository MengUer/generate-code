package priv.menguer.velocity.service.impl;

import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import priv.menguer.commons.core.util.StrUtils;
import priv.menguer.velocity.config.GenConfig;
import priv.menguer.velocity.constant.TypeConstant;
import priv.menguer.velocity.entity.AllTabColumns;
import priv.menguer.velocity.entity.ColumnInfo;
import priv.menguer.velocity.entity.TableInfo;
import priv.menguer.velocity.service.AbstracService;

/**
 * @description
 * @author menguer@126.com
 * @date 2020-8-30 13:57:39
 * @verifier
 * @check
 * @update
 * @remark
 */
@Deprecated
public class TableInfoServiceImpl extends AbstracService {

	public List<TableInfo> geTableInfoList() throws EncryptedDocumentException, IOException {
		List<TableInfo> tableInfos = new ArrayList<TableInfo>();
		switch (GenConfig.databaseType) {
		case "1":
			geTableInfoByDatabase(tableInfos);
			break;
		case "2":
			geTableInfoByExcel(tableInfos);
			break;
		case "3":
			geTableInfoByDatabase(tableInfos);
			geTableInfoByExcel(tableInfos);
			break;
		default:
			break;
		}

		if (tableInfos.isEmpty()) {
			return null;
		}
		return tableInfos;
	}

	/**
	 * 通过数据库获取表详情
	 * 
	 * @author menguer@126.com
	 * @time 2020-8-30 14:05:34
	 */
	private void geTableInfoByDatabase(List<TableInfo> tableInfos) {
		List<AllTabColumns> list = new AllTabColumnsServiceImpl().queryByParam(GenConfig.databaseSchema, GenConfig.databaseTables);
		Map<String, TableInfo> map = new HashMap<>();

		for (int i = 0; i < list.size(); i++) {
			AllTabColumns entity = list.get(i);
			String tableName = entity.getTableName();

			TableInfo tableInfo = null;
			boolean hasExist = map.containsKey(tableName);
			if (hasExist) {
				tableInfo = map.get(tableName);
				setColumnInfo(entity, tableInfo.getColumns());
			} else {
				List<ColumnInfo> columns = new ArrayList<>();
				setColumnInfo(entity, columns);

				tableInfo = new TableInfo();
				tableInfo.setTableName(tableName);
				tableInfo.setTableIsGenerateCode(BOOLEAN_YES);
				tableInfo.setColumns(columns);
				map.put(tableName, tableInfo);
			}
		}

		for (Entry<String, TableInfo> entry : map.entrySet()) {
			tableInfos.add(entry.getValue());
		}
	}

	private void setColumnInfo(AllTabColumns allTabColumns, List<ColumnInfo> columns) {
		String columnName = allTabColumns.getColumnName();
		String dataType = allTabColumns.getDataType();

		String myBatisType = null;// 对应MYBATIS的JDBC类型
		switch (dataType) {
		case TypeConstant.ORACLE_NUMBER:
			myBatisType = TypeConstant.MYBATIS_NUMERIC;
			break;
		case TypeConstant.ORACLE_VARCHAR2:
			myBatisType = TypeConstant.MYBATIS_VARCHAR;
			break;
		case TypeConstant.ORACLE_DATE:
			myBatisType = TypeConstant.MYBATIS_TIMESTAMP;
			break;
		case TypeConstant.ORACLE_CLOB:
			myBatisType = TypeConstant.MYBATIS_CLOB;
			break;
		case TypeConstant.ORACLE_FLOAT:
			myBatisType = TypeConstant.MYBATIS_NUMERIC;
			break;
		case TypeConstant.ORACLE_SDO_GEOMETRY:
			myBatisType = TypeConstant.MYBATIS_VARCHAR;
			break;
		default:
			myBatisType = TypeConstant.MYBATIS_VARCHAR;
			break;
		}
		ColumnInfo columnInfo = new ColumnInfo();
		columnInfo.setColumnJavaName(StrUtils.underlineToCamel(columnName.toLowerCase(), true));
		columnInfo.setColumnName(columnName);
		columnInfo.setColumnType(dataType);
		columnInfo.setMyBatisType(myBatisType);
		columns.add(columnInfo);
	}

	/**
	 * excel中首页的sheet名称
	 */
	private static final String EXCEL_HOME_PAGE = "HOME";

	/**
	 * 通过excel获取表详情
	 * 
	 * @author menguer@126.com
	 * @throws IOException
	 * @throws EncryptedDocumentException
	 * @time 2020-8-30 14:05:14
	 */
	private void geTableInfoByExcel(List<TableInfo> tableInfos) throws EncryptedDocumentException, IOException {
		Workbook workbook = WorkbookFactory.create(new File(GenConfig.databaseInfoFilePath));
		if (workbook.getNumberOfSheets() == 0) {
			return;
		}
		for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
			Sheet sheet = workbook.getSheetAt(i);
			if (sheet == null) {
				continue;
			}
			if (EXCEL_HOME_PAGE.equalsIgnoreCase(sheet.getSheetName())) {
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
				// HomeSheet entity = new HomeSheet();
				// entity.setProjectAbbName(getCellValue(sheet.getRow(1).getCell(0)));
				// entity.setProjectName(getCellValue(sheet.getRow(1).getCell(1)));
				// entity.setDbName(getCellValue(sheet.getRow(1).getCell(2)));
				// String dbcoding = getCellValue(sheet.getRow(1).getCell(3));
				// if (dbcoding == null) {
				// dbcoding = "UTF-8";
				// }
				// entity.setDbcoding(dbcoding);
				// entity.setDbType(getCellValue(sheet.getRow(3).getCell(0)));
				// entity.setDbUrl(getCellValue(sheet.getRow(3).getCell(1)));
				// entity.setDbUserName(getCellValue(sheet.getRow(3).getCell(2)));
				// entity.setDbPassword(getCellValue(sheet.getRow(3).getCell(3)));
				// return entity;
			} else {
				setTableInfo(sheet, tableInfos);
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
		// HomeSheet entity = new HomeSheet();
		// entity.setProjectAbbName(getCellValue(sheet.getRow(1).getCell(0)));
		// entity.setProjectName(getCellValue(sheet.getRow(1).getCell(1)));
		// entity.setDbName(getCellValue(sheet.getRow(1).getCell(2)));
		// String dbcoding = getCellValue(sheet.getRow(1).getCell(3));
		// if (dbcoding == null) {
		// dbcoding = "UTF-8";
		// }
		// entity.setDbcoding(dbcoding);
		// entity.setDbType(getCellValue(sheet.getRow(3).getCell(0)));
		// entity.setDbUrl(getCellValue(sheet.getRow(3).getCell(1)));
		// entity.setDbUserName(getCellValue(sheet.getRow(3).getCell(2)));
		// entity.setDbPassword(getCellValue(sheet.getRow(3).getCell(3)));
		// return entity;
	}

	/**
	 * 读取sheet页中的数据，存入List<TableInfo>中
	 * 
	 * @author menguer@126.com
	 * @time 2019年11月10日 下午3:38:44
	 * @param sheet
	 */
	private void setTableInfo(Sheet sheet, List<TableInfo> tableInfos) {
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
			columnInfo.setColumnJavaName(columnJavaName == null ? StrUtils.underlineToCamel(columnName, true) : columnJavaName);
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
}
