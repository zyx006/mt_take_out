package cn.czyx007.mt.config;

import cn.czyx007.mt.common.JacksonObjectMapper;
import cn.czyx007.mt.interceptor.LoginInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Arrays;
import java.util.List;

/**
 * @author : 张宇轩
 * @createTime : 2023/5/29 - 15:28
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    //添加拦截器
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //定义不被拦截的路径
        List<String> list = Arrays.asList(
                "/employee/login", "/employee/logout",
                "/user/sendMsg", "/user/login", "/user/loginout",
                "/backend/**", "/front/**");
        registry.addInterceptor(new LoginInterceptor())
                .addPathPatterns("/**").excludePathPatterns(list);
    }

    @Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        MappingJackson2HttpMessageConverter messageConverter = new MappingJackson2HttpMessageConverter();
        messageConverter.setObjectMapper(new JacksonObjectMapper());
        converters.add(0, messageConverter);
    }
}
