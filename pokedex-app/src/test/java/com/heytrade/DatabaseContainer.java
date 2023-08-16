package com.heytrade;

import java.sql.Connection;

import org.testcontainers.containers.ContainerLaunchException;
import org.testcontainers.containers.PostgreSQLContainer;

/**
 * Utility class to create a shared instance of database for test purposes 
 * 
 * @author HEYTRADE
 * @since 1.0.0
 */
public class DatabaseContainer extends PostgreSQLContainer<DatabaseContainer> {

	private static final String IMAGE_VERSION = "postgres:14.1-alpine";
	private static final Integer TIMEOUT = 120;
	private static DatabaseContainer container;

	private DatabaseContainer() {
		super(IMAGE_VERSION);
	}

	public static DatabaseContainer getInstance() {
		if (container == null) {
			container = new DatabaseContainer();
			Runtime.getRuntime().addShutdownHook(new Thread(container::stop));
		}
		return container;
	}
	
	@Override
	protected void waitUntilContainerStarted() {
		logger().info("Waiting for database connection to become available at {} using query '{}'", getJdbcUrl(), getTestQueryString());

		// Repeatedly try and open a connection to the DB and execute a test query
		long start = System.currentTimeMillis();
		try {
			withStartupTimeoutSeconds(TIMEOUT);
			withConnectTimeoutSeconds(TIMEOUT);
			synchronized (this) {
				while (System.currentTimeMillis() < start + (1000 * TIMEOUT)) {
					try {
						if (!isRunning()) {
							wait(500L);
							continue; // Don't attempt to connect yet
						}
						
						try (Connection connection = createConnection("")) {
							boolean testQuerySucceeded = connection.createStatement().execute(this.getTestQueryString());
							if (testQuerySucceeded) {
								break;
							}
						}
					} catch (NoDriverFoundException e) {
						// we explicitly want this exception to fail fast without retries
						throw e;
					} catch (Exception e) {
						// ignore so that we can try again
						logger().debug("Failure when trying test query", e);
						wait(500L);
					}
				}
			}
		}
		catch (InterruptedException e) {
			Thread.currentThread().interrupt();
			throw new ContainerLaunchException("Container startup wait was interrupted", e);
		}

		logger().info("Container is started (JDBC URL: {})", getJdbcUrl());
	}

	@Override
	public void start() {
		super.start();
		System.setProperty("DATASOURCE_URL", container.getJdbcUrl());
		System.setProperty("DATASOURCE_USR", container.getUsername());
		System.setProperty("DATASOURCE_PWD", container.getPassword());
	}

	@Override
	public void stop() {
		//do nothing, JVM handles shut down
	}
}