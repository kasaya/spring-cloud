package com.oycl.common.base;

/**
 * 基础MODEL
 */
public class BaseModel {
	/** 新增日期时间 */
	private String createDateTime;

	/** 更新日期时间 */
	private String lastUpdateTime;


	public String getCreateDateTime() {
		return createDateTime;
	}

	public void setCreateDateTime(String createDateTime) {
		this.createDateTime = createDateTime;
	}

	public String getLastUpdateTime() {
		return lastUpdateTime;
	}

	public void setLastUpdateTime(String lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}

}
