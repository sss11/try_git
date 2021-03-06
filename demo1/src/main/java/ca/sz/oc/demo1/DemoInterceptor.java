package ca.sz.oc.demo1;

import java.net.http.HttpResponse;

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

    String logprefix = "    ";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        log.info("DemoInterceptor, preHandle>>>>>>>>");

        if (!letPass()) {
            returnError(response);
            return false;
        }

        log.info("    <<<<<<<<DemoInterceptor, perHandle. return 200");
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
        log.info("    letPass>>>>>>>>>");
        boolean ret = true;

        if ("v-50-50-unhealthy".equalsIgnoreCase(env.getProperty("SERVICE_VERSION"))) {
            double random = Math.random() * 100;

            ret = random > 50 ? true : false;
            log.info("        v-50-50-unhealthy: random={} ret={}", (int) random, ret);
        }

        if ("v-50-50-unhealthy-always".equalsIgnoreCase(env.getProperty("SERVICE_VERSION"))) {
            ret = Demo1Application.healthy;
            log.info("        v-50-50-unhealthy-always: ret={}", ret);
        }

        if ("v-timeout-first-call".equalsIgnoreCase(env.getProperty("SERVICE_VERSION"))) {
            
            int errorCount = 1;
            if (env.getProperty("ERROR_COUNT")!=null) {
                errorCount = Integer.parseInt(env.getProperty("ERROR_COUNT"));
                log.info("        ERROR_COUNT={}", errorCount);
            }

            int sleepSec=30;
            if (env.getProperty("SLEEP_SECOND")!=null) {
                sleepSec = Integer.parseInt(env.getProperty("SLEEP_SECOND"));
                log.info("        SLEEP_SECOND={}", sleepSec);
            }

            if (Demo1Application.count < errorCount) {
                Demo1Application.count++;

                try {
                    Thread.sleep(sleepSec * 1000);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                ret = false;// use together with 500 error
            }
        }

        if ("v-slow-call".equalsIgnoreCase(env.getProperty("SERVICE_VERSION"))) {
            
            int minSecond = 1;
            if (env.getProperty("MIN_MS")!=null) {
                minSecond = Integer.parseInt(env.getProperty("MIN_MS"));
                log.info("        MIN_MS={}", minSecond);
            }

            int maxSecond = 1;
            if (env.getProperty("MAX_MS")!=null) {
                maxSecond = Integer.parseInt(env.getProperty("MAX_MS"));
                log.info("        MAX_MS={}", maxSecond);
            }
            
            int sleepMs=30;
            sleepMs = minSecond + (int)((maxSecond - minSecond)*Math.random());

            log.info("        v-slow-call: sleepMs={}", sleepMs);
            
            try {
                Thread.sleep(sleepMs);
            } catch (Exception e) {
                e.printStackTrace();
            }
            ret = true;

        }

        log.info("    <<<<<<<<letPass={}", ret);
        return ret;
    }

    private void returnError(HttpServletResponse response) throws Exception {
        log.info("    returnError>>>>>>>>>");

        if ("500".equalsIgnoreCase(env.getProperty("SERVER_ERRORCODE"))) {
            // 500
            log.info("        return 500");
            throw new Exception("500");
        }

        if ("503".equalsIgnoreCase(env.getProperty("SERVER_ERRORCODE"))) {
            // response.setStatus(503);
            log.info("        return 503");
            response.sendError(HttpStatus.SERVICE_UNAVAILABLE.value(),
                    HttpStatus.SERVICE_UNAVAILABLE.getReasonPhrase());
            return;
        }

        if (null != env.getProperty("SERVER_ERRORCODE")) {
            int err = Integer.parseInt(env.getProperty("SERVER_ERRORCODE"));
            if (err > 0) {
                log.info("        return {}", err);
                response.sendError(err, "SERVER_ERRORCODE=" + err);
            }
            return;
        }
    }

}