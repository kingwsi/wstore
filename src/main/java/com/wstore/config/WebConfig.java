package com.wstore.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;

/**
 * @ClassName WebConfig
 * @Author Koi
 * @Date 2018/8/10 18:02
 * @Version 1.0
 */
@Configuration
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**")
                .addResourceLocations("classpath:/META-INF/resources/")
                .addResourceLocations("classpath:/resources/")
                .addResourceLocations("classpath:/static/")
                .addResourceLocations("classpath:/public/");
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("index");
        registry.addViewController("/index.html").setViewName("index");
        registry.addViewController("/login").setViewName("login");
        registry.addViewController("/product.html").setViewName("edit-product");
    }

    /*@Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new HandlerInterceptor(){
            @Override
            public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

                String uid = CookieUtils.getCookieValue(request, "uid");
                if (uid != null){
                    if(redisService.checkValueByKey(ADMIN_LOGIN_KEY + uid)){
                        redisService.delayByKey(ADMIN_LOGIN_KEY + uid, ADMIN_TIME_OUT);
                        return true;
                    }
                }
                request.getRequestDispatcher("/login").forward(request,response);
                return false;
            }
        }).addPathPatterns("/**").excludePathPatterns("/login","/admin","/assets/**");
    }*/
}
