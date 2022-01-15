package swing;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class CheckMine extends JFrame implements ActionListener {

	private JFrame frame;
	private JPanel p;
	private int[] data;

	/** <p>地雷</p> */
	private static final String MINE = "mine";

	/**
	 * <p>周囲の地雷の数</p>
	 */
	public static final String ZERO = "0";

	/**
	 * <p>すでに確認したマスかのフラグ</p>
	 */
	public static final String CHECKED = "checked";

	public CheckMine (JFrame frame, JPanel p, int[] data) {
		this.frame = frame;
		this.p = p;
		this.data = data;
	}

	public void actionPerformed(ActionEvent e) {

		JButton b = (JButton)e.getSource();
		String name = b.getName();

		if(MINE.equals(name)) {
			for (int i = 0; i < data[GameMake.AREA]; i++) {
				JButton btn = (JButton)p.getComponent(i);
				String btnName = btn.getName();
				if(btn.getIcon() != null) {
					btn.setIcon(null);
				}
				if(!CHECKED.equals(btnName)) {
					setMineText(btn);
				}
			}

			int res = JOptionPane.showInternalConfirmDialog(p,
					"あなたの負けです。\nもう一度遊びますか？",
					"GAME OVER!!!", JOptionPane.YES_NO_OPTION);

			if (res == JOptionPane.YES_OPTION) {
				StartDisplay sd = new StartDisplay(frame, p);
				sd.makeFrame();
			} else {
				frame.dispose();
			}
		} else {
			if (ZERO.equals(name)) {
				openAllZero(b, p, data);
			} else {
				setMineText(b);
			}
		}

		int checkedCount = checkedCount(p);

		if (checkedCount == data[GameMake.AREA] - data[GameMake.IMINE]) {
			int res = JOptionPane.showInternalConfirmDialog(p,
					"あなたの勝ちです。\nもう一度遊びますか？",
					"Congratulations!!!", JOptionPane.YES_NO_OPTION);

			if (res == JOptionPane.YES_OPTION) {
				StartDisplay sd = new StartDisplay(frame, p);
				sd.makeFrame();
			} else {
				frame.dispose();
			}
		}
	}

	public static void openAllZero(JButton b, JPanel p, int[] data) {

		int position = Integer.parseInt(b.getActionCommand());

		setMineText(b);

		// 左上角の場合
		if (position == 0) {
			JButton right = (JButton)p.getComponent(position + 1);
			String rightName = right.getName();
			if(!CHECKED.equals(rightName)) {
				if (ZERO.equals(rightName)) {
					openAllZero(right, p, data);
				} else {
					setMineText(right);
					(right).setEnabled(false);
				}
			}

			JButton bottom = (JButton)p.getComponent(position + data[GameMake.WIDTH]);
			String bottomName = bottom.getName();
			if(!CHECKED.equals(bottomName)) {
				if (ZERO.equals(bottomName)) {
					openAllZero(bottom, p, data);
				} else {
					setMineText(bottom);
					(bottom).setEnabled(false);
				}
			}

			JButton lowerRight = (JButton)p.getComponent(position + data[GameMake.WIDTH] + 1);
			String lowerRightName = lowerRight.getName();
			if(!CHECKED.equals(lowerRightName)) {
				if (ZERO.equals(lowerRightName)) {
					openAllZero(lowerRight, p, data);
				} else {
					setMineText(lowerRight);
					(lowerRight).setEnabled(false);
				}
			}

		// 右上角の場合
		} else 	if (position == data[GameMake.WIDTH] - 1) {
			JButton left = (JButton)p.getComponent(position - 1);
			String leftName = left.getName();
			if(!CHECKED.equals(leftName)) {
				if (ZERO.equals(leftName)) {
					openAllZero(left, p, data);
				} else {
					setMineText(left);
					(left).setEnabled(false);
				}
			}

			JButton bottom = (JButton)p.getComponent(position + data[GameMake.WIDTH]);
			String bottomName = bottom.getName();
			if(!CHECKED.equals(bottomName)) {
				if (ZERO.equals(bottomName)) {
					openAllZero(bottom, p, data);
				} else {
					setMineText(bottom);
					(bottom).setEnabled(false);
				}
			}

			JButton lowerLeft = (JButton)p.getComponent(position + data[GameMake.WIDTH] - 1);
			String lowerLeftName = lowerLeft.getName();
			if(!CHECKED.equals(lowerLeftName)) {
				if (ZERO.equals(lowerLeftName)) {
					openAllZero(lowerLeft, p, data);
				} else {
					setMineText(lowerLeft);
					(lowerLeft).setEnabled(false);
				}
			}
		// 左下角の場合
		} else 	if (position == (data[GameMake.HEIGHT] - 1) * data[GameMake.WIDTH]) {

			JButton right = (JButton)p.getComponent(position + 1);
			String rightName = right.getName();
			if(!CHECKED.equals(rightName)) {
				if (ZERO.equals(rightName)) {
					openAllZero(right, p, data);
				} else {
					setMineText(right);
					(right).setEnabled(false);
				}
			}

			JButton up = (JButton)p.getComponent(position - data[GameMake.WIDTH]);
			String upName = up.getName();

			if(!CHECKED.equals(upName)) {
				if (ZERO.equals(upName)) {
					openAllZero(up, p, data);
				} else {
					setMineText(up);
					(up).setEnabled(false);
				}
			}

			JButton upperRight = (JButton)p.getComponent(position - data[GameMake.WIDTH] + 1);
			String upperRightName = upperRight.getName();
			if(!CHECKED.equals(upperRightName)) {
				if (ZERO.equals(upperRightName)) {
					openAllZero(upperRight, p, data);
				} else {
					setMineText(upperRight);
					(upperRight).setEnabled(false);
				}
			}
		// 右下角の場合
		} else 	if (position == data[GameMake.AREA] - 1) {

			JButton left = (JButton)p.getComponent(position - 1);
			String leftName = left.getName();

			if(!CHECKED.equals(leftName)) {
				if (ZERO.equals(leftName)) {
					openAllZero(left, p, data);
				} else {
					setMineText(left);
					(left).setEnabled(false);
				}
			}

			JButton up = (JButton)p.getComponent(position - data[GameMake.WIDTH]);
			String upName = up.getName();
			if(!CHECKED.equals(upName)) {
				if (ZERO.equals(upName)) {
					openAllZero(up, p, data);
				} else {
					setMineText(up);
					(up).setEnabled(false);
				}
			}

			JButton upperLeft = (JButton)p.getComponent(position - data[GameMake.WIDTH] - 1);
			String upperLeftName = upperLeft.getName();
			if(!CHECKED.equals(upperLeftName)) {
				if (ZERO.equals(upperLeftName)) {
					openAllZero(upperLeft, p, data);
				} else {
					setMineText(upperLeft);
					(upperLeft).setEnabled(false);
				}
			}
		// 上辺の場合
		} else 	if (position > 0 && position < data[GameMake.WIDTH] - 1) {

			JButton left = (JButton)p.getComponent(position - 1);
			JButton right = (JButton)p.getComponent(position + 1);
			JButton lowerLeft = (JButton)p.getComponent(position + data[GameMake.WIDTH] - 1);
			JButton bottom = (JButton)p.getComponent(position + data[GameMake.WIDTH]);
			JButton lowerRight = (JButton)p.getComponent(position + data[GameMake.WIDTH] + 1);

			String leftName = left.getName();
			if(!CHECKED.equals(leftName)) {
				if (ZERO.equals(leftName)) {
					openAllZero(left, p, data);
				} else {
					setMineText(left);
					(left).setEnabled(false);
				}
			}

			String rightName = right.getName();
			if(!CHECKED.equals(rightName)) {
				if (ZERO.equals(rightName)) {
					openAllZero(right, p, data);
				} else {
					setMineText(right);
					(right).setEnabled(false);
				}
			}

			String lowerLeftName = lowerLeft.getName();
			if(!CHECKED.equals(lowerLeftName)) {
				if (ZERO.equals(lowerLeftName)) {
					openAllZero(lowerLeft, p, data);
				} else {
					setMineText(lowerLeft);
					(lowerLeft).setEnabled(false);
				}
			}

			String bottomName = bottom.getName();
			if(!CHECKED.equals(bottomName)) {
				if (ZERO.equals(bottomName)) {
					openAllZero(bottom, p, data);
				} else {
					setMineText(bottom);
					(bottom).setEnabled(false);
				}
			}

			String lowerRightName = lowerRight.getName();
			if(!CHECKED.equals(lowerRightName)) {
				if (ZERO.equals(lowerRightName)) {
					openAllZero(lowerRight, p, data);
				} else {
					setMineText(lowerRight);
					(lowerRight).setEnabled(false);
				}
			}
		// 左辺の場合
		} else 	if (position % data[GameMake.WIDTH] == 0) {

			JButton up = (JButton)p.getComponent(position - data[GameMake.WIDTH]);
			JButton upperRight = (JButton)p.getComponent(position - data[GameMake.WIDTH] + 1);
			JButton right = (JButton)p.getComponent(position + 1);
			JButton bottom = (JButton)p.getComponent(position + data[GameMake.WIDTH]);
			JButton lowerRight = (JButton)p.getComponent(position + data[GameMake.WIDTH] + 1);

			String upName = up.getName();
			if(!CHECKED.equals(upName)) {
				if (ZERO.equals(upName)) {
					openAllZero(up, p, data);
				} else {
					setMineText(up);
					(up).setEnabled(false);
				}
			}

			String upperRightName = upperRight.getName();
			if(!CHECKED.equals(upperRightName)) {
				if (ZERO.equals(upperRightName)) {
					openAllZero(upperRight, p, data);
				} else {
					setMineText(upperRight);
					(upperRight).setEnabled(false);
				}
			}

			String rightName = right.getName();
			if(!CHECKED.equals(rightName)) {
				if (ZERO.equals(rightName)) {
					openAllZero(right, p, data);
				} else {
					setMineText(right);
					(right).setEnabled(false);
				}
			}

			String lowerRightName = lowerRight.getName();
			if(!CHECKED.equals(lowerRightName)) {
				if (ZERO.equals(lowerRightName)) {
					openAllZero(lowerRight, p, data);
				} else {
					setMineText(lowerRight);
					(lowerRight).setEnabled(false);
				}
			}

			String bottomName = bottom.getName();
			if(!CHECKED.equals(bottomName)) {
				if (ZERO.equals(bottomName)) {
					openAllZero(bottom, p, data);
				} else {
					setMineText(bottom);
					(bottom).setEnabled(false);
				}
			}
		// 右辺の場合
		} else 	if ((position % data[GameMake.WIDTH]) == (data[GameMake.WIDTH] - 1)) {

			JButton upperLeft = (JButton)p.getComponent(position - data[GameMake.WIDTH] - 1);
			JButton up = (JButton)p.getComponent(position - data[GameMake.WIDTH]);
			JButton left = (JButton)p.getComponent(position - 1);
			JButton lowerLeft = (JButton)p.getComponent(position + data[GameMake.WIDTH] - 1);
			JButton bottom = (JButton)p.getComponent(position + data[GameMake.WIDTH]);

			String upName = up.getName();
			if(!CHECKED.equals(upName)) {
				if (ZERO.equals(upName)) {
					openAllZero(up, p, data);
				} else {
					setMineText(up);
					(up).setEnabled(false);
				}
			}

			String upperLeftName = upperLeft.getName();

			if(!CHECKED.equals(upperLeftName)) {
				if (ZERO.equals(upperLeftName)) {
					openAllZero(upperLeft, p, data);
				} else {
					setMineText(upperLeft);
					(upperLeft).setEnabled(false);
				}
			}

			String leftName = left.getName();

			if(!CHECKED.equals(leftName)) {
				if (ZERO.equals(leftName)) {
					openAllZero(left, p, data);
				} else {
					setMineText(left);
					(left).setEnabled(false);
				}
			}

			String lowerLeftName = lowerLeft.getName();

			if(!CHECKED.equals(lowerLeftName)) {
				if (ZERO.equals(lowerLeftName)) {
					openAllZero(lowerLeft, p, data);
				} else {
					setMineText(lowerLeft);
					(lowerLeft).setEnabled(false);
				}
			}

			String bottomName = bottom.getName();
			if(!CHECKED.equals(bottomName)) {
				if (ZERO.equals(bottomName)) {
					openAllZero(bottom, p, data);
				} else {
					setMineText(bottom);
					(bottom).setEnabled(false);
				}
			}
		// 下辺の場合
		} else 	if ((position > (data[GameMake.HEIGHT] - 1) * data[GameMake.WIDTH])
				&& (position < data[GameMake.AREA] - 1)) {

			JButton upperLeft = (JButton)p.getComponent(position - data[GameMake.WIDTH] - 1);
			JButton up = (JButton)p.getComponent(position - data[GameMake.WIDTH]);
			JButton upperRight = (JButton)p.getComponent(position - data[GameMake.WIDTH] + 1);
			JButton left = (JButton)p.getComponent(position - 1);
			JButton right = (JButton)p.getComponent(position + 1);

			String upName = up.getName();
			if(!CHECKED.equals(upName)) {
				if (ZERO.equals(upName)) {
					openAllZero(up, p, data);
				} else {
					setMineText(up);
					(up).setEnabled(false);
				}
			}

			String upperRightName = upperRight.getName();
			if(!CHECKED.equals(upperRightName)) {
				if (ZERO.equals(upperRightName)) {
					openAllZero(upperRight, p, data);
				} else {
					setMineText(upperRight);
					(upperRight).setEnabled(false);
				}
			}

			String rightName = right.getName();

			if(!CHECKED.equals(rightName)) {
				if (ZERO.equals(rightName)) {
					openAllZero(right, p, data);
				} else {
					setMineText(right);
					(right).setEnabled(false);
				}
			}

			String upperLeftName = upperLeft.getName();

			if(!CHECKED.equals(upperLeftName)) {
				if (ZERO.equals(upperLeftName)) {
					openAllZero(upperLeft, p, data);
				} else {
					setMineText(upperLeft);
					(upperLeft).setEnabled(false);
				}
			}

			String leftName = left.getName();
			if(!CHECKED.equals(leftName)) {
				if (ZERO.equals(leftName)) {
					openAllZero(left, p, data);
				} else {
					setMineText(left);
					(left).setEnabled(false);
				}
			}
		// 真ん中の場合
		} else {

			JButton upperLeft = (JButton)p.getComponent(position - data[GameMake.WIDTH] - 1);
			JButton up = (JButton)p.getComponent(position - data[GameMake.WIDTH]);
			JButton upperRight = (JButton)p.getComponent(position - data[GameMake.WIDTH] + 1);
			JButton left = (JButton)p.getComponent(position - 1);
			JButton right = (JButton)p.getComponent(position + 1);
			JButton lowerLeft = (JButton)p.getComponent(position + data[GameMake.WIDTH] - 1);
			JButton bottom = (JButton)p.getComponent(position + data[GameMake.WIDTH]);
			JButton lowerRight = (JButton)p.getComponent(position + data[GameMake.WIDTH] + 1);

			String upName = up.getName();

			if(!CHECKED.equals(upName)) {
				if (ZERO.equals(upName)) {
					openAllZero(up, p, data);
				} else {
					setMineText(up);
					(up).setEnabled(false);
				}
			}

			String upperRightName = upperRight.getName();
			if(!CHECKED.equals(upperRightName)) {
				if (ZERO.equals(upperRightName)) {
					openAllZero(upperRight, p, data);
				} else {
					setMineText(upperRight);
					(upperRight).setEnabled(false);
				}
			}

			String rightName = right.getName();
			if(!CHECKED.equals(rightName)) {
				if (ZERO.equals(rightName)) {
					openAllZero(right, p, data);
				} else {
					setMineText(right);
					(right).setEnabled(false);
				}
			}

			String upperLeftName = upperLeft.getName();
			if(!CHECKED.equals(upperLeftName)) {
				if (ZERO.equals(upperLeftName)) {
					openAllZero(upperLeft, p, data);
				} else {
					setMineText(upperLeft);
					(upperLeft).setEnabled(false);
				}
			}

			String leftName = left.getName();
			if(!CHECKED.equals(leftName)) {
				if (ZERO.equals(leftName)) {
					openAllZero(left, p, data);
				} else {
					setMineText(left);
					(left).setEnabled(false);
				}
			}

			String lowerLeftName = lowerLeft.getName();

			if(!CHECKED.equals(lowerLeftName)) {
				if (ZERO.equals(lowerLeftName)) {
					openAllZero(lowerLeft, p, data);
				} else {
					setMineText(lowerLeft);
					(lowerLeft).setEnabled(false);
				}
			}

			String bottomName = bottom.getName();
			if(!CHECKED.equals(bottomName)) {
				if (ZERO.equals(bottomName)) {
					openAllZero(bottom, p, data);
				} else {
					setMineText(bottom);
					(bottom).setEnabled(false);
				}
			}

			String lowerRightName = lowerRight.getName();
			if(!CHECKED.equals(lowerRightName)) {
				if (ZERO.equals(lowerRightName)) {
					openAllZero(lowerRight, p, data);
				} else {
					setMineText(lowerRight);
					(lowerRight).setEnabled(false);
				}
			}
		}
	}

	/**
	 * <p>選択されているマスの数を数える
	 * @param p JPanel
	 * @return 選択されている数
	 */
	private int checkedCount(JPanel p) {

		int checkedCount = 0;

		for(int i = 0; i < data[GameMake.AREA]; i++) {
			if(CHECKED.equals(((JButton)p.getComponent(i)).getName())) {
				checkedCount++;
			}
		}

		return checkedCount;
	}

	/**
	 * <p>選択されたボタンに文字を表示させる</p>
	 */
	public static void setMineText(JButton b) {

		b.setFont(new Font("HGPｺﾞｼｯｸE", Font.BOLD, 20));

		if ("0".equals(b.getName())) {
			b.setText("");
		} else if (MINE.equals(b.getName())) {
			b.setText("*");
		}else {
			b.setText(b.getName());
		}

		b.setName(CHECKED);
		b.setEnabled(false);
	}

}
