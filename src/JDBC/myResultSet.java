package JDBC;

import java.io.InputStream;


import java.io.Reader;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.Array;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Date;
import java.sql.NClob;
import java.sql.Ref;
import java.sql.RowId;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.SQLXML;
import java.sql.Statement;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.Map;


public class myResultSet implements java.sql.ResultSet {
	
	private String[][] array;
	private int cursorRow=1;
	private static final int FETCH_FORWARD=1;
	private boolean isClosed=true;
	private String tableName = null;
	private String sqlQuery = null;
	
	public myResultSet() {
		isClosed=false;
	}
	@Override
	public boolean next() throws SQLException {
		trymain.log.info("Next() in ResultSet");
		if(isClosed())
			throw new SQLException("Resultset is closed");
		
		if(isLast())
			return false;
		cursorRow++;

		return true;
	}
	

	@Override
	public boolean previous() throws SQLException {
		trymain.log.info("Previous() in ResultSet");
		if(isClosed())
			throw new SQLException("Resultset is closed");
		if(isFirst())
			return false;
		cursorRow--;
		
		return true;
	}
	
	@Override
	public boolean absolute(int rowNum) throws SQLException {
		trymain.log.info("Executing Absolute() Method in ResultSet");
		if(isClosed())
			throw new SQLException("Resultset is closed");
		
		if(rowNum==0)
			cursorRow=2;
		
		else if(rowNum>0)
			cursorRow=rowNum+1;
		
		else
			cursorRow=array.length+1+rowNum;
		
		
		if(cursorRow>0 && cursorRow<array.length+1)
			return true;
		
		return false;
	}

	@Override
	public void afterLast() throws SQLException {
		trymain.log.info("AfterLast()");
		if(isClosed())
			throw new SQLException("Resultset is closed");
		cursorRow=array.length+1;
		
	}
	
	@Override
	public void beforeFirst() throws SQLException {
		if(isClosed())
			throw new SQLException("Resultset is closed");
		cursorRow=1;
	}
	
	@Override
	public boolean isAfterLast() throws SQLException {		
		if(isClosed())
			throw new SQLException("Resultset is closed");
		
		if(array.length<2)
			return false;

		return cursorRow==array.length+1;
	}
	
	@Override
		public boolean isBeforeFirst() throws SQLException {
		
			if(isClosed())
				throw new SQLException("Resultset is closed");
		
			if(array.length<2)
				return false;
			
			return cursorRow==1;
		}

	@Override
	public boolean isFirst() throws SQLException {
			
		if(isClosed())
			throw new SQLException("Resultset is closed");

		return cursorRow==2;
	}

	@Override
	public boolean isLast() throws SQLException {
		
		if(isClosed())
			throw new SQLException("Resultset is closed");
		

		return cursorRow==array.length;
	}
	
	@Override
	public boolean first() throws SQLException {
		
		if(isClosed())
			throw new SQLException("Resultset is closed");
		
		if(array.length<2)
			return false;
		
		cursorRow=2;
		return true;
	}
	
	@Override
	public boolean last() throws SQLException {
		if(isClosed())
			throw new SQLException("Resultset is closed");
		
		if(array.length<2)
			return false;
		
		cursorRow=array.length;
		return true;
	}
	
	@Override
	public void close() throws SQLException {
		isClosed=true;
		tableName = null;
		sqlQuery = null;
		cursorRow=1;
	}


	@Override
	public int findColumn(String columnLabel) throws SQLException {
		if(isClosed())
			throw new SQLException("Resultset is closed");
		if(array.length==0)
			throw new SQLException("Empty ResultSet");
		for(int i=0;i<array[0].length;i++){
			String name=array[0][i].split(",")[0];
			if(name.equals(columnLabel))
				return i+1;
		}
		throw new SQLException("ColumnLabel is not valid");
	}


	@Override
	public int getInt(int columnIndex) throws SQLException {

		if(isClosed())
			throw new SQLException("Resultset is closed");
		
		else if(columnIndex <1 || columnIndex>array[0].length)
			throw new SQLException("ColumnIndex is not valid");
		
		return Integer.parseInt(array[cursorRow-1][columnIndex-1]);
	}

	@Override
	public int getInt(String columnLabel) throws SQLException {
		
		try{
			int columnIndex = findColumn(columnLabel);		
			return getInt(columnIndex);
		}catch (SQLException  e) {
			throw new SQLException("ColumnLabel is not valid");
 		}
		
	}
	

	@Override
	public double getDouble(int columnIndex) throws SQLException {
		if(isClosed())
			throw new SQLException("Resultset is closed");
		
		else if(columnIndex <1 || columnIndex>array[0].length)
			throw new SQLException("ColumnIndex is not valid");

			
		return Double.parseDouble(array[cursorRow-1][columnIndex-1]);
	}

	@Override
	public double getDouble(String columnLabel) throws SQLException {
		
		try{
			int columnIndex = findColumn(columnLabel);		
			return getDouble(columnIndex);
		}catch (SQLException  e) {
			throw new SQLException("ColumnLabel is not valid");
 		}
		
	}
	
	@Override
	public long getLong(int columnIndex) throws SQLException {
		if(isClosed())
			throw new SQLException("Resultset is closed");
		
		else if(columnIndex <1 || columnIndex>array[0].length)
			throw new SQLException("ColumnIndex is not valid");
			
		return Long.parseLong(array[cursorRow-1][columnIndex-1]);
	}

	@Override
	public long getLong(String columnLabel) throws SQLException {
		
		try{
			int columnIndex = findColumn(columnLabel);		
			return getLong(columnIndex);
		}catch (SQLException  e) {
			throw new SQLException("ColumnLabel is not valid");
 		}
	}
	
	
	@Override
	public String getString(int columnIndex) throws SQLException {
		
		if(isClosed())
			throw new SQLException("Resultset is closed");
		
		else if(columnIndex <1 || columnIndex>array[0].length)
			throw new SQLException("ColumnIndex is not valid");
			
		return array[cursorRow-1][columnIndex-1];
	}

	@Override
	public String getString(String columnLabel) throws SQLException {
		
		try{
			int columnIndex = findColumn(columnLabel);		
			return getString(columnIndex);
		}catch (SQLException  e) {
			throw new SQLException("ColumnLabel is not valid");
 		}
		
	}
	
	@Override
	public float getFloat(int columnIndex) throws SQLException {
		
		if(isClosed())
			throw new SQLException("Resultset is closed");
		
		else if(columnIndex <1 || columnIndex>array[0].length)
			throw new SQLException("ColumnIndex is not valid");
		
		return Float.parseFloat(array[cursorRow-1][columnIndex-1]);
	}

	@Override
	public float getFloat(String columnLabel) throws SQLException {
		
		try{
			int columnIndex = findColumn(columnLabel);		
			return getFloat(columnIndex);
		}catch (SQLException  e) {
			throw new SQLException("ColumnLabel is not valid");
 		}
		
	}
	

	@Override
	public boolean getBoolean(int columnIndex) throws SQLException {
		
		if(isClosed())
			throw new SQLException("Resultset is closed");
		
		else if(columnIndex <1 || columnIndex>array[0].length)
			throw new SQLException("ColumnIndex is not valid");
		
		String element=array[cursorRow-1][columnIndex-1];

		if(element.equals("1") ||element.toLowerCase().equals("true"))
			return true;
		else if(element.equals("0")||element.toLowerCase().equals("false"))
			return false;
		else
			throw new SQLException("Type is not valid");
	}


	@Override
	public boolean getBoolean(String columnLabel) throws SQLException {
		
		try{
			int columnIndex = findColumn(columnLabel);		
			return getBoolean(columnIndex);
		}catch (SQLException  e) {
			throw new SQLException("ColumnLabel is not valid");
 		}
	}

	@Override
	public int getFetchDirection() throws SQLException {
		if(isClosed())
			throw new SQLException("Resultset is closed");
		return FETCH_FORWARD;	
	}
	
	@Override
	public boolean isClosed() throws SQLException {
		return isClosed;
	}

	@SuppressWarnings("deprecation")
	@Override
	public Date getDate(int columnIndex) throws SQLException {
		if(isClosed())
		throw new SQLException("Resultset is closed");
	
		else if(columnIndex <1 || columnIndex>array[0].length)
			throw new SQLException("ColumnIndex is not valid");
			
		
		String[] element=array[cursorRow-1][columnIndex-1].split("/");
		
		if(element.length==3)
			return new Date(Integer.parseInt(element[2])-1900, Integer.parseInt(element[1])-1, Integer.parseInt(element[0]));
	
		else
			throw new SQLException("Type is not valid");

	}
	
	@Override
	public Date getDate(String columnLabel) throws SQLException {
		try{
			int columnIndex = findColumn(columnLabel);		
			return getDate(columnIndex);
		}catch (SQLException  e) {
			throw new SQLException("ColumnLabel is not valid");
 		}
	}
	@Override
	public myResultSetMetaData getMetaData() throws SQLException {	
		if(isClosed())
			throw new SQLException("Resultset is closed");
		
		return new myResultSetMetaData(array, tableName);
	}
	
	@Override
	public Statement getStatement() throws SQLException {
		return new myStatement(sqlQuery);
	}
	
	public void setArray(ArrayList<ArrayList<String>> arrayList) {
		
		int n=arrayList.size();
		int m=0;
		if(n!=0)
			m=arrayList.get(0).size();
		array=new String[n][m];
		
		for(int i=0;i<n;i++){
			for(int j=0;j<m;j++){
				array[i][j]=arrayList.get(i).get(j);
			}
		}
		
	}
	public void setSQL(String q) {
		sqlQuery=q;
	}
	
	@Override
	public Object getObject(String columnLabel) throws SQLException {

		try{
			int columnIndex = findColumn(columnLabel);		
			return getObject(columnIndex);
		}catch (SQLException  e) {
			throw new SQLException("ColumnLabel is not valid");
 		}
	}

	@Override
	public Object getObject(int columnIndex) throws SQLException {
		if(isClosed())
			throw new SQLException("Resultset is closed");
		
		else if(columnIndex <1 || columnIndex>array[0].length)
			throw new SQLException("ColumnIndex is not valid");

		return (Object)array[cursorRow-1][columnIndex-1];
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	@Override
	public Array getArray(int arg0) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Array getArray(String arg0) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public byte getByte(int arg0) throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public byte getByte(String arg0) throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}


	@Override
	public int getConcurrency() throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getCursorName() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getHoldability() throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}


	@Override
	public String getNString(int arg0) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getNString(String arg0) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}
	

	public <T> T getObject(int arg0, Class<T> arg1) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public <T> T getObject(String arg0, Class<T> arg1) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Ref getRef(int arg0) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Ref getRef(String arg0) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getRow() throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public RowId getRowId(int arg0) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public RowId getRowId(String arg0) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SQLXML getSQLXML(int arg0) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SQLXML getSQLXML(String arg0) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public short getShort(int arg0) throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public short getShort(String arg0) throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Time getTime(int arg0) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Time getTime(String arg0) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Time getTime(int arg0, Calendar arg1) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Time getTime(String arg0, Calendar arg1) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Timestamp getTimestamp(int arg0) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Timestamp getTimestamp(String arg0) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Timestamp getTimestamp(int arg0, Calendar arg1) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Timestamp getTimestamp(String arg0, Calendar arg1)
			throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getType() throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public URL getURL(int arg0) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public URL getURL(String arg0) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public InputStream getUnicodeStream(int arg0) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public InputStream getUnicodeStream(String arg0) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SQLWarning getWarnings() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void insertRow() throws SQLException {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void moveToCurrentRow() throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void moveToInsertRow() throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void refreshRow() throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean relative(int arg0) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean rowDeleted() throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean rowInserted() throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean rowUpdated() throws SQLException {
		// TODO Auto-generated method stub
		return false;
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
	public void updateArray(int arg0, Array arg1) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateArray(String arg0, Array arg1) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateAsciiStream(int arg0, InputStream arg1)
			throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateAsciiStream(String arg0, InputStream arg1)
			throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateAsciiStream(int arg0, InputStream arg1, int arg2)
			throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateAsciiStream(String arg0, InputStream arg1, int arg2)
			throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateAsciiStream(int arg0, InputStream arg1, long arg2)
			throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateAsciiStream(String arg0, InputStream arg1, long arg2)
			throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateBigDecimal(int arg0, BigDecimal arg1) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateBigDecimal(String arg0, BigDecimal arg1)
			throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateBinaryStream(int arg0, InputStream arg1)
			throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateBinaryStream(String arg0, InputStream arg1)
			throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateBinaryStream(int arg0, InputStream arg1, int arg2)
			throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateBinaryStream(String arg0, InputStream arg1, int arg2)
			throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateBinaryStream(int arg0, InputStream arg1, long arg2)
			throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateBinaryStream(String arg0, InputStream arg1, long arg2)
			throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateBlob(int arg0, Blob arg1) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateBlob(String arg0, Blob arg1) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateBlob(int arg0, InputStream arg1) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateBlob(String arg0, InputStream arg1) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateBlob(int arg0, InputStream arg1, long arg2)
			throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateBlob(String arg0, InputStream arg1, long arg2)
			throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateBoolean(int arg0, boolean arg1) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateBoolean(String arg0, boolean arg1) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateByte(int arg0, byte arg1) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateByte(String arg0, byte arg1) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateBytes(int arg0, byte[] arg1) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateBytes(String arg0, byte[] arg1) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateCharacterStream(int arg0, Reader arg1)
			throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateCharacterStream(String arg0, Reader arg1)
			throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateCharacterStream(int arg0, Reader arg1, int arg2)
			throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateCharacterStream(String arg0, Reader arg1, int arg2)
			throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateCharacterStream(int arg0, Reader arg1, long arg2)
			throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateCharacterStream(String arg0, Reader arg1, long arg2)
			throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateClob(int arg0, Clob arg1) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateClob(String arg0, Clob arg1) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateClob(int arg0, Reader arg1) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateClob(String arg0, Reader arg1) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateClob(int arg0, Reader arg1, long arg2)
			throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateClob(String arg0, Reader arg1, long arg2)
			throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateDate(int arg0, Date arg1) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateDate(String arg0, Date arg1) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateDouble(int arg0, double arg1) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateDouble(String arg0, double arg1) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateFloat(int arg0, float arg1) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateFloat(String arg0, float arg1) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateInt(int arg0, int arg1) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateInt(String arg0, int arg1) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateLong(int arg0, long arg1) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateLong(String arg0, long arg1) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateNCharacterStream(int arg0, Reader arg1)
			throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateNCharacterStream(String arg0, Reader arg1)
			throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateNCharacterStream(int arg0, Reader arg1, long arg2)
			throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateNCharacterStream(String arg0, Reader arg1, long arg2)
			throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateNClob(int arg0, NClob arg1) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateNClob(String arg0, NClob arg1) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateNClob(int arg0, Reader arg1) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateNClob(String arg0, Reader arg1) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateNClob(int arg0, Reader arg1, long arg2)
			throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateNClob(String arg0, Reader arg1, long arg2)
			throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateNString(int arg0, String arg1) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateNString(String arg0, String arg1) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateNull(int arg0) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateNull(String arg0) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateObject(int arg0, Object arg1) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateObject(String arg0, Object arg1) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateObject(int arg0, Object arg1, int arg2)
			throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateObject(String arg0, Object arg1, int arg2)
			throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateRef(int arg0, Ref arg1) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateRef(String arg0, Ref arg1) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateRow() throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateRowId(int arg0, RowId arg1) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateRowId(String arg0, RowId arg1) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateSQLXML(int arg0, SQLXML arg1) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateSQLXML(String arg0, SQLXML arg1) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateShort(int arg0, short arg1) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateShort(String arg0, short arg1) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateString(int arg0, String arg1) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateString(String arg0, String arg1) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateTime(int arg0, Time arg1) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateTime(String arg0, Time arg1) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateTimestamp(int arg0, Timestamp arg1) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateTimestamp(String arg0, Timestamp arg1)
			throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean wasNull() throws SQLException {
		// TODO Auto-generated method stub
		return false;
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
	public void cancelRowUpdates() throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void clearWarnings() throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteRow() throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public InputStream getAsciiStream(int columnIndex) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public InputStream getAsciiStream(String columnLabel) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BigDecimal getBigDecimal(int columnIndex) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BigDecimal getBigDecimal(String columnLabel) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BigDecimal getBigDecimal(int columnIndex, int scale)
			throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BigDecimal getBigDecimal(String columnLabel, int scale)
			throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public InputStream getBinaryStream(int columnIndex) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public InputStream getBinaryStream(String columnLabel) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Blob getBlob(int columnIndex) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Blob getBlob(String columnLabel) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public byte[] getBytes(int columnIndex) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public byte[] getBytes(String columnLabel) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Reader getCharacterStream(int columnIndex) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Reader getCharacterStream(String columnLabel) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Clob getClob(int columnIndex) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Clob getClob(String columnLabel) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Date getDate(int columnIndex, Calendar cal) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Date getDate(String columnLabel, Calendar cal) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getFetchSize() throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Reader getNCharacterStream(int columnIndex) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Reader getNCharacterStream(String columnLabel) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public NClob getNClob(int columnIndex) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public NClob getNClob(String columnLabel) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object getObject(int columnIndex, Map<String, Class<?>> map)
			throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object getObject(String columnLabel, Map<String, Class<?>> map)
			throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}


}
