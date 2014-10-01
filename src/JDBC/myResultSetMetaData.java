package JDBC;

import java.sql.SQLException;
import java.util.StringTokenizer;

public class myResultSetMetaData implements java.sql.ResultSetMetaData {

	private String table[][];
	private int length;
	private int width;
	private String tableName = null;

	public myResultSetMetaData(String Table[][], String _tableName) {

		length = Table.length;
		width = Table[0].length;

		table = new String[length][width];
		table = Table;
		tableName = _tableName;

	}

	@Override
	public boolean isWrapperFor(Class<?> arg0) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public <T> T unwrap(Class<T> arg0) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getCatalogName(int arg0) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getColumnClassName(int arg0) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getColumnCount() throws SQLException {
		// TODO Auto-generated method stub

		return this.width;

	}

	@Override
	public int getColumnDisplaySize(int arg0) throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getColumnLabel(int col) throws SQLException {
		// TODO Auto-generated method stub

		if (col <= this.width && col >= 1) {

			col--;
			String str = table[0][col];
			StringTokenizer token = new StringTokenizer(str, ",");
			String label = token.nextToken();

			return label;

		} else
			throw new SQLException("column  requested out of table bounds  ");

	}

	@Override
	public String getColumnName(int col) throws SQLException {
		// TODO Auto-generated method stub
		if (col <= this.width && col >= 1) {

			col--;
			String str = table[0][col];
			StringTokenizer token = new StringTokenizer(str, ",");
			String label = token.nextToken();

			return label;

		} else
			throw new SQLException("column  requested out of table bounds  ");
	}

	@Override
	public int getColumnType(int col) throws SQLException {
		// TODO Auto-generated method stub

		if (col <= width && col >= 1) {
			col--;

			String str = table[0][col];
			StringTokenizer token = new StringTokenizer(str, ",");
			String name = token.nextToken();
			String type = token.nextToken();
			/*
			 * possible errors here according . we should check accurately the
			 * name of types returned by amr to make sure they match the words
			 * here . anyway I wrote them now and can be modified in
			 * logartithmic time easily :P :D
			 */

			if (type.equals("double"))
				return java.sql.Types.DOUBLE;
			else if (type.equals("integer"))
				return java.sql.Types.INTEGER;
			else if (type.equals("string"))
				return java.sql.Types.VARCHAR;
			else if (type.equals("boolean"))
				return java.sql.Types.BOOLEAN;
			else if (type.equals("date"))
				return java.sql.Types.DATE;
			else
				return java.sql.Types.NULL;

		} else
			throw new SQLException("column requested out of table bounds");

	}

	@Override
	public String getColumnTypeName(int col) throws SQLException {
		if (col <= this.width && col >= 1) {

			col--;

			String str = table[0][col];
			StringTokenizer token = new StringTokenizer(str, ",");
			String name = token.nextToken();
			String type = token.nextToken();

			return type;

		} else
			throw new SQLException("column requested out of table bounds");

	}

	@Override
	public int getPrecision(int arg0) throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getScale(int arg0) throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getSchemaName(int arg0) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getTableName(int col) throws SQLException {
		// TODO Auto-generated method stub

		if (col <= width && col >= 1) {

			col--;

			return this.tableName;

		} else
			throw new SQLException("column requested out of table bounds");

	}

	@Override
	public boolean isAutoIncrement(int col) throws SQLException {
		// TODO Auto-generated method stub
		// if (col <= width && col >= 1) {
		//
		// col--;
		// if (this.getColumnType(col) != java.sql.Types.INTEGER)
		// return false;
		// else {
		//
		// for (int i = 1; i < length - 1; i++) {
		// int Integer1 = Integer.parseInt(table[i][col]);
		// int Integer2 = Integer.parseInt(table[i + 1][col]);
		//
		// if (Integer2 != Integer1 + 1)
		// return false;
		//
		// }
		//
		// return true;
		//
		// }
		//
		// } else
		// throw new SQLException("column requested out of table bounds");

		return false;

	}

	@Override
	public boolean isCaseSensitive(int arg0) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isCurrency(int arg0) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isDefinitelyWritable(int arg0) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int isNullable(int arg0) throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean isReadOnly(int arg0) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isSearchable(int arg0) throws SQLException {
		// TODO Auto-generated method stub

		// return false; this is the default value
		return true;
	}

	@Override
	public boolean isSigned(int arg0) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isWritable(int arg0) throws SQLException {
		// TODO Auto-generated method stub
		// return false; this is the default value ;

		return true;
	}

}
