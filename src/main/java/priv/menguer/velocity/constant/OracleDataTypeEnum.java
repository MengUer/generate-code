package priv.menguer.velocity.constant;

/**
 * @description
 * @author menguer@126.com
 * @date 2020-8-30 16:03:38
 * @verifier
 * @check
 * @update
 * @remark
 */
public enum OracleDataTypeEnum {

	CLOB("CLOB"),

	DATE("DATE"),

	FLOAT("FLOAT"),

	SDO_GEOMETRY("SDO_GEOMETRY"),

	VARCHAR2("VARCHAR2"),

	NUMBER("NUMBER");

	private final String dataType;

	OracleDataTypeEnum(String dataType) {
		this.dataType = dataType;
	}

	public String getDataType() {
		return dataType;
	}

}
