package ch16.ex11;

import java.util.Arrays;

// 16.11
// GameとPlayerを発展させて三目並べのような簡単なゲームを実装しなさい。
// いくつかのPlayerの自走を、それぞれ何回か実行してスコアーを取りなさい。

public class Game {
	public static void main(String[] args) {
		String name;	// クラス名
		while ((name = getNextPlayer()) != null) {
			try {
				PlayerLoader loader = new PlayerLoader();
				Class<? extends Player> classOf = loader.loadClass(name).asSubclass(Player.class);
				Player[] players = {classOf.newInstance(),
									classOf.newInstance()};
				Game game = new Game();
				for (int i = 0;i < 10;i++) {
					game.doGame(players);
					System.out.println("finish");
					game.reportScore(name,players);
				}
			} catch (Exception e) {
				reportException(name, e);
			}
		}
	}

	static int testCount = 0;
	// 他のメソッドの定義
	static String getNextPlayer() {
		if (testCount++ > 0) return null;
		return "ch16.ex11.TestPlayer";
	}

	int[] score;
	void reportScore(String name, Player[] players) {
		if (score == null) score = new int[NUM_OF_PLAYER];
		if (winner == -1)
			System.out.println(name+": no winner");
		else {
			System.out.println(name+": winner is "+curPlayer);
			score[curPlayer]++;
		}
		for (int i = 0;i < NUM_OF_PLAYER;i++) {
			System.out.println(players[i].getName() + ": "+score[i]+"point");
		}
	}

	static void reportException(String name, Exception e) {

	}

	// 以下ゲームのStatus
	private final int NUM_OF_PLAYER = 2;
	private final int boardLength = 3;
	private int[] playerOrder;
	private int[][] board;
	private int curPlayer = -1;
	private int winner = -2;
	public final int getBoardWidth() {
		return boardLength;
	}
	public final int getBoardHeight() {
		return boardLength;
	}
	private final void init()  {
		playerOrder = new int[NUM_OF_PLAYER];
		shufflePlayerOrder();
		setNewBoard();
	}
	private final boolean doGame(Player[] players) {
		if (players.length != NUM_OF_PLAYER) {
			throw new IllegalArgumentException("num of player is only two: "+players.length);
		}
		init();
		printBoard();
		while(true) {
			for (curPlayer = 0;curPlayer < NUM_OF_PLAYER;curPlayer++) {
				Player player = players[playerOrder[curPlayer]];
				System.out.println(player.name+"'s turn");
				player.play(this);
				printBoard();
				winner = isFinish();// 終了条件
				if (winner == -2) continue;
				return true;
			}
		}
	}
	private void shufflePlayerOrder() {
		// 人数多い場合Collections.shuffleなど。今回は二人のためいらない
		playerOrder[0] = (int)Math.floor(Math.random()*2);
		playerOrder[1] = 1-playerOrder[0];
		System.out.println("first: "+playerOrder[0]);
		System.out.println("second: "+playerOrder[1]);
	}
	private void setNewBoard() {
		board = new int[boardLength][boardLength];
		for (int row = 0;row < boardLength;row++) {
			Arrays.fill(board[row], -1);
		}
	}
	public final int getNumOfPlayer() {
		return NUM_OF_PLAYER;
	}
	public final boolean set(int row, int col) {
		if (row < 0 || boardLength < row || col < 0 || boardLength < col)
			return false;
		if (board[row][col] != -1)
			return false;
		board[row][col] = curPlayer;
		return true;
	}
	public final int[][] getBoard() {
		int[][] boardClone = new int[boardLength][boardLength];
		for (int r = 0;r < boardLength;r++)
			for (int c = 0;c < boardLength;c++)
				boardClone[r][c] = board[r][c];
		return boardClone;
	}
	public final void printBoard() {
		for (int row = 0;row < boardLength;row++) {
			for (int col = 0;col < boardLength;col++) {
				System.out.print(getSymbol(board[row][col]));
			}
			System.out.println();
		}
	}

	public final String getSymbol(int i) {
		if (i < -1 || NUM_OF_PLAYER < i) {
			throw new IllegalArgumentException("player "+i+" don't exist");
		}
		switch (i) {
		case -1:
			return "-";
		case 0:
			return "o";
		case 1:
			return "x";
		}
		return " ";
	}

	private int isFinish() {
		if (checkCol() || checkRow() || checkDiag()) {
			return curPlayer;
		}
		for (int r = 0;r < boardLength;r++)
			for (int c = 0; c < boardLength;c++)
				if (board[r][c] == -1) return -2;
		return -1;
	}

	private boolean checkCol() {
		boolean[] eachCol = new boolean[boardLength];
		Arrays.fill(eachCol, true);
		for (int r = 0;r < boardLength;r++) {
			for (int c = 0;c < boardLength;c++) {
				eachCol[c] &= board[r][c] == curPlayer;
			}
		}
		for (int i = 0;i < eachCol.length;i++) {
			if (eachCol[i]) return true;
		}
		return false;
	}
	private boolean checkRow() {
		boolean[] eachRow = new boolean[boardLength];
		Arrays.fill(eachRow, true);
		for (int c = 0;c < boardLength;c++) {
			for (int r = 0;r < boardLength;r++) {
				eachRow[r] &= board[r][c] == curPlayer;
			}
		}
		for (int i = 0;i < eachRow.length;i++) {
			if (eachRow[i]) return true;
		}
		return false;
	}
	private boolean checkDiag() {
		boolean[] diag = new boolean[2];
		Arrays.fill(diag, true);
		for (int c = 0;c < boardLength;c++) {
			diag[0] &= board[c][c] == curPlayer;
			diag[1] &= board[c][boardLength-c-1] == curPlayer;
		}
		return (diag[0]||diag[1]);
	}
	public Game clone() {
		Game game = new Game();
		int num_of_player = this.playerOrder.length;
		int[] playerOrder = new int[num_of_player];
		for (int i = 0;i < num_of_player;i++)
			playerOrder[i] = this.playerOrder[i];

		int boardLength = this.boardLength;
		int[][] board = new int[boardLength][boardLength];
		for (int i = 0;i < num_of_player;i++)
			for (int j = 0;j < num_of_player;j++)
				board[i][j] = this.board[i][j];

		game.curPlayer = curPlayer;
		game.winner = winner;
		game.playerOrder = playerOrder;
		game.board = board;
		return this;
	}
}
