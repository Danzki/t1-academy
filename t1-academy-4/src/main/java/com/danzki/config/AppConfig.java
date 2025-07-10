package com.danzki.config;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
@ComponentScan(basePackages = "com.danzki")
public class AppConfig {
    @Bean
    public DataSource dataSource() {
        HikariDataSource config = new HikariDataSource();

        // Основные параметры подключения
        config.setJdbcUrl("jdbc:postgresql://localhost:5434/ms_store_db");
        config.setSchema("ms_store_schema");
        config.setUsername("postgres");
        config.setPassword("postgres");
        config.setDriverClassName("org.postgresql.Driver");

        // Настройки пула соединений
        config.setPoolName("SpringBootHikariCP");
        config.setMaximumPoolSize(10);
        config.setMinimumIdle(5);
        config.setIdleTimeout(30000);
        config.setConnectionTimeout(20000);
        config.setMaxLifetime(600000);

        // Дополнительные параметры
        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", "250");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");

        return config;
    }
}
