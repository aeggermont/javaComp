package amazonShoppingCar;

/*
 * This class represents the abstraction for a product catalog. it 
 * defines a hashmap for each product line available in the product
 * catalog. Currently only eight product lines were added and only
 * two were populated with actual products. This class has the 
 * following functionality:
 * 
 *  <ul>
 *  <li> Initializes product line collections
 *  <li> Loads product objects for test
 *  <li> Creates a catalog 
 *  <li> Lists a catalog
 *  <li> Retrieves the information for a specific product based 
 *       on the product line
 *  </ul>
 *  
 */

import java.util.*;

public class ProductCatalog
{

	/*
	 * Collections to hold several products per product
	 * category
	 */
		
	private static HashMap KindleFireProducts = new HashMap();
	private static HashMap unlimitedInstantVideos = new HashMap();
	private static HashMap electronicsAndComputers = new HashMap();
	private static HashMap toysKidsBaby = new HashMap();
    private static HashMap mp3sAndCloudPlayer = new HashMap();
	private static HashMap amazonFireTV = new HashMap();
	private static HashMap clothingShoesJewelry = new HashMap();
	private static HashMap amazonCloudDrive = new HashMap();

	
	/*
	 * Creates several product objects and loads them in their
	 * corresponding product category collections for testing 
	 * Each product added to each HashMap is identified by a product
	 * category.
	 */
	public static void loadProducts(){
	
		// Load Kindle Fire Products
		KindleFireProducts.put(100, new Product("Kindle Fire HD", 100, 350.50));
		KindleFireProducts.put(101, new Product("Kindle Fire HDX", 101, 350.50));
		KindleFireProducts.put(102, new Product("Kindle Fire HDX 8.9", 102, 350.50));
		KindleFireProducts.put(103, new Product("Kindle Fire Accessories", 103, 350.50));
		KindleFireProducts.put(104, new Product("Instant Video", 104, 350.50));
	
		// Load Unlimited Instant Videos
		unlimitedInstantVideos.put(201, new Product("Amazon Instant Video", 201, 100));
		unlimitedInstantVideos.put(202, new Product("Prime Instant Video", 202, 110));
		unlimitedInstantVideos.put(203, new Product("Shop Instant Video", 203, 140));
		unlimitedInstantVideos.put(204, new Product("Your Watchlist", 204, 150));
		unlimitedInstantVideos.put(205, new Product("Your Video Library", 205, 330));
		unlimitedInstantVideos.put(206, new Product("Watch Anywher", 206, 500));
	}
	
	/*
	 * Creates a catalog of products using the product categories 
	 * collections created in the class
	 * 
	 */
	public enum Catalog { 

		KindleFireTablets("Kindle Fire Tablets", KindleFireProducts),
		UnlimitedInstantVideos("Unlimited Instant Videos", unlimitedInstantVideos);
		
		private String departmentName;
		private HashMap products = new HashMap();
    
		Catalog(String departmentName,  HashMap products) {
			this.departmentName = departmentName;
			this.products = products;
		}
		
		/*
		 *  Gets the department name
		 *  
		 *   @return department name
		 */
		public String getDepartmentName(){ return departmentName; }
		
		/*
		 * Gets the products for a specific product category
		 * 
		 * @return a collection of products included in a product
		 *         category
		 */
		public HashMap getProducts(){ return products; }
		
	}
	
	/*
	 * Lists the product categories and products available in the 
	 * product catalog
	 */
	public static void listCatalog(){
		for ( Catalog ap : Catalog.values() ){
			//System.out.println(ap.departmentName);	
			System.out.println(ap.getDepartmentName());
			HashMap productList = ap.getProducts();
			
			// Get a set of entries
			Set set = productList.entrySet();
			Iterator items = set.iterator();
			while (items.hasNext()){
				Map.Entry item = (Map.Entry)items.next();
				
				System.out.format("%s, %f, %d \n", ((Product)item.getValue()).getName() , 
						                            ((Product)item.getValue()).getPrice());
			}
		}
		
		
	}
	
	/*
	 *  Retrieves the information for a given product from the product catalog 
	 *  based on the product catalog ID.
	 *  
	 *  @param id the product category ID
	 *  @param the product category
	 *  @return the product object
	 */
	public static Product getProduct(int id, String category){
		System.out.println(Catalog.KindleFireTablets.products.get("Amazon Instant Video"));
		
		Product product;
		
		if (category.contentEquals("UnlimitedInstantVideos")){
			product = (Product)Catalog.UnlimitedInstantVideos.products.get(id); 
		}else if (category.contentEquals("KindleFireProducts")){
			product = (Product)Catalog.KindleFireTablets.products.get(id); 
		}else{
			return null;
		}
		
		return product;
	}
	
}