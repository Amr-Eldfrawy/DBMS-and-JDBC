package DBMS;
import java.io.File;
import java.util.ArrayList;

public class Database {
	String name;
	ArrayList<String> tableURL;// path of tables

	public Database(String name) {

		this.name = name;
		String test = DBMS.PATH + "\\" + name;
		File file = new File(test);
		file.mkdirs();

	}

	public void addTable() {

	}
	
}
