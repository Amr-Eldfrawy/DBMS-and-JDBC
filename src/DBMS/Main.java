//package DBMS;
//import java.io.*;
//
//public class Main {
//
//
//
//	public static void main(String[] args) throws IOException {
//		
//		DBMS.PATH="C:\\ggg";
//		checkDir();
//	 
//		 
//		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
//		sql_parser dbms_Parser = new sql_parser();
//		String input = br.readLine();
//		dbms_Parser.set_Query(input);
//		if (!dbms_Parser.validate()) {
//			System.out.println("invalid entry !");
//		}
//
//	}
//	
//    /* check for the main directory in C:\\Directory if not create it ,,, this path will hold all databases files  */
//	private static void checkDir() {
//		// TODO Auto-generated method stub
//		
//		if (new File(DBMS.PATH).exists() == false)
//			new File(DBMS.PATH).mkdirs();                 // mddirs >> create empty folder 
//
//	}
//
//}
