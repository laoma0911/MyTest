package com.medlinker.dentist.provider.aop;


import com.medlinker.dentist.exception.DentistException;
import com.medlinker.dentist.uac.api.exception.MedlinkerException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class DentistExceptionAspect {
    private static Log log = LogFactory.getLog(DentistExceptionAspect.class);

    public void afterThrowing(Exception ex) {

        if (ex instanceof DentistException) {
            throw (DentistException) ex;
        } else if (ex instanceof MedlinkerException) {
            MedlinkerException de = (MedlinkerException) ex;
            throw new DentistException(de.getErrCode(), de.getErrMsg());
        } else {
            log.error(ex.getMessage(), ex);
            throw new DentistException("1", ex.getMessage());
        }

    }
}
