package sudoku;

import java.util.Scanner;

public class SudokuGame extends SudokuBoard{

	/**
	 * @param args
	 */
	public SudokuGame(int boardSize, boolean row){
		super(boardSize, row );
	}
	
	public int getPositionValues(int xposition, int yposition) {
		return sodoku[xposition][yposition];
	}

	public  void setPositionValues(int xposition, int yposition, int value) {
		sodoku[xposition][yposition] = value;
	}
	
	
}
