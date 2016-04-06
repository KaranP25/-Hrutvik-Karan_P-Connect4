package KaranP.Rudi;

import java.util.Random;

public class AIPlayer {
	private final static int BOARD_ROW = 6, BOARD_COL = 7;
	private int[][] grid;
	private final static int EMPTY = 0, P1 = 1, COMPUTER = 99;
	private int currentPlayer;
	
	private int compRow, compCol;
	
	public AIPlayer(int[][] grid){
		this.grid = grid;
		resetAI();
	}
	
	public void resetAI(){
		for (int i = 0; i < BOARD_ROW; i++) {
			for (int j = 0; j < BOARD_COL; j++) {
				grid[i][j] = EMPTY;
			}
		}
		this.compRow = 0;
		this.compCol = 0;
	}
	
	private int getRandomCol(){
		Random randStart = new Random();
		int x = randStart.nextInt((BOARD_COL - 1) + 1);
		System.out.println(x); 
		return x;
	}
	
	public void compMove(){
		int col;
		do{
			col = getRandomCol();
		}while(isColumnFull(col));
		int row = getRowPlacement(col);
		grid[row][col] = COMPUTER;
		this.compRow = row;
		this.compCol = col;
	}
	
	public int getAIRow(){
		return compRow;
	}
	
	public int getAICol(){
		return compCol;
	}
	
	private int getRowPlacement(int col){
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
	
	private boolean isColumnFull(int col){
		boolean isFull;
		if(grid[0][col] != EMPTY){
			isFull = true;
		}else{
			isFull = false;
		}
		
		return isFull;
	}
	
	public void switchCurrentPlayer() {	
		if (currentPlayer == P1) {
			currentPlayer = COMPUTER;
		} else if (currentPlayer == COMPUTER) {
			currentPlayer = P1;
		}		
	}
	
	public void setCurrentPlayer(int turn){
		// turn = 1 > player turn
		// turn = 99 > computer's turn
		this.currentPlayer = turn;
	}
	
	public int getCurrentPlayer(){
		return currentPlayer;
	}
	
	
	public void updateAIArray (int row, int col, int change){
		grid[row][col] = change;
	}
	public int getCell(int x, int y){
		return grid[x][y];
	}
	
	public void print(){
		for (int i = 0; i < BOARD_ROW; i++) {
			for (int j = 0; j < BOARD_COL; j++) {
				System.out.print(grid[i][j]);
			}
			System.out.println();
		}
	}

}
