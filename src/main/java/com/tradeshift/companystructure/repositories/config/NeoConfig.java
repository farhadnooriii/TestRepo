package com.tradeshift.companystructure.repositories.config;

/**
 * <h1> NeoConfig </h1>
 * This class is used as key value data that
 * contains value for connect to neo4j database.
 *
 * @author Farhad Noori
 * @version 1.0
 * @since 2019-01-10
 */
public class NeoConfig {

    static public final String SERVER_URI = "bolt://localhost:7687";
    static public final String DOMAIN_PACKAGE = "com.tradeshift.companystructure.domain";
    static public final String SERVER_USERNAME = "neo4j";
    static public final String SERVER_PASSWORD = "123";
}
