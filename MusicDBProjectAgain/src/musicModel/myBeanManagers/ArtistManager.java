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

import Connection.MyJDBCCloser;
import Connection.MyJDBConnector;
import musicModel.MyBeans.Artist;


public class ArtistManager {
	
	private static Connection conn = MyJDBConnector.getJDBCConnection();
	
	public static Artist getArtist(int pkArtistId) {
		
		String sql = "SELECT * FROM artist WHERE pkArtistId=?";
		Artist art = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, pkArtistId);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				art = new Artist();
				art.setPkArtistId(pkArtistId);
				art.setArtistName(rs.getString("artist_name"));
				art.setCountry(rs.getString("country"));
			}
		} catch (SQLException e) {
			System.err.println("Error message: " + e.getMessage());
			System.err.println("Error code: " +e.getErrorCode());
			System.err.println("SQL state: " +e.getSQLState());
		}finally {
			MyJDBCCloser.close(rs, pstmt);
		}
		return art;
	}
	
	public static List<Artist> getAllArtists() {
		List<Artist> artistList = new ArrayList<>();
		String sql = "SELECT * FROM artist";
		Statement stmt = null;
		ResultSet rs = null;
		try {
			stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			rs = stmt.executeQuery(sql);
			while(rs.next()) {
				Artist art = new Artist();
				art.setPkArtistId(rs.getInt("pk_artist_id"));
				art.setArtistName(rs.getString("artist_name"));
				art.setCountry(rs.getString("country"));
				
				artistList.add(art);	
			}	
		} catch (SQLException e) {
			System.err.println("Error message: " + e.getMessage());
			System.err.println("Error code: " +e.getErrorCode());
			System.err.println("SQL state: " +e.getSQLState());	
		}finally {
			MyJDBCCloser.close(rs, stmt);
		}
		return artistList;
	}
	
	public static boolean updateArtist(Artist art) {
		boolean isUpdated = true;
		int affectedRows = 0;
		PreparedStatement pstmt = null;
		if(art == null) {
			return !isUpdated;
		}
		else {
			String sql = "UPDATE artist SET artist_name=?, country=? WHERE pk_artist_id=?";	
			try {
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, art.getArtistName());
				pstmt.setString(2, art.getCountry());
				pstmt.setInt(3, art.getPkArtistId());
				affectedRows = pstmt.executeUpdate();
				
			} catch (SQLException e) {
				System.err.println("Error message: " + e.getMessage());
				System.err.println("Error code: " +e.getErrorCode());
				System.err.println("SQL state: " +e.getSQLState());
			}finally {
				MyJDBCCloser.close(pstmt);
			}
			
			if(affectedRows==0) {
				System.out.println("Artist incorrect updated . . .");
				return !isUpdated;
			}else {
				System.out.println("Artist correct updated . . .");
				return isUpdated;
			}
		}
	}
	
	public static boolean addArtist(Artist art) {
		boolean isAdded = true;
		int affectedRows = 0;
		PreparedStatement pstmt = null;
		if(art == null) {
			return !isAdded;
		}
		else {
			String sql = "INSERT INTO artist (artist_name, country) VALUES (?,?)";
			try {
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, art.getArtistName());
				pstmt.setString(2, art.getCountry());
				affectedRows = pstmt.executeUpdate();
			} catch (SQLException e) {
				System.err.println("Error message: " + e.getMessage());
				System.err.println("Error code: " +e.getErrorCode());
				System.err.println("SQL state: " +e.getSQLState());
			}finally {
				MyJDBCCloser.close(pstmt);
			}
			
			if(affectedRows==0) {
				System.out.println("No artist added . . .");
				return !isAdded;
			}else {
				System.out.println("artist added . . .");
				return isAdded;
			}
		}
	}
	
	public static boolean deleteArtist(int pkArtistId) {

		boolean isDeleted = true;
		int affectedRows = 0;
		
		PreparedStatement pstmt = null;
		String sql = "DELETE FROM artist WHERE pk_artist_id=?";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, pkArtistId);
			affectedRows = pstmt.executeUpdate();
		}catch (SQLException e) {
			System.err.println("Error message: " + e.getMessage());
			System.err.println("Error code: " +e.getErrorCode());
			System.err.println("SQL state: " +e.getSQLState());
		}finally {
			MyJDBCCloser.close(pstmt);
		}
		
		if(affectedRows==0) {
			System.out.println("No artist deleted . . .");
			return !isDeleted;
		}else {
			System.out.println("artist deleted . . .");
			return isDeleted;
		}
	}
	
	// a more generic function than "getAllArtists", just put in an empty string
	public static List<Artist> searchArtists(String artistName) {
		List<Artist> artistList = new ArrayList<>();
		String sql = "SELECT * FROM artist WHERE artist_name LIKE ?";
		PreparedStatement pstmt = null;
		ResultSet rs = null;		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, artistName+"%"); 
			rs = pstmt.executeQuery();
			System.out.println("HEJ inne i try");
			while(rs.next()) {
				Artist art = new Artist();
				art.setPkArtistId(rs.getInt("pk_artist_id"));
				art.setArtistName(rs.getString("artist_name"));
				art.setCountry(rs.getString("country"));	
				System.out.println(art);
				artistList.add(art);	
				System.out.println("HEJ PRIRN");
			}		
		} catch (SQLException e) {
			System.err.println("Error message: " + e.getMessage());
			System.err.println("Error code: " +e.getErrorCode());
			System.err.println("SQL state: " +e.getSQLState());
		}finally {
			MyJDBCCloser.close(rs, pstmt);
		}
		return artistList;
	}
}
