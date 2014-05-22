package productdb.server;

import java.util.*;


import productdb.DeptCode;
import productdb.Product;
import productdb.ProductAlreadyExistsException;
import productdb.ProductDB;
import productdb.ProductNotFoundException;
import java.io.FileNotFoundException;
import java.io.EOFException;

import java.io.BufferedOutputStream;
import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import java.io.InputStreamReader;
import java.io.BufferedReader;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;


/**
 * Implementation of ProductDB that stores products in memory
 * 
 * @author Antonio Eggermont
 *
 */
public class ProductDBImpl implements ProductDB {

	private static Map<Integer, Product> productCatalog = new HashMap<Integer, Product>();
	private static final String FILE_NAME = "productDB.bin";
	private static int lastProductIdAdded = 0;
		
	/**
	 * Constructs a path to store and load products  
	 * 
	 * @return full qualified path name to read and write to the product catalog file
	 */
	private static String buildPathFileName() {
		String fileName = System.getProperty("user.home");
		fileName = fileName + System.getProperty("file.separator");
		fileName = fileName + FILE_NAME;
		System.out.format("Setting output path to: %s \n",  fileName);
		
		return fileName;
	}
	
	
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
     * @throws 
     */
	@Override
    public void saveProductsToDisk() {
		
		String fileOut = buildPathFileName();
                  
        try {
        	
			ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(fileOut));

			for (Map.Entry<Integer, Product>entry : productCatalog.entrySet()){
				output.writeObject((Product)entry.getValue());
				//System.out.println(entry.getValue());
			}	
			output.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
        
    }
	
    /**
     * Load the products from a file.  An implementation of this
     * class decides where to read the products from.
     * @throws 
     */
	@Override
    public void loadProductsFromDisk()  throws IOException, FileNotFoundException{
		
		File fileIn = new File (buildPathFileName());
		boolean endOfFile = false;
		
		ObjectInputStream input = new ObjectInputStream(new FileInputStream(fileIn));

		while(!endOfFile){
			try{
				Product tmpProd = (Product)input.readObject();
				productCatalog.put(tmpProd.getId(), tmpProd);
			}catch (EOFException e){
				endOfFile = true; 
			}catch (IOException e){
				e.printStackTrace();
			}catch (ClassNotFoundException e){
				e.printStackTrace();
			}
		}
		
	    input.close();		
    }
    
	
	/**
	 * @param args
	 */
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		
		ProductDB productDB = null;
		productDB = new ProductDBImpl();
	
		
		System.out.println("===== Loading Data to Database ================");

		try{
			productDB.loadProductsFromDisk();
		} catch ( IOException e){
			e.printStackTrace();
		}
		
		/*
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
		*/
		
		System.out.println("===== Lists All Products ================");
		
		for (Product item: productDB.getAllProducts()){
			System.out.println("-------------------------");
			System.out.println(item.getName());
			System.out.println(item.getId());
			System.out.println(item.getPrice());		
		}
		
		System.out.println("===== Saving Data to Disk ================");
		
		//productDB.saveProductsToDisk();
		
		
		
		
	}
}
