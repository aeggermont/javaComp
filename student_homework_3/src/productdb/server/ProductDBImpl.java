package productdb.server;

import java.util.*;


import productdb.DeptCode;
import productdb.Product;
import productdb.ProductAlreadyExistsException;
import productdb.ProductDB;
import productdb.ProductNotFoundException;

import java.io.BufferedOutputStream;
import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import java.io.InputStreamReader;
import java.io.BufferedReader;


/**
 * Implementation of ProductDB that stores products in memory
 * 
 * @author Antonio Eggermont
 *
 */
public class ProductDBImpl implements ProductDB {

	private static Map<Integer, Product> productCatalog = new HashMap<Integer, Product>();
	private static int lastProductIdAdded = 0;
		
	/**
	 * Retrieve product by primary key
	 * @param productId
	 * @return null if not found
	 */
	@Override
	public Product getProduct(int productId) {
		
		if (productCatalog.containsKey(productId)){
			return productCatalog.get(productId);
		}else{
			return null;	
		}		
	}

	/**
	 * Retrieve products by department code
	 * @param code
	 * @return empty list if none found
	 */
	@Override
	public List<Product> getProductsByDept(DeptCode code) {
		
		List<Product> productsByDept = new ArrayList<Product>();
		
		for( Map.Entry<Integer, Product>entry : productCatalog.entrySet()){
			if ( entry.getValue().getDept() == code ){
				productsByDept.add(entry.getValue());
			}
		}
		
		return productsByDept;
	}

	/**
	 * Retrieve all products in database
	 * @return empty list if no products in database
	 */
	@Override
	public List<Product> getAllProducts() {
		
		List<Product> productList = new ArrayList<Product>();
		
		for( Map.Entry<Integer, Product>entry : productCatalog.entrySet()) {
			productList.add(entry.getValue());
		}
		
		return productList;
	}

	/**
	 * Add given product to database
	 * @param p
	 * @throws ProductAlreadyExistsException if there is already a product with same name
	 */
	@Override
	public void addProduct(Product product)
			throws ProductAlreadyExistsException {

		String name = product.getName();
		
		if (productCatalog.containsKey(product.getId())) {
			throw new ProductAlreadyExistsException("Product ID already exists!");
		}
				
		for( Map.Entry<Integer, Product>entry : productCatalog.entrySet()){
			
			if (entry.getValue().getName().equals(name)){
				throw new ProductAlreadyExistsException("Product already exists!");
			} 
		}
	
		lastProductIdAdded += 1;
		product.setId(lastProductIdAdded);
		productCatalog.put(lastProductIdAdded, product);
		
	}

	/**
	 * Update product in database with given information
	 * @param p
	 * @throws ProductNotFoundException if can't find given product by id
	 */
	@Override
	public void updateProduct(Product product) throws ProductNotFoundException {
		
		for( Map.Entry<Integer, Product>entry : productCatalog.entrySet()){
			if ((entry.getValue().getName() == product.getName())){
				product.setId(entry.getKey());
				entry.setValue(product);
				return;
			}
		}
		
		throw new ProductNotFoundException("Product %s does not exist!");
	}

	/**
	 * Remove product from database by product id
	 * @param productId
	 * @throws ProductNotFoundException if can't find given product by id
	 */
	@Override
	public void deleteProduct(int productId) throws ProductNotFoundException {
	
		if ( productCatalog.containsKey(productId)){
			productCatalog.remove(productId);
		}else{
			throw new ProductNotFoundException("Product ID does not exist");
		}
	}

    /**
     * Save the existing products in the database into a file.
     * An implementation of this class decides which File I/O 
     * technique to use and the location of the file.
     */
	@Override
    public void saveProductsToDisk() {
		
		String binaryProductDemo = "/Users/alberttsoi/dev/UCSC-JavaComp/student_homework_3/productDB.bin";
        File outputFile = new File(binaryProductDemo);
        
        DataOutputStream out;       
        
        try {
        	out = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(outputFile)));
        	
        	for( Map.Entry<Integer, Product>entry : productCatalog.entrySet()) {
        		out.writeLong(entry.getKey());
            	out.writeUTF(entry.getValue().getName());
            	out.writeDouble(entry.getValue().getPrice());
            	out.writeUTF(entry.getValue().getDept().name());
            	System.out.print("Product Saved:  ");
                System.out.println(entry.getValue());
        	}
 	
        	out.close();
        
        } catch (FileNotFoundException e){
         	e.printStackTrace();
        } catch (IOException e){
            e.printStackTrace();
        }
    }
	
    /**
     * Load the products from a file.  An implementation of this
     * class decides where to read the products from.
     */
	@SuppressWarnings("deprecation")
	@Override
    public void loadProductsFromDisk() {
				
		String binaryFileIn = "/Users/alberttsoi/dev/UCSC-JavaComp/student_homework_3/productDB.bin";
    	File fileIn = new File(binaryFileIn);
    	
    	try{
    		DataInputStream in = new DataInputStream(new BufferedInputStream(new FileInputStream(fileIn)));	
    		BufferedReader d = new BufferedReader(new InputStreamReader(in));
            Integer index =0;
    	    while ( index < 3){
    	    	
    	    	long pk = in.readLong();
    	    	//String code = in.readUTF();
    	    	String desc = in.readUTF();
    	    	double price = in.readDouble();
			
    	    	System.out.println(pk);	
    	    	//System.out.println(code);
    	    	System.out.println(desc);
    	    	System.out.println(price);
    	    	index += 1;
    	    }
    	    
    		in.close();
    		
    	}catch(IOException e){
    		e.printStackTrace();
    	}
		
    }
    
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		ProductDB productDB = null;
		productDB = new ProductDBImpl();
	
		try{
			productDB.addProduct(new Product("ipod1", 126.0, DeptCode.ELECTRONICS));
			productDB.addProduct(new Product("ipod2", 127.3, DeptCode.ELECTRONICS));
			productDB.addProduct(new Product("ipod3", 128.4, DeptCode.ELECTRONICS));
			productDB.addProduct(new Product("ipod4", 129.4, DeptCode.ELECTRONICS));
			productDB.addProduct(new Product("ipod5", 122.6, DeptCode.ELECTRONICS));
			productDB.addProduct(new Product("The Lord of the Rings 1", 400.23, DeptCode.BOOK));
			productDB.addProduct(new Product("The Lord of the Rings 2", 410.23, DeptCode.BOOK));
			productDB.addProduct(new Product("The Lord of the Rings 3", 420.23, DeptCode.BOOK));
		} catch (ProductAlreadyExistsException mes) {
			System.out.println("Unable to add product " + mes);
		}
		
		System.out.println("===== Products by Department ================");
		
		for (Product item : productDB.getProductsByDept(DeptCode.BOOK)){
			System.out.println("-------------------------");
			System.out.println(item.getName());
			System.out.println(item.getId());
			System.out.println(item.getPrice());	
		}
		
		try{
		    productDB.addProduct(new Product("ipod1", 126.0, DeptCode.ELECTRONICS));
		} catch (ProductAlreadyExistsException mes) {
			System.out.println("Unable to add product " + mes);
		}
		

		
		System.out.println("===== Lists All Products ================");
		
		for (Product item: productDB.getAllProducts()){
			System.out.println("-------------------------");
			System.out.println(item.getName());
			System.out.println(item.getId());
			System.out.println(item.getPrice());		
		}
		
		try{
			productDB.deleteProduct(3);
		} catch (ProductNotFoundException mesg){
			System.out.println(mesg);
		}
		
		System.out.println("===== Lists All Products (Again) ================");
		
		for (Product item: productDB.getAllProducts()){
			System.out.println("-------------------------");
			System.out.println(item.getName());
			System.out.println(item.getId());
			System.out.println(item.getPrice());		
		}
		
		System.out.println("===== Updating Product ================");
		
		Product updatedItem = new Product("ipod4", 900.90, DeptCode.COMPUTER);
		
		try {
			productDB.updateProduct(updatedItem);
		} catch(ProductNotFoundException mesg ){
			System.out.println(mesg);
		}
		
		System.out.println("===== Lists All Products (Again) ================");
		
		for (Product item: productDB.getAllProducts()){
			System.out.println("-------------------------");
			System.out.println(item.getName());
			System.out.println(item.getId());
			System.out.println(item.getPrice());		
		}
		
		System.out.println("===== Testing Binary File Writes ================");
		
		productDB.saveProductsToDisk();
		productDB.loadProductsFromDisk();
	}
}
