package sudoku;

import java.util.Scanner;

/*
 * 
 * Homework 2
 * Antonio A. Eggermont
 * Sudoku Game Version 2
 * 
 */

public class GameDriver extends SodukuVerify{

	private static boolean inputChoise(){
		
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
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		int boardSize;
		boolean row = true;
		
		System.out.println("**** Welcome to Sudoku Game ****");
		System.out.println("  Let's play a game! :) ");
		
		boardSize = setBoardSize();
		row = inputChoise();
		
		System.out.println(row);
		System.out.println(boardSize);
		
		SudokuGame game = new SudokuGame(boardSize, row);
		game.displayBoard();
		
		playGame(game,boardSize, row);
		
				
	}
}