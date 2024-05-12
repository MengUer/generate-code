package priv.menguer.velocity.entity;

import java.io.Serializable;

import lombok.Data;

/**
 * @author menguer@126.com
 * @date 2019年11月8日 下午7:20:58
 * @version 1.0.0
 * @description
 */
@Data
public class ColumnInfo implements Serializable {
	private static final long serialVersionUID = -3739267277168640451L;

	private String columnName;// 字段名称
	private String columnType;// 字段类型,excel中存的是java数据类型,但要经过判断转成数据库的数据类型
	private String columnJavaName;// java中字段名称
	private String columnComment;// 注释
	private String columnIsPrimaryKey;// 是否为主键
	private String columnIsUniqueKey;// 是否为唯一键

	private String columnNumber;// 字段编号
	private String columnLength;// 字段长度
	private String columnPrecision;// 字段精度
	private String columnIsNull;// 是否为空
	private String columnDefault;// 默认值
	private String columnIsLikeQuery;// 是否模糊查询
	private String columnIsWebShow;// 是否在页面展示
	private String myBatisType;// 对应MYBATIS的JDBC类型

	public ColumnInfo() {
	}

	public ColumnInfo(String columnName, String columnType, String columnJavaName, String columnComment, String columnIsPrimaryKey,
			String columnIsUniqueKey) {
		super();
		this.columnName = columnName;
		this.columnType = columnType;
		this.columnJavaName = columnJavaName;
		this.columnComment = columnComment;
		this.columnIsPrimaryKey = columnIsPrimaryKey;
		this.columnIsUniqueKey = columnIsUniqueKey;
	}
}
