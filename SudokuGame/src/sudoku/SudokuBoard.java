package sudoku;

/*
 * The SudokuBoard is the base class to draw a two dimensional array 
 * to represent a board of integers. The state information encapsulated 
 * in this class includes:
 * <ul>
 * <li> The board size ( x and y axis sizes)
 * <li> The actual two dimensional array storing integers entered by the game player 
 * <li> Rendering of the current values already entered into the array for the game
 * </ul>
 * @author Antonio A. Eggermont
 * @version 2.0  
 */
public class SudokuBoard {

	
	protected int [][] sodoku;
	private static int boardSize;

	/*
	 * Constructor for invocation by sub class. 
	 * Sets the Sudoku board with a given board size in the
	 * x and y axis. 
	 * 
	 * @param boardSize  the number of columns and rows that define the size of the board
	 * @param row        a boolean argument that defines whether the input will be by row
	 *                   or by column when playing the game
	 * @since 2.0                          
	 */
	public SudokuBoard( int boardSize, boolean row){
		sodoku = new int[boardSize][boardSize];
		SudokuBoard.boardSize = boardSize;
	}
	
	/*
	 * Prints the board in the prompt 
	 * 
	 * @since 2.0
	 */
	public void displayBoard(){
		
	    for (int x=0; x < boardSize; x++){
	    	for (int y=0; y < boardSize ; y++){
	    		System.out.format("[%d]", sodoku[x][y]);
	   	}
	    	System.out.println();
	    }
	}
}
