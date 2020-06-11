package ca.sz.oc.demo1;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class DemoInterceptor implements HandlerInterceptor {

    @Autowired
    Environment env;
    
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        log.info("DemoInterceptor, perHandle");
        
        if (! letPass()) {
            log.info("DemoInterceptor, perHandle. letPass=false");
            throw new Exception("letPass=false");
        }
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

    private boolean letPass() {
        boolean ret = true;

        if ("v-50-50-unhealthy".equalsIgnoreCase(env.getProperty("SERVICE_VERSION"))) {
            double random = Math.random() * 100;
            ret = random > 50 ? true : false;
            // log.info("Simple log statement with inputs {}, {} and {}", 1, 2, 3);
            log.info("v-50-50-unhealthy random={} health={}", (int) random, ret);
        }
        return ret;
    }

}