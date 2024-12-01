package priv.menguer.velocity.mapper;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import priv.menguer.velocity.config.GenConfig;

/**
 * @description
 * @author menguer@126.com
 * @date 2023年12月26日 下午9:57:47
 * @verifier
 * @check
 * @update
 * @remark
 */
public abstract class AbstractMapper {

	private Connection connect;
	private Statement statement;

	protected ResultSet getResultSet(String sql) {
		initConnection();
		initStatement();
		try {
			return statement.executeQuery(sql);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	private void initConnection() {
		try {
			connect = DriverManager.getConnection(GenConfig.url, GenConfig.user, GenConfig.password);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void initStatement() {
		try {
			statement = connect.createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	protected void close() {
		if (statement != null) {
			try {
				statement.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if (connect != null) {
			try {
				connect.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
