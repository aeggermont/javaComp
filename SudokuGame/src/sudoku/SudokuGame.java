package sudoku;


/*
 * 
 * SudokuGame is the base class used by the game driver class to
 * instantiate the sudoku board.
 * 
 * @author Antonio A. Eggermont
 * @version 2.0  
 * 
 */

public class SudokuGame extends SudokuBoard{

    /*
     * Constructor for invocation by sub class. Sets the board
     * size and whether the user input will be by row or column
     * 
     * @param boardSize the size of the board 
     * @row true if the user input will be by row or false
     *      if the user input will be by column
     * @since 2.0 
     * 
     */
	public SudokuGame(int boardSize, boolean row){
		super(boardSize, row );
	}
	
	/*
	 * Returns the value stored in the x,y position in the board
	 * 
	 * @param xposition x coordinate position in the board
	 * @param yposition y coordinate position in the board
	 * @return a positive integer for the value stored in x,y position
	 * @since 2.0 
	 * 
	 */
	public int getPositionValues(int xposition, int yposition) {
		return sodoku[xposition][yposition];
	}

	/*
	 * Sets the value of the board in a given x,y coordinate position
	 * 
	 * @param xposition the x coordinate position in the board. 
	 * @param xposition the y coordinate position in the board
	 * @since 2.0
	 *  
	 */
	
	public  void setPositionValues(int xposition, int yposition, int value) {
		sodoku[xposition][yposition] = value;
	}	
}