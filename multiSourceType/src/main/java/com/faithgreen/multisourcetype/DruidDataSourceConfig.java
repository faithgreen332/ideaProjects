//package com.faithgreen.multisourcetype;
//
//import com.alibaba.druid.pool.DruidDataSource;
//import com.alibaba.druid.support.http.StatViewServlet;
//import org.springframework.boot.context.properties.ConfigurationProperties;
//import org.springframework.boot.web.servlet.ServletRegistrationBean;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import javax.servlet.Servlet;
//import javax.sql.DataSource;
//import java.util.HashMap;
//
//@Configuration
//public class DruidDataSourceConfig {
//
//    @ConfigurationProperties(prefix = "spring.datasource")
//    @Bean
//    public DataSource druidDataSource() {
//        return new DruidDataSource();
//    }
//
//    @Bean
//    public ServletRegistrationBean druidServletRegistrationBean() {
//        ServletRegistrationBean<Servlet> statViewServletServletRegistrationBean = new ServletRegistrationBean<>(new StatViewServlet(), "/druid/*");
//        HashMap<String, String> initParams = new HashMap<>();
//        initParams.put("loginUsername", "admin");
//        initParams.put("loginPassword", "1111");
//        // 标识只有本机可以访问
////        initParams.put("allow","localhost");
//        // 为 null 或者为空值，标识允许所有访问
//        initParams.put("allow", "");
//        // 标识禁止此ip访问此巨鲸
//        initParams.put("faith", "192.168.172.3");
//
//        statViewServletServletRegistrationBean.setInitParameters(initParams);
//        return statViewServletServletRegistrationBean;
//    }
//
//    //配置 Druid 监控 之  web 监控的 filter
//    //WebStatFilter：用于配置Web和Druid数据源之间的管理关联监控统计
////    @Bean
////    public FilterRegistrationBean webStatFilter() {
////        FilterRegistrationBean bean = new FilterRegistrationBean();
////        bean.setFilter(new WebStatFilter());
////
////        //exclusions：设置哪些请求进行过滤排除掉，从而不进行统计
////        Map<String, String> initParams = new HashMap<>();
////        initParams.put("exclusions", "*.js,*.css,/druid/*");
////        bean.setInitParameters(initParams);
////
////        //"/*" 表示过滤所有请求
//////        bean.setUrlPatterns(Arrays.asList("/*"));
////        return bean;
////    }
//}
