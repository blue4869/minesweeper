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
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Random;
import java.util.stream.Collectors;
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

		JButton selectedButton = (JButton)e.getSource();
		String selectedButtonNumber = selectedButton.getActionCommand();

		// 地雷位置生成
		Random rand = new Random();
		IntStream minePosStream = rand.ints(0, data[AREA]);
		int[] minePosArray =  minePosStream
				.filter(minePos -> !String.valueOf(minePos).equals(selectedButtonNumber))
				.distinct().limit(data[I_MINE])
				.sorted().toArray();

		// 地雷をボタンに設定する。地雷がない場合は周囲の地雷数を設定する。
		int minePos_j = 0;
		aroundPosMap = new HashMap<>();
		for (Component component : p.getComponents()) {
			if (component instanceof JButton btn) {
				int btn_i = Integer.parseInt(btn.getActionCommand());
				if (minePos_j < data[I_MINE] && btn_i == minePosArray[minePos_j]) {
					btn.setName(Constants.MINE);
					minePos_j++;
				} else {
					btn.setName(String.valueOf(setAroundMineNumber(btn_i, minePosArray)));
				}
				btn.removeActionListener(this);
				btn.addActionListener(new CheckMine(this.frame, this.p, data));
				btn.addMouseListener(this);
			}
		}

		// クリックされたボタンを開ける
		CheckMine.nameCheck(selectedButton, p, data);

	}

	/**
	 * <p>周囲に埋められている地雷の数を設定する</p>
	 * @param pos ボタン位置
	 * @return JButton
	 */
	public int setAroundMineNumber(int pos, int[] minePosArray) {

		int upperLeft = pos - data[WIDTH] - 1;
		int up = pos - data[WIDTH];
		int upperRight = pos - data[WIDTH] + 1;
		int left = pos - 1;
		int right = pos + 1;
		int lowerLeft = pos + data[WIDTH] - 1;
		int bottom = pos + data[WIDTH];
		int lowerRight = pos + data[WIDTH] + 1;

		List<Integer> aroundButtonPosList;
		if (pos == 0) {
			// 左上角の場合
			aroundButtonPosList = List.of(right, bottom, lowerRight);
		} else if (pos == data[WIDTH] - 1) {
			// 右上角の場合
			aroundButtonPosList = List.of(left, lowerLeft, bottom);
		} else if (pos == (data[HEIGHT] - 1) * data[WIDTH]) {
			// 左下角の場合
			aroundButtonPosList = List.of(up, upperRight, right);
		} else if (pos == data[AREA] - 1) {
			// 右下角の場合
			aroundButtonPosList = List.of(up, upperLeft, left);
		} else if (pos < data[WIDTH] - 1) {
			// 上辺の場合
			aroundButtonPosList = List.of(left, right, lowerLeft, bottom, lowerRight);
		} else if (pos % data[WIDTH] == 0) {
			// 左辺の場合
			aroundButtonPosList = List.of(up, upperRight, right, bottom, lowerRight);
		} else if (pos % data[WIDTH] == data[WIDTH] - 1) {
			// 右辺の場合
			aroundButtonPosList = List.of(up, upperLeft, left, bottom, lowerLeft);
		} else if (pos > (data[HEIGHT] - 1) * data[WIDTH] && pos < data[AREA] - 1) {
			// 下辺の場合
			aroundButtonPosList = List.of(up, upperLeft, left, right, upperRight);
		} else {
			// 真ん中の場合
			aroundButtonPosList = List.of(upperLeft, up, upperRight, left, right, lowerLeft, bottom, lowerRight);
		}

		aroundPosMap.put(pos, aroundButtonPosList);
		List<Integer> minePosList = Arrays.stream(minePosArray).boxed().collect(Collectors.toList());
		long mineCount = aroundButtonPosList.stream()
				.filter(minePosList::contains)
				.count();

		return Math.toIntExact(mineCount);
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
			JButton selectedBtn = (JButton)e.getSource();
			int number = Integer.parseInt(selectedBtn.getActionCommand());
			JButton btn = (JButton) p.getComponent(number);
			if(selectedBtn.isEnabled() && !CheckMine.CHECKED.equals(selectedBtn.getName())) {
				ImageIcon icon = new ImageIcon("./icon/flag.png");
				btn.setIcon(icon);
				btn.setEnabled(false);
			} else if (!selectedBtn.isEnabled() && Objects.nonNull(selectedBtn.getIcon())) {
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

