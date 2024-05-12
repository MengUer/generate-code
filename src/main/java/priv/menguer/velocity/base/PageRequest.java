package priv.menguer.velocity.base;

import java.io.Serializable;

/**
 * 分页请求控制
 */
public class PageRequest implements Serializable {
	/**
	 * 起始数据角标
	 */
	private Integer startIndex;

	/**
	 * 结束数据角标
	 */
	private Integer endIndex;

	/**
	 * 排序参数，默认为gid
	 */
	private String orderBy = "GID";

	/**
	 * 排序条件，默认为降序
	 */
	private String order = "DESC";

	/**
	 * 开始时间
	 */
	private String startTime;

	/**
	 * 结束时间
	 */
	private String endTime;

	/**
	 * 携带token
	 */
	private String requestToken;

	public PageRequest() {
	}

	public PageRequest(Integer startIndex, Integer endIndex, String orderBy, String order, String startTime, String endTime,
			String requestToken) {
		this.startIndex = startIndex;
		this.endIndex = endIndex;
		this.orderBy = orderBy;
		this.order = order;
		this.startTime = startTime;
		this.endTime = endTime;
		this.requestToken = requestToken;
	}

	public Integer getStartIndex() {
		return startIndex;
	}

	public void setStartIndex(Integer startIndex) {
		this.startIndex = startIndex;
	}

	public Integer getEndIndex() {
		return endIndex;
	}

	public void setEndIndex(Integer endIndex) {
		this.endIndex = endIndex;
	}

	public String getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getRequestToken() {
		return requestToken;
	}

	public void setRequestToken(String requestToken) {
		this.requestToken = requestToken;
	}
}
