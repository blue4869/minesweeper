package minesweeper.minesweeper;

public class Constants {
    /** 地雷判別用文字列 */
    public static final String MINE = "mine";

    /** 画面表示用地雷 */
    public static final String DISPLAY_MINE = "*";

    /** ClientPropertyのキー */
	public static final String KEY_KIND = "kind";
	public static final String KEY_STATE = "state";
    public static final String KEY_COUNT = "count";
    
    public enum Kind {
        MINE, NUMBER
    }

	public enum State {
		HIDDEN, REVEALED, FLAGGED
	}
}
