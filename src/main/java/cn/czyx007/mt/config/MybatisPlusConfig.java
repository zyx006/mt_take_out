package cn.czyx007.mt.config;

import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author : 张宇轩
 * @createTime : 2023/5/30 - 10:42
 */
//这个类是MybatisPlus的配置类
@Configuration //该注解表示这个类是一个配置类。
public class MybatisPlusConfig {

    @Bean //该注解加在方法上，表示该方法返回的实例交给spring管理
    public MybatisPlusInterceptor mybatisPlusInterceptor(){
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor());
        return interceptor;
    }
}