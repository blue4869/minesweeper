package swing;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

public class StartDisplay extends JFrame implements ActionListener {

	private JFrame frame;
	private JPanel p;

	public StartDisplay(JFrame frame, JPanel p) {

		this.frame = frame;
		this.p = p;

	}

	public void makeFrame() {

		frame.dispose();

		frame = new JFrame();
		p = new JPanel();

		frame.setTitle("マインスイーパー");
		frame.setBounds(100, 100, 400, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JButton btn = new JButton("Game Start!!");

		// 難易度を選択するラジオボタンを作成
		JPanel radio = new JPanel();

		ButtonGroup levels = new ButtonGroup();

		JRadioButton easy = new JRadioButton("簡単", true);
		JRadioButton normal = new JRadioButton("普通");
		JRadioButton hard = new JRadioButton("困難");

		easy.setName("easy");
		normal.setName("normal");
		hard.setName("hard");

		levels.add(easy);
		levels.add(normal);
		levels.add(hard);

		radio.add(easy);
		radio.add(normal);
		radio.add(hard);

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
