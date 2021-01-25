package com.hisaige.dbcore.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import com.hisaige.dbcore.config.properties.DbProperties;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.boot.autoconfigure.SpringBootVFS;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

/**
 * @author chenyj
 * 2020/5/16 - 17:04.
 **/
@EnableConfigurationProperties({DbProperties.class})
public class DbConfiguration {

    private DbProperties dbProperties;

    public DbConfiguration(DbProperties dbProperties){
        this.dbProperties = dbProperties;
    }

    @Bean(name = "core_db_druidDataSource",initMethod = "init",destroyMethod = "close")
    @ConfigurationProperties(prefix = "spring.datasource.hisaige.druid")
    public DruidDataSource druidDataSource() {
        return DruidDataSourceBuilder.create().build();
    }

    @Bean(name = "core_db_sqlSessionFactory")
    public SqlSessionFactory sqlSessionFactory(@Qualifier("core_db_druidDataSource") DruidDataSource dataSource)
            throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        // 设置mybatis的xml所在位置
        bean.setMapperLocations(
                new PathMatchingResourcePatternResolver().getResources("classpath*:" + dbProperties.getMapperLocation() + "/*.xml"));
        bean.setVfs(SpringBootVFS.class);
        org.apache.ibatis.session.Configuration configuration = new org.apache.ibatis.session.Configuration();
        configuration.setMapUnderscoreToCamelCase(true);
        configuration.setCallSettersOnNulls(true);
        bean.setConfiguration(configuration);
        return bean.getObject();
    }

    @Bean(name = "core_db_sqlSessionTemplate")
    public SqlSessionTemplate sqlSessionTemplate1(@Qualifier("core_db_sqlSessionFactory") SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

    /**
     * 配置监控服务器
     *
     * @return 返回监控注册的servlet对象
     */
    @Bean
    public ServletRegistrationBean<StatViewServlet> statViewServlet() {

        ServletRegistrationBean<StatViewServlet> servletRegistrationBean = new ServletRegistrationBean<>(
                new StatViewServlet(), dbProperties.getStatViewServlet().getUrlPattern());

        boolean enable = Boolean.valueOf(dbProperties.getStatViewServlet().getEnable());
        //是否启用
        servletRegistrationBean.setEnabled(enable);

        if(enable){
            // 添加IP白名单
            servletRegistrationBean.addInitParameter("allow", dbProperties.getStatViewServlet().getAllow()); // 本机使用IP将不能访问，但可以使用localhost作IP访问
            // 添加IP黑名单，当白名单和黑名单重复时
            // ，黑名单优先级更高
            servletRegistrationBean.addInitParameter("deny", dbProperties.getStatViewServlet().getDeny());
            // 添加控制台管理用户
            servletRegistrationBean.addInitParameter("loginUsername", dbProperties.getStatViewServlet().getUsername());
            servletRegistrationBean.addInitParameter("loginPassword", dbProperties.getStatViewServlet().getPassword());
            // 是否能够重置数据
            servletRegistrationBean.addInitParameter("resetEnable", dbProperties.getStatViewServlet().getResetEnable());

        }
        return servletRegistrationBean;
    }

    /**
     * 配置服务过滤器
     *
     * @return 返回过滤器配置对象
     */
    @Bean
    public FilterRegistrationBean<WebStatFilter> statFilter() {
        FilterRegistrationBean<WebStatFilter> filterRegistrationBean = new FilterRegistrationBean<>(
                new WebStatFilter());
        // 添加过滤规则
        filterRegistrationBean.addUrlPatterns("/*");
        // 忽略过滤格式
        filterRegistrationBean.addInitParameter("exclusions", "*.js,*.gif,*.jpg,*.png,*.css,*.ico,/druid/*,");
        return filterRegistrationBean;
    }

}
