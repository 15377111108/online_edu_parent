package com.atguigu.config;

import com.baomidou.mybatisplus.core.injector.ISqlInjector;
import com.baomidou.mybatisplus.extension.injector.LogicSqlInjector;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.security.auth.kerberos.DelegationPermission;

/**
 * Date:2022/6/28
 *
 * @author:yz
 */
@Configuration
public class MybatisPlusConfig {
    /**
     * 分页插件
     * @return
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }

    /**
     * 逻辑删除
     * @return
     */
   @Bean
    public ISqlInjector sqlInjector(){
        return new LogicSqlInjector();
   }
}
