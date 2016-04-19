package com.medlinker.dentist.exception;

import com.medlinker.dentist.uac.api.exception.MedlinkerException;

@SuppressWarnings("serial")
public class DentistException extends MedlinkerException {
    public DentistException() {
    }

    public DentistException(String code) {
        super(code);
    }

    public DentistException(String code, String errMsg) {
        super(code, errMsg);
    }

    /**
     * 创建提示异常
     *
     * @param errMsg 错误提示信息
     * @return
     */
    public static DentistException newInstance(String errMsg) {
        DentistException exception = new DentistException("1", errMsg);
        return exception;
    }
}
