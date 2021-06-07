package com.sstec.spring.aurora.config;


import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

@Configuration
public class DataSourceConfig {

    @Bean(name="writer.rds.com")
    @Primary
    @ConfigurationProperties(prefix="datasource.writer")
    public DataSource getWriterDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name="reader.rds.com")
    @ConfigurationProperties(prefix="datasource.reader")
    public DataSource getReaderDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name="transactionManager")
    @Autowired
    @Primary
    DataSourceTransactionManager getWriterTransactionManager(@Qualifier ("writer.rds.com") DataSource writerDataSource) {
        return new DataSourceTransactionManager(writerDataSource);
    }

    @Bean(name=TransactionalOverReadReplica.READ_REPLICA)
    @Autowired
    DataSourceTransactionManager getReaderTransactionManager(@Qualifier ("reader.rds.com") DataSource readerDataSource) {
        return new DataSourceTransactionManager(readerDataSource);
    }
}
