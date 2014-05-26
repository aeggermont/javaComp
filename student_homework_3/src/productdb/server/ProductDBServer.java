package productdb.server;

import java.io.IOException;
import productdb.ProductDB;
import java.net.ServerSocket;
import java.net.Socket;

import java.io.InputStreamReader;
import java.io.BufferedReader;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * The main program for the server for homework #5
 * 
 * @author aeggermont
 *
 */
public class ProductDBServer extends ProductDBImpl{
	
	public static final int PORT_NO = 8900;
	public enum ACTIVITIES { ADD, UPDATE, DELETE, LIST, SEARCH };
	
	public ProductDBServer(){
		super();
	}
	
	
	public static void processRequest(Socket socket, String mesg) throws IOException {
	
		System.out.println("got request: " + mesg);
		
		if (mesg.trim().startsWith("QUIT")){
			System.out.println(" ... server is shutting down");
			socket.close();
			System.exit(0);
		}
		
		String [] tokens = mesg.split(" ");
		PrintWriter pw = new PrintWriter(socket.getOutputStream());
		
		if( tokens.length != 3){
			pw.println("[ERROR] invalid command: " + mesg);
			socket.close();
			return;
		}
		
		String [] productParams = tokens[1].split(",");
		String results = null;
		
		if (productParams.length < 1 ){
			pw.println("[ERROR[ invalid command: " + mesg);
			socket.close();
			return;
		}
		
		String activity = tokens[0].trim();
		
		ACTIVITIES task = ACTIVITIES.valueOf(activity.toUpperCase());
		
		switch(task){
		
		case ADD:
			System.out.println("About to add a product ");
			break;
		case UPDATE:
			System.out.println("About to update a product");
			break;
		case DELETE:
			System.out.println("About to delete a product");
			break;
		case LIST:
			System.out.println("About to list all products");
			break;
		case SEARCH:
			System.out.println("About to find a product");
			break;
		default:
			pw.println("[ERROR[ invalid command: " + mesg);
			socket.close();
			return;
		}
		
		System.out.println("send back result: " + results );
		
		pw.flush();
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
		
		try{
			productDB.loadProductsFromDisk();
			
		}catch( IOException e){
			e.printStackTrace();
		}
		
		System.out.println("****** Initializing Server ****** ");
		
		ServerSocket serverSocket = new ServerSocket(productDB.PORT_NO);
		
		System.out.println("server is accepting requests now ");
		
		while(true){
			
			Socket socket = serverSocket.accept();	
			BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			String line = reader.readLine();
			
			try{
				processRequest(socket, line);
			} catch (IOException e){
				e.printStackTrace();
			}
		}
		
		

	}

}
