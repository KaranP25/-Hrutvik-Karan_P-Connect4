package KaranP.Rudi;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

@SuppressWarnings("serial")
/**
 * This class is the panel which contains all the algorithms required to play
 * the game It contains the main board that runs both the multiplayer and AI
 * games as well as displays and check for the winner
 * 
 * @author Hrutvik & Karan
 * @version 1.0
 */
public class ConnectFourPanel extends JPanel implements ActionListener {
	private final static int GRID_ROW = 7, GRID_COL = 7, HGAP = 10, VGAP = 10;
	private final static int PANEL_WIDTH = (50 * GRID_ROW) + (GRID_ROW * HGAP) + 10;
	private final static int PANEL_HEIGHT = (50 * GRID_COL) + (GRID_COL * VGAP) + 10;

	private static JLabel[][] board;
	private static JButton[] placement;
	private static int[][] grid;
	private final static int BOARD_ROW = 6, BOARD_COL = 7;
	private final static int EMPTY = 0, P1 = 1, P2 = 2, COMPUTER = 99;

	private String gameType;
	private int currentPlayer;
	private boolean canMoveBeMade = true;
	private int movesPossible = BOARD_ROW * BOARD_COL;
	AIPlayer playWithComputer;
	MultiPlayer multiPlayers;

	@SuppressWarnings("static-access")
	public ConnectFourPanel() {
		setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
		setBackground(new Color(0, 0, 0));

		//
		setLayout(new GridLayout(GRID_ROW, GRID_COL, HGAP, VGAP));
		setBorder(BorderFactory.createLineBorder(Color.BLACK));

		placement = new JButton[BOARD_COL];
		for (int i = 0; i < placement.length; i++) {
			this.placement[i] = new JButton();
			placement[i].setHorizontalAlignment(SwingConstants.CENTER);
			placement[i].setContentAreaFilled(false);
			placement[i].setEnabled(false);
			placement[i].setBorder(BorderFactory.createLineBorder(Color.WHITE));
			placement[i].addActionListener(this);
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
		multiPlayers = new MultiPlayer(grid);
		playWithComputer = new AIPlayer(grid);

	}

	/**
	 * This method is from the ActionListener class and handles events
	 */
	public void actionPerformed(ActionEvent event) {

		int colPlaced, rowPlaced;
		for (int i = 0; i < placement.length; i++) {
			if (event.getSource() == placement[i]) {
				colPlaced = i;

				if (gameType.equals("MultiPlayerConnect4")) {
					currentPlayer = multiPlayers.getCurrentPlayer();
					rowPlaced = multiPlayers.getRowPlacedAt(colPlaced);
					multiPlayers.setRowPlaced(rowPlaced);
					multiPlayers.setColPlaced(colPlaced);

					if (currentPlayer == P1) {
						multiPlayers.placePlayer1Chip();
						bluePlaced(multiPlayers.getRow(), multiPlayers.getCol());
					} else if (currentPlayer == P2) {
						multiPlayers.placePlayer2Chip();
						redPlaced(multiPlayers.getRow(), multiPlayers.getCol());
					}

					multiPlayers.switchCurrentPlayer();
					movesPossible -= 1;

				} else if (gameType.equals("ComputerPlayer")) {
					currentPlayer = playWithComputer.getCurrentPlayer();
					if (currentPlayer == P1) {
						rowPlaced = playWithComputer.getRowPlacement(colPlaced);
						grid[rowPlaced][colPlaced] = P1;
						bluePlaced(rowPlaced, colPlaced);
						playWithComputer.updateArrayCell(rowPlaced, colPlaced, P1);
						movesPossible -= 1;
					}
					if (isNextMovePossible() && !isWinnerFound()) {
						playWithComputer.switchCurrentPlayer();
						computersTurn();
					}

				}
				updateArray();
				if (isWinnerFound() || isTieGame()) {
					displayResult();
				}
			}
		}
	}

	/**
	 * This Method load the images and set the image the JButton and JLabel for the board
	 */
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

	/**
	 * This method returns the player that will go first in the multiplayer game
	 * @return
	 */
	public int getRandomPlayer() {
		return multiPlayers.getRandomPlayer();
	}

	/**
	 * This methods allows the computer to place a chip on the board
	 */
	public void computersTurn() {
		currentPlayer = playWithComputer.getCurrentPlayer();
		if (currentPlayer == COMPUTER) {
			playWithComputer.compMove();
			int compRowPlaced = playWithComputer.getAIRow();
			int compColPlaced = playWithComputer.getAICol();
			greenPlaced(compRowPlaced, compColPlaced);
			grid[compRowPlaced][compColPlaced] = COMPUTER;

			playWithComputer.switchCurrentPlayer();
			movesPossible -= 1;
		}
	}

	/**
	 * This method sets the turn of who goes first 
	 * @param turn
	 */
	public void setTurn(int turn) {
		playWithComputer.setCurrentPlayer(turn);
		this.currentPlayer = turn;
	}

	/**
	 * This method updates the grid array based on data from either AIPlayer or MultiPlayer class
	 */
	private void updateArray() {
		for (int i = 0; i < BOARD_ROW; i++) {
			for (int j = 0; j < BOARD_COL; j++) {
				if (gameType.equals("MultiPlayerConnect4")) {
					grid[i][j] = multiPlayers.getCell(i, j);
				} else if (gameType.equals("ComputerPlayer")) {
					grid[i][j] = playWithComputer.getCell(i, j);
				}
			}
		}
	}

	/**
	 * This method sets the background of the specific JLabel to blue based on parameter
	 * @param posRow - row position of 2d JLabel
	 * @param posCol - column position of 2d JLabel
	 */
	private void bluePlaced(int posRow, int posCol) {
		board[posRow][posCol].setBackground(Color.BLUE);
		// switchPlayer = true;
		if (multiPlayers.isColumnFilled(posCol) || playWithComputer.isColumnFull(posCol)) {
			placement[posCol].setEnabled(false);
			placement[posCol].setToolTipText("Column FILLED");
		}
	}

	/**
	 * This method sets the background of the specific JLabel to red based on parameter
	 * @param posRow - row position of 2d JLabel
	 * @param posCol - column position of 2d JLabel
	 */
	private void redPlaced(int posRow, int posCol) {
		board[posRow][posCol].setBackground(Color.RED);
		// switchPlayer = true;
		if (multiPlayers.isColumnFilled(posCol)) {
			placement[posCol].setEnabled(false);
			placement[posCol].setToolTipText("Column FILLED");
		}
	}

	/**
	 * This method sets the background of the specific JLabel to green based on parameter
	 * @param posRow - row position of 2d JLabel
	 * @param posCol - column position of 2d JLabel
	 */
	private void greenPlaced(int posRow, int posCol) {
		board[posRow][posCol].setBackground(new Color(102, 255, 102));
		// switchPlayer = true;
		if (playWithComputer.isColumnFull(posCol)) {
			placement[posCol].setEnabled(false);
			placement[posCol].setToolTipText("Column FILLED");
		}
	}

	/**
	 * This method disables or enables the board components based on the parameter
	 * @param visible - true - enables the board components, false - disables the board components
	 */
	public void setBoardVisible(boolean visible) {
		if (visible) {
			for (int i = 0; i < placement.length; i++) {
				placement[i].setEnabled(true);
			}
			for (int i = 0; i < BOARD_ROW; i++) {
				for (int j = 0; j < BOARD_COL; j++) {
					board[i][j].setEnabled(true);
				}
			}
		} else if (!visible) {
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

	/**
	 * This method checks if a game has resulted in a tie
	 * @return
	 */
	private boolean isTieGame() {
		if (movesPossible < 1) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * This method is called if the winner is found and disables the board and sets the
	 * moves done by the winner of either game JLabel to true
	 * @param c1x
	 * @param c1y
	 * @param c2x
	 * @param c2y
	 * @param c3x
	 * @param c3y
	 * @param c4x
	 * @param c4y
	 */
	private void winnerFound(int c1x, int c1y, int c2x, int c2y, int c3x, int c3y, int c4x, int c4y) {
		this.canMoveBeMade = false;
		setBoardVisible(false);

		board[c1x][c1y].setEnabled(true);
		board[c2x][c2y].setEnabled(true);
		board[c3x][c3y].setEnabled(true);
		board[c4x][c4y].setEnabled(true);
	}

	/**
	 * This method returns true or false if the board has a winner or not
	 * @return
	 */
	private boolean isWinnerFound() {
		int tempCheck;
		// vertical win check
		for (int i = BOARD_ROW - 1; i >= 3; i--) {
			for (int j = BOARD_COL - 1; j >= 0; j--) {
				tempCheck = grid[i][j];
				if (tempCheck != EMPTY && tempCheck == grid[i - 1][j] && tempCheck == grid[i - 2][j]
						&& tempCheck == grid[i - 3][j]) {
					winnerFound(i, j, i - 1, j, i - 2, j, i - 3, j);
					return true;
				}
			}
		}
		// horizontal win check
		for (int i = BOARD_ROW - 1; i >= 0; i--) {
			for (int j = 0; j < BOARD_COL - 3; j++) {
				tempCheck = grid[i][j];
				if (tempCheck != EMPTY && tempCheck == grid[i][j + 1] && tempCheck == grid[i][j + 2]
						&& tempCheck == grid[i][j + 3]) {
					winnerFound(i, j, i, j + 1, i, j + 2, i, j + 3);
					return true;
				}
			}
		}
		// Diagonal win check
		for (int i = 0; i < BOARD_ROW - 3; i++) {
			for (int j = 0; j < BOARD_COL - 3; j++) {
				tempCheck = grid[i][j];
				if (tempCheck != EMPTY && tempCheck == grid[i + 1][j + 1] && tempCheck == grid[i + 2][j + 2]
						&& tempCheck == grid[i + 3][j + 3]) {
					winnerFound(i, j, i + 1, j + 1, i + 2, j + 2, i + 3, j + 3);
					return true;
				}
			}
		}
		for (int i = BOARD_ROW - 3; i < BOARD_ROW; i++) {
			for (int j = 0; j < BOARD_COL - 3; j++) {
				tempCheck = grid[i][j];
				if (tempCheck != EMPTY && tempCheck == grid[i - 1][j + 1] && tempCheck == grid[i - 2][j + 2]
						&& tempCheck == grid[i - 3][j + 3]) {
					winnerFound(i, j, i - 1, j + 1, i - 2, j + 2, i - 3, j + 3);
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * This method displays the winner or if a game has been tied  
	 */
	private void displayResult() {
		if(isWinnerFound()){
			if (currentPlayer == 1 || currentPlayer == 2) {
				JOptionPane.showMessageDialog(null, "Player " + currentPlayer + " wins!!!");
			} else if (currentPlayer == 99) {
				JOptionPane.showMessageDialog(null, "Computer wins!!!");
			}
		} else if (movesPossible < 1) {
			setBoardVisible(false);
			JOptionPane.showMessageDialog(null, "Tie Game!");
		}
	}

	/**
	 * This method returns if a next move is possible by the computer 
	 * @return
	 */
	private boolean isNextMovePossible() {
		if (movesPossible == 0) {
			canMoveBeMade = false;
		}
		if (canMoveBeMade) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * This method resets the board by setting the JLabels and JButtons back to its original state.
	 * It also resets the instances of other classes based on which gamemode the user is playing
	 */
	public void gameReset() {
		for (int i = 0; i < BOARD_ROW; i++) {
			for (int j = 0; j < BOARD_COL; j++) {
				grid[i][j] = EMPTY;
				board[i][j].setBackground(Color.WHITE);
				board[i][j].setEnabled(false);
			}
		}
		for (int i = 0; i < placement.length; i++) {
			placement[i].setEnabled(false);
			placement[i].setToolTipText("");
		}
		if (gameType.equals("MultiPlayerConnect4")) {
			multiPlayers.resetMultiPlayerArray();
		} else if (gameType.equals("ComputerPlayer")) {
			playWithComputer.resetAIArray();
			canMoveBeMade = true;
		}
		currentPlayer = EMPTY;
		gameType = "";
		movesPossible = BOARD_ROW * BOARD_COL;
	}

	/**
	 * This method sets the game mode that will be player on the board
	 * It will be either Multiplayer or with a computer
	 * @param type
	 */
	public void setGameMode(String type) {
		this.gameType = type;
	}

	/**
	 * This method returns the gameType that is being played
	 * @return
	 */
	public String getGameMode() {
		return gameType;
	}
}
