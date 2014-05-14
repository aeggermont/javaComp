package amazonShoppingCar;

import java.util.*;

/*
 * A class to represent a shopping cart for a product catalog. 
 * This class has the following functionality
 * 
 *<ul>
 *<li> Create an instance of a product catalog
 *<li> Add a product to the shopping cart
 *<li> Return the total num,ber of items in the shopping cart
 *</ul>
 * 
 * @author Antonio A. Eggermont
 * @version 1.0  
 * 
 */

public class ShoppingCart extends ProductCatalog{

	private static ArrayList shoppingCart = new ArrayList();
	
	/*
	 * Constructor for invocation by sub class.
	 * 
	 */
	
	public ShoppingCart(){
	    super();	
	}
	
	/*
	 * Adds a product to an initialized shopping cart
	 * 
	 * @param an object of type product 
	 * 
	 */
	public static void addProductToCart(Product item){
		shoppingCart.add(item);
	}

	/*
	 * Computes the number of items currently added in the shopping cart
	 * 
	 * @return the number of items currently in the shopping cart
	 * 
	 */
	public static int getNumItemsInCart(){
		return shoppingCart.size();
	}
	
	/*
	 * Computes the total amount in money for products 
	 * added to the shopping cart.
	 * 
	 * @return amount in money for products currently added in the 
	 *         shopping cart.
	 */
	public static double getTotalAmmountInCart(){
		
		double total = 0;

		for( int i=0; i < shoppingCart.size() ; i++){
			total += ((Product)shoppingCart.get(i)).getPrice();
		}
		
		return total;
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		loadProducts();
		//listCatalog();	

		Product product1 = getProduct(101, "KindleFireProducts");
		Product product2 = getProduct(203, "UnlimitedInstantVideos");
		
		addProductToCart(product1);
		addProductToCart(product2);
		
		System.out.println(product1.getName());
		System.out.println(product1.getPrice());
		
		System.out.println(product2.getName());
		System.out.println(product2.getPrice());
		
		System.out.println("============================");
		System.out.println(getNumItemsInCart());
		System.out.println(getTotalAmmountInCart());
		System.out.println("============================");
	}
}