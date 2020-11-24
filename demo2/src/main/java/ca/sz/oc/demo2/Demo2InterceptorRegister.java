package ca.sz.oc.demo2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Component
public class Demo2InterceptorRegister extends WebMvcConfigurerAdapter {
    @Autowired
    Demo2Interceptor demo2Interceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(demo2Interceptor);
    }
}
