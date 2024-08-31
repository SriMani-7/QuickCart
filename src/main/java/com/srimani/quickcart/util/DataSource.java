package com.srimani.quickcart.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public interface DataSource {

	Connection getConnection() throws SQLException;

	default <R> R withQuery(String query, StatementFunction<R> statementFunction) {
		try (var con = getConnection(); var st = con.prepareStatement(query)) {
			return statementFunction.exec(st);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public interface StatementFunction<R> {
		R exec(PreparedStatement statement) throws SQLException;
	}
}
