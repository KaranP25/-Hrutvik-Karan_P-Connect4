package KaranP.Rudi;

import java.awt.Color;
import java.util.Random;

public class MultiPlayerConnect4 {
	private int[][] grid;
	private final static int EMPTY = 0, P1 = 1, P2 = 2;
	private int currentPlayer;
	private boolean switchPlayer;
	
	public MultiPlayerConnect4(int[][] grid){
		this.grid = grid;
	}
	
	public void switchCurrentPlayer() {
		if (switchPlayer) {
			if (currentPlayer == 1) {
				currentPlayer = 2;
			} else if (currentPlayer == 2) {
				currentPlayer = 1;
			}
		}
	}

	public int getRandomPlayer() {
		Random randStart = new Random();
		currentPlayer = randStart.nextInt((P2 - P1) + 1) + P1;
		switchPlayer = false;
		return currentPlayer;
	}	

	public void placeChip(int col, int change) {
		int rowPlaced = 0;
		for (int i = 5; i >= 0; i--) {
			if (grid[i][col] == EMPTY) {
				grid[i][col] = change;
				rowPlaced = i;
				break;
			}
		}
		System.out.println();
		if (change == 1){
			ConnectFourPanel.bluePlaced(rowPlaced, col);
		}else if (change == 2){
			ConnectFourPanel.redPlaced(rowPlaced, col);
		}
		switchPlayer = true;
	}
	
	public int getCell(int x, int y){
		return grid[x][y];
	}
	
	public int getCurrentPlayer(){
		return currentPlayer;
	}
	
}
