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
		
		Product ipod = new Product("ipod1", 125.0, DeptCode.ELECTRONICS);
				
		Assert.assertNotNull(productDB.getAllProducts());
		Assert.assertNotNull(productDB.getProductsByDept(DeptCode.ELECTRONICS));
				
		int beforeTotalCount = productDB.getAllProducts().size();
		int beforeDeptCount = productDB.getProductsByDept(DeptCode.ELECTRONICS).size();
		
		
		productDB.addProduct(ipod);

		System.out.println(beforeTotalCount);
		System.out.println(beforeDeptCount);
		
		System.out.println("********* start testing ************");
		
		Assert.assertNull(ipod.getId());
		
		ipod.setId(1);
		
		Assert.assertNotNull(productDB.getProduct(ipod.getId()));
		Assert.assertNotNull(productDB.getAllProducts());
		
		System.out.println(productDB.getAllProducts().size());
		System.out.println(productDB.getProductsByDept(ipod.getDept()).size());
		
		
		Assert.assertEquals(productDB.getAllProducts().size(), beforeTotalCount+1);
		Assert.assertEquals(productDB.getProductsByDept(ipod.getDept()).size(), beforeDeptCount +1);
		
		double newPrice = ipod.getPrice() + Math.random() * 100;
		ipod.setPrice(newPrice);
		productDB.updateProduct(ipod);
		
		// TODO Assert.assertEquals(newPrice, productDB.getProduct(ipod.getId()).getPrice());
		
	
		// testing getAllProducts()
		List<Product> productList = productDB.getAllProducts();
		int size = productList.size();
		productList.remove(0);
		productList = productDB.getAllProducts();
		// TODO Assert.(size, productList.size());
		
		
		try {
			Product ipod2 = new Product(10, new String("ipod1"), 125.0, DeptCode.ELECTRONICS);
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
		System.exit(0);
	}

}
