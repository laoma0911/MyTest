package com.medlinker.dentist.uac.consumer.util;

import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.context.support.MessageSourceAccessor;

public class MessageSourceUtil  implements MessageSourceAware{

	private static MessageSourceAccessor accessor;
	@Override
	public void setMessageSource(MessageSource messageSource) {
		accessor = new MessageSourceAccessor(messageSource);
	}
	
	public static String getMessage(String code){
		return accessor.getMessage(code, null, null, null);
	}

}
