package ch01.ex14;

class Walkman {
	private static int terminal = 1;
	private Song[] songs;
	public int getTerminal() {
		return terminal;
	}
	public void setTerminal(int i) {
		terminal = i;
	}
	public Song[] getSongs() {
		return songs;
	}
	public void setSongs(Song[] songs) {
		this.songs = songs;
	}
	public Song getSongs(int i) {
		return songs[i];
	}
	public void setSongs(int i, Song song) {
		songs[i] = song;
	}
	public void listen() {
		System.out.println("test: listen() (Walkman)");
	}
}