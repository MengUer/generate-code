package priv.menguer.velocity.constant;

/**
 * @author menguer@126.com
 * @description
 * @date 2020-8-22 10:01:45
 * @verifier
 * @check
 * @update
 * @remark
 */
public enum DatabaseDriverEnum {
    ORACLE("oracle.jdbc.OracleDriver"),

    MYSQL("com.mysql.cj.jdbc.Driver"),

    DAMENG("dm.jdbc.driver.DmDriver"),

    ;

    private final String value;

    DatabaseDriverEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }

}
