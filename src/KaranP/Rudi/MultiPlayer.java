package KaranP.Rudi;

import java.util.Random;

/**
 * This class contains methods and instance variables of a player object (P1 and P2)
 * it stores and decides on the correct position to place the player corresponding chip
 * @author Hrutvik & Karan
 * @version 1.0 
 */
public class MultiPlayer{	
	private int rowPlaced, colPlaced;
	private int[][] grid;
	private final static int EMPTY = 0, P1 = 1, P2 = 2;
	private int currentPlayer;
	private final static int BOARD_ROW = 6, BOARD_COL = 7;
	
	/**
	 * This Constructor method initiates the playing of multiplayer game
	 * @param grid
	 */
	public MultiPlayer(int[][] grid){
		this.grid = grid;
		resetMultiPlayerArray();
	}	
	
	/**
	 * This method resets the value of each cell to zero
	 */
	public void resetMultiPlayerArray(){
		for (int i = 0; i < BOARD_ROW; i++) {
			for (int j = 0; j < BOARD_COL; j++) {
				grid[i][j] = EMPTY;
			}
		}
	}
	
	/**
	 * This method sets the column the chip is being placed at
	 * @param col
	 */
	public void setColPlaced(int col){
		this.colPlaced = col;
	}
	
	/**
	 * This method sets the row the chip is being placed at
	 * @param row
	 */
	public void setRowPlaced(int row){
		this.rowPlaced = row;
	}
	
	/**
	 * This method returns the row placement for the player on the board
	 * @return
	 */
	public int getRow(){
		return rowPlaced;
	}
	
	/**
	 * This method returns the column placement for the player on the board
	 * @return
	 */
	public int getCol(){
		return colPlaced;
	}
	
	/**
	 * This method return the row that chip must be placed at depending on the column
	 * and the availability of spaces that is left to be filled
	 * @param col
	 * @return
	 */
	public int getRowPlacedAt(int col){
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
	
	/**
	 * This method will switch the curentPlayer to the next one 
	 */
	public void switchCurrentPlayer() {	
		if (currentPlayer == 1) {
			currentPlayer = 2;
		} else if (currentPlayer == 2) {
			currentPlayer = 1;
		}		
	}
	
	/**
	 * This method will update the instance array(grid) based on the players turn
	 */
	public void placePlayer1Chip(){
		if(currentPlayer == P1){
			grid[rowPlaced][colPlaced] = P1;
		}
	}
	
	/**
	 * This method will update the instance array(grid) based on the players turn
	 */
	public void placePlayer2Chip(){
		if(currentPlayer == P2){
			grid[rowPlaced][colPlaced] = P2;
		}	 
	}
	
	/**
	 * This method checks if the column that the chip is being placed at,
	 * is not full
	 * @param col
	 * @return
	 */
	public boolean isColumnFilled(int col) {
		if (grid[0][col] != 0) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * This method return a random player to go first during every new game
	 * @return
	 */
	public int getRandomPlayer() {
		Random randStart = new Random();
		currentPlayer = randStart.nextInt((P2 - P1) + 1) + P1;
		return currentPlayer;
	}
	
	/**
	 * This method return the value at the specific index
	 * it is mainly used to update the array in ConnectFourPanel
	 * @param x: rows
	 * @param y: columns
	 * @return
	 */
	public int getCell(int x, int y){
		return grid[x][y];
	}
	
	/**
	 * This method return the current player that will be making its move
	 * @return
	 */
	public int getCurrentPlayer(){
		return currentPlayer;
	}
	
}
