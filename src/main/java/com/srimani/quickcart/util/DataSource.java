package com.srimani.quickcart.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.apache.logging.log4j.LogManager;

/**
 * Represents a source of database connections. This interface provides a method
 * to get a {@link Connection} and a default method to execute a query with a
 * {@link PreparedStatement}.
 */
public interface DataSource {

	/**
	 * Obtains a {@link Connection} from the data source.
	 *
	 * @return a {@link Connection} object.
	 * @throws SQLException if a database access error occurs.
	 */
	Connection getConnection() throws SQLException;

	/**
	 * Executes a query with the provided {@link StatementFunction} using a
	 * {@link PreparedStatement}. The query is executed within a try-with-resources
	 * block to ensure proper resource management.
	 *
	 * @param query             the SQL query to be executed.
	 * @param statementFunction a {@link StatementFunction} that processes the
	 *                          {@link PreparedStatement}.
	 * @param <R>               the type of the result returned by the
	 *                          {@link StatementFunction}.
	 * @return the result of executing the {@link StatementFunction} or {@code null}
	 *         if an exception occurs.
	 */
	default <R> R withQuery(String query, StatementFunction<R> statementFunction) {
		try (var con = getConnection(); var st = con.prepareStatement(query)) {
			return statementFunction.exec(st);
		} catch (SQLException e) {
			var logger = LogManager.getLogger();
			logger.error(e.getMessage(), e);
			return null;
		}
	}

	/**
	 * Functional interface for executing operations on a {@link PreparedStatement}.
	 * Implementations should define the logic for processing the
	 * {@link PreparedStatement}.
	 *
	 * @param <R> the type of result that is produced by executing the
	 *            {@link PreparedStatement}.
	 */
	interface StatementFunction<R> {
		R exec(PreparedStatement statement) throws SQLException;
	}
}
