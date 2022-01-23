package swing.minesweeper;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

/**
 * アプリケーション開始時の難易度設定画面を表示する
 */
public class StartDisplay extends JFrame implements ActionListener {

	/** ウインドウタイトル */
	private static final String TITLE = "マインスイーパー";

	/**
	 * 難易度選択画面を生成する。
	 */
	public void makeFrame() {

		JFrame frame = new JFrame();
		frame.setTitle(TITLE);
		frame.setBounds(100, 100, 400, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JButton btn = new JButton("Game Start!!");

		// 難易度を選択するラジオボタンを作成
		JRadioButton easy = new JRadioButton("簡単", true);
		easy.setName(ConstLevels.EASY.getLevel());
		JRadioButton normal = new JRadioButton("普通");
		normal.setName(ConstLevels.NORMAL.getLevel());
		JRadioButton hard = new JRadioButton("困難");
		hard.setName(ConstLevels.HARD.getLevel());

		ButtonGroup levels = new ButtonGroup();
		levels.add(easy);
		levels.add(normal);
		levels.add(hard);

		JPanel radio = new JPanel();
		radio.add(easy);
		radio.add(normal);
		radio.add(hard);

		JPanel p = new JPanel();
		p.add(btn);
		frame.getContentPane().add(radio, BorderLayout.NORTH);
		frame.getContentPane().add(p, BorderLayout.CENTER);

		btn.addActionListener(new NewGame(frame, p, radio));

		frame.setVisible(true);
	}

	public void actionPerformed(ActionEvent e) {

		makeFrame();
	}

}
