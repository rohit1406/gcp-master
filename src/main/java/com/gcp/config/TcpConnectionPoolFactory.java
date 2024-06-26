package com.gcp.config;

import java.io.IOException;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ResourceLoader;

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
	@Value("${JDBC_URL}")
	private String JDBC_URL;
	@Value("${TRUST_CERT_KEYSTORE_PATH:}")
	private String TRUST_CERT_KEYSTORE_PATH;
	@Value("${TRUST_CERT_KEYSTORE_PASSWD}")
	private String TRUST_CERT_KEYSTORE_PASSWD;
	@Value("${CLIENT_CERT_KEYSTORE_PATH:}")
	private String CLIENT_CERT_KEYSTORE_PATH;
	@Value("${CLIENT_CERT_KEYSTORE_PASSWD}")
	private String CLIENT_CERT_KEYSTORE_PASSWD;
	@Value("${gcp.ssl.enabled:false}")
	private boolean sslEnabled;
	@Autowired
	private ResourceLoader resourceLoader;
	
	@Bean
	public DataSource createConnectionPool() throws IOException {
		HikariConfig config = new HikariConfig();
		config.setJdbcUrl(JDBC_URL);
		//config.setJdbcUrl(String.format("jdbc:mysql:///%s", DB_NAME));
		config.setUsername(DB_USER);
		config.setPassword(DB_PASS);
		//config.addDataSourceProperty("socketFactory", "com.google.cloud.sql.mysql.SocketFactory");
	    //config.addDataSourceProperty("cloudSqlInstance", "cl-mysql");
		if (sslEnabled && CLIENT_CERT_KEYSTORE_PATH != null && TRUST_CERT_KEYSTORE_PATH != null) {
		      config.addDataSourceProperty("trustCertificateKeyStoreUrl",
		          String.format("file:%s", resourceLoader.getResource(TRUST_CERT_KEYSTORE_PATH).getFile()));
		      config.addDataSourceProperty("trustCertificateKeyStorePassword", TRUST_CERT_KEYSTORE_PASSWD);
		      config.addDataSourceProperty("clientCertificateKeyStoreUrl",
		          String.format("file:%s", resourceLoader.getResource(CLIENT_CERT_KEYSTORE_PATH).getFile()));
		      config.addDataSourceProperty("clientCertificateKeyStorePassword",
		          CLIENT_CERT_KEYSTORE_PASSWD);
		    }
		configureConnectionPool(config);
		return new HikariDataSource(config);
	}
}
