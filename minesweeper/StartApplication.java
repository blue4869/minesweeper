package swing;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class StartApplication extends JFrame {

	public static void main(String[] args) {

		JFrame frame = new JFrame();
		JPanel p = new JPanel();

		StartDisplay sd = new StartDisplay(frame, p);
		sd.makeFrame();
	}

}