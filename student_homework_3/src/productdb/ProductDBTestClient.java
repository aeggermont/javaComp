package productdb;

import java.util.List;

import productdb.server.ProductDBImpl;
import productdb.client.ProductDBClient;
import productdb.util.Assert;



/**
 * This class exercises all the methods in the @link{ProductDB} interface
 *  
 * @author hluu
 *
 */
public class ProductDBTestClient {

	public static void main(String[] args) throws Exception {
		
		ProductDB productDB = null;
		productDB = new ProductDBClient();
		
		testProductServer(productDB);
	}

	/**
	 * DO NOT MODIFY ANYTHING IN THIS METHOD.  THIS METHOD SHOULDN'T THROW
	 * ANY EXCEPTION IF PRODUCT DB SERVER WAS IMPLENTED ACCORDING TO ProductDB
	 * INTERFACE 
	 * 
	 * @param productDB
	 * @throws ProductAlreadyExistsException
	 * @throws ProductNotFoundException
	 */
	private static void testProductServer(ProductDB productDB)
			throws ProductAlreadyExistsException, ProductNotFoundException {
		
		Product ipod = new Product("ipod", 125.0, DeptCode.ELECTRONICS);
		System.out.println(ipod);
		
		Assert.assertNotNull(productDB.getAllProducts());
		Assert.assertNotNull(productDB.getProductsByDept(DeptCode.ELECTRONICS));
		
		int beforeTotalCount = productDB.getAllProducts().size();
		int beforeDeptCount = productDB.getProductsByDept(DeptCode.ELECTRONICS).size();
		
		productDB.addProduct(ipod);
		ipod = productDB.getProduct(1);
		
		System.out.println("********* start testing ************");
		System.out.println(ipod.getId());
		Assert.assertNotNull(ipod.getId());
		Assert.assertNotNull(productDB.getProduct(ipod.getId()));
		Assert.assertNotNull(productDB.getAllProducts());
		
		Assert.assertEquals(productDB.getAllProducts().size(), beforeTotalCount+1);
		Assert.assertEquals(productDB.getProductsByDept(ipod.getDept()).size(), beforeDeptCount +1);
		
		double newPrice = ipod.getPrice() + Math.random() * 100;
		ipod.setPrice(newPrice);
		productDB.updateProduct(ipod);
		Assert.assertEquals(newPrice, productDB.getProduct(ipod.getId()).getPrice());
		
		// testing getAllProducts()
		List<Product> productList = productDB.getAllProducts();
		int size = productList.size();
		productList.remove(0);
		productList = productDB.getAllProducts();
		Assert.assertEquals(size, productList.size());
		
		
		try {
			Product ipod2 = new Product(new String("ipod"), 125.0, DeptCode.ELECTRONICS);
			productDB.addProduct(ipod2);
			Assert.fail("should've gotten ProductAlreadyExistsException");
		} catch (ProductAlreadyExistsException pae) {
			// expecting this
		}
		
		productDB.deleteProduct(ipod.getId());
		Assert.assertNotNull(ipod.getId());
		
		ipod.setId(Integer.MAX_VALUE);
		try {
			productDB.updateProduct(ipod);
			Assert.fail("should've gotten ProductNotFoundException");
		} catch (ProductNotFoundException pnfe) {
			// expecting this
		}
		
		try {
			productDB.deleteProduct(ipod.getId());
			Assert.fail("should've gotten ProductNotFoundException");
		} catch (ProductNotFoundException pnfe) {
			// expecting this
		}
		System.out.println("********* done testing ************");
	}

}
