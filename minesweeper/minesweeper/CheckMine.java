package swing.minesweeper;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class CheckMine extends JFrame implements ActionListener {

	private final JFrame frame;
	private final JPanel p;
	private final int[] data;

	/** 地雷 */
	private static final String MINE = "mine";

	/** 周囲の地雷の数 */
	public static final String ZERO = "0";

	/** すでに確認したマスかのフラグ */
	public static final String CHECKED = "checked";

	public CheckMine (JFrame frame, JPanel p, int[] data) {
		this.frame = frame;
		this.p = p;
		this.data = data;
	}

	/**
	 * ボタンを左クリックした場合の地雷チェック処理を行う
	 *
	 * @param e イベントの発生起因
	 */
	public void actionPerformed(ActionEvent e) {

		JButton b = (JButton)e.getSource();
		String name = b.getName();
		if(MINE.equals(name)) {
			for (Component component : p.getComponents()) {
				if (component instanceof JButton btn) {
					String btnName = btn.getName();
					if (Objects.nonNull(btn.getIcon())) {
						btn.setIcon(null);
					}
					if (!CHECKED.equals(btnName)) {
						setMineText(btn);
					}
				}
			}

			int res = JOptionPane.showInternalConfirmDialog(p,
					"あなたの負けです。\nもう一度遊びますか？",
					"GAME OVER!!!", JOptionPane.YES_NO_OPTION);
			selectContinue(frame, res);
		} else {
			nameCheck(b, p, data);
		}

		int checkedCount = Math.toIntExact(Arrays.stream(p.getComponents())
				.filter(component -> CHECKED.equals(component.getName()))
				.count());

		if (checkedCount == data[GameMake.AREA] - data[GameMake.I_MINE]) {
			int res = JOptionPane.showInternalConfirmDialog(p,
					"あなたの勝ちです。\nもう一度遊びますか？",
					"Congratulations!!!", JOptionPane.YES_NO_OPTION);
			selectContinue(frame, res);
		}
	}

	public static void openAllZero(JButton b, JPanel p, int[] data) {

		int position = Integer.parseInt(b.getActionCommand());
		setMineText(b);

		List<Integer> aroundPosList = GameMake.aroundPosMap.get(position);
		aroundPosList.forEach(e -> nameCheck((JButton)p.getComponent(e), p, data));
	}

	/**
	 * ゲーム終了時に表示されたダイアログのボタンに応じて<br>
	 * ゲームの再開、アプリの終了を選択する
	 *
	 * @param frame　画面
	 * @param res　ダイアログからの返却値
	 */
	public static void selectContinue(JFrame frame, int res) {
		frame.dispose();
		if (res == JOptionPane.YES_OPTION) {
			StartDisplay sd = new StartDisplay();
			sd.makeFrame();
		}
	}

	/**
	 * <p>選択されたボタンに文字を表示させる</p>
	 */
	public static void setMineText(JButton b) {

		b.setFont(new Font("HGPｺﾞｼｯｸE", Font.BOLD, 20));

		if (MINE.equals(b.getName())) {
			b.setText(Constants.DISPLAY_MINE);
		} else {
			b.setText(b.getName());
		}

		b.setName(CHECKED);
		b.setEnabled(false);
	}

	/**
	 * 選択したボタンの名前で地雷数、または地雷位置かの判定を行う
	 *
	 * @param button 左クリックしたボタン
	 * @param p 本アプリのパネル
	 * @param data ゲームの各データ
	 */
	public static void nameCheck(JButton button, JPanel p, int[] data) {
		String buttonName = button.getName();
		if (CHECKED.equals(buttonName)) {
			return;
		}

		if (ZERO.equals(buttonName)) {
			openAllZero(button, p, data);
		} else {
			setMineText(button);
			button.setEnabled(false);
		}
	}
}
