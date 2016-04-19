package com.medlinker.dentist.uac.consumer.exception;

import com.alibaba.fastjson.JSON;
import com.medlinker.dentist.uac.api.exception.MedlinkerException;
import com.medlinker.dentist.uac.consumer.util.MessageSourceUtil;
import com.medlinker.dentist.uac.api.base.model.MsgBean;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.TypeMismatchException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class MedlinkerExceptionHandler implements HandlerExceptionResolver{
	private static Log log = LogFactory.getLog(MedlinkerExceptionHandler.class);
	@Override
	@ResponseBody
	public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception exception) {
		
		MsgBean msg = new MsgBean();
		if(exception instanceof TypeMismatchException){//类型不正确
			TypeMismatchException typeEx=(TypeMismatchException)exception;
			msg.setFailure(1, String.format("参数值[%s]不是正确的类型", typeEx.getValue()));
		}else if(exception instanceof MissingServletRequestParameterException){//没有必传参数
			MissingServletRequestParameterException missingEx=(MissingServletRequestParameterException)exception;
			msg.setFailure(1, String.format("参数[%s]不可以为空", missingEx.getParameterName()));
		}else if(exception instanceof MedlinkerException){
			MedlinkerException ex = (MedlinkerException)exception;
			String errorMsg = MessageSourceUtil.getMessage(ex.getErrCode());
			if(errorMsg == null && !"null".equals(errorMsg)){
				errorMsg = "";
			}
			if(ex.getErrMsg() != null){
				errorMsg += ex.getErrMsg();
			}
			msg.setFailure(ex.getErrCode(), errorMsg);
		}else{
			msg.setFailure(1, exception.getMessage());
			log.error(String.format("【%s】接口异常：%s", request.getRequestURI(),exception.getMessage()),exception);
		}
		try {
			response.setContentType("text/javascript; charset=utf-8");
			response.getWriter().write(JSON.toJSONString(msg.returnMsg()));
			response.flushBuffer();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return new ModelAndView();
	}
}
