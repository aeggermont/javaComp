package amazonShoppingCar;

/*
 *  This class represents a product that can be added in a product catalog
 *  to be available for shoppers in a shopping cart
 *               
 * @author Antonio A. Eggermont
 * @version 1.0  
 */

public class Product {

	private String name;
	private int sn;
	private double price;
	
	/*
	 * Constructor for invocation of class Product by a sub class.
	 * 
	 * @param name the name of the product
	 * @param id   an assigned id for the product
	 * @param price a price for the product
	 * @param number of items in the inventory  
	 */
	public Product(String name, int sn, double price){
		this.name = name;
		this.sn = sn;
		this.price = price;
	}
	
	/*
	 * Gets the name of a product
	 * 
	 * @return the name of the product
	 */
	public String getName() {
		return name;
	}

	/*
	 * Sets the price for a product
	 * 
	 * @param price the price in dollars for a product
	 */
	public void setPrice(double price) {
		this.price = price;
	}
	
	/*
	 * Gets the price set for a product
	 * 
	 * @return price the value set for a product
	 */
	public double getPrice() {
		return price;
	}

	/*
	 * Sets or updates the name of a product
	 * 
	 * @param name a name for the product
	 */
	public void setName(String name) {
		this.name = name;
	}

	/*
	 * Gets the serial number set for a product
	 * 
	 * @return sn the serial number set for a product
	 */
	public int getSerialNumber() {
		return sn;
	}

	/*
	 * Sets or updates the serial number for a product
	 * 
	 * @param sn te serial number for a product
	 * 
	 */
	public void setSerialNumber(int sn) {
		this.sn = sn;
	}
}