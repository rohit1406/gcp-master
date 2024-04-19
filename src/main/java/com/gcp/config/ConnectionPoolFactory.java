package com.gcp.config;

import com.zaxxer.hikari.HikariConfig;

public class ConnectionPoolFactory {
	public static HikariConfig configureConnectionPool(HikariConfig config) {
		config.setMaximumPoolSize(5);
		config.setMinimumIdle(5);
		config.setConnectionTimeout(10000);
		config.setIdleTimeout(600000);
		config.setMaxLifetime(1800000);
		return config;
	}
}
