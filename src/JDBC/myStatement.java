package JDBC;

import java.sql.BatchUpdateException;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.util.LinkedList;
import java.util.Queue;

import DBMS.DBMS;
import DBMS.sql_parser;


public class myStatement implements java.sql.Statement {
	String sqlStat;
	boolean close = true;
	private int [] arr = new int[10];
	private Queue<String> batch = new LinkedList<String>();
	private int queryTimeout;
	private String path;
	public myStatement(String x){
		sqlStat=x;
	}
	public myStatement(String pathpassed, int n){
		sqlStat="";
		queryTimeout = Integer.MAX_VALUE;
		close = false;
		path=pathpassed;
	}
	@Override
	public boolean isWrapperFor(Class<?> iface) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public <T> T unwrap(Class<T> iface) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addBatch(String sql) throws SQLException {
		// TODO Auto-generated method stub
		trymain.log.info("Adding Batch");
		if(!close){
			batch.add(sql);
		}
		else{
			trymain.log.error(new SQLException());
			throw new SQLException();
		}
	}

	@Override
	public void cancel() throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void clearBatch() throws SQLException {
		// TODO Auto-generated method stub
		trymain.log.info("Clearing Batch");
		if(!close){
			while(batch.size()>0){
				batch.remove();
			}
		}
		else{
			trymain.log.error(new SQLException());
			throw new SQLException();
		}
		
	}

	@Override
	public void clearWarnings() throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void close() throws SQLException {
		// TODO Auto-generated method stub
		close = true;
	}

	public void closeOnCompletion() throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean execute(String sql) throws SQLException {
		// TODO Auto-generated method stub
		trymain.log.info("Executing SQL STATEMENT");
		DBMS obj = new DBMS();
		if(!close){
			sql_parser ob = new sql_parser(sql);
			ob.validate();
			return obj.getcheck();
		}
		else{
			trymain.log.error(new SQLException());
			throw new SQLException();
		}
	}

	@Override
	public boolean execute(String arg0, int arg1) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean execute(String arg0, int[] arg1) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean execute(String arg0, String[] arg1) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int[] executeBatch() throws SQLException {
		// TODO Auto-generated method stub
		trymain.log.info("Executing Batch()");
		DBMS obj = new DBMS();
		boolean comp = true;
		if(!close){
			for(int i=0; i<batch.size() && comp ; i++){
				String sql = batch.remove();
				sql_parser ob = new sql_parser(sql);
				if(!sql_parser.if_select(sql) && !ob.validate()){
					comp = false;
					throw new BatchUpdateException();
				}
				arr[i] = obj.getcounter();
			}
			return arr;
		}
		else{
			trymain.log.error(new SQLException());
			throw new SQLException();
		}
	}

	@Override
	public myResultSet executeQuery(String sql) throws SQLException {
		// TODO Auto-generated method stub
		trymain.log.info("Executing Query()");
		DBMS obj = new DBMS();
		if(!close && sql_parser.if_select(sql)){
			sqlStat=sql;
			sql_parser ob = new sql_parser(sql);
			ob.validate();
			myResultSet object = new myResultSet();
			object.setArray(obj.getable());	
			object.setSQL(sql);
			return object;
		}
		else{
			trymain.log.error(new SQLException());
			throw new SQLException();
		}
	}

	@Override
	public int executeUpdate(String sql) throws SQLException {
		// TODO Auto-generated method stub
		trymain.log.info("Executing Update()");
		DBMS obj = new DBMS();
		if(!close && !sql_parser.if_select(sql)){
			sql_parser ob = new sql_parser(sql);
			ob.validate(); 
			return obj.getcounter();	
		}
		else{
			trymain.log.error(new SQLException());
			throw new SQLException();
		}
	}

	@Override
	public int executeUpdate(String arg0, int arg1) throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public int executeUpdate(String arg0, int[] arg1) throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int executeUpdate(String arg0, String[] arg1) throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public myConnection getConnection() throws SQLException {
		// TODO Auto-generated method stub
		return new myConnection(path);
	}

	@Override
	public int getFetchDirection() throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getFetchSize() throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public myResultSet getGeneratedKeys() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getMaxFieldSize() throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getMaxRows() throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean getMoreResults() throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean getMoreResults(int arg0) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int getQueryTimeout() throws SQLException {
		// TODO Auto-generated method stub
		trymain.log.info("Getting  QueryTimeout()");
		if(!close){
			return queryTimeout;
		}
		else{
			throw new SQLException();
		}
		
	}

	@Override
	public myResultSet getResultSet() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getResultSetConcurrency() throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getResultSetHoldability() throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getResultSetType() throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getUpdateCount() throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public SQLWarning getWarnings() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean isCloseOnCompletion() throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isClosed() throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isPoolable() throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setCursorName(String arg0) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setEscapeProcessing(boolean arg0) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setFetchDirection(int arg0) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setFetchSize(int arg0) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setMaxFieldSize(int arg0) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setMaxRows(int arg0) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setPoolable(boolean arg0) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setQueryTimeout(int arg0) throws SQLException {
		// TODO Auto-generated method stub
		trymain.log.info("Setting QueryTimeout()");
		if(!close){
			queryTimeout = arg0;
		}
		else{
			trymain.log.error(new SQLException());
			throw new SQLException();
		}
	}
	public String getSQL(){
		return sqlStat;
	}
}
