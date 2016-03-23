package KaranP.Rudi;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class SetUpPanel extends JPanel {
	private static JButton twoPlayersBtn, playAIBtn, newGameBtn, randomPlayerBtn;
	private static JLabel player1Lbl, player2Lbl, AILbl;
	private JPanel mainPanel, setUpPanel;
	private ConnectFourBoard startConnectFour;

	public SetUpPanel() {
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

		newGameBtn = new JButton("Start A New Game");
		newGameBtn.setFont(new Font("Sans Serif", Font.ITALIC, 20));
		newGameBtn.setHorizontalAlignment(SwingConstants.CENTER);
		newGameBtn.setContentAreaFilled(true);
		newGameBtn.setEnabled(true);
		newGameBtn.addActionListener(new ButtonActionListener());

		randomPlayerBtn = new JButton("Choose a Random Player");
		randomPlayerBtn.setFont(new Font("Sans Serif", Font.ITALIC, 20));
		randomPlayerBtn.setHorizontalAlignment(SwingConstants.CENTER);
		randomPlayerBtn.setContentAreaFilled(true);
		randomPlayerBtn.setEnabled(true);
		randomPlayerBtn.addActionListener(new ButtonActionListener());

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

		startConnectFour = new ConnectFourBoard();

		// Set up the panels
		setUpPanel = new JPanel();
		setUpPanel.setBackground(Color.BLACK);
		// setUpPanel.setLayout(new GridLayout(1, 2, 40, 40));
		setUpPanel.setLayout(new FlowLayout(5, 40, 2));
		setUpPanel.add(twoPlayersBtn);
		setUpPanel.add(playAIBtn);

		mainPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout());
		setBackground(Color.BLACK);
		setPreferredSize(new Dimension(790, 840));
		add(setUpPanel, BorderLayout.NORTH);
		add(startConnectFour, BorderLayout.CENTER);
	}

	private class ButtonActionListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			if (event.getSource() == twoPlayersBtn) {
				startConnectFour.setGameMode("MultiPlayer");
				twoPlayersBtn.setVisible(false);
				playAIBtn.setVisible(false);

				setUpPanel.remove(twoPlayersBtn);
				setUpPanel.remove(playAIBtn);
				setUpPanel.add(randomPlayerBtn);
				randomPlayerBtn.setVisible(true);				
				
			}if (event.getSource() == playAIBtn) {
				startConnectFour.setGameMode("ComputerPlayer");
				twoPlayersBtn.setVisible(false);
				playAIBtn.setVisible(false);

				setUpPanel.remove(twoPlayersBtn);
				setUpPanel.remove(playAIBtn);
				setUpPanel.add(player1Lbl);
				setUpPanel.add(AILbl);
				startConnectFour.setBoardVisible(true);
				
			}if (event.getSource() == randomPlayerBtn) {
				int currentPlayer = startConnectFour.getRandomPlayer();
				JOptionPane.showMessageDialog(null, "Player " + currentPlayer +" goes first");
				randomPlayerBtn.setVisible(false);
				setUpPanel.remove(randomPlayerBtn);
				
				if(currentPlayer == 1){
					player1Lbl.setText("***Player 1 > Blue Chip");
				}else if(currentPlayer == 2){
					player2Lbl.setText("***Player 2 > Red Chip");
				}			
				setUpPanel.add(newGameBtn);
				setUpPanel.add(player1Lbl);
				setUpPanel.add(player2Lbl);
				
				startConnectFour.setBoardVisible(true);
				
			}if (event.getSource() == newGameBtn) {
				startConnectFour.gameReset();
				startConnectFour.setBoardVisible(false);
				
				setUpPanel.remove(newGameBtn);
				setUpPanel.remove(player1Lbl);
				setUpPanel.remove(player2Lbl);				
				
				setUpPanel.add(twoPlayersBtn);
				setUpPanel.add(playAIBtn);
				twoPlayersBtn.setVisible(true);
				playAIBtn.setVisible(true);
				
				player1Lbl.setText("Player 1 > Blue Chip");
				player2Lbl.setText("Player 2 > Red Chip");
			}
			
		}		
	}
}
