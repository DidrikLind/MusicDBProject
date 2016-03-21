package Connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MyJDBConnector {
	
	//user created only for music database.
	private static final String USERNAME = "music2";
	private static final String PASSWORD = "music2";
	private static final String JDBC_URL  ="jdbc:mysql://localhost/music2";
	private static Connection conn = null;
	
	public static Connection getJDBCConnection() {
		if(conn==null) {
			try {
				conn = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
				System.out.println("We are all set with the JDBC-Connection . . .");
			} catch (SQLException e) {
				System.err.println("Error message: " + e.getMessage());
				System.err.println("Error code: " + e.getErrorCode());
				System.err.println("SQL state: " + e.getSQLState());
			}	
			return conn;
		}else {
			return conn;
		}
	}
}
