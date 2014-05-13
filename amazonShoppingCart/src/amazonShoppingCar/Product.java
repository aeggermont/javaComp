package amazonShoppingCar;

/*
 *  Class Product
 *  Description: This class represents a product that has been added in a product catalog
 *               to be available for shoppers in a shopping cart
 */

public class Product {

	private String name;
	private int id;
	private double price;
	private int inventory = 0;
	
	public Product(String name, int id, double price, int inventory){
		this.name = name;
		this.id = id;
		this.price = price;
		this.inventory = inventory;
	}
	
	public String getName() {
		return name;
	}

	public void setPrice(double price) {
		this.price = price;
	}
	
	public int getInventory() {
		return inventory;
	}

	public void subtractInventory(int quantity) {
		this.inventory = inventory - quantity;
	}
	
	public void addInventory(int quanity){
		this.inventory += quanity;
	}
	
	public double getPrice() {
		return price;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}


	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
}