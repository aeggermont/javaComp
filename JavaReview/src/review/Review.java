package review;

import java.util.Scanner;

public class Review {
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("Java Basic Review!");
		
		// Integers
		int myInt = 123;
		System.out.println(myInt);
		
		
		// Doubles
		double myDouble = 123.50;
		
		// Integer math, is there a problem?
		System.out.println("myInt/5: " + (myInt / 5));
		
		// Better precision with a double
		System.out.println("myInt/5: " + (myDouble / 5));
		
		// Another type-cast by forcing double match
		System.out.println("myInt/5.0: " + ((double)myInt / 5));

		// ***** reading stdin from keyboard   *****
		Scanner keyboard = new Scanner(System.in);
		System.out.println("Please enter an integer: ");
		
		// Wait for the user to enter an integer
		int input = 0;

		try{
			input = keyboard.nextInt();
		} catch(Exception e){
			System.out.println("invalid input quiting .... ");
			System.exit(1);
		}
		
		// Using switch statements 
		switch(input) {
			case 123: System.out.println("You entered 123"); break;
			case 124: System.out.println("You entered 124"); break;
			case 125: System.out.println("You entered 125"); break;
			default: System.out.println("That's a boring number");			
		}
		
		// Test to see if what the user entered matches our number
		if (input == myInt){
			System.out.println(" Numbers matchj :-)");
		}else{
			System.out.println("Numbers do not match :-(");
		}
		
		// **** Strings ****
		String myStr = "Antonio Aranda";	
		String strInput = null;
		
		System.out.println(myStr);
		System.out.println("substring: " + myStr.substring(3,8));
		
		// wait for the user to enter a string
		try {
			strInput = keyboard.nextLine();
		}catch (Exception e){
			System.out.println("invalid input! quitting!!!");
			System.exit(1);
		}
		
		// test to see if what the user entered matches what we have
		
		if ( myStr == strInput ){
			System.out.println("Strings match!");
		}else{
			System.out.println("Strings do not match!");
		}
		
		//System.out.println("Converting myStr to int: "+Integer.parseInt(myStr));
		
		
		// ******* Arrays Review *****
		
		// declare an int array
		
		int[] grades;
		
		// Allocate memory for 5 indices
		grades = new int[5];
		
		// Assign some values to the array
		grades[0] = 100;
		grades[1] = 70;
		grades[2] = 90;
		
		// Print some of these values
		System.out.println(grades[0]);
		System.out.println(grades[1]);
	
		// Printing all values
		for( int i = 0; i < grades.length; i++){
			System.out.println("Grade: " + (i + 1 )+ ": " + grades[i]);
		}
		
		// pre=store the length into a variable 
		for (int i=0, j=grades.length; i < j; i++){
			System.out.println("Grade "+(i+1)+": "+grades[i]);
		}
		
		int i = 0, j = grades.length;
		
		// Using a while loop to iterate over the grades array
		while( i < j){
			System.out.println("Grade "+(i+1)+": "+grades[i]);
			i++;
		}
		
		// Using a do while loop. This loop evaluates a condition after an
		// initial run. In other words, the loop is guaranteed to run at least
		// once
		do {
		    System.out.println("Grade "+(i+1)+": "+grades[i]);
		} while( ++i < j);
		
	}
}