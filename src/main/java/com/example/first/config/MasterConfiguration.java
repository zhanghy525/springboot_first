package com.example.first.config;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;

@Configuration
@MapperScan(basePackages = MasterConfiguration.PACKAGE,sqlSessionFactoryRef = "masterSqlSessionFactory")
public class MasterConfiguration {
    static final String PACKAGE = "com.example.first.master.dao";
    static final String MAPPER_LOCATION = "classpath:mapper/master/*.xml";

    @Primary//只能有一个primary数据源
    @Bean(name="masterdatasource")
    @ConfigurationProperties("datasource.master")
    public DataSource masterDataSource() {
        return DruidDataSourceBuilder.create().build();
    }
    @Bean(name = "masterTransactionManager")
    @Primary//只能有一个primary数据源
    public DataSourceTransactionManager masterTransactionManager() {
        //为masterDataSource追加事务
        return new DataSourceTransactionManager(masterDataSource());
    }
    @Bean(name = "masterSqlSessionFactory")
    @Primary//只能有一个primary数据源
    public SqlSessionFactory mysqlSqlSessionFactory(@Qualifier("masterdatasource") DataSource dataSource)
            throws Exception {
        final SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(dataSource);
        //如果不使用xml的方式配置mapper，则可以省去下面这行mapper location的配置。
        sessionFactory.setMapperLocations(new PathMatchingResourcePatternResolver()
                .getResources(MasterConfiguration.MAPPER_LOCATION));
        return sessionFactory.getObject();
    }
}
