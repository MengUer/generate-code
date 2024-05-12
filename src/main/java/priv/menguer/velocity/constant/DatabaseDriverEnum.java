package priv.menguer.velocity.constant;

/**
 * @description
 * @author menguer@126.com
 * @date 2020-8-22 10:01:45
 * @verifier
 * @check
 * @update
 * @remark
 */
public enum DatabaseDriverEnum {
	ORACLE("oracle.jdbc.OracleDriver"),

	MYSQL("");

	private final String value;

	DatabaseDriverEnum(String value) {
		this.value = value;
	}

	public String getValue() {
		return this.value;
	}

}
