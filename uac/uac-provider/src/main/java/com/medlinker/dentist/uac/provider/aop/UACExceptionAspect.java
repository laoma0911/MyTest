package com.medlinker.dentist.uac.provider.aop;

import com.medlinker.dentist.uac.api.exception.UACException;
import com.medlinker.dentist.uac.api.exception.MedlinkerException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class UACExceptionAspect {
	private static Log log = LogFactory.getLog(UACExceptionAspect.class);

	public void afterThrowing(Exception ex) {

		if (ex instanceof UACException) {
			throw (UACException) ex;
		} else if (ex instanceof MedlinkerException) {
			MedlinkerException de = (MedlinkerException) ex;
			throw new UACException(de.getErrCode(), de.getErrMsg());
		} else {
			log.error(ex.getMessage(), ex);
			throw new UACException("1", ex.getMessage());
		}

	}
}
