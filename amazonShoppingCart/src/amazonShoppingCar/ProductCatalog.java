package amazonShoppingCar;

import java.util.*;


public class ProductCatalog
{

	/*
	 * Loading Kindle Fire Tablet Products
	 */
	
	private static HashMap KindleFireProducts     = new HashMap();
	private static HashMap unlimitedInstantVideos = new HashMap();
	private static HashMap electronicsAndComputers = new HashMap();
	private static HashMap toysKidsBaby = new HashMap();
	private static HashMap mp3sAndCloudPlayer = new HashMap();
	private static HashMap amazonFireTV = new HashMap();
	private static HashMap clothingShoesJewelry = new HashMap();
	private static HashMap amazonCloudDrive = new HashMap();

	
	public static void loadProducts(){
	
		// Load Kindle Fire Products
		KindleFireProducts.put(100, new Product("Kindle Fire HD", 100, 350.50, 10));
		KindleFireProducts.put(101, new Product("Kindle Fire HDX", 101, 350.50, 15));
		KindleFireProducts.put(102, new Product("Kindle Fire HDX 8.9", 102, 350.50, 3));
		KindleFireProducts.put(103, new Product("Kindle Fire Accessories", 103, 350.50, 4));
		KindleFireProducts.put(104, new Product("Instant Video", 104, 350.50, 50));
	
		// Load Unlimited Instant Videos
		unlimitedInstantVideos.put(201, new Product("Amazon Instant Video", 201, 100, 10));
		unlimitedInstantVideos.put(202, new Product("Prime Instant Video", 202, 110, 2));
		unlimitedInstantVideos.put(203, new Product("Shop Instant Video", 203, 140, 50));
		unlimitedInstantVideos.put(204, new Product("Your Watchlist", 204, 150, 30));
		unlimitedInstantVideos.put(205, new Product("Your Video Library", 205, 330, 10));
		unlimitedInstantVideos.put(206, new Product("Watch Anywher", 206, 500, 10));
	}
	
	
	public enum Catalog { 

		KindleFireTablets("Kindle Fire Tablets", KindleFireProducts),
		UnlimitedInstantVideos("Unlimited Instant Videos", unlimitedInstantVideos);
		
		private String departmentName;
		private HashMap products = new HashMap();
    
		Catalog(String departmentName,  HashMap products) {
			this.departmentName = departmentName;
			this.products = products;
		}
		
		public String getDepartmentName(){ return departmentName; }
		public HashMap getProducts(){ return products; }
		
	}
	
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
						                            ((Product)item.getValue()).getPrice() ,  
						                            ((Product)item.getValue()).getInventory()    );
			}
		}
		
		
	}
	
	/*
	 *  Returns a product object
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
	
	
	public static void main(String [] args){
		// Loading products for Kindle Fire Tablets		
		//
		loadProducts();
		//listCatalog();
		Product foo = (Product)Catalog.UnlimitedInstantVideos.products.get(202);
		System.out.println(foo.getName());
		System.out.println(foo.getPrice());
		
		//System.out.println(Catalog.UnlimitedInstantVideos.products.get("Amazon Instant Video"));
		
	}		
}