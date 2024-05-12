package priv.menguer.velocity.constant;

/**
 * @package priv.menguer.velocity.constant
 * @file TemplateEnum.java
 * @description
 * @author menguer@126.com
 * @date 2020-7-24 23:25:44
 * @verifier
 * @check
 * @update
 * @remark
 */
public enum TemplateEnum {
	ENTITY("entity"),

	DAO("dao"),

	SERVICE("service"),

	SERVICEIMPL("serviceImpl"),

	CONTROLLER("controller"),

	MAPPER("mapper");

	private final String value;

	TemplateEnum(String value) {
		this.value = value;
	}

	public String getValue() {
		return this.value;
	}

}
