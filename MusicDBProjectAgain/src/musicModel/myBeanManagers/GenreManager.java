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

import musicModel.MyBeans.Genre;
import Connection.MyJDBCCloser;
import Connection.MyJDBConnector;




public class GenreManager {

	private static Connection conn = MyJDBConnector.getJDBCConnection();
	
	public static Genre getGenre(int pkGenreId) {
		String sql = "SELECT * FROM genre WHERE pk_genre_id=?";
		Genre gen = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;	
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, pkGenreId);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				gen = new Genre();
				gen.setPkGenreId(pkGenreId);
				gen.setGenreName(rs.getString("genre_name"));
			}
		} catch (SQLException e) {
			System.err.println("Error message: " + e.getMessage());
			System.err.println("Error code: " +e.getErrorCode());
			System.err.println("SQL state: " +e.getSQLState());
		} finally {
			MyJDBCCloser.close(rs, pstmt);
		}
		return gen;
	}
	
	public static List<Genre> getAllGenres() {
		List<Genre> genreList = new ArrayList<>();
		String sql = "SELECT * FROM genre";
		Statement stmt = null;
		ResultSet rs = null;	
		try {
			stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			rs = stmt.executeQuery(sql);
			while(rs.next()) {
				Genre gen = new Genre();
				gen.setPkGenreId(rs.getInt("pk_genre_id"));
				gen.setGenreName(rs.getString("genre_name"));
				genreList.add(gen);	
			}
		} catch (SQLException e) {
			System.err.println("Error message: " + e.getMessage());
			System.err.println("Error code: " +e.getErrorCode());
			System.err.println("SQL state: " +e.getSQLState());	
		}finally {
			MyJDBCCloser.close(rs, stmt);
		}
		return genreList;
	}
	
	public static boolean updateGenre(Genre gen) {
		boolean isUpdated = true;
		int affectedRows = 0;	
		PreparedStatement pstmt = null;
		if(gen == null) {
			return !isUpdated;
		}
		else {
			String sql = "UPDATE genre SET genre_name=? WHERE pk_genre_id=?";			
			try {
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, gen.getGenreName());
				pstmt.setInt(2, gen.getPkGenreId());
				affectedRows = pstmt.executeUpdate();
				
			} catch (SQLException e) {
				System.err.println("Error message: " + e.getMessage());
				System.err.println("Error code: " +e.getErrorCode());
				System.err.println("SQL state: " +e.getSQLState());
			} finally {
				MyJDBCCloser.close(pstmt);
			}
			
			if(affectedRows==0) {
				System.out.println("Genre incorrect updated . . .");
				return !isUpdated;
			}else {
				System.out.println("Genre correct updated . . .");
				return isUpdated;
			}
		}
	}
	
	public static boolean addGenre(Genre gen) {
		boolean isAdded = true;
		int affectedRows = 0;
		PreparedStatement pstmt = null;
		if(gen == null) {
			return !isAdded;
		}
		else {
			String sql = "INSERT INTO genre (genre_name) VALUES (?)";
			try {
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, gen.getGenreName());
				affectedRows = pstmt.executeUpdate();
			} catch (SQLException e) {
				System.err.println("Error message: " + e.getMessage());
				System.err.println("Error code: " +e.getErrorCode());
				System.err.println("SQL state: " +e.getSQLState());
				return !isAdded;
			}finally {
				MyJDBCCloser.close(pstmt);
			}
			
			if(affectedRows==0) {
				System.out.println("No genre added . . .");
				return !isAdded;
			}else {
				System.out.println("genre added . . .");
				return isAdded;
			}
		}
	}
	
	public static boolean deleteGenre(int pkGenreId) {
		boolean isDeleted = true;
		int affectedRows = 0;
		PreparedStatement pstmt = null;	
		String sql = "DELETE FROM genre WHERE pk_genre_id=?";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, pkGenreId);
			affectedRows = pstmt.executeUpdate();
		}catch (SQLException e) {
			System.err.println("Error message: " + e.getMessage());
			System.err.println("Error code: " +e.getErrorCode());
			System.err.println("SQL state: " +e.getSQLState());
			return !isDeleted;
		}finally {
			MyJDBCCloser.close(pstmt);
		}
		
		if(affectedRows==0) {
			System.out.println("No genre deleted . . .");
			return !isDeleted;
		}else {
			System.out.println("genre deleted . . .");
			return isDeleted;
		}
	}

	public static List<Genre> searchGenres(String genreName) {
		List<Genre> genreList = new ArrayList<>();
		String sql = "SELECT * FROM genre WHERE genre_name LIKE ?";
		PreparedStatement pstmt = null;
		ResultSet rs = null;		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, genreName+"%"); 
			rs = pstmt.executeQuery();
			System.out.println("HEJ inne i try");
			while(rs.next()) {
				Genre gen = new Genre();
				gen.setPkGenreId(rs.getInt("pk_genre_id"));
				gen.setGenreName(rs.getString("genre_name"));	
				genreList.add(gen);	
				System.out.println("HEJ PRIRN");
			}
			return genreList;
			
		} catch (SQLException e) {
			System.err.println("Error message: " + e.getMessage());
			System.err.println("Error code: " +e.getErrorCode());
			System.err.println("SQL state: " +e.getSQLState());
			return null;	
		}finally {
			MyJDBCCloser.close(rs, pstmt);
		}
	}
}
