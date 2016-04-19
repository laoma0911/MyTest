package com.medlinker.dentist.uac.api.exception;


public class MedlinkerException extends RuntimeException{
	private static final long serialVersionUID = -2084401215148116454L;
	private String errCode;
	private String errMsg;
	public MedlinkerException() {
	}

	public MedlinkerException(String code) {
		super();
		this.errCode = code;
	}
	public MedlinkerException(String code, String errMsg) {
		super(errMsg);
		this.errCode = code;
		this.errMsg = errMsg;
	}
	public String getErrCode() {
		return errCode;
	}
	
	public String getErrMsg(){
		return errMsg;
	}
}
