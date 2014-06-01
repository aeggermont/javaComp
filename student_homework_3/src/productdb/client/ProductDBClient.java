package productdb.client;

/* Base Imports */

import java.util.List;
import productdb.DeptCode;
import productdb.Product;
import productdb.ProductAlreadyExistsException;
import productdb.ProductDB;
import productdb.ProductNotFoundException;

/* Additional imports */

import java.util.ArrayList;
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

	public static final int PORT_NO = 8900;
	private static Socket socket_fd;
	
	
	public void connectToServer(){
		
		try{
			socket_fd = new Socket("localhost", PORT_NO);
		} catch (IOException e){
			e.printStackTrace();
		}
	}
	
		
	public void closeConnection(){
		try{
			socket_fd.close();
		} catch (IOException e){
			e.printStackTrace();
			System.exit(1);
		}		
	}
	
	public void stopServer(){
		
		try{
			socket_fd.close();
		} catch (IOException e){
			e.printStackTrace();
		}
	}
	
	
	@Override
	public Product getProduct(int productId) {
		
		String mesg = null;
		mesg = "SEARCH " + String.valueOf(productId) + ",";
		Product prodInfo = null;
		connectToServer();
		
		try{
			BufferedReader reader = new BufferedReader(new InputStreamReader(socket_fd.getInputStream()));
			PrintWriter pw = new PrintWriter(socket_fd.getOutputStream());
			pw.println(mesg);
			pw.flush();
			
			String result = reader.readLine();
			reader.close();
			System.out.println(result);
            
			
		}catch ( IOException e){
			e.printStackTrace();
			return null;
		}finally{
			closeConnection();
		}
		
		return prodInfo;
	}

	@Override
	public List<Product> getProductsByDept(DeptCode code) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Product> getAllProducts() {
		
		List<Product> products = new ArrayList<Product>();
		
		String mesg = null;   
		String line = null;
		String [] tokens;
		
		mesg = "LIST " + ",";

		connectToServer();
		
		try{
			BufferedReader reader = new BufferedReader(new InputStreamReader(socket_fd.getInputStream()));
			PrintWriter pw = new PrintWriter(socket_fd.getOutputStream());
			pw.println(mesg);
			pw.flush();
			
			line = reader.readLine();
			
			System.out.println("results: " + line);
			
			// IF not [ERROR] in results
			
			while(true){
				line = reader.readLine();
				if ( line == null) { break; }
				
				tokens = line.split(",");
				
				if ( tokens.length == 4){
					
					// System.out.println(tokens[0].split(":")[1]);
					// System.out.println(tokens[1].split(":")[1]);
					// System.out.println(tokens[3].split(":")[1]);
					// System.out.println((DeptCode.valueOf(tokens[2].split(":")[1])));
					
					products.add(new Product ( Integer.parseInt(tokens[0].split(":")[1]),
											   tokens[1].split(":")[1],
											   Double.parseDouble(tokens[3].split(":")[1]),
											   DeptCode.valueOf(tokens[2].split(":")[1]) ));
				}
			}
			
			reader.close();
			
		} catch( IOException e){
			e.printStackTrace();
		} finally {
			closeConnection();
		}
	
		return products;
	}

	@Override
	public void addProduct(Product product)
			throws ProductAlreadyExistsException {
		
		String mesg = null;       
        mesg = "ADD " + product.getName() + "," + product.getDept().name() + "," + product.getPrice();
        System.out.println(mesg);
        connectToServer();
        
        try {
        	
        	BufferedReader reader = new BufferedReader(new InputStreamReader(socket_fd.getInputStream()));
        	PrintWriter pw = new PrintWriter(socket_fd.getOutputStream());
        	pw.println(mesg);
        	pw.flush();
        	
        	String line = reader.readLine();
			reader.close();
			System.out.println("results: " + line);
                 
        } catch ( IOException e){
        	e.printStackTrace();
        }finally{
        	closeConnection();
        }

	}

	@Override
	public void updateProduct(Product product) throws ProductNotFoundException {
		
		String mesg = null;
		mesg = "UPDATE " + product + ",";
		
		System.out.println(mesg);
		
		connectToServer();
		
		try{
			BufferedReader reader = new BufferedReader( new InputStreamReader(socket_fd.getInputStream()));
			PrintWriter pw = new PrintWriter(socket_fd.getOutputStream());
			pw.println(mesg);
			pw.flush();
			
			String line = reader.readLine();
			reader.close();
			
			System.out.println("results: " + line);
		} catch (IOException e){
			e.printStackTrace();
		}finally{
			closeConnection();
		}
		
	}

	@Override
	public void deleteProduct(int productId) throws ProductNotFoundException {
		
		String mesg = null;
		mesg = "DELETE " + String.valueOf(productId);
		
		System.out.println(mesg);
		connectToServer();
		
		try{
			BufferedReader reader = new BufferedReader(new InputStreamReader(socket_fd.getInputStream()));
			PrintWriter pw = new PrintWriter(socket_fd.getOutputStream());
        	pw.println(mesg);
        	pw.flush();
        	
        	String line = reader.readLine();
        	reader.close();
        	System.out.println("results: " + line);
        	
		}catch(IOException e){
			e.printStackTrace();
		}finally{
			closeConnection();
		}
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
		
		Product updatedProd = new Product("ipod1", 200.00, DeptCode.ELECTRONICS);
		
		
		try {
			productDB.getProduct(5);
			productDB.addProduct( new Product("ipodX1", 131.0, DeptCode.ELECTRONICS));
			productDB.getAllProducts();
			productDB.deleteProduct(1);
			//productDB.updateProduct(updatedProd);
			
			//productDB.quitServer(socket);
		} catch (ProductAlreadyExistsException e){
			e.printStackTrace();
		} catch ( ProductNotFoundException e){
			e.printStackTrace();
		}
			
		for (Product item: productDB.getAllProducts()){
			System.out.println("-------------------------");
			System.out.println(item.getName());
			System.out.println(item.getId());
			System.out.println(item.getPrice());		
		}		
	}

}
