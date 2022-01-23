package swing.minesweeper;

import javax.swing.JFrame;

public class StartApplication extends JFrame {

    /**
     * アプリケーション開始処理
     *
     * @param args 文字列（設定不要）
     */
    public static void main(String[] args) {
        StartDisplay sd = new StartDisplay();
        sd.makeFrame();
    }
}