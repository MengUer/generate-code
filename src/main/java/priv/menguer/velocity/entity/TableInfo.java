package priv.menguer.velocity.entity;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

/**
 * @author menguer@126.com
 * @date 2019年11月8日 下午7:35:46
 * @version 1.0.0
 * @description
 */
@Data
public class TableInfo implements Serializable {
	private static final long serialVersionUID = -7476671788839043555L;

	private String owner;// 模式名
	private String tableName;// 表名 英文
	private String tableComment;// 表定义

	private String tableEngine;// 表引擎;MyISAM(无事务,查询慢,插入极快);InnoDB(有事务,查询快,插入慢)
	private String tableSubproject;// 该表所属的子工程
	private String tableIsGenerateDB;// 是否生成数据库结构
	private String tableIsGenerateCode;// 是否生成java代码
	private String tableIsGenerateWebCode;// 是否生成页面代码
	private String tableIsAutoIncrement;// 是否主键自增
	private List<ColumnInfo> columns;// 表字段信息集合

	public TableInfo() {
	}

	public TableInfo(String owner, String tableName, String tableComment, List<ColumnInfo> columns) {
		super();
		this.owner = owner;
		this.tableName = tableName;
		this.tableComment = tableComment;
		this.columns = columns;
	}
}
