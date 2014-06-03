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
	
		System.out.println("got request: " + mesg);
		
		if (mesg.trim().startsWith("QUIT")){
			System.out.println(" ... server is shutting down");
			socket.close();
			System.exit(0);
		}
		
		String [] tokens = mesg.split(" ");
		PrintWriter pw = new PrintWriter(socket.getOutputStream());
		
		String activity = tokens[0].trim();
		System.out.println("Activity ---> ");
		System.out.println(activity);
		
		ACTIVITIES task = ACTIVITIES.valueOf(activity.toUpperCase());
				
	
		if(( tokens.length != 2) && (!task.name().equals("LIST"))){
			System.out.println("[ERROR] 1. invalid command: " + mesg);
			pw.println("[ERROR] invalid command: " + mesg);
			pw.flush();
			socket.close();
			return;
		}
		
		String [] productParams = tokens[1].split(",");
		String results = null;
		
		if (  (productParams.length < 1 ) && (!task.name().equals("LIST")) &&  (!task.name().equals("LISTALL"))){
			System.out.println("[ERROR] 2. invalud command: " + productParams);
			pw.println("[ERROR[ invalid command: " + mesg);
			pw.flush();
			socket.close();
			return;
		}
			
		Date ts = Calendar.getInstance().getTime();
		
		switch(task){
		
		case ADD:
			System.out.println("About to add a product ");
			
			Product prod = new Product(productParams[0], Double.parseDouble(productParams[2]), DeptCode.valueOf(productParams[1]));
			
			System.out.println("===== Serialized object ======= ");
			System.out.println(prod);
			
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
		    System.out.println("About to update product ... ");
		    
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
			System.out.println("About to delete a product");
			
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
			System.out.println("About to list products by product code");
			results = "[OK] About to print products by code" + " [" + ts + "]";
			System.out.println(productParams[0].split(":")[1]);
			
			for ( Product item : getProductsByDept( DeptCode.valueOf(productParams[0].split(":")[1]))){
				System.out.println(item);
				pw.println(item);
				pw.flush();
			}
						
			break;
			
		case LISTALL:
			System.out.println("About to list all products");
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
			results = "[OK] " + prodInfo;
			System.out.println(results);
			pw.println(results);
			pw.flush();	
			break;
			
		case LOAD:
			break;
		case SAVE:
			break;
		case QUIT:
			break;
			
		default:
			pw.println("[ERROR[ invalid command: " + mesg);
			results = "[ERROR[ invalid command: " + mesg + "[" + ts + "]";
			socket.close();
			return;
		}
		
		System.out.println("sent back result: " + results );
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

		/*
		try{
			productDB.loadProductsFromDisk();
			
		}catch( IOException e){
			e.printStackTrace();
		}
		
		System.out.println("****** Initializing Server ****** ");
		*/
		
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
