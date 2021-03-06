package productdb;

import java.io.IOException;
import java.net.Socket;
import java.util.List;

/**
 * DO NOT CHANGE ANYTHING IN THIS INTERFACE.
 * 
 * Homework #3 is about providing an implementation of this class
 * 
 * @author hluu
 *
 */
public interface ProductDB {

	
	/**
	 * Retrieve product by primary key
	 * @param productId
	 * @return null if not found
	 */
	Product getProduct(int productId);

	/**
	 * Retrieve products by department code
	 * @param code
	 * @return empty list if none found
	 */
	List<Product> getProductsByDept(DeptCode code);

	/**
	 * Retrieve all products in database
	 * @return empty list if no products in database
	 */
	List<Product> getAllProducts();

	/**
	 * Add given product to database
	 * @param p
	 * @throws ProductAlreadyExistsException if there is already a product with same name
	 */
	void addProduct(Product product) throws ProductAlreadyExistsException;

	/**
	 * Update product in database with given information
	 * @param p
	 * @throws ProductNotFoundException if can't find given product by id
	 */
	void updateProduct(Product product) throws ProductNotFoundException;

	/**
	 * Remove product from database by product id
	 * @param productId
	 * @throws ProductNotFoundException if can't find given product by id
	 */
	void deleteProduct(int productId) throws ProductNotFoundException;
	
    /**
     * Save the existing products in the database into a file.
     * An implementation of this class decides which File I/O 
     * technique to use and the location of the file.
     */
    public void saveProductsToDisk() throws IOException;
	
    /**
     * Load the products from a file.  An implementation of this
     * class decides where to read the products from.
     * @throws IOException 
     */
    public void loadProductsFromDisk() throws IOException;
 
    /**
     * Sends a message to the server to stop it and client endd
     * connection with it
     * @throws IOException
     */
    public String quitServer();
}
