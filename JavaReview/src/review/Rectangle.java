package review;

public class Rectangle {
	
	private int width;
	private int height;
	private static int dimension = 670;
	
	/**
	 * Overloading Constructors - different ways of initializing an object  
	 */
	
	public Rectangle() {
		dimension = 2000;
	}
	
	public static void setDimenstion(int dimension){
		dimension = dimension;
	}
	
	public static int getDimension(){
		return dimension;
	}
	
	private int getHeight() {
		return height;
	}
	
	public void setHeight(int height) {
		this.height = height;
	}

	private void setWidth(int width) {
		this.width = width;
	}

	public Rectangle(int w, int h){
		this.width = w;
		this.height = h;
	}
	
	public int getWidth(){ return this.width; }
	
	public int getHieight() { return this.height; }
	
	public int getArea() { return this.width * this.height; }
	
	public int getPerimeter() { 
		return 2 * width + 2 * height;
	}
	
	/**
	 * Modifying the behavior of a method
	 */
	
	@Override
	public String toString() {
		return this.width + "," + this.height;
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
