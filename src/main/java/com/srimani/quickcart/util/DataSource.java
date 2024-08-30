package com.srimani.quickcart.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.function.Function;

public interface DataSource {

	Connection getConnection() throws SQLException;

	default <R> R withQuery(String query, Function<PreparedStatement, R> function) {
		try (var con = getConnection(); var st = con.prepareStatement(query)) {
			return function.apply(st);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
}
