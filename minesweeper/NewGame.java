package swing;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

public class NewGame extends JFrame implements ActionListener {

	private JFrame frame;
	private JPanel p;
	private JPanel radio;

	public NewGame(JFrame frame, JPanel p, JPanel radio) {

		this.frame = frame;
		this.p = p;
		this.radio = radio;
	}


	public void actionPerformed(ActionEvent e) {

		String level = null;

		for (int i = 0; i < radio.getComponentCount() ; i++) {

			if(((JRadioButton)radio.getComponent(i)).isSelected()) {
				level = radio.getComponent(i).getName();
			}
		}

		GameMake gm = new GameMake(level);
		gm.makeFrame(frame, p);
	}
}
