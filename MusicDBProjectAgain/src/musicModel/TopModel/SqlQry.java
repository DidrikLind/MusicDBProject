package musicModel.TopModel;

public enum SqlQry {
	JOIN1("SELECT track_name,artist_name, album_name, genre_name, track_time FROM track t"
			+ " INNER JOIN artist art ON t.fk_artist_id = art.pk_artist_id"
			+ " INNER JOIN album alb ON t.fk_album_id = alb.pk_album_id"
			+ " INNER JOIN genre gen ON t.fk_genre_id = gen.pk_genre_id"),
	JOIN2("SELECT artist_name, album_name FROM album alb"
			+ " INNER JOIN artist art ON alb.fk_artist_id=pk_artist_id"),
	ALBUM("SELECT * FROM album"),
	ARTIST("SELECT * FROM artist"),
	TRACK("SELECT * FROM track"),
	GENRE("SELECT * FROM genre"),
	CSTM(" ");
	
	private String qry;
	
	private SqlQry(String qry) {
		this.qry = qry;
	}
	
	public String getQry() {
		return qry;
	}
	
	public void setQry(String qry) {
		this.qry = qry;
	}
}
