package swing;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class GameMake extends JFrame implements ActionListener, MouseListener {

	/**
	 * <p>難易度：簡単</p>
	 */
	private static final String EASY = "easy";

	/**
	 * <p>難易度：普通</p>
	 */
	private static final String NORMAL = "normal";

	/**
	 * <p>難易度：困難</p>
	 */
	private static final String HARD = "hard";

	/**
	 * <p>heightの格納位置
	 */
	public static final int HEIGHT = 0;

	/**
	 * <p>widthの格納位置</p>
	 */
	public static final int WIDTH = 1;

	/**
	 * <p>areaの格納位置</p>
	 */
	public static final int AREA = 2;

	/**
	 * <p>mineの格納位置</p>
	 */
	public static final int IMINE = 3;

	/**
	 * <p>縦のボタン数</p>
	 */
	private int height;

	/**
	 * <p>横のボタン数</p>
	 */
	private int width;

	/**
	 * <p>地雷の数</p>
	 */
	private int mine;

	/**
	 * <p>ボタンの総数</p>
	 */
	private int area;

	/**
	 * <p>ボタン</p>
	 */
	JButton[] btn;

	JFrame tframe;
	JPanel tp;

	/**
	 * <p>地雷</p>
	 */
	private static final String MINE = "mine";

	/**
	 * <p>コンストラクタ</p>
	 */
	public GameMake(String level) {

		if (EASY.equals(level)) {

			height = 10;
			width = 10;
			mine = 20;

		} else if (NORMAL.equals(level)) {

			height = 10;
			width = 20;
			mine = 50;

		} else if (HARD.equals(level)) {

			height = 15;
			width = 30;
			mine = 70;

		}

		area = height * width;
	}

	public void makeFrame(JFrame frame, JPanel p) {

		tframe = frame;
		tp = p;

		tp.removeAll();
		tframe.setTitle("MineSweeper!");
		tframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		tframe.setBounds(100, 100, 50 * width, 50 * height);

		tp.setLayout(new GridLayout(height, width));

		this.btn = new JButton[area];

		for (int i = 0; i < area; i++) {
			btn[i] = new JButton();
		}

		for (int i = 0; i < area; i++) {
			this.btn[i].setActionCommand(String.valueOf(i));
			this.btn[i].addActionListener(this);
			tp.add(this.btn[i]);
		}

		tframe.setContentPane(tp);
		tframe.setVisible(true);
	}

	public void actionPerformed (ActionEvent e) {

		int[] data = {height, width, area, mine};
		JButton b = (JButton)e.getSource();

		this.btn = makeScreen(b);
		this.btn = mineNumber(this.btn);

		for(int i = 0; i < area; i++) {
			this.btn[i].setActionCommand(String.valueOf(i));
		}

		tp.removeAll();

		for (int i = 0; i < area; i++) {
			this.btn[i].addActionListener(new CheckMine(tframe, tp, data));
			this.btn[i].addMouseListener(this);
			tp.add(this.btn[i]);
		}

		JButton selectedBtn = btn[Integer.parseInt(b.getActionCommand())];

		if (CheckMine.ZERO.equals(selectedBtn.getName())) {
			CheckMine.openAllZero(selectedBtn, tp, data);
		} else {
			CheckMine.setMineText(btn[Integer.parseInt(b.getActionCommand())]);
		}

		tframe.setContentPane(tp);

	}

	/**
	 * <p>周囲に埋められている地雷の数を設定する</p>
	 * @param btn JButton
	 * @param gm
	 * @return JButton
	 */
	public JButton[] mineNumber(JButton[] btn) {

		for (int i = 0; i < area; i++) {

			if (!MINE.equals(btn[i].getName())) {

				int mineNumber = 0;
				int upperLeft = i - width - 1;
				int up = i - width;
				int upperRight = i - width + 1;
				int left = i - 1;
				int right = i + 1;
				int lowerLeft = i + width - 1;
				int bottom = i + width;
				int lowerRight = i + width + 1;

				// 左上角の場合
				if (i == 0) {

					if(MINE.equals(btn[right].getName())) {
						mineNumber++;
					}
					if (MINE.equals(btn[bottom].getName())) {
						mineNumber++;
					}
					if (MINE.equals(btn[lowerRight].getName())) {
						mineNumber++;
					}

				// 右上角の場合
				} else if (i == width - 1) {

					if (MINE.contentEquals(btn[left].getName())) {
						mineNumber++;
					}
					if (MINE.contentEquals(btn[lowerLeft].getName())) {
						mineNumber++;
					}
					if (MINE.contentEquals(btn[bottom].getName())) {
						mineNumber++;
					}

				// 左下角の場合
				} else if (i == (height - 1) * width) {

					if (MINE.contentEquals(btn[up].getName())) {
						mineNumber++;
					}
					if (MINE.contentEquals(btn[upperRight].getName())) {
						mineNumber++;
					}
					if (MINE.contentEquals(btn[right].getName())) {
						mineNumber++;
					}

				// 右下角の場合
				} else if (i == area - 1) {

					if (MINE.contentEquals(btn[up].getName())) {
						mineNumber++;
					}
					if (MINE.contentEquals(btn[upperLeft].getName())) {
						mineNumber++;
					}
					if (MINE.contentEquals(btn[left].getName())) {
						mineNumber++;
					}

				// 上辺の場合
				} else if (i > 0 && i < width - 1) {

					if (MINE.equals(btn[left].getName())) {
						mineNumber++;
					}
					if (MINE.equals(btn[right].getName())) {
						mineNumber++;
					}
					if (MINE.equals(btn[lowerLeft].getName())) {
						mineNumber++;
					}
					if (MINE.equals(btn[bottom].getName())) {
						mineNumber++;
					}
					if(MINE.equals(btn[lowerRight].getName())) {
						mineNumber++;
					}

				// 左辺の場合
				} else if (i % width == 0) {

					if (MINE.contentEquals(btn[up].getName())) {
						mineNumber++;
					}
					if (MINE.contentEquals(btn[upperRight].getName())) {
						mineNumber++;
					}
					if (MINE.contentEquals(btn[right].getName())) {
						mineNumber++;
					}
					if (MINE.contentEquals(btn[bottom].getName())) {
						mineNumber++;
					}
					if (MINE.contentEquals(btn[lowerRight].getName())) {
						mineNumber++;
					}

				// 右辺の場合
				} else if ( (i % width) == (width - 1)) {

					if (MINE.contentEquals(btn[up].getName())) {
						mineNumber++;
					}
					if (MINE.contentEquals(btn[upperLeft].getName())) {
						mineNumber++;
					}
					if (MINE.contentEquals(btn[left].getName())) {
						mineNumber++;
					}
					if (MINE.contentEquals(btn[bottom].getName())) {
						mineNumber++;
					}
					if (MINE.contentEquals(btn[lowerLeft].getName())) {
						mineNumber++;
					}

				// 下辺の場合
				} else if ((i > (height - 1) * width) && (i < area - 1)) {
					if (MINE.contentEquals(btn[up].getName())) {
						mineNumber++;
					}
					if (MINE.contentEquals(btn[upperLeft].getName())) {
						mineNumber++;
					}
					if (MINE.contentEquals(btn[left].getName())) {
						mineNumber++;
					}
					if (MINE.contentEquals(btn[right].getName())) {
						mineNumber++;
					}
					if (MINE.contentEquals(btn[upperRight].getName())) {
						mineNumber++;
					}

				// 真ん中の場合
				} else {

					if (MINE.contentEquals(btn[up].getName())) {
						mineNumber++;
					}
					if (MINE.contentEquals(btn[upperLeft].getName())) {
						mineNumber++;
					}
					if (MINE.contentEquals(btn[left].getName())) {
						mineNumber++;
					}
					if (MINE.contentEquals(btn[right].getName())) {
						mineNumber++;
					}
					if (MINE.contentEquals(btn[upperRight].getName())) {
						mineNumber++;
					}
					if (MINE.contentEquals(btn[lowerLeft].getName())) {
						mineNumber++;
					}
					if (MINE.contentEquals(btn[bottom].getName())) {
						mineNumber++;
					}
					if (MINE.contentEquals(btn[lowerRight].getName())) {
						mineNumber++;
					}
				}

				btn[i].setName(String.valueOf(mineNumber));
			}
		}

		return btn;
	}


	/**
	 * <p>地雷が設置されている箇所を設定する。
	 * @return 地雷が設置されているか否か
	 */
	public JButton[] makeScreen(JButton b) {

		JButton[] btn = new JButton[area];
		int[] mine = Mine(b);

		for (int i = 0; i < area; i++) {

			btn[i] = new JButton();

			for(int j = 0; j < this.mine ; j++) {

				if (i == mine[j]) {

					btn[i].setName("mine");
					break;

				} else {

					btn[i].setName("");
				}
			}
		}

		return btn;
	}

	/**
	 * <p>地雷の設置箇所を決定する</p>
	 * @param gm ゲームの難易度
	 * @return 地雷の設置箇所
	 */
	private int[] Mine(JButton b) {

		List<Integer> mineList = new ArrayList<Integer>();
		int mine;
		int[] returnMine = new int[this.mine];

		for (int i = 0; i < this.mine; i++) {

			do {

				mine = (int)(Math.random() * area);

			} while (mineList.contains(mine) || String.valueOf(mine).equals(b.getActionCommand()));

			mineList.add(mine);

		}

		for(int i = 0; i < this.mine ; i++) {

			returnMine[i] = mineList.get(i);
		}

		return returnMine;
	}


	public void mouseEntered(MouseEvent e) {

	}

	public void mouseExited(MouseEvent e) {

	}

	public void mousePressed(MouseEvent e) {

	}

	public void mouseReleased(MouseEvent e) {

	}

	public void mouseClicked(MouseEvent e) {

		if(e.getButton() == MouseEvent.BUTTON3) {

			JButton thisBtn = (JButton)e.getSource();
			int number = Integer.parseInt(thisBtn.getActionCommand());
			JButton btn = (JButton)((JPanel)tframe.getContentPane()).getComponent(number);

			if(thisBtn.isEnabled() && !CheckMine.CHECKED.equals(thisBtn.getName())) {

				ImageIcon icon = new ImageIcon("./icon/flag.png");

				btn.setIcon(icon);
				btn.setEnabled(false);

			} else if (!thisBtn.isEnabled() && thisBtn.getIcon() != null) {

				btn.setEnabled(true);
				btn.setIcon(null);

			}

			flagCount();

		}
	}

	public void flagCount() {

		int checkedCount = 0;
		int flagCount = 0;

		for(int i = 0; i < area; i++) {

			JButton btn = (JButton)tp.getComponent(i);

			if (btn.getIcon() != null) {

				flagCount++;

			}

			if (MINE.equals(btn.getName()) && btn.getIcon() != null) {

				checkedCount++;

			}
		}

		if(checkedCount == mine && flagCount == mine) {

			int res = JOptionPane.showInternalConfirmDialog(tp,
					"あなたの勝ちです。\nもう一度遊びますか？",
					"Congratulations!!!", JOptionPane.YES_NO_OPTION);

			if (res == JOptionPane.YES_OPTION) {

				StartDisplay sd = new StartDisplay(tframe, tp);
				sd.makeFrame();

			} else {

				tframe.dispose();
			}
		}
	}
}

