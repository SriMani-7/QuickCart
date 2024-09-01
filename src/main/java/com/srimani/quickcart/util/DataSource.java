package com.srimani.quickcart.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public interface DataSource {

	Connection getConnection() throws SQLException;

	default <R> R withQuery(String query, StatementFunction<R> statementFunction) {
		try (var con = getConnection(); var st = con.prepareStatement(query)) {
			return statementFunction.exec(st);
		} catch (SQLException e) {
			var logger = LogManager.getLogger();
			logger.error(e.getMessage(), e);
			return null;
		}
	}

	interface StatementFunction<R> {
		R exec(PreparedStatement statement) throws SQLException;
	}
}
