package com.wstore.order.config;

import com.wstore.common.utils.CookieUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.concurrent.TimeUnit;

/**
 * 登录拦截器
 *
 * @ClassName OrderHandler
 * @Author wangshu
 * @Date 18-9-25 上午10:55
 * @Version 1.0
 */
@Configuration
public class OrderHandler implements WebMvcConfigurer {

    @Value("${login.href}")
    private String LOGIN_HREF;

    @Autowired
    StringRedisTemplate stringRedisTemplate;

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
        registry.addViewController("/ordercenter").setViewName("order-center");
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new HandlerInterceptor() {
            @Override
            public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
                String uid = CookieUtils.getCookieValue(request, "UID");
                String url = "http://" + request.getServerName() //服务器地址
                        + ":"
                        + request.getServerPort()           //端口号
                        + request.getContextPath()      //项目名称
                        + request.getServletPath();      //请求页面或其他地址
                url = java.net.URLEncoder.encode(url, "UTF-8");
                if (uid == null) {
                    response.sendRedirect(LOGIN_HREF + "?href="+url);
                    return false;
                }
                if (stringRedisTemplate.opsForValue().get(uid) != null) {
                    stringRedisTemplate.expire(uid,30, TimeUnit.MINUTES);
                    return true;
                }else {
                    response.sendRedirect(LOGIN_HREF + "?href=" + url);
                    return false;
                }
            }
        }).addPathPatterns("/**").excludePathPatterns(LOGIN_HREF, "/error","/test/**","/pay/notify");
    }
}
