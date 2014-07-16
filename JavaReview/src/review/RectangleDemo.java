package review;

/*
 * Some notes:
 * 	java.lang.Object: Root of Java class hierarchy
 * 
 */

public class RectangleDemo {

	private static void doSomethig(int value, Rectangle rec){
		System.out.println("About to do something ... ");
		
		rec.setHeight(value);
		System.out.println(value);
	}
	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
	
		// TODO Auto-generated method stub
		Rectangle rec = new Rectangle(5,10);
				
		Rectangle rec1 = rec;
		
		System.out.println(rec1.getArea());
		System.out.println(rec1.getPerimeter());

		doSomethig(100, rec1);
		System.out.println(rec.getArea());
		System.out.println(rec.getPerimeter());
		
		System.out.println(rec1.toString());
		
		if ( rec1.equals(rec)){
			System.out.println("Both objects are equial!");
		}
		
		System.out.println("About to print dimension ... ");
		//rec1.setDimenstion(1000);
		System.out.println(rec1.getDimension());
		
	}

}
