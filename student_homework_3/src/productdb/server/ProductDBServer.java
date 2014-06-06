package productdb.server;

import java.io.IOException;

import productdb.DeptCode;
import productdb.ProductDB;
import productdb.Product;
import productdb.ProductNotFoundException;

import java.net.ServerSocket;
import java.net.Socket;

import java.io.InputStreamReader;
import java.io.BufferedReader;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;

import java.util.ArrayList;
import java.util.Date;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import productdb.ProductAlreadyExistsException;

/**
 * The main program for the server for homework #5
 * 
 * @author aeggermont
 *
 */
public class ProductDBServer extends ProductDBImpl{
	
	public static final int PORT_NO = 8900;
	public enum ACTIVITIES { ADD, UPDATE, DELETE, LIST, SEARCH, LISTALL, LOAD, SAVE, QUIT};
	
	public ProductDBServer(){
		super();
	}
	
	
	public void processRequest(Socket socket, String mesg) throws IOException {
	
		String [] productParams;
		String results = null;
		Date ts = Calendar.getInstance().getTime();
		String [] tokens = mesg.split(";");
		
		System.out.println("got request: " + mesg);
		
		PrintWriter pw = new PrintWriter(socket.getOutputStream());
		
		String activity = tokens[0].trim();		
		ACTIVITIES task = ACTIVITIES.valueOf(activity.toUpperCase());
						
		productParams = tokens[1].split(",");

		if (  (productParams.length < 1 ) && (!task.name().equals("LIST")) && 
			  (!task.name().equals("LISTALL")) &&  (!task.name().equals("SAVE"))  &&      
			  (!task.name().equals("QUIT")) && (!task.name().equals("LOAD")) ){
			
			System.out.println("[ERROR] 2. invalud command: " + productParams);
			pw.println("[ERROR[ invalid command: " + mesg);
			pw.flush();
			socket.close();
			return;
		}
		
		
		switch(task){
		
		case ADD:
			System.out.println("About to add a product ");
			
			Product prod = new Product(productParams[0], Double.parseDouble(productParams[2]), DeptCode.valueOf(productParams[1]));
			
			try {
				addProduct(prod);
				results = "[OK] added product " + prod + " [" + ts + "]";
			} catch (ProductAlreadyExistsException e){
				results = "[ERROR] product already exists " + prod + " [" + ts + "]";
				System.out.println(results);
			}finally{
				System.out.println(results);
				pw.println(results);
				pw.flush();
			}
		
			break;
			
		case UPDATE:
		    
			Product prodUpdated = new Product(productParams[0], 
					                          Double.parseDouble(productParams[2]), 
					                          DeptCode.valueOf(productParams[1]));
			
			try{
				updateProduct(prodUpdated);
				results = "[OK] Product updated " + "[" + ts + "]";
			}catch (ProductNotFoundException e){
				results = "[ERROR] " + e.getMessage() + "[" + ts + "]";
			}finally{
				pw.println(results);
				pw.flush();
			}
			
			break;
			
		case DELETE:
			try{
				deleteProduct(Integer.parseInt(productParams[0]));
				results = "[OK] Product deleted " + "[" + ts + "]";
			} catch(ProductNotFoundException e) {
				results = "[ERROR] " + e.getMessage() + "[" + ts + "]";
				break;
			}finally{
				pw.println(results);
				pw.flush();
			}
			
			break;
			
		case LIST:
			results = "[OK] About to print products by code" + " [" + ts + "]";
			//System.out.println(productParams[0].split(":")[1]);
			
			for ( Product item : getProductsByDept( DeptCode.valueOf(productParams[0].split(":")[1]))){
				System.out.println(item);
				pw.println(item);
				pw.flush();
			}
						
			break;
			
		case LISTALL:
	
			results = "[OK] List all products " + "[" + ts + "]";
			pw.println(results);
			pw.flush();
			
			for( Product item: getAllProducts()){
				pw.println(item);
				pw.flush();
			}
			
			break;
			
		case SEARCH:
			Product prodInfo = getProduct(Integer.parseInt(productParams[0]));
			results = "[OK] " + "productId:" + prodInfo.getId() + "," + 
			          "name:" + prodInfo.getName() + "," + 
			          "dept:" + prodInfo.getDept().name() + "," + 
					  "price:" + prodInfo.getPrice();
			
			pw.println(results);
			pw.flush();	
			break;
			
		case LOAD:
			try{
			    loadProductsFromDisk();
			    results = "[OK] DB loaded from disk " + "[" + ts + "]";    
			}catch( IOException e){
				results = "[ERROR] " + e.getMessage() + "[" + ts + "]";
			}finally{
				pw.println(results);
			    pw.flush();
			}
			
			break;
			
		case SAVE:
			try{
				saveProductsToDisk();
				results = "[OK] Saved product DB to disk [ "+ ts + "]";
			}catch(IOException e){
				results = "[ERROR] " + e.getMessage() + "[" + ts + "]";
			}finally{
				pw.println(results);
				pw.flush();
			}
			
			break;
			
		case QUIT:
			results = quitServer() + "[" + ts + "]";
			pw.println(results);
			pw.flush(); 
			socket.close();
			System.exit(0);
			break;

		default:
			pw.println("[ERROR[ invalid command: " + mesg);
			results = "[ERROR[ invalid command: " + mesg + "[" + ts + "]";
			socket.close();
			return;
		}
		
		socket.close();
	    return;
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
			
		ProductDBServer productDB =  new ProductDBServer();
		System.out.println(" ****** Loading Data to Database ******");
		
		ServerSocket serverSocket = new ServerSocket(productDB.PORT_NO);
		
		System.out.println("server is accepting requests now ");
		
		while(true){
			
			Socket socket = serverSocket.accept();	
			BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			String line = reader.readLine();
			
			try{
				productDB.processRequest(socket, line);
			} catch (IOException e){
				e.printStackTrace();
			} finally{
				for( Product item: productDB.getAllProducts()){
					System.out.println("==========");
					System.out.println(item);
				}
			}
		}
	}
}
