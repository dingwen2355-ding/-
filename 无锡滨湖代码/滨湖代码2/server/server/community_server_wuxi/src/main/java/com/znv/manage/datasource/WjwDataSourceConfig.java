package com.znv.manage.datasource;

import com.alibaba.druid.pool.DruidDataSource;
import com.github.pagehelper.PageHelper;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;
import java.util.Properties;

/**
 * @author 时智超
 * @ClassName: ManageDataSourceConfig
 * @Description: 数据库连接
 * @date 2018/5/16 16:43
 */
@Configuration
@MapperScan(basePackages = {"com.znv.manage.wjw.dao"}, sqlSessionTemplateRef  = "wjwSqlSessionTemplate")
public class WjwDataSourceConfig {

    @Bean(name = "wjwSqlDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.wjw")
    public DataSource setDataSource() {
        return new DruidDataSource();
    }

    @Bean(name = "wjwSqlTransactionManager")
    public DataSourceTransactionManager setTransactionManager(@Qualifier("wjwSqlDataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean(name = "wjwSqlSessionFactory")
    public SqlSessionFactory setSqlSessionFactory(@Qualifier("wjwSqlDataSource") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        //mybatis 下划线转驼峰
        org.apache.ibatis.session.Configuration configuration = new org.apache.ibatis.session.Configuration();
        configuration.setMapUnderscoreToCamelCase(true);
        bean.setConfiguration(configuration);
        //mybatis 分页插件
        PageHelper pageHelper = new PageHelper();
        Properties p = new Properties();
        p.setProperty("offsetAsPageNum", "true");
        p.setProperty("rowBoundsWithCount", "true");
        p.setProperty("reasonable", "true");
        //通过设置pageSize=0或者RowBounds.limit = 0就会查询出全部的结果。
        //p.setProperty("pageSizeZero", "true");
        //配置mysql数据库的方言
        //p.setProperty("dialect","mysql");
        pageHelper.setProperties(p);
        //添加插件
        bean.setPlugins(new Interceptor[]{pageHelper});


        bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:/mybatis/manage/mapper/wjwSql/*.xml"));
        return bean.getObject();
    }

    @Bean(name = "wjwSqlSessionTemplate")
    public SqlSessionTemplate setSqlSessionTemplate(@Qualifier("wjwSqlSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory);
    }
}
