package KaranP.Rudi;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;


public class SetUpPanel extends JPanel{
	private static JButton twoPlayersBtn, playAIBtn;
	private static JLabel player1Lbl, player2Lbl, AILbl;
	private JPanel mainPanel, setUpPanel;
	private ConnectFourPanel startConnectFour;
	
	public SetUpPanel(){
		// Buttons
		twoPlayersBtn = new JButton("Play With Two Player");
		twoPlayersBtn.setFont(new Font("Sans Serif", Font.ITALIC, 20));
		twoPlayersBtn.setHorizontalAlignment(SwingConstants.CENTER);
		twoPlayersBtn.setContentAreaFilled(true);
		twoPlayersBtn.setEnabled(true);
		twoPlayersBtn.addActionListener(new ButtonActionListener());
		
		playAIBtn = new JButton("Play With Computer");
		playAIBtn.setFont(new Font("Sans Serif", Font.ITALIC, 20));
		playAIBtn.setHorizontalAlignment(SwingConstants.CENTER);
		playAIBtn.setContentAreaFilled(true);
		playAIBtn.setEnabled(false);
		playAIBtn.addActionListener(new ButtonActionListener());
		
		// Labels
		player1Lbl = new JLabel("Player 1 > Blue Chip");
		player1Lbl.setFont(new Font("Sans Serif", Font.BOLD, 24));
		player1Lbl.setHorizontalAlignment(SwingConstants.CENTER);
		player1Lbl.setOpaque(true);
		player1Lbl.setBackground(Color.BLUE);
		player1Lbl.setForeground(Color.WHITE);
		player1Lbl.setBorder(BorderFactory.createLineBorder(Color.WHITE));
		
		player2Lbl = new JLabel("Player 2 > Red Chip");
		player2Lbl.setFont(new Font("Sans Serif", Font.BOLD, 24));
		player2Lbl.setHorizontalAlignment(SwingConstants.CENTER);
		player2Lbl.setOpaque(true);
		player2Lbl.setBackground(Color.RED);
		player2Lbl.setForeground(Color.WHITE);
		player2Lbl.setBorder(BorderFactory.createLineBorder(Color.WHITE));
		
		AILbl = new JLabel("Computer > Red Chip");
		AILbl.setFont(new Font("Sans Serif", Font.BOLD, 24));
		AILbl.setHorizontalAlignment(SwingConstants.CENTER);
		AILbl.setOpaque(true);
		AILbl.setBackground(Color.RED);
		AILbl.setForeground(Color.WHITE);
		AILbl.setBorder(BorderFactory.createLineBorder(Color.WHITE));
		
		startConnectFour = new ConnectFourPanel();
		
		// Set up the panels
		setUpPanel = new JPanel();
		setUpPanel.setBackground(Color.BLACK);
		setUpPanel.setLayout(new GridLayout(1, 2, 40, 40));
		setUpPanel.add(twoPlayersBtn);
		setUpPanel.add(playAIBtn);
		
		mainPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout());
		setBackground(Color.BLACK);
		setPreferredSize(new Dimension(790,840));
		add(setUpPanel, BorderLayout.NORTH);
		add(startConnectFour, BorderLayout.CENTER);
	}
	
	private class ButtonActionListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			if (event.getSource() == twoPlayersBtn) {	
				twoPlayersBtn.setVisible(false);
				playAIBtn.setVisible(false);
				
				setUpPanel.remove(twoPlayersBtn);
				setUpPanel.remove(playAIBtn);
				setUpPanel.add(player1Lbl);
				setUpPanel.add(player2Lbl);
				
				startConnectFour.setBoardVisible(true);
			}
			
			if (event.getSource() == playAIBtn) {	
				twoPlayersBtn.setVisible(false);
				playAIBtn.setVisible(false);
				
				setUpPanel.remove(twoPlayersBtn);
				setUpPanel.remove(playAIBtn);
				setUpPanel.add(player1Lbl);
				setUpPanel.add(AILbl);
				startConnectFour.setBoardVisible(true);
				
			}


		}
	}
}
