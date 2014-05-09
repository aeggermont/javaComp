package amazonShoppingCar;

import java.util.*;

import amazonShoppingCar.ProductCatalog.Catalog;


public class ShoppingCart extends ProductCatalog{

	private static ArrayList shoppingCart = new ArrayList();
	
	
	public ShoppingCart(){
	    super();	
	}
	
	public static void addProductToCart(Product item){
		shoppingCart.add(item);
	}

	public static int getNumItemsInCart(){
		return shoppingCart.size();
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
		
		System.out.println(getNumItemsInCart());
		
	}
}