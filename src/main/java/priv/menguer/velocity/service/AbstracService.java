package priv.menguer.velocity.service;

import java.io.InputStream;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

/**
 * @description
 * @author menguer@126.com
 * @date 2020-8-30 10:48:33
 * @verifier
 * @check
 * @update
 * @remark
 */
public abstract class AbstracService {
	/*
	 * 判断值
	 */
	public static final String BOOLEAN_YES = "YES";

	public static final String BOOLEAN_NO = "NO";

	/**
	 * 工程英文简称
	 */
	public static String projectAbbName;

	/**
	 * 工程中文名称
	 */
	public static String projectName;

	/**
	 * 数据库名称
	 */
	public static String dbName;

	/**
	 * 数据库编码
	 */
	public static String dbcoding;

	/**
	 * 数据库类型
	 */
	public static String dbType;

	/**
	 * 数据库地址
	 */
	public static String dbUrl;

	/**
	 * 数据库用户名
	 */
	public static String dbUserName;

	/**
	 * 数据库密码
	 */
	public static String dbPassword;

	private SqlSession sqlSession;

	protected SqlSession getSqlSession() {
		sqlSession = Singleton.INSTANCE.getInstance().openSession();
		return sqlSession;
	}

	protected void closeSqlSession() {
		if (sqlSession != null) {
			sqlSession.close();
		}
	}

	private enum Singleton {
		INSTANCE;

		private SqlSessionFactory instance;

		private Singleton() {
			String resource = "mybatis-config.xml";
			try {
				InputStream inputStream = Resources.getResourceAsStream(resource);
				instance = new SqlSessionFactoryBuilder().build(inputStream);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		public SqlSessionFactory getInstance() {
			return instance;
		}
	}
}
