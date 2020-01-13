package ch16.ex11;

// TODO 16.11
// GameとPlayerを発展させて三目並べのような簡単なゲームを実装しなさい。
// いくつかのPlayerの自走を、それぞれ何回か実行してスコアーを取りなさい。

// TODO 16.12
// 練習問題16.11の結果を修正して、findResourceとfindResourcesを実装することで、プレーヤーの戦略が付属するリソースを使用できるようにしなさい。


public class Game {
	public static void main(String[] args) {
		String name;	// クラス名
		while ((name = getNextPlayer()) != null) {
			try {
				PlayerLoader loader = new PlayerLoader();
				Class<? extends Player> classOf = loader.loadClass(name).asSubclass(Player.class);
				Player player = classOf.newInstance();
				Game game = new Game();
				player.play(game);
				game.reportScore(name);
			} catch (Exception e) {
				reportException(name, e);
			}
		}
	}

	// 他のメソッドの定義
	static String getNextPlayer() {
		return "";
	}

	static void reportScore(String name) {

	}

	static void reportException(String name, Exception e) {

	}
}
