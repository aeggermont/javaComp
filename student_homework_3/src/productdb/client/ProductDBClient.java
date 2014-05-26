package productdb.client;

/* Base Imports */

import java.util.List;
import productdb.DeptCode;
import productdb.Product;
import productdb.ProductAlreadyExistsException;
import productdb.ProductDB;
import productdb.ProductNotFoundException;

/* Additional imports */

import java.util.Date;
import java.util.Calendar;
import java.net.Socket;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

/**
 * Client side of homework #5
 * 
 * @author hluu
 *
 */
public class ProductDBClient implements ProductDB {

	public static final int PORT_NO = 8888;
	
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

    @Override
    public void saveProductsToDisk(){
    	// TODO Auto-generated method stub
    }
	
    @Override
    public void loadProductsFromDisk(){
    	//TODO Auto-generated method stub
    }
    
    /*
     * Stop remote product server
     * @throws 
     */
    @Override
    public void quitServer(Socket socket) throws IOException{
    	
    	String msg;
    	
    	Date d = Calendar.getInstance().getTime();
    	msg = "QUIT [" + d + "]";
    	
    	System.out.println("About to send message ... ");
    	System.out.println(msg);
    	
    	BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

    	PrintWriter pw = new PrintWriter(socket.getOutputStream());
    	pw.println(msg);
    	pw.flush();
    	
    	// String line = reader.readLine();
    	// reader.close();
    	// System.out.println("result: " + line);
    	
    	System.out.println("Closing session ...");
    	socket.close();
    }
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		ProductDB productDB = null;
		productDB = new ProductDBClient();
		
		try {
			Socket socket = new Socket("localhost", ProductDBClient.PORT_NO);
			productDB.quitServer(socket);
		} catch (IOException e){
			e.printStackTrace();
		}	
	}

}
