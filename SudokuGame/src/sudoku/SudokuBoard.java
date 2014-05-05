package sudoku;

/*
 * Homework 2
 * Antonio A. Eggermont
 * Sudoku Game Version 2
 * 
 */

public class SudokuBoard {

	/**
	 * @param args
	 */
	
	protected static int [][] sodoku;
	private static int boardSize;

	/*
	 * Constructor class 
	 */
	public SudokuBoard( int boardSize, boolean row){
		sodoku = new int[boardSize][boardSize];
		SudokuBoard.boardSize = boardSize;
	}
	
	/*
	 * Prints the board
	 */
	public static void displayBoard(){
		
	    for (int x=0; x < boardSize; x++){
	    	for (int y=0; y < boardSize ; y++){
	    		System.out.format("[%d]", sodoku[x][y]);
	   	}
	    	System.out.println();
	    }
	}
}
