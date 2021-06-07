package com.sstec.spring.aurora.config;

public final class TransactionalOverReadReplica {

    // Name of transaction manager that should be passed to @Transactional annotation value in order to
    // use read replica node for SQL queries
    public static final String READ_REPLICA = "transactionManagerOverReadReplica";

}