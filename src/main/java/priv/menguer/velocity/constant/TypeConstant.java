package priv.menguer.velocity.constant;

/**
 * @package priv.menguer.velocity.constant
 * @file TypeConstant.java
 * @description
 * @author menguer@126.com
 * @date 2020年7月23日 下午8:39:41
 * @verifier
 * @check
 * @update
 * @remark
 */
public class TypeConstant {
	/*
	 * 8中基本数据类型的包装类和Date/String,与excel中的字段类型保持一致
	 */
	public static final String JAVA_BOOLEAN = "Boolean";
	public static final String JAVA_BYTE = "Byte";
	public static final String JAVA_CHARACTER = "Character";
	public static final String JAVA_DATE = "Date";
	public static final String JAVA_DOUBLE = "Double";
	public static final String JAVA_FLOAT = "Float";
	public static final String JAVA_INTEGER = "Integer";
	public static final String JAVA_LONG = "Long";
	public static final String JAVA_SHORT = "Short";
	public static final String JAVA_STRING = "String";

	/*
	 * mybatis中的jdbc类型
	 */
	public static final String MYBATIS_NUMERIC = "NUMERIC";
	public static final String MYBATIS_TIMESTAMP = "TIMESTAMP";
	public static final String MYBATIS_VARCHAR = "VARCHAR";
	public static final String MYBATIS_BOOLEAN = "BOOLEAN";
	public static final String MYBATIS_CLOB = "CLOB";

	/*
	 * oracle中的数据类型
	 */
	public static final String ORACLE_CLOB = "CLOB";
	public static final String ORACLE_DATE = "DATE";
	public static final String ORACLE_FLOAT = "FLOAT";
	public static final String ORACLE_SDO_GEOMETRY = "SDO_GEOMETRY";
	public static final String ORACLE_VARCHAR2 = "VARCHAR2";
	public static final String ORACLE_NUMBER = "NUMBER";
}
