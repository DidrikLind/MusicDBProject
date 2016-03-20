package musicModel.TopModel;



import java.util.List;

import javax.swing.table.DefaultTableModel;

import musicModel.MyBeans.Album;
import musicModel.MyBeans.Artist;
import musicModel.MyBeans.Genre;
import musicModel.MyBeans.Track;
import musicModel.myBeanManagers.AlbumManager;
import musicModel.myBeanManagers.ArtistManager;
import musicModel.myBeanManagers.GeneralManager;
import musicModel.myBeanManagers.GenreManager;
import musicModel.myBeanManagers.TrackManager;


public class MyTopModel {

	//Class to handle data from beans, by using the Managers.
	

	//TODO write shorter code!
	public DefaultTableModel fetchTableData(Table table, String searchStr) {
		
		//DefaultTableModel dm = new DefaultTableModel();
		DefaultTableModel dm = new DefaultTableModel() {
			//making ALL cells uneditable for user.
			@Override
		    public boolean isCellEditable(int row, int column) {
		       return false;
		    }
		};
		String[] colonTitles = null;
		Object[] rowData = null;
		
		switch(table) {
			case ALBUM:
				List<Album> albList = AlbumManager.searchAlbums(searchStr);
				colonTitles = GeneralManager.getColonTitles("SELECT * FROM album");
				addColonTitlesToTable(dm,colonTitles);
				
				rowData = new Object[3];
				System.out.println(rowData.length);
				for(int i=0; i<albList.size(); i++) {
					rowData[0] = albList.get(i).getPkAlbumId();
					rowData[1] = albList.get(i).getAlbumName();
					rowData[2] = albList.get(i).getFkArtistId();
					dm.insertRow(i, rowData);
				}
				return dm;
			case ARTIST:
				List<Artist> artList = ArtistManager.searchArtists(searchStr);
				colonTitles = GeneralManager.getColonTitles("SELECT * FROM artist");
				addColonTitlesToTable(dm,colonTitles);
				
				rowData = new Object[3];
				System.out.println(rowData.length);
				for(int i=0; i<artList.size(); i++) {
					rowData[0] = artList.get(i).getPkArtistId();
					rowData[1] = artList.get(i).getArtistName();
					rowData[2] = artList.get(i).getCountry();
					dm.insertRow(i, rowData);
				}
				return dm;
			case TRACK:
				List<Track> trkList= TrackManager.searchTracks(searchStr);
				colonTitles = GeneralManager.getColonTitles("SELECT * FROM track"); 
				addColonTitlesToTable(dm,colonTitles);
				rowData = new Object[7];
				System.out.println(rowData.length);
				for(int i=0; i<trkList.size(); i++) {
					rowData[0] = trkList.get(i).getPkTrackId();
					rowData[1] = trkList.get(i).getTrackName();
					rowData[2] = trkList.get(i).getTrackTime();
					rowData[3] = trkList.get(i).getFkArtistId();
					rowData[4] = trkList.get(i).getFkAlbumId();
					rowData[5] = trkList.get(i).getReleaseDate();
					rowData[6] = trkList.get(i).getFkGenreId();
					dm.insertRow(i, rowData);
				}
				return dm;
			case GENRE:
				List<Genre> genList= GenreManager.searchGenres(searchStr);
				colonTitles = GeneralManager.getColonTitles("SELECT * FROM genre"); //TODO change getColonTitles, to have input
				addColonTitlesToTable(dm,colonTitles);
				rowData = new Object[2];
				System.out.println(rowData.length);
				for(int i=0; i<genList.size(); i++) {
					rowData[0] = genList.get(i).getPkGenreId();
					rowData[1] = genList.get(i).getGenreName();
					dm.insertRow(i, rowData);
				}
				return dm;
			default:
				System.out.println("No DefaultTableModel");
				return null;
		}
	}
	
	private void addColonTitlesToTable(DefaultTableModel dm, String[] colonTitles) {
		for(int i=0; i<colonTitles.length;i++)
			dm.addColumn(colonTitles[i]);
	}
	
	
	public boolean addData(Table table, String[] addStuff) {
		
		switch(table) {
			case ALBUM:
				return AlbumManager.addAlbum(new Album(addStuff[1],Integer.parseInt(addStuff[2]))); 
			case ARTIST:
				return ArtistManager.addArtist(new Artist(addStuff[1], addStuff[2]));
			case TRACK:
				return TrackManager.addTrack(new Track(addStuff[1], Integer.parseInt(addStuff[2]),
						Integer.parseInt(addStuff[3]), Integer.parseInt(addStuff[4]),
						Integer.parseInt(addStuff[5]), Integer.parseInt(addStuff[4])));
			case GENRE:
				return GenreManager.addGenre(new Genre(addStuff[1]));
			default:
				System.out.println("No data added");
				return false;
		}
	}

	public boolean updateData(Table table, String[] updateStuff) {
		switch(table) {
		case ALBUM:
			return AlbumManager.updateAlbum(new Album(Integer.parseInt(updateStuff[0]), updateStuff[1],Integer.parseInt(updateStuff[2]))); 
		case ARTIST:
			return ArtistManager.updateArtist(new Artist(Integer.parseInt(updateStuff[0]), updateStuff[1], updateStuff[2]));
		case TRACK:
			return TrackManager.updateTrack(new Track(Integer.parseInt(updateStuff[0]), updateStuff[1], Integer.parseInt(updateStuff[2]),
					Integer.parseInt(updateStuff[3]), Integer.parseInt(updateStuff[4]),
					Integer.parseInt(updateStuff[5]), Integer.parseInt(updateStuff[6])));
		case GENRE:
			return GenreManager.updateGenre(new Genre(Integer.parseInt(updateStuff[0]),updateStuff[1]));
		default:
			System.out.println("No data updated");
			return false;
		}
	}
	
	public boolean deleteData(Table table, String[] deleteStuff) {
		switch(table) {
		case ALBUM:
			return AlbumManager.deleteAlbum(Integer.parseInt(deleteStuff[0])); 
		case ARTIST:
			return ArtistManager.deleteArtist(Integer.parseInt(deleteStuff[0]));
		case TRACK:
			return TrackManager.deleteTrack(Integer.parseInt(deleteStuff[0]));
		case GENRE:
			return GenreManager.deleteGenre(Integer.parseInt(deleteStuff[0]));
		default:
			System.out.println("No data deleted");
			return false;
		}
	}

	//TODO this one shall hopefully use GeneralManager, the general of them all, YARR! :)
	public DefaultTableModel fetchSpecificTableData(SqlQry sql, String searchStr) {
		
		//DefaultTableModel dm = new DefaultTableModel();
		DefaultTableModel dm = new DefaultTableModel() {
			//making ALL cells uneditable for user.
			@Override
		    public boolean isCellEditable(int row, int column) {
		       return false;
		    }
		};
		String[] colonTitles = null;
		Object[][] array = null;
		int numOfRows, numOfColons;
		//TODO add more default joins. :O
		switch(sql) {
			case JOIN1:
				array = GeneralManager.joinTables(sql.JOIN1.getQry(), searchStr);
				numOfRows = array.length;
				numOfColons = array[0].length;
				System.out.println("numOFRows in topmodel: " + numOfRows);
				System.out.println("numOfCols in topmode: " + numOfColons);
				printTestArray(array);
				colonTitles = GeneralManager.getColonTitles(sql.JOIN1.getQry());
				
				
				//inserting colonTitles
				addColonTitlesToTable(dm,colonTitles);	
				//inserting rows.
				for(int r=0; r<numOfRows; r++) {
					dm.insertRow(r, array[r]);
				}
				return dm;
			case JOIN2:
				array = GeneralManager.joinTables(sql.JOIN2.getQry(), searchStr);
				numOfRows = array.length;
				numOfColons = array[0].length;
				System.out.println("numOFRows in topmodel: " + numOfRows);
				System.out.println("numOfCols in topmode: " + numOfColons);
				printTestArray(array);
				colonTitles = GeneralManager.getColonTitles(sql.JOIN2.getQry());
				
				//inserting colonTitles
				addColonTitlesToTable(dm,colonTitles);	
				//inserting rows.
				for(int r=0; r<numOfRows; r++) {
					dm.insertRow(r, array[r]);
				}
				return dm;
			case CSTMJOIN:
				array = GeneralManager.joinTables(sql.CSTMJOIN.getQry(), searchStr);
				numOfRows = array.length;
				numOfColons = array[0].length;
				System.out.println("numOFRows in topmodel: " + numOfRows);
				System.out.println("numOfCols in topmode: " + numOfColons);
				printTestArray(array);
				colonTitles = GeneralManager.getColonTitles(sql.CSTMJOIN.getQry());
				
				//inserting colonTitles
				addColonTitlesToTable(dm,colonTitles);	
				//inserting rows.
				for(int r=0; r<numOfRows; r++) {
					dm.insertRow(r, array[r]);
				}
				return dm;	
		}
		
		return null;
		
	}
	
	private  void printTestArray(Object[][] array) {
		for(int r=0; r<array.length; r++) {
			for(int c=0; c<array[0].length; c++) {
				
				if(c%array[0].length==0)
					System.out.println("");
				System.out.print(array[r][c]);
			}
		}
		//System.out.println(array);
	}
	
	
}
