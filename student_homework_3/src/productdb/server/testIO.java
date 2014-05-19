package productdb.server;


import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;


import productdb.Product;
import productdb.DeptCode;


public class testIO {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		  String binaryProductDemo = "/Users/alberttsoi/dev/UCSC-JavaComp/student_homework_3/productDB.bin";
          File outputFile = new File(binaryProductDemo);

          try {
               DataInputStream in = new DataInputStream(new BufferedInputStream(new FileInputStream(outputFile)));
               long pk = in.readLong();
               String code = in.readUTF();
               String desc = in.readUTF();
               double price = in.readDouble();
               
               in.close();
          } catch (IOException e){
        	  e.printStackTrace();
          }
          System.out.println("** reading completed **");
	
	}

}
