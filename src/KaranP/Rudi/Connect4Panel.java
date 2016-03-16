package KaranP.Rudi;

import javax.swing.*;
import java.awt.*;

public class Connect4Panel extends JPanel {
	int ROWS = 7, COLUMNS = 7, HGAP = 10, VGAP = 10;
	JLabel[][] board;
	JButton[] placement;

	public Connect4Panel() {
		setPreferredSize(new Dimension(770, 770));
		setBackground(new Color(0, 0, 0));

		//
		setLayout(new GridLayout(ROWS, COLUMNS, HGAP, VGAP));
		setBorder(BorderFactory.createLineBorder(Color.BLACK));

		placement = new JButton[7];
		for (int i = 0; i < placement.length; i++) {
			placement[i] = new JButton("");
			placement[i].setContentAreaFilled(true);
			placement[i].setEnabled(true);
			placement[i].setBorder(BorderFactory.createLineBorder(Color.WHITE));
			add(placement[i]);
		}

		board = new JLabel[7][6];
		for (int i = 0; i < 7; i++) {
			for (int j = 0; j < 6; j++) {
				board[i][j] = new JLabel("?");
				board[i][j].setForeground(Color.WHITE);
				board[i][j].setBorder(BorderFactory.createLineBorder(Color.WHITE));
				add(board[i][j]);
			}
		}

	}
}
