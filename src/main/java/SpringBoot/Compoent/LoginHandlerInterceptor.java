package SpringBoot.Compoent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 登陆拦截器，没有进行登陆就不能进行一些用户操作
 */
public class LoginHandlerInterceptor implements HandlerInterceptor {
    // 日志
    private Logger logger = LoggerFactory.getLogger(LoginHandlerInterceptor.class);

    // 预先检查
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Object user = request.getSession().getAttribute("loginUser");
        if (user == null) {
            // 没有登陆
            request.setAttribute("message", "你没有登陆");
            request.getRequestDispatcher("/").forward(request, response);

            return false;
        } else {
            // 已经登陆
            return true;
        }
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        logger.info("postHandle...");
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        logger.info("afterCompletion...");
    }
}
