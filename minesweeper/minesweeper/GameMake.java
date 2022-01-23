package swing.minesweeper;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Random;
import java.util.stream.IntStream;

public class GameMake extends JFrame implements ActionListener, MouseListener {

	/** JFrame */
	private final JFrame frame;

	/** JPanel */
	private final JPanel p;

	/** ボタンの列数、行数、全ボタン数、地雷数格納用配列 */
	private final int[] data = new int[4];

	/** ボタンの周辺位置情報を格納するMap */
	public static Map<Integer, List<Integer>> aroundPosMap;

	/** ボタン_列数の格納位置 */
	public static final int WIDTH = 0;

	/** ボタン_行数の格納位置 */
	public static final int HEIGHT = 1;

	/** data[AREA]の格納位置 */
	public static final int AREA = 2;

	/** data[I_MINE]の格納位置 */
	public static final int I_MINE = 3;

	/**
	 * コンストラクタ
	 *
	 * @param frame JFrame
	 * @param p JPanel
	 */
	public GameMake(JFrame frame, JPanel p) {
		this.frame = frame;
		this.p = p;
	}

	/**
	 * ゲーム画面作製処理
	 * ゲーム開始画面から受け取った難易度を基に行数、列数を決定する
	 *
	 * @param level ゲーム開始画面で選択した難易度
	 */
	public void makeFrame(String level) {

		ConstLevels prop = ConstLevels.getConstMap().get(level);
		data[WIDTH] = prop.getWidth();
		data[HEIGHT] = prop.getHeight();
		data[AREA] = data[WIDTH] * data[HEIGHT];
		data[I_MINE] = prop.getMineNum();

		p.removeAll();
		frame.setTitle("MineSweeper!");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setBounds(100, 100, 50 * data[WIDTH], 50 * data[HEIGHT]);
		p.setLayout(new GridLayout(data[HEIGHT], data[WIDTH]));

		var btn = new JButton[data[AREA]];
		for (int i = 0; i < data[AREA]; i++) {
			btn[i] = new JButton();
			btn[i].setActionCommand(String.valueOf(i));
			btn[i].addActionListener(this);
			p.add(btn[i]);
		}

		frame.setContentPane(p);
		frame.setVisible(true);
	}

	/**
	 * ゲーム開始時、最初のボタンを押下した時の処理<br>
	 * いきなりゲームオーバーにならないように、最初に押したボタンは地雷にならないように設定する
	 */
	public void actionPerformed (ActionEvent e) {

		JButton b = (JButton)e.getSource();
		JButton[] btn = embedMine();
		btn = mineNumber(btn);

		this.p.removeAll();
		for(int i = 0; i < data[AREA]; i++) {
			btn[i].setActionCommand(String.valueOf(i));
			btn[i].addActionListener(new CheckMine(this.frame, this.p, data));
			btn[i].addMouseListener(this);
			this.p.add(btn[i]);
		}

		JButton selectedBtn = btn[Integer.parseInt(b.getActionCommand())];
		CheckMine.nameCheck(selectedBtn, p, data);

		this.frame.setContentPane(p);
	}

	/**
	 * <p>周囲に埋められている地雷の数を設定する</p>
	 * @param btn JButton
	 * @return JButton
	 */
	public JButton[] mineNumber(JButton[] btn) {

		aroundPosMap = new HashMap<>();
		for (int i = 0; i < data[AREA]; i++) {
			JButton targetBtn = btn[i];
			if (Constants.MINE.equals(targetBtn.getName())) {
				// 地雷ならば周囲の地雷数は不要
				continue;
			}

			int upperLeft = i - data[WIDTH] - 1;
			int up = i - data[WIDTH];
			int upperRight = i - data[WIDTH] + 1;
			int left = i - 1;
			int right = i + 1;
			int lowerLeft = i + data[WIDTH] - 1;
			int bottom = i + data[WIDTH];
			int lowerRight = i + data[WIDTH] + 1;

			if (i == 0) {
				// 左上角の場合
				aroundPosMap.put(i, List.of(right, bottom, lowerRight));
			} else if (i == data[WIDTH] - 1) {
				// 右上角の場合
				aroundPosMap.put(i, List.of(left, lowerLeft, bottom));
			} else if (i == (data[HEIGHT] - 1) * data[WIDTH]) {
				// 左下角の場合
				aroundPosMap.put(i, List.of(up, upperRight, right));
			} else if (i == data[AREA] - 1) {
				// 右下角の場合
				aroundPosMap.put(i, List.of(up, upperLeft, left));
			} else if (i < data[WIDTH] - 1) {
				// 上辺の場合
				aroundPosMap.put(i, List.of(left, right, lowerLeft, bottom, lowerRight));
			} else if (i % data[WIDTH] == 0) {
				// 左辺の場合
				aroundPosMap.put(i, List.of(up, upperRight, right, bottom, lowerRight));
			} else if ( (i % data[WIDTH]) == (data[WIDTH] - 1)) {
				// 右辺の場合
				aroundPosMap.put(i, List.of(up, upperLeft, left, bottom, lowerLeft));
			} else if ((i > (data[HEIGHT] - 1) * data[WIDTH]) && (i < data[AREA] - 1)) {
				// 下辺の場合
				aroundPosMap.put(i, List.of(up, upperLeft, left, right, upperRight));
			} else {
				// 真ん中の場合
				aroundPosMap.put(i, List.of(upperLeft, up, upperRight, left, right, lowerLeft, bottom, lowerRight));
			}
			targetBtn.setName(String.valueOf(aroundMineNumber(btn, i)));
		}

		return btn;
	}

	/**
	 * 周辺の地雷数を計算する
	 * @return 地雷数
	 */
	private int aroundMineNumber(JButton[] btn, int i) {
		int mineNumber = 0;
		for (Integer pos : aroundPosMap.get(i)) {
			if (Constants.MINE.equals(btn[pos].getName())) {
				mineNumber++;
			}
		}
		return mineNumber;
	}

	/**
	 * <p>地雷が設置されている箇所を設定する。
	 * @return 地雷が設置されているか否か
	 */
	public JButton[] embedMine() {

		JButton[] btn = new JButton[data[AREA]];
		int[] mine = Mine();

		for (int button_i = 0; button_i < data[AREA]; button_i++) {
			btn[button_i] = new JButton();
			for(int mine_j = 0; mine_j < data[I_MINE] ; mine_j++) {
				if (button_i == mine[mine_j]) {
					btn[button_i].setName(Constants.MINE);
					break;
				} else {
					btn[button_i].setName(Constants.NONE);
				}
			}
		}

		return btn;
	}

	/**
	 * <p>地雷の設置箇所を決定する</p>
	 *
	 * @return 地雷の設置箇所
	 */
	private int[] Mine() {

		Random rand = new Random();
		IntStream minePosStream = rand.ints(0, data[AREA]);

		return minePosStream.distinct().limit(data[I_MINE]).sorted().toArray();
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
			JButton btn = (JButton) frame.getContentPane().getComponent(number);

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

		for (Component component : p.getComponents()) {
			if (component instanceof JButton btn) {
				if (Objects.nonNull(btn.getIcon())) {
					flagCount++;
				}
				if (Constants.MINE.equals(btn.getName()) && Objects.nonNull(btn.getIcon())) {
					checkedCount++;
				}
			}
		}

		if(checkedCount == data[I_MINE] && flagCount == data[I_MINE]) {
			int res = JOptionPane.showInternalConfirmDialog(p,
					"あなたの勝ちです。\nもう一度遊びますか？",
					"Congratulations!!!", JOptionPane.YES_NO_OPTION);

			CheckMine.selectContinue(frame, res);
		}
	}
}

