package KaranP.Rudi;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class ConnectFourPanel extends JPanel {
	private final static int GRID_ROW = 7, GRID_COL = 7, HGAP = 10, VGAP = 10;
	private static JLabel[][] board;
	private static JButton[] placement;
	private static int[][] grid;
	private final static int BOARD_ROW = 6, BOARD_COL = 7;
	private final static int EMPTY = 0, P1 = 1, P2 = 2;

	private boolean turnP1;
	private boolean turnP2;

	public ConnectFourPanel() {
		setPreferredSize(new Dimension(770, 780));
		setBackground(new Color(0, 0, 0));

		//
		setLayout(new GridLayout(GRID_ROW, GRID_COL, HGAP, VGAP));
		setBorder(BorderFactory.createLineBorder(Color.BLACK));

		placement = new JButton[BOARD_COL];
		for (int i = 0; i < placement.length; i++) {
			placement[i] = new JButton();
			placement[i].setHorizontalAlignment(SwingConstants.CENTER);
			placement[i].setContentAreaFilled(false);
			placement[i].setEnabled(false);
			placement[i].setBorder(BorderFactory.createLineBorder(Color.WHITE));
			placement[i].addActionListener(new ButtonActionListener());
			add(placement[i]);
		}

		board = new JLabel[BOARD_ROW][BOARD_COL];
		grid = new int[BOARD_ROW][BOARD_COL];
		for (int i = 0; i < BOARD_ROW; i++) {
			for (int j = 0; j < BOARD_COL; j++) {
				board[i][j] = new JLabel("");
				grid[i][j] = EMPTY;
				board[i][j].setOpaque(true);
				board[i][j].setBackground(Color.WHITE);
				board[i][j].setBorder(BorderFactory.createLineBorder(Color.WHITE));
				board[i][j].setEnabled(false);
				add(board[i][j]);
			}
		}
		loadImageFile();
	}

	private class ButtonActionListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			int columnPlaced;

			if (event.getSource() == placement[0]) {
				
			}

		}
	}

	private void loadImageFile() {
		try {
			Image downArrow = ImageIO.read(getClass().getResource("/resources/DownArrow.png"));
			for (int i = 0; i < placement.length; i++) {
				placement[i].setIcon(new ImageIcon(downArrow));
			}
			Image area = ImageIO.read(getClass().getResource("/resources/area.png"));
			for (int i = 0; i < BOARD_ROW; i++) {
				for (int j = 0; j < BOARD_COL; j++) {
					board[i][j].setIcon(new ImageIcon(area));
				}
			}
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Images Could Not Be Loaded!");
			System.exit(0); // terminates code
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Something Went Wrong!");
			System.exit(0); // terminates code
		}
	}

	private void bluePlaced(int posRow, int posCol) {
		board[posRow][posCol].setBackground(Color.BLUE);

	}

	private void redPlaced(int posRow, int posCol) {
		board[posRow][posCol].setBackground(Color.RED);
	}

	public void setBoardVisible(boolean visible){
		if(visible){
			for (int i = 0; i < placement.length; i++) {
				placement[i].setEnabled(true);
			}
			for (int i = 0; i < BOARD_ROW; i++) {
				for (int j = 0; j < BOARD_COL; j++) {
					board[i][j].setEnabled(true);
				}
			}
		}else if(!visible){
			for (int i = 0; i < placement.length; i++) {
				placement[i].setEnabled(false);
			}
			for (int i = 0; i < BOARD_ROW; i++) {
				for (int j = 0; j < BOARD_COL; j++) {
					board[i][j].setEnabled(false);
				}
			}
		}
	}
}
