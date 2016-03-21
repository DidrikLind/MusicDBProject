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

import musicModel.MyBeans.Track;
import Connection.MyJDBCCloser;
import Connection.MyJDBConnector;




public class TrackManager {

	private static Connection conn = MyJDBConnector.getJDBCConnection();
	
	public static Track getTrack(int pkTrackId) {
		String sql = "SELECT * FROM track WHERE pk_track_id=?";
		Track trk = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, pkTrackId);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				trk = new Track();
				trk.setPkTrackId(pkTrackId);
				trk.setTrackName(rs.getString("track_name"));
				trk.setTrackTime(rs.getInt("track_time"));
				trk.setFkArtistId(rs.getInt("fk_artist_id"));
				trk.setFkAlbumId(rs.getInt("fk_album_id"));
				trk.setReleaseDate(rs.getInt("release_date"));
				trk.setFkGenreId(rs.getInt("fk_genre_id")); // måste lägga till fk_genre_id i databas.
			}	
		} catch (SQLException e) {
			System.err.println("Error message: " + e.getMessage());
			System.err.println("Error code: " +e.getErrorCode());
			System.err.println("SQL state: " +e.getSQLState());
		}finally {
			MyJDBCCloser.close(rs, pstmt);
		}
		return trk;
	}
	

	public static List<Track> getAllTracks() {

		List<Track> trackList = new ArrayList<>();
		String sql = "SELECT * FROM track";
		Statement stmt = null;
		ResultSet rs = null;
		
		try {
			stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			rs = stmt.executeQuery(sql);
			while(rs.next()) {
				Track trk = new Track();
				trk.setPkTrackId(rs.getInt("pk_track_id"));
				trk.setTrackName(rs.getString("track_name"));
				trk.setTrackTime(rs.getInt("track_time"));
				trk.setFkArtistId(rs.getInt("fk_artist_id"));
				trk.setFkAlbumId(rs.getInt("fk_album_id"));
				trk.setReleaseDate(rs.getInt("release_date"));
				trk.setFkGenreId(rs.getInt("fk_genre_id")); //// måste lägga till fk_genre_id i databas.
				
				trackList.add(trk);	
			}
			return trackList;
			
		} catch (SQLException e) {
			System.err.println("Error message: " + e.getMessage());
			System.err.println("Error code: " +e.getErrorCode());
			System.err.println("SQL state: " +e.getSQLState());
			return null;	
		}finally {
			MyJDBCCloser.close(rs, stmt);
		}
	}
	
	public static boolean updateTrack(Track trk) {
		boolean isUpdated = true;
		int affectedRows = 0;
		PreparedStatement pstmt = null;
		if(trk == null) {
			return !isUpdated;
		}
		else {
			String sql = "UPDATE track SET track_name=?, track_time=?,"
					+ " fk_artist_id=?, fk_album_id=?, release_date=? "
					+ "WHERE pk_track_id=?";
				
			try {
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, trk.getTrackName());
				pstmt.setInt(2, trk.getTrackTime());
				pstmt.setInt(3, trk.getFkArtistId());
				pstmt.setInt(4, trk.getFkAlbumId());
				pstmt.setInt(5, trk.getReleaseDate());
				pstmt.setInt(6, trk.getPkTrackId());
				affectedRows = pstmt.executeUpdate();
				
			} catch (SQLException e) {
				System.err.println("Error message: " + e.getMessage());
				System.err.println("Error code: " +e.getErrorCode());
				System.err.println("SQL state: " +e.getSQLState());
				return !isUpdated;
			}finally {
				MyJDBCCloser.close(pstmt);
			}
			
			if(affectedRows==0) {
				System.out.println("Track incorrect updated . . .");
				return !isUpdated;
			}else {
				System.out.println("Track correct updated . . .");
				return isUpdated;
			}
		}
	}
	
	public static boolean addTrack(Track trk) {
		boolean isAdded = true;
		int affectedRows = 0;
		if(trk == null) {
			return !isAdded;
		}
		else {
			String sql = "INSERT INTO track (track_name, track_time, fk_artist_id,"
					+ " fk_album_id, release_date, fk_genre_id) VALUES (?,?,?,?,?,?)";
			try {
				PreparedStatement pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, trk.getTrackName());
				pstmt.setInt(2, trk.getTrackTime());
				pstmt.setInt(3, trk.getFkArtistId());
				pstmt.setInt(4, trk.getFkAlbumId());
				pstmt.setInt(5, trk.getReleaseDate());			
				pstmt.setInt(6, trk.getFkGenreId()); // måste lägga till fk_genre_id i databas.
				affectedRows = pstmt.executeUpdate();
			} catch (SQLException e) {
				System.err.println("Error message: " + e.getMessage());
				System.err.println("Error code: " +e.getErrorCode());
				System.err.println("SQL state: " +e.getSQLState());
				return !isAdded;
			}
			
			if(affectedRows==0) {
				System.out.println("No track added . . .");
				return !isAdded;
			}else {
				System.out.println("track added . . .");
				return isAdded;
			}
		}
	}
	
	public static boolean deleteTrack(int pkTrackId) {

		boolean isDeleted = true;
		int affectedRows = 0;
		String sql = "DELETE FROM track WHERE pk_track_id=?";
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, pkTrackId);
			affectedRows = pstmt.executeUpdate();
		}catch (SQLException e) {
			System.err.println("Error message: " + e.getMessage());
			System.err.println("Error code: " +e.getErrorCode());
			System.err.println("SQL state: " +e.getSQLState());
			return !isDeleted;
		}
		
		if(affectedRows==0) {
			System.out.println("No album deleted . . .");
			return !isDeleted;
		}else {
			System.out.println("album deleted . . .");
			return isDeleted;
		}
	}

	public static List<Track> searchTracks(String trackName) {
		List<Track> trackList = new ArrayList<>();
		String sql = "SELECT * FROM track WHERE track_name LIKE ?";
		PreparedStatement pstmt = null;
		ResultSet rs = null;	
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, trackName+"%"); 
			rs = pstmt.executeQuery();
			System.out.println("HEJ inne i try");
			while(rs.next()) {
				Track trk = new Track();
				trk.setPkTrackId(rs.getInt("pk_track_id"));
				trk.setTrackName(rs.getString("track_name"));
				trk.setTrackTime(rs.getInt("track_time"));
				trk.setFkArtistId(rs.getInt("fk_artist_id"));
				trk.setFkAlbumId(rs.getInt("fk_album_id"));
				trk.setReleaseDate(rs.getInt("release_date"));	
				trk.setFkGenreId(rs.getInt("fk_genre_id")); // måste lägga till i databas.
				System.out.println(trk);
				trackList.add(trk);	
				System.out.println("HEJ PRIRN");
			}
			return trackList;
			
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
