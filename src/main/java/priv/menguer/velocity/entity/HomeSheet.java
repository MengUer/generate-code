package priv.menguer.velocity.entity;

/**
 * @description
 * @author menguer@126.com
 * @date 2020-8-30 14:11:20
 * @verifier
 * @check
 * @update
 * @remark
 */
public class HomeSheet {
	/**
	 * 工程英文简称
	 */
	private String projectAbbName;

	/**
	 * 工程中文名称
	 */
	private String projectName;

	/**
	 * 数据库名称
	 */
	private String dbName;

	/**
	 * 数据库编码
	 */
	private String dbcoding;

	/**
	 * 数据库类型
	 */
	private String dbType;

	/**
	 * 数据库地址
	 */
	private String dbUrl;

	/**
	 * 数据库用户名
	 */
	private String dbUserName;

	/**
	 * 数据库密码
	 */
	private String dbPassword;

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("{\"projectAbbName\":\"").append(projectAbbName).append("\", \"projectName\":\"")
				.append(projectName).append("\", \"dbName\":\"").append(dbName).append("\", \"dbcoding\":\"")
				.append(dbcoding).append("\", \"dbType\":\"").append(dbType).append("\", \"dbUrl\":\"").append(dbUrl)
				.append("\", \"dbUserName\":\"").append(dbUserName).append("\", \"dbPassword\":\"").append(dbPassword)
				.append("\"}");
		return builder.toString();
	}

	public String getProjectAbbName() {
		return projectAbbName;
	}

	public void setProjectAbbName(String projectAbbName) {
		this.projectAbbName = projectAbbName;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getDbName() {
		return dbName;
	}

	public void setDbName(String dbName) {
		this.dbName = dbName;
	}

	public String getDbcoding() {
		return dbcoding;
	}

	public void setDbcoding(String dbcoding) {
		this.dbcoding = dbcoding;
	}

	public String getDbType() {
		return dbType;
	}

	public void setDbType(String dbType) {
		this.dbType = dbType;
	}

	public String getDbUrl() {
		return dbUrl;
	}

	public void setDbUrl(String dbUrl) {
		this.dbUrl = dbUrl;
	}

	public String getDbUserName() {
		return dbUserName;
	}

	public void setDbUserName(String dbUserName) {
		this.dbUserName = dbUserName;
	}

	public String getDbPassword() {
		return dbPassword;
	}

	public void setDbPassword(String dbPassword) {
		this.dbPassword = dbPassword;
	}

}
