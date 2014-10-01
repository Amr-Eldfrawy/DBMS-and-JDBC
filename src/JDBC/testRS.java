package JDBC;

import static org.junit.Assert.*;


import java.sql.SQLException;
import java.util.ArrayList;

import org.junit.Test;


public class testRS {
	
	myResultSet rs=new myResultSet();
	ArrayList<ArrayList<String>> LL=new ArrayList<ArrayList<String>>();
	ArrayList<String> L=new ArrayList<String>();
	public void begin() throws SQLException {
		L.add("age");
		L.add("name");
		LL.add(L);
		L=new ArrayList<String>();
		L.add("4");
		L.add("ahmed");

		LL.add(L);
		L=new ArrayList<String>();
		L.add("41");
		L.add("aly");
		LL.add(L);
//
		rs.setArray(LL);
//		
	}
	
	@Test
	public void testFirst() throws SQLException {
		begin();
		rs.first();
		assertEquals(rs.isFirst(), true);
	}
	
	@Test
	public void testBeforeFirst() throws SQLException {
		begin();
		rs.setArray(LL);rs.setArray(LL);
		rs.beforeFirst();
		assertEquals(rs.isBeforeFirst(), true);
	}
	@Test
	public void testLast() throws SQLException {
		begin();
		rs.last();
		assertEquals(rs.isLast(), true);
	}
	@Test
	public void testAfterLast() throws SQLException {
		begin();
		rs.afterLast();
		assertEquals(rs.isAfterLast(), true);
	}
	@Test
	public void testAbsolute1() throws SQLException {
		begin();
		rs.absolute(1);
		assertEquals(rs.getString(2), "ahmed");
	}
	@Test
	public void testAbsolute2() throws SQLException {
		begin();
		rs.absolute(1);
		assertEquals(rs.getInt(1), 4);
	}
	@Test
	public void testFindColomn1() throws SQLException {
		begin();
		rs.absolute(2);
		assertEquals(rs.getInt("age"), 41);
	}
	@Test
	public void testFindColomn2() throws SQLException {
		begin();
		rs.absolute(2);
		assertEquals(rs.getString("name"), "aly");
	}
	@Test
	public void testAboluteInvalid() throws SQLException {
		begin();
		assertEquals(rs.absolute(20), false);
	}
	@Test
	public void testAboluteInvalid2() throws SQLException {
		begin();
		assertEquals(rs.absolute(-20), false);
	}
	
	
	@Test
	public void testIsClosed() throws SQLException {
		begin();
		assertEquals(rs.isClosed(), false);
	}
	
	@Test
	public void testIsClosed2() throws SQLException {
		begin();
		rs.close();
		assertEquals(rs.isClosed(), true);
	}
}
