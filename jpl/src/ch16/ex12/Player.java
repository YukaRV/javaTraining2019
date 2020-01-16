package ch16.ex12;

public abstract class Player {
	static int number = 0;
	String name = "no name";
	public Player() {
		this("Player-"+number);
	}
	public Player(String name) {
		this.name = name;
		number++;
	}
	public String getName() {
		return name;
	}
	public final void play(Game game) {
		while(true) {
		int selectPos = strategy(game);
		if (selectPos == -1) {
			System.out.println("pass");
			break;
		}
		int r = selectPos/game.getBoardWidth();
		int c = selectPos%game.getBoardWidth();
		if (game.set(r, c)) {
			break;
		}
		System.out.println("cannot put");
		}
	}
	abstract int strategy(Game game);
}
