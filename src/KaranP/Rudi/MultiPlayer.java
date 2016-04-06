package KaranP.Rudi;

import java.awt.Color;
import java.util.Random;

import javax.swing.BorderFactory;
import javax.swing.JLabel;

/**
 * This class contains methods and instance variables of a player object (P1 and P2)
 * @author hrutvik and karan
 *
 */
public class MultiPlayer{	
	private int rowPlaced, colPlaced;
	private int[][] grid;
	private final static int EMPTY = 0, P1 = 1, P2 = 2;
	private int currentPlayer;
	private final static int BOARD_ROW = 6, BOARD_COL = 7;
	
	public MultiPlayer(int[][] grid){
		this.grid = grid;
		resetMultiplayerArray();
	}	
	
	public void resetMultiplayerArray(){
		for (int i = 0; i < BOARD_ROW; i++) {
			for (int j = 0; j < BOARD_COL; j++) {
				grid[i][j] = EMPTY;
			}
		}
	}
	
	public void setColPlaced(int col){
		this.colPlaced = col;
	}
	
	public void setRowPlaced(int row){
		this.rowPlaced = row;
	}
	
	public void upDateList(){
		grid[rowPlaced][colPlaced] = currentPlayer;
	}
	
	public void switchCurrentPlayer() {	
		if (currentPlayer == 1) {
			currentPlayer = 2;
		} else if (currentPlayer == 2) {
			currentPlayer = 1;
		}		
	}
	
	public void placePlayer1Chip(){
		if(currentPlayer == P1){
			ConnectFourPanel.bluePlaced(rowPlaced, colPlaced);
		}
	}
	
	public void placePlayer2Chip(){
		if(currentPlayer == P2){
			ConnectFourPanel.redPlaced(rowPlaced, colPlaced);
		}	 
	}
	
	public int getRandomPlayer() {
		Random randStart = new Random();
		currentPlayer = randStart.nextInt((P2 - P1) + 1) + P1;
		return currentPlayer;
	}
	
	public int getCell(int x, int y){
		return grid[x][y];
	}
	
	public int getCurrentPlayer(){
		return currentPlayer;
	}
	
}
