package com.gcp.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

@Configuration
public class TcpConnectionPoolFactory extends ConnectionPoolFactory {
	@Value("${DB_USER}")
	private String DB_USER;
	@Value("${DB_PASS}")
	private String DB_PASS;
	@Value("${DB_NAME}")
	private String DB_NAME;
	@Value("${INSTANCE_HOST}")
	private String INSTANCE_HOST;
	@Value("${DB_PORT}")
	private String DB_PORT;
	//@Value("${TRUST_CERT_KEYSTORE_PATH:}")
	private String TRUST_CERT_KEYSTORE_PATH;
	@Value("${TRUST_CERT_KEYSTORE_PASSWD}")
	private String TRUST_CERT_KEYSTORE_PASSWD;
	//@Value("${CLIENT_CERT_KEYSTORE_PATH:}")
	private String CLIENT_CERT_KEYSTORE_PATH;
	@Value("${CLIENT_CERT_KEYSTORE_PASSWD}")
	private String CLIENT_CERT_KEYSTORE_PASSWD;
	
	@Bean
	public DataSource createConnectionPool() {
		HikariConfig config = new HikariConfig();
		config.setJdbcUrl(String.format("jdbc:mysql://%s:%s/%s", INSTANCE_HOST, DB_PORT, DB_NAME));
		//config.setJdbcUrl(String.format("jdbc:mysql:///%s", DB_NAME));
		config.setUsername(DB_USER);
		config.setPassword(DB_PASS);
		//config.addDataSourceProperty("socketFactory", "com.google.cloud.sql.mysql.SocketFactory");
	    //config.addDataSourceProperty("cloudSqlInstance", "cl-mysql");
		if (CLIENT_CERT_KEYSTORE_PATH != null && TRUST_CERT_KEYSTORE_PATH != null) {
		      config.addDataSourceProperty("trustCertificateKeyStoreUrl",
		          String.format("file:%s", TRUST_CERT_KEYSTORE_PATH));
		      config.addDataSourceProperty("trustCertificateKeyStorePassword", TRUST_CERT_KEYSTORE_PASSWD);
		      config.addDataSourceProperty("clientCertificateKeyStoreUrl",
		          String.format("file:%s", CLIENT_CERT_KEYSTORE_PATH));
		      config.addDataSourceProperty("clientCertificateKeyStorePassword",
		          CLIENT_CERT_KEYSTORE_PASSWD);
		    }
		configureConnectionPool(config);
		return new HikariDataSource(config);
	}
}
