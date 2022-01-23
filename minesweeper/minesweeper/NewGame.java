package swing.minesweeper;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.Optional;

public class NewGame extends JFrame implements ActionListener {

    private final JFrame frame;
    private final JPanel p;
    private final JPanel radio;

    /**
     * 新規にゲーム画面を作成するクラスのコンストラクタ
     *
     * @param frame 画面
     * @param p パネル
     * @param radio パネル（ラジオボタン）
     */
    public NewGame(JFrame frame, JPanel p, JPanel radio) {

        this.frame = frame;
        this.p = p;
        this.radio = radio;
    }

    /**
     * 難易度選択画面でゲーム開始ボタンを押下した時の処理<br>
     * 難易度に応じたゲーム画面を作成する。
     *
     * @param e ゲーム開始ボタン
     */
    public void actionPerformed(ActionEvent e) {
        Optional<Component> selectedLevel = Arrays.stream(radio.getComponents())
                .filter(component -> ((JRadioButton) component).isSelected())
                .findFirst();
        String level = selectedLevel.get().getName();

        GameMake gm = new GameMake(frame, p);
        gm.makeFrame(level);
    }
}
