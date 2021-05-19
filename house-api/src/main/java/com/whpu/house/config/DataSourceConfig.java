package com.whpu.house.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;


@Configuration
public class DataSourceConfig {
//  @Value("${jdbc_url:jdbc:mysql://sunxiaoyuan.com:3307/house?useUnicode=true&characterEncoding=utf8}")
  @Value("${jdbc_url:jdbc:mysql://bj-cynosdbmysql-grp-lj12merc.sql.tencentcdb.com:22989/test_house?serveTimezone=UTC&useUnicode=true&characterEncoding=UTF-8}")
  String jdbcUrl;
  @Value("${jdbc_username:root}")
  String jdbcUsername;
  @Value("${jdbc_password:Deng2021}")
  String jdbcPassword;


  @Bean
  public DataSource dataSource(){
    return DataSourceBuilder.create().url(jdbcUrl).username(jdbcUsername).password(jdbcPassword).build();
  }
}
