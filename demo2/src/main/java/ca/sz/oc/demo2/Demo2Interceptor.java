package ca.sz.oc.demo2;

//import java.net.http.HttpResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.util.Date;

@Component
public class Demo2Interceptor implements HandlerInterceptor {

    @Autowired
    Environment env;

    String logprefix = "    ";

    private final String X_REQUEST_ID1 = "X-Request-ID";
    private final String X_AUTHZ = "Authorization";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {

        String xRequestId1 = request.getHeader(X_REQUEST_ID1);
        String xAuthz = request.getHeader(X_AUTHZ);

        System.out.println( LocalDateTime.now() + ", xRequestId1=" + xRequestId1 + ", xAuthz=" + xAuthz+ ", DemoInterceptor, preHandle>>>>>>>>" + request.getRequestURI());
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
            ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler,
            Exception exception) throws Exception {
    }
}
