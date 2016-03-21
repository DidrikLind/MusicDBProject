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




public class AlbumManager {

	private static Connection conn = MyJDBConnector.getJDBCConnection();

	public static Album getAlbum(int pkAlbumId) {
		
		String sql = "SELECT * FROM album WHERE pk_album_id=?";
		Album alb = null;
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, pkAlbumId);
			rs = pstmt.executeQuery();
		
			if(rs.next()) {
				alb = new Album();
				alb.setPkAlbumId(pkAlbumId);
				alb.setAlbumName(rs.getString("album_name"));
				alb.setFkArtistId(rs.getInt("fk_artist_id"));
			}

		} catch (SQLException e) {
			System.err.println("Error message: " + e.getMessage());
			System.err.println("Error code: " +e.getErrorCode());
			System.err.println("SQL state: " +e.getSQLState());
		} finally {
			MyJDBCCloser.close(rs, pstmt);
		}
		return alb;
	}
	
	public static List<Album> getAllAlbums() {
		List<Album> albumList = new ArrayList<>();
		String sql = "SELECT * FROM album";
		Statement stmt = null;
		ResultSet rs = null;
		try {
			stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			rs = stmt.executeQuery(sql);
			while(rs.next()) {
				Album alb = new Album();
				alb.setPkAlbumId(rs.getInt("pk_album_id"));
				alb.setAlbumName(rs.getString("album_name"));
				alb.setFkArtistId(rs.getInt("fk_artist_id"));		
				albumList.add(alb);	
			}		
		} catch (SQLException e) {
			System.err.println("Error message: " + e.getMessage());
			System.err.println("Error code: " +e.getErrorCode());
			System.err.println("SQL state: " +e.getSQLState());	
		}finally {
			MyJDBCCloser.close(rs, stmt);
		}
		return albumList;
	}
		
	public static boolean updateAlbum(Album alb) {
		boolean isUpdated = true;
		int affectedRows = 0;	
		PreparedStatement pstmt = null;
		if(alb == null) {
			return !isUpdated;
		}
		else {
			String sql = "UPDATE album SET album_name=?, fk_artist_id=? WHERE pk_album_id=?";
				
			try {
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, alb.getAlbumName());
				pstmt.setInt(2, alb.getFkArtistId());
				pstmt.setInt(3, alb.getPkAlbumId());
				affectedRows = pstmt.executeUpdate();
				
			} catch (SQLException e) {
				System.err.println("Error message: " + e.getMessage());
				System.err.println("Error code: " +e.getErrorCode());
				System.err.println("SQL state: " +e.getSQLState());
			} finally {
				MyJDBCCloser.close(pstmt);
			}
			
			if(affectedRows==0) {
				System.out.println("Album incorrect updated . . .");
				return !isUpdated;
			}else {
				System.out.println("Album correct updated . . .");
				return isUpdated;
			}
		}
	}
	
	public static boolean addAlbum(Album alb) {
		boolean isAdded = true;
		int affectedRows = 0;
		PreparedStatement pstmt = null;
		if(alb == null) {
			return !isAdded;
		}
		else {
			String sql = "INSERT INTO album (album_name, fk_artist_id) VALUES (?,?)";
			try {
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, alb.getAlbumName());
				pstmt.setInt(2, alb.getFkArtistId());
				affectedRows = pstmt.executeUpdate();
			} catch (SQLException e) {
				System.err.println("Error message: " + e.getMessage());
				System.err.println("Error code: " +e.getErrorCode());
				System.err.println("SQL state: " +e.getSQLState());
			}finally {
				MyJDBCCloser.close(pstmt);
			}
			
			if(affectedRows==0) {
				System.out.println("No album added . . .");
				return !isAdded;
			}else {
				System.out.println("album added . . .");
				return isAdded;
			}
		}
	}
	
	public static boolean deleteAlbum(int pkAlbumId) {
		boolean isDeleted = true;
		int affectedRows = 0;
		PreparedStatement pstmt = null;
		String sql = "DELETE FROM album WHERE pk_album_id=?";
			try {
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, pkAlbumId);
				affectedRows = pstmt.executeUpdate();
			}catch (SQLException e) {
				System.err.println("Error message: " + e.getMessage());
				System.err.println("Error code: " +e.getErrorCode());
				System.err.println("SQL state: " +e.getSQLState());
			}finally {
				MyJDBCCloser.close(pstmt);
			}
			
			if(affectedRows==0) {
				System.out.println("No album deleted . . .");
				return !isDeleted;
			}else {
				System.out.println("album deleted . . .");
				return isDeleted;
			}
	}

//can only use instead of getAllAlbums, just with an empty string then!
	public static List<Album> searchAlbums(String albumName) {
		List<Album> albumList = new ArrayList<>();
		String sql = "SELECT * FROM album WHERE album_name LIKE ?";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, albumName+"%"); 
			rs = pstmt.executeQuery();
			while(rs.next()) {
				Album alb = new Album();
				alb.setPkAlbumId(rs.getInt("pk_album_id"));
				alb.setAlbumName(rs.getString("album_name"));
				alb.setFkArtistId(rs.getInt("fk_artist_id"));	
				albumList.add(alb);	
			}	
		} catch (SQLException e) {
			System.err.println("Error message: " + e.getMessage());
			System.err.println("Error code: " +e.getErrorCode());
			System.err.println("SQL state: " +e.getSQLState());	
		}finally {
			MyJDBCCloser.close(rs, pstmt);
		}
		return albumList;
	}
}
