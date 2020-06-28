package it.polito.tdp.artsmia.model;

public class Artist {

	int artistId;
	String artistName;
	
	public Artist(int artistId, String artistName) {
		super();
		this.artistId = artistId;
		this.artistName = artistName;
	}
	public int getArtistId() {
		return artistId;
	}
	public void setArtistId(int artistId) {
		this.artistId = artistId;
	}
	public String getArtistName() {
		return artistName;
	}
	public void setArtistName(String artistName) {
		this.artistName = artistName;
	}
	
	@Override
	public String toString() {
		return artistId+" "+artistName;
	}
	
	
}
