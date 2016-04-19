package com.medlinker.dentist.app.filter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * medlinker uac拦截器
 *
 * @author mazb
 */
public class MedlinkerConsumerFilter implements HandlerInterceptor {
    private Log log = LogFactory.getLog(MedlinkerConsumerFilter.class);


//    @Autowired
//    private UserInfoService userInfoService;

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler) throws Exception {
        log.debug("进入spring mvc拦截器");
        /* 1.检测用户Token */
//        try {
//            boolean checkUserTokenResult = this.checkUserToken(request);
//            if (!checkUserTokenResult) {
//                log.error("token验证失败");
//                throw new UACException("10012");
//            }
//        } catch (UACException uacEx) {
//            String[] tokens = request.getParameterValues("token");
//            if (tokens == null || tokens.length == 0) {
//                log.error("token验证失败,没有接到token");
//            } else {
//                for (String token : tokens) {
//                    if (token.contains("-")) {
//                        log.error(String.format("token验证失败，值为[%s]", token));
//                    }
//                }
//            }
//            throw uacEx;
//        } catch (Exception e) {
//            //uac-provider系统不可用
//            throw new UACException("10002");
//        }
//        log.debug("通过spring mvc拦截器的验证，走好……");
        System.out.println("=====================走你");
        return true;
    }

    /**
     * 检查用户token
     *
     * @param request
     * @return
     */
    private boolean checkUserToken(HttpServletRequest request) {
        /* 1.判断是否需要验证token */
//        String uri = getContextPath(request);
//        if (FilterMethods.isNoLoginFilter(uri)) {
//            return true;
//        }
//        log.debug("相对路径【" + uri + "】需要登陆拦截");
//        /* 2.获取token */
//        String[] tokens = request.getParameterValues("token");
//        if (tokens == null || tokens.length == 0) throw new UACException("10010");
//        String token = null;
//        for (String t : tokens) {
//            if (t.contains("-")) {
//                token = t;
//                break;
//            }
//        }
//        if (token == null || "".equals(token)) {
//            throw new UACException("10012");
//        }
//        return userInfoService.checkIsOnline(token);
        return Boolean.TRUE;
    }


    /**
     * 获取请求的相对的url
     *
     * @param request
     * @return
     */
    private String getContextPath(HttpServletRequest request) {
        String uri = request.getServletPath();
        String pathInfo = request.getPathInfo();
        if (pathInfo != null && pathInfo.length() > 0) {
            uri = uri + pathInfo;
        }
        return uri;
    }

    @Override
    public void postHandle(HttpServletRequest request,
                           HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request,
                                HttpServletResponse response, Object handler, Exception ex)
            throws Exception {

    }
}
