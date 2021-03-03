package com.test.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class JDBCTemplate {
	
	public static Connection getConnection() {
		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		String url = "jdbc:oracle:thin:@128.168.25.30:1521:xe";
		String user = "admin";
		String password = "admin1234";
		
		Connection con = null;
		
		try {
			con=DriverManager.getConnection(url, user, password);
			con.setAutoCommit(false);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return con;
		
	}
	public static void close (ResultSet rs, Statement stmt, Connection con) {
		close(rs);
		close(stmt);
		close(con);
	}
	
	public static void clese (Statement stmt, Connection con) {
		close(stmt);
		close(con);
	}
	
	public static void commit(Connection con) {
		try {
			con.commit();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void rollback(Connection con) {
		try {
			con.rollback();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

}