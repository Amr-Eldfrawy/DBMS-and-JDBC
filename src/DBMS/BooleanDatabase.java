package DBMS;

public class BooleanDatabase {
	String value;
	public BooleanDatabase(String bool) {
		value = bool;
	}
	public static boolean check(String val)
	{
		if(val.equalsIgnoreCase("true")||val.equalsIgnoreCase("false")) return true;
		return false;
	}
}
