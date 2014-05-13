package sudoku;

import java.util.Scanner;

/*
 * Verifies correctness of  user input when entering integers 
 * based on the size of the board.
 * @author Antonio A. Eggermont
 * @version 2.0  
 * 
 */

public class SodukuVerify {

    private static final int MAX_NUM_TRIES = 3;
	
    /*
     * Verifies whether the user input followed the expected 
     * character pattern.
     * 
     * @param boardSize sets the size of the board in the x and y axis 
     * @param scannerline scanner object to enter user input into the program 
     * @return true if characters entered are a valid string or false 
     *         otherwise 
     */
    
	public static boolean verifyinput(int boardSize, Scanner scannerline){
		int numtries = 0;
	    String input;
	    
	    System.out.println("Enter row/column of numbers:");
	    
	    while(scannerline.hasNext()){
 	    	if (scannerline.hasNext("([0-9]+,){" + Integer.toString(boardSize-1)  + "," + Integer.toString(boardSize-1) + "}[0-9]+")){	
 		    	return true;
 	    	}else if(numtries > MAX_NUM_TRIES){
 	    		System.out.println("You have reached maximum number of attempts :( ");
 	    		return false;
 	    	} else{
 	    		numtries++;
 	    		input = scannerline.next();
 	    		System.out.println("Invalid input, enter row of numbers again");
 	    		continue;
 	    	}
 	    }
	    
	    return false;
	}
	
	
	/*
	 * Compares all columns and rows in the board array to find out whether
	 * consecutive values are equal across rows or columns
	 *  
	 * @param game a SudokuGame object allowing access to the array board
	 * @param boardSize the size of the game board
	 * @return true if columns or rows do not have continues repetitive numbers, 
	 *         otherwise return false
	 */
	public static boolean verifyboard(SudokuGame game, int boardSize){
		
		int lastvalueinrow    = -100000;
		int lastvalueincolumn = -100000;
		
	    for (int x=0; x < boardSize; x++){
	    	for (int y=0; y < boardSize ; y++){
	    		if (lastvalueinrow == game.sodoku[x][y])
	    			return false;
	    
	    		if (lastvalueincolumn == game.sodoku[y][x])
	    			return false;
	    		    		
	    		lastvalueinrow = game.sodoku[x][y];
	    		lastvalueincolumn = game.sodoku[y][x];
	    	}
	    }	
	    
	    return true;
	}
}
