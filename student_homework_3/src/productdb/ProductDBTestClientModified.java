package productdb;

import java.util.List;

import productdb.server.ProductDBImpl;
import productdb.client.ProductDBClient;

import productdb.util.Assert;
import java.io.IOException;

/**
 * This class exercises all the methods in the @link{ProductDB} interface
 *  
 * @author aeggermont
 * @date   03/06/2014
 */
public class ProductDBTestClientModified {

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
		
		
		int beforeTotalCount = productDB.getAllProducts().size();
		int beforeDeptCount = productDB.getProductsByDept(DeptCode.ELECTRONICS).size();
		
		/*
		try {
			productDB.loadProductsFromDisk();
		} catch (IOException e) {
			Assert.fail("shouldn't ve gotten IOException");
		}
		*/
		
		Assert.assertNotNull(productDB.getAllProducts());
		
		System.out.println("-------------------------");
		for (Product item: productDB.getAllProducts()){
			System.out.println(item);
		}
		

		Assert.assertNotNull(productDB.getAllProducts());
		Product ipod = new Product("ipod1", 155.0, DeptCode.BOOK);
		
		productDB.addProduct(ipod);
		Assert.assertNotNull(productDB.getProductsByDept(DeptCode.ELECTRONICS));
					
		System.out.println(beforeTotalCount);
		System.out.println(beforeDeptCount);
		
		System.out.println("********* start testing ************");
		
		ipod = productDB.getProduct(1);
		
		Assert.assertNotNull(productDB.getProduct(ipod.getId()));
		Assert.assertNotNull(productDB.getAllProducts());
		
		System.out.println(productDB.getAllProducts().size());
		System.out.println(productDB.getProductsByDept(ipod.getDept()).size());
		
		Assert.assertEquals(productDB.getAllProducts().size(), beforeTotalCount+1);
		Assert.assertEquals(productDB.getProductsByDept(ipod.getDept()).size(), beforeDeptCount +1);
		
		double newPrice = ipod.getPrice() + Math.random() * 100;
		
		ipod.setPrice(newPrice);
		System.out.println(ipod);
		productDB.updateProduct(ipod);
		
		Assert.assertEquals(newPrice, productDB.getProduct(ipod.getId()).getPrice());
	
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
		
		try{
			productDB.saveProductsToDisk();
		}catch(IOException e){
			Assert.fail("shouldn't ve gotten IOException");
		}

		System.out.println("-------------------------");
		for (Product item: productDB.getAllProducts()){
			System.out.println(item);	
		}
		
		
		Assert.assertNotNull(productDB.quitServer());
		
		System.out.println("********* done testing ************");
		
		
	}
}