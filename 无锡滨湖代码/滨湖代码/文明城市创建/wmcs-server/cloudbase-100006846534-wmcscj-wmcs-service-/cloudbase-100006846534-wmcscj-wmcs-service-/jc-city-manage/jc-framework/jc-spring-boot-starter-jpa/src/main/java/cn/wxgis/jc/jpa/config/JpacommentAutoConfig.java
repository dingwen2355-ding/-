package cn.wxgis.jc.jpa.config;


import cn.hutool.core.util.StrUtil;
import cn.wxgis.jc.common.enums.basedata.DbTypeEnum;
import cn.wxgis.jc.jpa.service.AlterCommentService;
import cn.wxgis.jc.jpa.service.JpacommentService;
import cn.wxgis.jc.jpa.service.impl.MysqlAlterCommentServiceImpl;
import cn.wxgis.jc.jpa.service.impl.OracleAlterCommentServiceImpl;
import cn.wxgis.jc.jpa.service.impl.PgSqlAlterCommentServiceImpl;
import cn.wxgis.jc.jpa.service.impl.SqlServerAlterCommentServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.sql.DataSource;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import java.util.Map;

@Slf4j
@Configuration
@EnableConfigurationProperties({JpacommentProperties.class})
@AutoConfigureAfter({EntityManager.class, JdbcTemplate.class, JpaProperties.class})
@ConditionalOnProperty(prefix = "jpa.comment", value = "enable", havingValue = "true")
public class JpacommentAutoConfig {

    @PersistenceContext
    private EntityManager entityManager;

    @Resource
    private JdbcTemplate jdbcTemplate;

    @Resource
    DataSource dataSource;

    @Resource
    JpaProperties jpaProperties;

    @Bean
    @ConditionalOnMissingBean
    public AlterCommentService alterCommentService() throws SQLException {
        DatabaseMetaData metaData = dataSource.getConnection().getMetaData();
        String databaseType = metaData.getDatabaseProductName().toUpperCase();
        String schema = "";
        AlterCommentService service = null;
        if (databaseType.contains(DbTypeEnum.MYSQL.getValue())) {
            schema = jdbcTemplate.queryForObject("select database() from dual", String.class);
            service = new MysqlAlterCommentServiceImpl();
        } else if (databaseType.contains(DbTypeEnum.SQLSERVER.getValue())) {
            schema = "dbo";
            String jpaDefaultSchema = "default_schema";
            Map<String, String> params = jpaProperties.getProperties();
            if (params != null && StrUtil.isNotBlank(params.get(jpaDefaultSchema))) {
                schema = params.get(jpaDefaultSchema);
            }
            service = new SqlServerAlterCommentServiceImpl();
        } else if (databaseType.contains(DbTypeEnum.ORACLE.getValue())) {
            schema = jdbcTemplate.queryForObject("select SYS_CONTEXT('USERENV','CURRENT_SCHEMA') CURRENT_SCHEMA from dual", String.class);
            service = new OracleAlterCommentServiceImpl();
        } else if (databaseType.contains(DbTypeEnum.POSTGRESQL.getValue())) {
            schema = jdbcTemplate.queryForObject(" SELECT CURRENT_SCHEMA ", String.class);
            service = new PgSqlAlterCommentServiceImpl();
        } else {
            service = null;
            log.error("can not find DatabaseProductName {}", databaseType);
        }
        if (service != null) {
            service.setSchema(schema);
            service.setJdbcTemplate(jdbcTemplate);
            log.debug("当前数据库schema为 {}", service.getSchema());
        }

        return service;
    }


    @Bean(initMethod = "init")
    @ConditionalOnMissingBean
    public JpacommentService jpacommentService() throws SQLException {
        JpacommentService service = new JpacommentService();
        service.setEntityManager(entityManager);
        service.setAlterCommentService(alterCommentService());
        return service;
    }
}
