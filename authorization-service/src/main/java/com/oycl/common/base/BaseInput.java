package com.oycl.common.base;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * @author cango
 */
public class BaseInput {

	/**
     * 签名
     */
	@NotNull
	@NotEmpty
	private String sign;


	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}
}
