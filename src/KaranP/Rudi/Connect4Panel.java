package KaranP.Rudi;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class Connect4Panel extends JPanel {
	int ROWS = 7, COLUMNS = 7, HGAP = 10, VGAP = 10;
	JLabel[][] board;
	JButton[] placement;

	public Connect4Panel() {
		setPreferredSize(new Dimension(770, 780));
		setBackground(new Color(0, 0, 0));

		//
		setLayout(new GridLayout(ROWS, COLUMNS, HGAP, VGAP));
		setBorder(BorderFactory.createLineBorder(Color.BLACK));

		placement = new JButton[7];
		for (int i = 0; i < placement.length; i++) {
			placement[i] = new JButton();
			placement[i].setHorizontalAlignment(SwingConstants.CENTER);
			placement[i].setContentAreaFilled(false);
			placement[i].setEnabled(true);
			placement[i].setBorder(BorderFactory.createLineBorder(Color.WHITE));
			add(placement[i]);
		}		
		
		board = new JLabel[7][6];
		for (int i = 0; i < 7; i++) {
			for (int j = 0; j < 6; j++) {
				board[i][j] = new JLabel();
				board[i][j].setOpaque(true);
				board[i][j].setBackground(Color.RED);
				//board[i][j].setForeground(Color.WHITE);
				board[i][j].setBorder(BorderFactory.createLineBorder(Color.WHITE));
				add(board[i][j]);
			}
		}		
		loadImageFile();
		
	}
	private void loadImageFile(){
		try {
		    Image downArrow = ImageIO.read(getClass().getResource("/resources/DownArrow.png"));
		    for (int i = 0; i < placement.length; i++) {
		    	placement[i].setIcon(new ImageIcon(downArrow));
		    }
		    Image area = ImageIO.read(getClass().getResource("/resources/area.png"));
		    for (int i = 0; i < 7; i++) {
				for (int j = 0; j < 6; j++) {
					board[i][j].setIcon(new ImageIcon(area));
				}
			}		    
		  }catch (IOException e) {
				JOptionPane.showMessageDialog(null, "Images Could Not Be Loaded!");
				System.exit(0); // terminates code
		  }catch (Exception e) {
				JOptionPane.showMessageDialog(null, "Something Went Wrong!");
				System.exit(0); // terminates code
		  }
	}
}
