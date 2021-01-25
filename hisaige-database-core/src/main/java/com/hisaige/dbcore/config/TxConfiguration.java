package com.hisaige.dbcore.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.aop.framework.autoproxy.BeanNameAutoProxyCreator;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.interceptor.TransactionInterceptor;

import java.util.Properties;

/**
 * 数据库1
 * 声明式事务配置，只在service层起作用
 * @author chenyj
 * @date 2019年5月10日
 */
public class TxConfiguration {

    private DruidDataSource druidDataSource;

	public TxConfiguration(@Qualifier("core_db_druidDataSource") DruidDataSource druidDataSource) {
	    this.druidDataSource = druidDataSource;
    }

    @Bean("core_db_transactionManager")
    public DataSourceTransactionManager transactionManager() {
        DataSourceTransactionManager transManager = new DataSourceTransactionManager();
//		transManager.setDataSource(druidDataSource());
//		dataSource.setFilters(dataBaseProperties.getFilters());
        transManager.setDataSource(druidDataSource);
        return transManager;
    }

    // 创建事务通知
    @Bean(name = "core_db_txAdvice")
    public TransactionInterceptor getAdvisor() throws Exception {
 
        Properties properties = new Properties();
        //更新方法，遇到异常则回滚
        properties.setProperty("add*", "PROPAGATION_REQUIRED,-Exception");
        properties.setProperty("insert*", "PROPAGATION_REQUIRED,-Exception");
        properties.setProperty("save*", "PROPAGATION_REQUIRED,-Exception");
        properties.setProperty("del*", "PROPAGATION_REQUIRED,-Exception");
        properties.setProperty("batch*", "PROPAGATION_REQUIRED,-Exception");
        properties.setProperty("update*", "PROPAGATION_REQUIRED,-Exception");
        properties.setProperty("set*", "PROPAGATION_REQUIRED,-Exception");
        properties.setProperty("new*", "PROPAGATION_REQUIRED,-Exception");
        
        //只读方法，不需要异常回滚
        properties.setProperty("get*", "PROPAGATION_REQUIRED,readOnly");
        properties.setProperty("count*", "PROPAGATION_REQUIRED,readOnly");
        properties.setProperty("find", "PROPAGATION_REQUIRED,readOnly");
        properties.setProperty("query*", "PROPAGATION_REQUIRED,readOnly");
        properties.setProperty("is*", "PROPAGATION_REQUIRED,readOnly");
        properties.setProperty("load*", "PROPAGATION_REQUIRED,readOnly");
        properties.setProperty("process*", "PROPAGATION_REQUIRED,readOnly");

        TransactionInterceptor transactionInterceptor = new TransactionInterceptor();

        transactionInterceptor.setTransactionManager(transactionManager());
        transactionInterceptor.setTransactionAttributes(properties);
        return transactionInterceptor;
 
    }
 
    @Bean(name = "core_db_txProxy")
    public BeanNameAutoProxyCreator txProxy() {
        BeanNameAutoProxyCreator creator = new BeanNameAutoProxyCreator();
        creator.setInterceptorNames("core_db_txAdvice"); //可以是多个，逗号分隔
        creator.setBeanNames("*Service", "*ServiceImpl");
        creator.setProxyTargetClass(true);
        return creator;
    }
}
