package DBMS;
public class pair_Data {

	String data_1, data_2;

	/*
	 * pair holds 2 strings we going to use it in 2 cases 1- insert into table
	 * pair(data,column_name) 2-create table pair(column_name,data_type)
	 * 
	 */
	// ////////////////////////////////////////////////////////////////////////////////////
	/*
	 * 
	 * 
	 * 
	 * very important : the comments below were written by me ( aboelhassan)
	 * just to avoid conflict I and tarek used this class in a differenet way
	 * than that used by omar ahmed and amr el defrawy so I 've written these
	 * comments if you don't understand it just ignore it :D
	 */
	// /////////////////////////////////////////////////////////////////////////////////////////
	/*
	 * me(aboelhassan) and tarek used this class as the following : string
	 * data_1 will carry the attribute of the table for example name or age
	 * string data_2 will carry the type of the attribute for example integer ,
	 * string
	 * 
	 * we didn't understand the comments written above .... if there is a
	 * problem conceringin attributes of the table . we have to look here .
	 */

	// //////////////////////////////////////////////////////////////////////////////////////////

	public pair_Data(String s1, String s2) {
		data_1 = s1;
		data_2 = s2;
	}

	public String get_Data_1() {
		return data_1;
	}

	public String get_Data_2() {
		return data_2;
	}

}
