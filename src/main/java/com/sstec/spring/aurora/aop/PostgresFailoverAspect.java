package com.sstec.spring.aurora.aop;

import com.zaxxer.hikari.HikariDataSource;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
//import org.postgresql.util.PSQLException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

/**
 * Failover support for Aurora Postgres multi-AZ setup. Evict SQL connection from the pool on postgres error code: 25006
 * (read_only_sql_transaction). This works only for writer nodes and can lead to reader nodes stay unused after
 * failover event.
 *
 * Reader and writer endpoints can be a victim of DNS caching in failover scenario. Connections pointing to the
 * writer instance can be exchanged with reader one and that will causes error for each following write query.
 */
@Aspect
@Component
public class PostgresFailoverAspect {

    //https://www.postgresql.org/docs/9.4/errcodes-appendix.html
    private static final String SQL_STATE_READ_ONLY_SQL_TRANSACTION = "25006";

    Logger logger = LoggerFactory.getLogger(PostgresFailoverAspect.class);

    @Qualifier("writer.rds.com")
    @Autowired
    private DataSource writerDataSource;

    @Qualifier("reader.rds.com")
    @Autowired
    private DataSource readerDataSource;

    @AfterThrowing(pointcut = "within(com.sstec.spring.aurora..*)", throwing = "exception")
    public void catchDbQueryException(Exception exception) {
        // Disabled because jdbc driver supports it https://github.com/awslabs/aws-postgresql-jdbc
        // Once enabled, enable driver in pom.xml and change application.properties

//        if (exception.getClass() == JpaSystemException.class) {
//            Throwable causeLevel1 = exception.getCause();
//            if (causeLevel1.getClass() == GenericJDBCException.class) {
//                Throwable causeLevel2 = causeLevel1.getCause();
//                PSQLException psqlException = (PSQLException) causeLevel2;
//
//                if (psqlException.getSQLState().equals(PostgresFailoverAspect.SQL_STATE_READ_ONLY_SQL_TRANSACTION)) {
//                    softEvictConnections(writerDataSource);
//                    softEvictConnections(readerDataSource);
//                }
//            }
//        }
    }

    private void softEvictConnections(DataSource dataSource) {
        try {
            ((HikariDataSource) (dataSource)).getHikariPoolMXBean().softEvictConnections();
            logger.debug("Called softEvictConnections() on the data source");
        } catch (Exception exception) {
            logger.error("Failed to evict connections from the pool", exception);
        }
    }
}
