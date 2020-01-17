package ch16.ex11;

public class TestPlayer extends Player{
	public TestPlayer() {
		super();
	}
	public TestPlayer(String name) {
		super(name);
	}

	@Override
	int strategy() {
		Game game = getGame();
		int[][] board = game.getBoard();
		for (int r = 0;r < board.length;r++) {
			for (int c = 0;c < board[r].length;c++) {
				if (board[r][c] == -1)
					return r*game.getBoardWidth()+c;
			}
		}
		return -1;
	}
}
