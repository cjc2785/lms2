package com.ss.lms2.db;

import java.time.LocalDate;
import java.sql.Date;
import java.sql.PreparedStatement;

/*
 * Wraps a PreparedStatement object
 */

public class QueryParameterList {
	
	private PreparedStatement statement;
	
	public QueryParameterList(PreparedStatement statement) {
		this.statement = statement;
	}

	public void setString(int index, String value) {
		try {
			statement.setString(index, value);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	public void setInt(int index, int value) {
		try {
			statement.setInt(index, value);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	public void setDate(int index, LocalDate value) {
		try {
			statement.setDate(index, Date.valueOf(value));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}