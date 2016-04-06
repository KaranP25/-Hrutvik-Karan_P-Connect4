package KaranP.Rudi;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.*;

public class ConnectFourPanel extends JPanel {
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
	private boolean switchPlayer;
	private boolean canMoveBeMade = true ;
	private int movesPossible = BOARD_ROW * BOARD_COL;
	AIPlayer playWithComputer;
	MultiPlayerConnect4 multiPlayerConnect4;

	public ConnectFourPanel() {
		setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
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
		multiPlayerConnect4 = new MultiPlayerConnect4(grid);
		playWithComputer = new AIPlayer(grid);
	}

	private class ButtonActionListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			int colPlaced, rowPlaced;
			for (int i = 0; i < placement.length; i++) {
				if (event.getSource() == placement[i]) {
					colPlaced = i;
					rowPlaced = getRowPlacedAt(colPlaced);
					
					if (gameType.equals("MultiPlayerConnect4")){
						multiPlayerConnect4.switchCurrentPlayer();
						currentPlayer = multiPlayerConnect4.getCurrentPlayer();
						
						multiPlayerConnect4.setRowPlaced(rowPlaced);
						multiPlayerConnect4.setColPlaced(colPlaced);
						multiPlayerConnect4.upDateList();
						
						if (currentPlayer == P1) {
							multiPlayerConnect4.placePlayer1Chip();
						} else if (currentPlayer == P2) {
							multiPlayerConnect4.placePlayer2Chip();
						}
						movesPossible -= 1;	
						
					}else if (gameType.equals("ComputerPlayer")){
						currentPlayer = playWithComputer.getCurrentPlayer();
						if (currentPlayer == P1) {
							grid[rowPlaced][colPlaced] = P1;
							bluePlaced(rowPlaced, colPlaced);
							playWithComputer.updateAIArray(rowPlaced, colPlaced, P1);
							print();	
							movesPossible -= 1;	
						}
						if(isNextMovePossible() && !isWinnerFound()){
							playWithComputer.switchCurrentPlayer();
							computersTurn();	
						}
						
					}
					updateArray();
					checkTieGame();
					if(isWinnerFound()){
						if (currentPlayer == 1 || currentPlayer == 2){
							JOptionPane.showMessageDialog(null, "Player " + currentPlayer + " wins!!!");
						}else if (currentPlayer == 99){
							JOptionPane.showMessageDialog(null, "Computer wins!!!");
						}
					}	
				}
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
	
	// multiplayer ****************************************************
	public int getRandomPlayer(){
		return multiPlayerConnect4.getRandomPlayer();
	}
	//**********************************************************
	
	//AI
	public void computersTurn(){
	currentPlayer = playWithComputer.getCurrentPlayer();
		if(currentPlayer == COMPUTER){
			playWithComputer.compMove();
			int compRowPlaced = playWithComputer.getAIRow();
			int compColPlaced = playWithComputer.getAICol();
			System.out.println(compRowPlaced);
			greenPlaced(compRowPlaced, compColPlaced);
			grid[compRowPlaced][compColPlaced] = COMPUTER;
			//playWithComputer.updateAIArray(compRowPlaced, compColPlaced, COMPUTER);
			
			playWithComputer.switchCurrentPlayer();
			movesPossible -= 1;	
		}
	}
	
	public void setTurn(int turn){
		playWithComputer.setCurrentPlayer(turn);
		this.currentPlayer = turn;
	}
	
	public void print(){
		for (int i = 0; i < BOARD_ROW; i++) {
			for (int j = 0; j < BOARD_COL; j++) {
				System.out.print(grid[i][j]);
			}
			System.out.println();
		}
	}
	
	private int getRowPlacedAt(int col){
		int rowPlaceable = BOARD_ROW - 1;;
		boolean rowPlacementFound = false;
		do{
			if (grid[rowPlaceable][col] == EMPTY) {
				rowPlacementFound = true;
			}else if(rowPlaceable >= 0){
				rowPlaceable -= 1;
			}
			
		}while(!rowPlacementFound);
		
		return rowPlaceable;
	}
	
	private void updateArray(){
		for (int i = 0; i < BOARD_ROW; i++) {
			for (int j = 0; j < BOARD_COL; j++) {
				if(gameType.equals("MultiPlayerConnect4")){
					grid[i][j] = multiPlayerConnect4.getCell(i, j);
				}else if(gameType.equals("ComputerPlayer")){
					grid[i][j] = playWithComputer.getCell(i, j);
				}
			}
		}
	}
	
	public static boolean isColumnFilled(int col) {
		if (grid[0][col] != 0) {
			return true;
		} else {
			return false;
		}
	}

	public static void bluePlaced(int posRow, int posCol) {
		board[posRow][posCol].setBackground(Color.BLUE);
		//switchPlayer = true;
		if (isColumnFilled(posCol)) {
			placement[posCol].setEnabled(false);
			placement[posCol].setToolTipText("Column FILLED");
		}
	}

	public static void redPlaced(int posRow, int posCol) {
		board[posRow][posCol].setBackground(Color.RED);
		//switchPlayer = true;
		if (isColumnFilled(posCol)) {
			placement[posCol].setEnabled(false);
			placement[posCol].setToolTipText("Column FILLED");
		}
	}
	
	public static void greenPlaced(int posRow, int posCol) {
		board[posRow][posCol].setBackground(new Color(102, 255, 102));
		//switchPlayer = true;
		if (isColumnFilled(posCol)) {
			placement[posCol].setEnabled(false);
			placement[posCol].setToolTipText("Column FILLED");
		}
	}

	
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
	
	private void checkTieGame(){
		System.out.println(movesPossible);
		if (movesPossible < 1){
			setBoardVisible(false);
			JOptionPane.showMessageDialog(null, "Tie Game!");
		}
		
	}

	private void winnerFound(int c1x, int c1y, int c2x, int c2y, int c3x, int c3y, int c4x, int c4y) {
		this.canMoveBeMade = false;
		setBoardVisible(false);

		board[c1x][c1y].setEnabled(true);
		board[c2x][c2y].setEnabled(true);
		board[c3x][c3y].setEnabled(true);
		board[c4x][c4y].setEnabled(true);

	}

	private boolean isWinnerFound() {
		int tempCheck;
		// vertical win
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
		// horizontal win
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
		// Diagonal win 
		for (int i = 0; i < BOARD_ROW - 3; i++) { // > 
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
	
	private boolean isNextMovePossible(){
		if(canMoveBeMade){
			return true;
		}else{
			return false;
		}
	}

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
		if(gameType.equals("MultiPlayerConnect4")){
			multiPlayerConnect4.resetAI();
		}else if(gameType.equals("ComputerPlayer")){
			playWithComputer.resetAI();
			canMoveBeMade = true;
		}
		currentPlayer = EMPTY;
		gameType = "";		
		movesPossible = BOARD_ROW * BOARD_COL;
	}

	public void setGameMode(String type) {
		this.gameType = type;
	}
	
	public String getGameMode() {
		return gameType;
	}
}

