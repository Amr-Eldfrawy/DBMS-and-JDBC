package JDBC;

import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import org.apache.log4j.Logger;

public class trymain {
	public static final Logger log = Logger.getLogger(trymain.class);
	public trymain()throws SQLException {
		UI();
	}
	public static void main(String[] args) throws SQLException{
		new trymain();
	}
	public void UI(){
//		Before Running make sure you make a directory(folder) in C named Directory
//		And that you made a file inside this folder named Properties.txt
		/*inside Properties.txt write ----> 
		 * Username abc
		 * Password 123
		 */
		
		String url = "D:\\Abbas";
		java.sql.Connection con;
		try{
			Properties prp = new Properties();
			Driver d = new myDriver();
			prp.put("Username", "abc");
			prp.put("Password", "123");
			con = d.connect(url, prp);
			log.info("YYYYAAARAB");
			Statement stmt = con.createStatement();
			stmt.execute(" CREATE DATABASE aly");
//			stmt.execute(" INSERT INTO group, shady (id,mark) VALUES (125,30)");
//			System.out.println(stmt.executeUpdate(" SELECT * FROM group, shady"));
//			myResultSet x = (myResultSet) stmt.executeQuery("SELECT * FROM group, shady");
//			System.out.println();
//			x.beforeFirst();
//			while(x.next()){
//				System.out.print(x.getString(1)+"     ");
//				System.out.println(x.getString(2));
//			}
//			System.out.println("-------------------------");
//			x.afterLast();
//			while(x.previous()){
//				System.out.print(x.getString("id")+"      ");
//				System.out.println(x.getString(2));
//			}
//			x.afterLast();
//			System.out.println(x.getString(1));
			/*
			 * Add what ever code you with :)
			 * 
			 * */
			con.close();
		}catch(SQLException ex){
			
			System.err.println(ex.getMessage());
		}

	}

}
