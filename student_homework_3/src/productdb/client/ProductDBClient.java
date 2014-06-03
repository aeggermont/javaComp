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
	
	/*
	 * Connects to the ProductDB server
	 * @param server name
	 * @throws IO exception for unsuccessful  connections
	 */
	public void connectToServer(){
		
		try{
			socket_fd = new Socket("localhost", PORT_NO);
		} catch (IOException e){
			e.printStackTrace();
		}
	}
	
	/*
	 * Close current connection with the ProductDB server
	 * @throws IO exception for unsuccessful  connections
	 */
	public void closeConnection(){
		try{
			socket_fd.close();
		} catch (IOException e){
			e.printStackTrace();
			System.exit(1);
		}		
	}
	
	
	@Override
	public Product getProduct(int productId) {
		
		Product prodInfo = null;
		String mesg = null;
        String result;
        
		mesg = "SEARCH " + String.valueOf(productId) + ",";
		
		connectToServer();
		
		try{
			BufferedReader reader = new BufferedReader(new InputStreamReader(socket_fd.getInputStream()));
			PrintWriter pw = new PrintWriter(socket_fd.getOutputStream());
			pw.println(mesg);
			pw.flush();
			result = reader.readLine();
			reader.close();
		}catch ( IOException e){
			e.printStackTrace();
			return null;
		}finally{
			closeConnection();
		}
		
		if (!result.contains("[ERROR] ProductNotFound")) { 
			
			 String [] resultLine = result.split(" ");
			 String [] tokens = resultLine[1].split(",");
			
			 prodInfo = new Product ( Integer.parseInt(tokens[0].split(":")[1]),
					   tokens[1].split(":")[1],
					   Double.parseDouble(tokens[3].split(":")[1]),
					   DeptCode.valueOf(tokens[2].split(":")[1]) );
		}
		
		return prodInfo;
	}

	@Override
	public List<Product> getProductsByDept(DeptCode code) {
		
		List<Product> products = new ArrayList<Product>();
		String mesg = null;   
		String lineMesg = null;
		String [] tokens;
		
		mesg = "LIST " + "deptCode:" + code.name() + ",";
		
		connectToServer();
		
		try{
			BufferedReader reader = new BufferedReader(new InputStreamReader(socket_fd.getInputStream()));
			PrintWriter pw = new PrintWriter(socket_fd.getOutputStream());
			pw.println(mesg);
			pw.flush();
			//lineMesg = reader.readLine();
		
			while(true){
				lineMesg = reader.readLine();
				if ( lineMesg == null) { break; }				
				tokens = lineMesg.split(",");
				
				if ( tokens.length == 4){		
					products.add(new Product ( Integer.parseInt(tokens[0].split(":")[1]),
											   tokens[1].split(":")[1],
											   Double.parseDouble(tokens[3].split(":")[1]),
											   DeptCode.valueOf(tokens[2].split(":")[1]) ));
				}
			}
			
		}catch(IOException e){
			e.printStackTrace();
		}finally{
			closeConnection();
		}
		
		return products;
	}

	@Override
	public List<Product> getAllProducts() {
		
		List<Product> products = new ArrayList<Product>();
	
		String mesg = null;   
		String line = null;
		String [] tokens;
		
		mesg = "LISTALL " + ",";

		connectToServer();
		
		try{
			BufferedReader reader = new BufferedReader(new InputStreamReader(socket_fd.getInputStream()));
			PrintWriter pw = new PrintWriter(socket_fd.getOutputStream());
			pw.println(mesg);
			pw.flush();
			line = reader.readLine();

			while(true){
				line = reader.readLine();
				
				if ( line == null) { break; }
	
				tokens = line.split(",");
				
				if ( tokens.length == 4){		
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
		String line = null;
        mesg = "ADD " + product.getName() + "," + product.getDept().name() + "," + product.getPrice();
        connectToServer();
        
        try {
        	BufferedReader reader = new BufferedReader(new InputStreamReader(socket_fd.getInputStream()));
        	PrintWriter pw = new PrintWriter(socket_fd.getOutputStream());
        	pw.println(mesg);
        	pw.flush();
        	line = reader.readLine();
			reader.close();
        } catch ( IOException e){
        	e.printStackTrace();
        }finally{
        	closeConnection();
        }

        if (line.contains("[ERROR] product already exists")){
        	throw new ProductAlreadyExistsException(line);
        }
        
	}

	@Override
	public void updateProduct(Product product) throws ProductNotFoundException {
		
		String mesg = null;
		String line = null;
		mesg = "UPDATE " + product.getName() + "," + product.getDept().name() + "," + product.getPrice();
		connectToServer();
		
		try{
			BufferedReader reader = new BufferedReader( new InputStreamReader(socket_fd.getInputStream()));
			PrintWriter pw = new PrintWriter(socket_fd.getOutputStream());
			pw.println(mesg);
			pw.flush();	
			line = reader.readLine();
			reader.close();
		} catch (IOException e){
			e.printStackTrace();
		}finally{
			closeConnection();
		}
		
		if (line.contains("[ERROR] ProductNotFound")) { 
			throw new ProductNotFoundException(line); 
		}
	}

	@Override
	public void deleteProduct(int productId) throws ProductNotFoundException {
		
		String mesg = null;
		String lineMesg = null;
		mesg = "DELETE " + String.valueOf(productId);
		connectToServer();
		
		try{
			BufferedReader reader = new BufferedReader(new InputStreamReader(socket_fd.getInputStream()));
			PrintWriter pw = new PrintWriter(socket_fd.getOutputStream());
        	pw.println(mesg);
        	pw.flush();
        	lineMesg = reader.readLine();
        	reader.close();
        	System.out.println("results: " + lineMesg);     	
		}catch(IOException e){
			e.printStackTrace();
		}finally{
			closeConnection();
		}
		
		if (lineMesg.contains("[ERROR] product not found")) { 
			throw new ProductNotFoundException(lineMesg); 
		}else{
			System.out.println(lineMesg);
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
		
		ProductDB productDB = null;
		productDB = new ProductDBClient();
		
		Product updatedProd = new Product("ipod3", 200.00, DeptCode.ELECTRONICS);
		
		
		try {
			System.out.println(productDB.getProduct(5));
			productDB.updateProduct(updatedProd);
			productDB.addProduct( new Product("ipodM4", 131.0, DeptCode.ELECTRONICS));
			productDB.deleteProduct(4);
			//productDB.quitServer(socket);
		} catch (ProductAlreadyExistsException e){
			e.printStackTrace();
		} catch ( ProductNotFoundException e){
			e.printStackTrace();
		}
		
		
		System.out.println("Printing products by department");
		
		for (Product item: productDB.getProductsByDept(DeptCode.ELECTRONICS)){
			System.out.println("-------------------------");
			System.out.println(item.getName());
			System.out.println(item.getDept().name());
			System.out.println(item.getId());
			System.out.println(item.getPrice());	
		}
		
		System.out.println("Printing all products");
		
		for (Product item: productDB.getAllProducts()){
			System.out.println("-------------------------");
			System.out.println(item.getName());
			System.out.println(item.getDept().name());
			System.out.println(item.getId());
			System.out.println(item.getPrice());	
		}
				
	}

}
