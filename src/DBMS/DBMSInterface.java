package DBMS;
import java.util.ArrayList;

public interface DBMSInterface {
	 
	
	public void createDB(String name);
	public void  TableView (String databas_name , String table_name);
	
	public void insert_into_table( String DB,String table,
			ArrayList<pair_Data> data);

	public void update_into_table(String DB, String table, ArrayList<condition> Con ,
			ArrayList<condition>  newData);

	public void delete_into_table( String DB,String table, ArrayList<condition> Con );

	public void select_into_table( String DB,String table,  ArrayList<condition> Con  );

	public boolean check_table_and_database(String database_name,
			String table_name);

	public boolean check_database(String DB);

	public void createTable( String DB,String name, ArrayList<pair_Data> e);
	
	public boolean check_column_is_valid( String DB,String table,String columnName);
	
	public String get_colomn_type( String DB,String table, String  colNumber);
}
