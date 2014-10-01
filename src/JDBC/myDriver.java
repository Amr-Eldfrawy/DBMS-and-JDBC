package JDBC;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverPropertyInfo;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.Properties;
import java.util.logging.Logger;

import DBMS.DBMS;


public class myDriver implements java.sql.Driver{
	String Username,Password;
	public myDriver (){
	
	}
	
	@Override
	public boolean acceptsURL(String url) throws SQLException {
		trymain.log.info("Entered acceptsURL");
		String path = url;
		File f = new File(path);
		if (f.exists()) {
			trymain.log.info("The Path of the given Directory exists");
			return true;
			
		}
		trymain.log.info("The Path of the given Directory Doesn't exists");
		return false;
	}

	@Override
	public Connection connect(String url, Properties prp) throws SQLException{
		if(this.acceptsURL(url)){
			try {
				BufferedReader br = new BufferedReader(new FileReader(url+"\\Properties.txt"));
				String[] line = null;
				for(int i=0;i<2;i++){
					line = br.readLine().split(" ");
					if(line[0].equals("Username")){
						Username = line[1];
						if(!Username.equals(prp.get("Username"))) throw new SQLException();
					}
					else if(line[0].equals("Password")){
						Password = line[1];
						if(!Password.equals(prp.get("Password"))) throw new SQLException();
					}
				}
				DBMS dbms = new DBMS();
				dbms.setpath(url);
				trymain.log.info("Connected to the Directory Succesfully");
				Connection c = new myConnection(url);
				
				return c;
			} catch (IOException e) {
				trymain.log.info("Can't Connect To the Directory");
				trymain.log.error(e,e);
				throw new SQLException();
			}
		}
		throw new SQLException();
		//return null;
		}
	@Override
	public int getMajorVersion() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getMinorVersion() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public DriverPropertyInfo[] getPropertyInfo(String url, Properties prp)
			throws SQLException {
		trymain.log.info("Getting Property Info");
		DriverPropertyInfo[] dpi = new DriverPropertyInfo[2];
		dpi[0] = new DriverPropertyInfo("Username", Username);
		dpi[1] = new DriverPropertyInfo("Password", Password);
		return dpi;
	}

	@Override
	public boolean jdbcCompliant() {
		// TODO Auto-generated method stub
		return false;
	}

	public Logger getParentLogger() throws SQLFeatureNotSupportedException {
		// TODO Auto-generated method stub
		return null;
	}

}
