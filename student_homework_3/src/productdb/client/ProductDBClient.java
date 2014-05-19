package productdb.client;

import java.util.List;

import productdb.DeptCode;
import productdb.Product;
import productdb.ProductAlreadyExistsException;
import productdb.ProductDB;
import productdb.ProductNotFoundException;

/**
 * Client side of homework #5
 * 
 * @author hluu
 *
 */
public class ProductDBClient implements ProductDB {

	@Override
	public Product getProduct(int productId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Product> getProductsByDept(DeptCode code) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Product> getAllProducts() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addProduct(Product product)
			throws ProductAlreadyExistsException {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateProduct(Product product) throws ProductNotFoundException {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteProduct(int productId) throws ProductNotFoundException {
		// TODO Auto-generated method stub

	}

	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
