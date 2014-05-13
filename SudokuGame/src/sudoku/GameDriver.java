package sudoku;

import java.util.Scanner;

/*
 * 
 * GameDruver is the base class to start the SudokuGame. This class
 * provide with user input functionality and instantiates a new
 * game.
 * 
 * @author Antonio A. Eggermont
 * @version 2.0  
 * 
 */

public class GameDriver extends SodukuVerify{

	/*
	 * Allows for the selection of input type for characters entered
	 * by column or by row.  Program can exit if the option quit is
	 * selected. 
	 * 
	 * @return true if input will be entered by row or false if input 
	 *         will be entered by column
	 * 
	 */
	private static boolean inputChoice(){
		
		int selection = 0;
		boolean row = true;
		Scanner scanner = new Scanner(System.in);
	
		System.out.println("Chose how to enter the data: ");
		System.out.println("\t 1) Row");
		System.out.println("\t 2) Column");
		System.out.println("\t Quit");
		
		while (scanner.hasNext()){
			if (scanner.hasNextInt()){
				selection = scanner.nextInt();
				
				if (selection == 1){
					row = true;
					break;
				}else if (selection == 2){
					row = false;
					break;
				}else{
					System.out.println(" *** Invalid choice ***");
					System.out.println("Chose how to enter the data: ");
					System.out.println("\t 1) Row");
					System.out.println("\t 2) Column");
					System.out.println("\t Quit");
				    continue;	
				}
			}else{
				String str = scanner.next();
				if ("Quit".equals(str) || ("quit").equals(str)){
					System.out.println("**** Game over! ****");
					System.exit(0);
				}else{
					System.out.println(" *** Invalid choice ***");
					System.out.println("Chose how to enter the data: ");
					System.out.println("\t 1) Row");
					System.out.println("\t 2) Column");
					System.out.println("\t Quit");
				    continue;	
				}
			}
		}
		

		return row;
	}
	
	/*
	 * Allows for the selection of board size with two choices: 4x4 or 9x9
	 * 
	 * @return board size
	 * 
	 */
	public static int setBoardSize(){
		
		int selection = 0;	
		int boardSize = 0;
		Scanner scanner = new Scanner(System.in);
		
		System.out.println("Chose a board size or quit:");
		System.out.println("  1) 4x4 \n  2) 9x9 \n  Quit");
		
		while( scanner.hasNext() ){
			if (scanner.hasNextInt() ){
				selection = scanner.nextInt();
		 
				if (selection == 1){
					boardSize = 4;
					break;
				} else if (selection == 2){
					boardSize = 9;
					break;
				} else {
					System.out.println("*** Invalid choice ***");
					System.out.println("  Let's play a game! :) ");
					System.out.println("Chose a board size or quit:");
					System.out.println("  1) 4x4 \n  2) 9x9 \n  Quit");	
					continue;
				}
				
			}else{
				
				String str = scanner.next();
				if ("Quit".equals(str) || ("quit").equals(str)){
					System.out.println("**** Game over! ****");
					System.exit(0);
				}else{
					System.out.println("*** Invalid choice ***");
					System.out.println("  Let's play a game! :) ");
					System.out.println("Chose a board size or quit:");
					System.out.println("  1) 4x4 \n  2) 9x9 \n  Quit");	
					continue;	
				}
			}
		}
				
		return boardSize;
	}
	
	
	/*
	 * Initializes base game configuration and
	 * start starts the Sudoku game. 
	 * 
	 * @param game a instance of the Sudoku game
	 * @param boardSize the size for the board dimensions
	 * @row true if input will be entered by row or false if input will be entered 
	 *      by column. 
	 */
	
	public static void playGame(SudokuGame game, int boardSize, boolean row){
		
		String input;
		Scanner scannerline  = new Scanner(System.in);
	     
	    System.out.println("Starting game... ");
	    System.out.format("Playing with %d by %d board", boardSize, boardSize);
	    
	    if (row){
	    	System.out.println("Entering data by row");
	    }else{
	    	System.out.println("Entering data by column");
	    }
	    	    
	    System.out.println("Enter next row of numbers");
	    
	    for( int x=0; x< boardSize; x++ ){
	    	if ( verifyinput(boardSize, scannerline)){
	    		input = scannerline.next();
	    		String [] parts = input.split(",");   		
	    		for (int y=0; y < boardSize ; y++){
	    			if (row){
	    				game.sodoku[x][y] = Integer.parseInt(parts[y]);
	    			}else{
	    				game.sodoku[y][x] = Integer.parseInt(parts[y]);
	    			}
	    		}
	    		game.displayBoard();
	    		System.out.println("Enter next row of numbers");
	    		
	    	}else{
	    		System.out.println("***  GAME OVER *** ");
	    		System.exit(0);
	    	}
	    }    
	    
	    scannerline.close();
	    
	    if (verifyboard(game, boardSize)){
	    	System.out.println("**** SODUKU! **** ");	
	    }else{
	    	System.out.println("**** NO SODUKU! **** ");	
	    }
	    
	    game.displayBoard();
	}
	
	/**
	 * Main entry function for the game
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		int boardSize;
		boolean row = true;
		
		System.out.println("**** Welcome to Sudoku Game ****");
		System.out.println("  Let's play a game! :) ");
		
		boardSize = setBoardSize();
		row = inputChoice();
		
		System.out.println(row);
		System.out.println(boardSize);
		
		SudokuGame game = new SudokuGame(boardSize, row);
		game.displayBoard();
		
		playGame(game,boardSize, row);
		
				
	}
}