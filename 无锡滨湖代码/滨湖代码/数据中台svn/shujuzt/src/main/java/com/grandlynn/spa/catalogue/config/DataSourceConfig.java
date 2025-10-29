package com.grandlynn.spa.catalogue.config;

import com.zaxxer.hikari.HikariDataSource;
import javax.sql.DataSource;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
@MapperScan(
   basePackages = {"com.grandlynn.spa.catalogue.mapper*"}
)
public class DataSourceConfig {
   @Value("${spring.datasource.driverClassName}")
   private String driverClassName;
   @Value("${spring.datasource.url}")
   private String jdbcUrl;
   @Value("${spring.datasource.username}")
   private String userName;
   @Value("${spring.datasource.password}")
   private String password;

   @Primary
   @Bean(
      name = {"dataSource"}
   )
   @ConfigurationProperties(
      prefix = "spring.datasource"
   )
   public DataSource dataSource() {
      HikariDataSource hds = new HikariDataSource();
      hds.setJdbcUrl(this.jdbcUrl);
      hds.setDriverClassName(this.driverClassName);
      hds.setUsername(this.userName);
      hds.setPassword(this.password);
      return hds;
   }
}
