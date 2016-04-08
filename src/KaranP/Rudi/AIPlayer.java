package KaranP.Rudi;

import java.util.Random;

/**
 * This class contains methods and instance variables of the AI/Computer object
 * It choose a completely random move to play along with the player
 * It also chooses a position rather than random move if the player is about to win
 * 
 * @author Hrutvik & Karan
 * @version 1.0
 */
public class AIPlayer {
	private int[][] grid;
	private final int BOARD_ROW = 6, BOARD_COL = 7;
	private final int EMPTY = 0, P1 = 1, COMPUTER = 99;
	private int currentPlayer;

	private int compRow, compCol, forceCompCol;
	
	/**
	 * Initiate the AI Player
	 * @param grid: gets the 2D array
	 */
	public AIPlayer(int[][] grid) {
		this.grid = grid;
		resetAIArray();
	}

	/**
	 * This method resets the value of each cell to zero
	 */
	public void resetAIArray() {
		for (int i = 0; i < BOARD_ROW; i++) {
			for (int j = 0; j < BOARD_COL; j++) {
				this.grid[i][j] = EMPTY;
			}
		}
		this.compRow = 0;
		this.compCol = 0;
	}
	
	/**
	 * This method returns a random column for AI move
	 * @return
	 */
	private int getRandomCol() {
		Random randStart = new Random();
		int x = randStart.nextInt((BOARD_COL - 1) + 1);
		return x;
	}

	/**
	 * This method set the computer chip for the board based on either on random column or
	 * the possibility of player winning resulting in blocking players win chance
	 */
	public void compMove() {
		int col;
		if (!isPlayerPossibleToWin()) {
			do {
				col = getRandomCol();
			} while (isColumnFull(col));
		} else {
			col = forceCompCol;
		}
		int row = getRowPlacement(col);
		this.grid[row][col] = COMPUTER;
		this.compRow = row;
		this.compCol = col;
	}

	/**
	 * This method returns the AI row placement on the connect four board
	 * @return
	 */
	public int getAIRow() {
		return compRow;
	}
	
	/**
	 * This method returns the AI col placement on the connect four board
	 * @return
	 */
	public int getAICol() {
		return compCol;
	}

	private boolean isPlayerPossibleToWin() {
		boolean found = false;
		boolean isCompColForced = false;

		// Vertical > Has more priority
		for (int i = BOARD_ROW - 1; i >= 3; i--) {
			for (int j = BOARD_COL - 1; j >= 0; j--) {
				if (grid[i][j] == P1 && grid[i - 1][j] == P1 && grid[i - 2][j] == P1 && grid[i - 3][j] == EMPTY) {
					found = true;
					if (found && !isCompColForced) {
						this.forceCompCol = j;
						isCompColForced = true;
					}
				}
			}
		}

		// Horizontal
		for (int i = BOARD_ROW - 1; i >= 0; i--) {
			for (int j = 0; j < BOARD_COL - 3; j++) {
				if (grid[i][j + 1] == P1 && grid[i][j + 2] == P1 && grid[i][j + 3] == P1 && grid[i][j] == EMPTY) {
					found = true;
					if (found && !isCompColForced && !isBottomRowOfColFilled(i, j)) {
						this.forceCompCol = j;
						isCompColForced = true;
					}
				}
			}
		}
		for (int i = BOARD_ROW - 1; i >= 0; i--) {
			for (int j = 0; j < BOARD_COL - 3; j++) {
				if (grid[i][j] == P1 && grid[i][j + 2] == P1 && grid[i][j + 3] == P1 && grid[i][j + 1] == EMPTY) {
					found = true;
					if (found && !isCompColForced && !isBottomRowOfColFilled(i, j + 1)) {
						forceCompCol = j + 1;
						isCompColForced = true;
					}
				}
			}
		}
		for (int i = BOARD_ROW - 1; i >= 0; i--) {
			for (int j = 0; j < BOARD_COL - 3; j++) {
				if (grid[i][j] == P1 && grid[i][j + 1] == P1 && grid[i][j + 3] == P1 && grid[i][j + 2] == EMPTY) {
					found = true;
					if (found && !isCompColForced && !isBottomRowOfColFilled(i, j + 2)) {
						this.forceCompCol = j + 2;
						isCompColForced = true;
					}
				}
			}
		}
		for (int i = BOARD_ROW - 1; i >= 0; i--) {
			for (int j = 0; j < BOARD_COL - 3; j++) {
				if (grid[i][j] == P1 && grid[i][j + 1] == P1 && grid[i][j + 2] == P1 && grid[i][j + 3] == EMPTY) {

					found = true;
					if (found && !isCompColForced && !isBottomRowOfColFilled(i, j + 3)) {
						this.forceCompCol = j + 3;
						isCompColForced = true;
					}
				}
			}
		}

		return found;
	}

	private boolean isBottomRowOfColFilled(int row, int col) {
		if (row == 5) {
			return false;
		} else if (grid[row + 1][col] == EMPTY) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * This method return the row that chip must be placed at depending on the column
	 * and the availability of spaces that is left to be filled
	 * @param col
	 * @return
	 */
	public int getRowPlacement(int col) {
		int rowPlaceable = BOARD_ROW - 1;
		boolean rowPlacementFound = false;

		while (!rowPlacementFound) {
			if (grid[rowPlaceable][col] == EMPTY) {
				rowPlacementFound = true;
			} else if (rowPlaceable >= 0) {
				rowPlaceable--;
			}
		}

		return rowPlaceable;
	}

	/**
	 * This method checks if the column that the chip is being placed at,
	 * is not full
	 * @param col
	 * @return
	 */
	public boolean isColumnFull(int col) {
		boolean isFull;
		if (grid[0][col] != EMPTY) {
			isFull = true;
		} else {
			isFull = false;
		}
		return isFull;
	}
	
	/**
	 * This method will switch the curentPlayer to the next one
	 */
	public void switchCurrentPlayer() {
		if (currentPlayer == P1) {
			this.currentPlayer = COMPUTER;
		} else if (currentPlayer == COMPUTER) {
			this.currentPlayer = P1;
		}
	}

	/**
	 * This method will sets the current player based on user decision of who goes first 
	 * @param turn
	 */
	public void setCurrentPlayer(int turn) {
		// turn = 1 > player turn
		// turn = 99 > computer's turn
		this.currentPlayer = turn;
	}

	/**
	 * This method return the current player that will be making its move
	 * @return
	 */
	public int getCurrentPlayer() {
		return currentPlayer;
	}

	/**
	 * This method updates the instance 2d array based on player change at row and column
	 * @param row
	 * @param col
	 * @param change
	 */
	public void updateArrayCell(int row, int col, int change) {
		this.grid[row][col] = change;
	}
	
	/**
	 *  This method return the value at the specific index
	 * it is mainly used to update the array in ConnectFourPanel
	 * @param x: rows
	 * @param y: column
	 * @return
	 */
	public int getCell(int x, int y) {
		return grid[x][y];
	}

}