package ca.sz.oc.demo1;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
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
        log.info("DemoInterceptor, preHandle");
        
        if (! letPass()) {
            log.info("DemoInterceptor, perHandle. letPass=false");
            // throw new Exception("letPass=false");
            // response.setStatus(503);
            response.sendError(HttpStatus.SERVICE_UNAVAILABLE.value(), HttpStatus.SERVICE_UNAVAILABLE.getReasonPhrase());
            return false;
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
            // if (Demo1Application.healthy == null) {
            //     Demo1Application.healthy = ret;
            //     log.info("letPass healthy={}", Demo1Application.healthy);
            // }
            // log.info("Simple log statement with inputs {}, {} and {}", 1, 2, 3);
            log.info("v-50-50-unhealthy random={} health={}", (int) random, ret);
        }

        if (Demo1Application.healthy!=null && Demo1Application.healthy) {
            ret = true;
            log.info("v-50-50-unhealthy healthy always");
        }
            
        return ret;
    }

}