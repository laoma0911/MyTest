package com.medlinker.dentist.uac.api.exception;


@SuppressWarnings("serial")
public class UACException extends MedlinkerException {
	public UACException() {
	}

	public UACException(String code) {
		super(code);
	}

	public UACException(String code, String errMsg) {
		super(code, errMsg);
	}

	/**
	 * 创建提示异常
	 * 
	 * @param errMsg
	 *            错误提示信息
	 * @return
	 */
	public static UACException newInstance(String errMsg) {
		UACException exception = new UACException("1", errMsg);
		return exception;
	}
}
