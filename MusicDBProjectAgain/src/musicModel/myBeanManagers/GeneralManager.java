package musicModel.myBeanManagers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.swing.table.DefaultTableModel;

import musicModel.MyBeans.Album;
import Connection.MyJDBCCloser;
import Connection.MyJDBConnector;




public class GeneralManager {

	private static Connection conn = MyJDBConnector.getJDBCConnection();
	

	
	public static String[] getColonTitles(String sql) {
		String[] colonTitles = null;
		Statement stmt = null;
		ResultSet rs = null;
		ResultSetMetaData rsmd = null;
		try {
			stmt = conn.createStatement();
			stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			rs = stmt.executeQuery(sql);
			rsmd = rs.getMetaData();
			colonTitles = new String[rsmd.getColumnCount()];
			for(int i=0; i<colonTitles.length; i++) {
				colonTitles[i] = rsmd.getColumnName(i+1);
			}
		} catch (SQLException e) {
			System.err.println("Error message: " + e.getMessage());
			System.err.println("Error code: " +e.getErrorCode());
			System.err.println("SQL state: " +e.getSQLState());
		} finally {
			MyJDBCCloser.close(rs, stmt);
		}
		return colonTitles;
	}

	
	public static Object[][] qryTables(String sql, String searchStr) {
		Object[][] joinArray;
		Statement stmt = null;
		ResultSet rs = null;
		
		try {
			stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			rs = stmt.executeQuery(sql);
			ResultSetMetaData rsmd = rs.getMetaData();
			//ugly, but let be for now :)
			int numOfColons = rsmd.getColumnCount();
			rs.last();
			int numOfRows = rs.getRow();
			rs.beforeFirst();
			joinArray = new String[numOfRows][numOfColons];
			int whichRow = 0;
			while(rs.next()) {
				//fill the join array up
				for(int whichCol=0; whichCol<numOfColons; whichCol++) {
					joinArray[whichRow][whichCol] = rs.getObject(whichCol+1) + "";
				}
				whichRow++;
			}	
			return joinArray;
			
		} catch (SQLException e) {
			System.err.println("Error message: " + e.getMessage());
			System.err.println("Error code: " +e.getErrorCode());
			System.err.println("SQL state: " +e.getSQLState());	
			return null;
		}finally {
			MyJDBCCloser.close(rs, stmt);
		}
	}
}

