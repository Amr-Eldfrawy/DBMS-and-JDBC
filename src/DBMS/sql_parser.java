package DBMS;

import java.util.ArrayList;
import java.util.Stack;
 
 
/*
 SELECT * FROM database_name, table name
 SELECT FROM database_name, table name WHERE condition
 SELECT FROM database_name, table name WHERE condition
 DELETE FROM db_name, table WHERE condition
 DELETE FROM db_name, table WHERE condition condition and/or condition
 UPDATE database, table_name SET column1=value1,column2=value2,... WHERE some_column=some_value
 INSERT INTO database_name, table_name (column1,column2,column3,...) VALUES (value1,value2,value3,...)
 INSERT INTO DBMS, test (column1,column2,column3,) VALUES (value1,value2,value3,)
 CREATE DATABASE db_name
 CREATE TABLE table_name AT db_name WHERE ('column1' 'data type', 'column2' 'data type','column3' 'data type')
 */
public class sql_parser {
 
        String query;
        ArrayList<String> data;
        DBMS dbms = new DBMS();
 
        public sql_parser() {
 
        }
 
        public sql_parser(String input) {
                query = input;
        }
 
        public void set_Query(String input) {
                query = input;
        }
 
        public boolean validate() {
                data = omar_validation.chk(query);
                return casting();
        }
 
        private boolean casting() {
 
                if (data.size() < 1)
                        return false;
                if (!braket())
                        return false;
                String pro = data.get(0);
                if (pro.equalsIgnoreCase("select")) {
                        return select();
                } else if (pro.equalsIgnoreCase("delete")) {
                        return delete();
                } else if (pro.equalsIgnoreCase("update")) {
                        return update();
                } else if (pro.equalsIgnoreCase("insert")) {
 
                        return insert_eval();
                } else if (pro.equalsIgnoreCase("create")) {
                        return create_val();
                }
                return false;
        }
 
        private boolean braket() {
                Stack<Character> s = new Stack<Character>();
                for (char x : query.toCharArray()) {
                        if (!(x == '\'' | x == '(' | x == ')'))
                                continue;
                        if (s.isEmpty())
                                s.add(x);
                        else {
                                if (s.peek() == x && x == '\'')
                                        s.pop();
                                else if (s.peek() == '(' && x == ')')
                                        s.pop();
                                else
                                        s.add(x);
                        }
                }
                if (!s.isEmpty())
                        return false;
                return true;
        }
 
        private ArrayList<String> split() {
                String[] xxx = query.split("WHERE");
                return omar_validation.chk2(xxx[1]);
        }
 
        public static boolean if_select(String x) {
                sql_parser ob = new sql_parser(x);
                ob.data = omar_validation.chk(ob.query);
                if (ob.data.size() < 1)
                        return false;
                if (!ob.braket())
                        return false;
                return ob.selection();
        }
 
        private boolean select() {
                if (!data.get(0).equalsIgnoreCase("select"))
                        return false;
                if (data.size() == 5) {
                        if (!data.get(1).equals("*")
                                        || !data.get(2).equalsIgnoreCase("from"))
                                return false;
                        if (!dbms.check_table_and_database(data.get(3), data.get(4)))
                                return false;
                        dbms.selectAll(data.get(3), data.get(4));
                } else {
                        if (data.size() < 8 || !data.get(1).equalsIgnoreCase("from"))
                                return false;
                        if (!data.get(4).equalsIgnoreCase("where"))
                                return false;
                        if (!dbms.check_table_and_database(data.get(2), data.get(3)))
                                return false;
 
                        for (int i = 5; i + 2 < data.size();) {
                                if ((data.get(i).equalsIgnoreCase("and") | data.get(i)
                                                .equalsIgnoreCase("or")) & !(i + 3 < data.size()))
                                        return false;
                                if (data.get(i).equalsIgnoreCase("and")
                                                | data.get(i).equalsIgnoreCase("or")) {
                                        i++;
                                        continue;
                                }
                                if (!(data.get(i + 1).equals("=") |data.get(i + 1).equals("<>") | data.get(i + 1).equals("<") | data
                                                .get(i + 1).equals(">")))
                                        return false;
                                if (!dbms.check_column_is_valid(data.get(2), data.get(3),
                                                data.get(i)))
                                        return false;
                                if (!condition.check_type(
                                                dbms.get_colomn_type(data.get(2), data.get(3),
                                                                data.get(i)), data.get(i + 2)))
                                        return false;
                                i += 3;
                        }
 
                        ArrayList<String> arr = split();
                        ArrayList<condition> pair = new ArrayList<condition>();
 
                        for (int i = 0; i < arr.size();) {
                                String z = arr.get(i);
                                if (z.equals("(") | z.equals(")")) {
                                        pair.add(new condition(z, "", ""));
                                        i++;
                                        continue;
                                } else if (z.equalsIgnoreCase("AND")) {
                                        pair.add(new condition("*", "", ""));
                                        i++;
                                        continue;
                                } else if (z.equalsIgnoreCase("or")) {
                                        pair.add(new condition("+", "", ""));
                                        i++;
                                        continue;
                                } else if (z.equalsIgnoreCase("<>")) {
                                        pair.add(new condition("!", "", ""));
                                        i++;
                                        continue;
                                } else {
                                        pair.add(new condition(z, arr.get(i + 1), arr.get(i + 2)));
                                        i += 3;
                                        continue;
                                }
                        }
 
                        dbms.select(data.get(2), data.get(3), pair);
 
                }
                return true;
        }
 
        private boolean delete() {
                ArrayList<String> arr = split();
                ArrayList<condition> pair = new ArrayList<condition>();
                if (data.size() < 8 || !data.get(1).equalsIgnoreCase("from")
                                || !data.get(4).equalsIgnoreCase("where"))
                        return false;
                if (!dbms.check_table_and_database(data.get(2), data.get(3)))
                        return false;
                for (int i = 5; i + 2 < data.size();) {
                        if ((data.get(i).equalsIgnoreCase("and") | data.get(i)
                                        .equalsIgnoreCase("or")) & !(i + 3 < data.size()))
                                return false;
                        if (data.get(i).equalsIgnoreCase("and")
                                        | data.get(i).equalsIgnoreCase("or")) {
                                i++;
                                continue;
                        }
                        if (!(data.get(i + 1).equals("=") |data.get(i + 1).equals("<>") | data.get(i + 1).equals("<") | data
                                        .get(i + 1).equals(">")))
                                return false;
                        if (!dbms.check_column_is_valid(data.get(2), data.get(3),
                                        data.get(i)))
                                return false;
                        if (!condition
                                        .check_type(
                                                        dbms.get_colomn_type(data.get(2), data.get(3),
                                                                        data.get(i)), data.get(i + 2)))
                                return false;
                        i += 3;
                }
 
                for (int i = 0; i < arr.size();) {
                        String z = arr.get(i);
                        if (z.equals("(") | z.equals(")")) {
                                pair.add(new condition(z, "", ""));
                                i++;
                                continue;
                        } else if (z.equalsIgnoreCase("AND")) {
                                pair.add(new condition("*", "", ""));
                                i++;
                                continue;
                        } else if (z.equalsIgnoreCase("or")) {
                                pair.add(new condition("+", "", ""));
                                i++;
                                continue;
                        } else if (z.equalsIgnoreCase("<>")) {
                                pair.add(new condition("!", "", ""));
                                i++;
                                continue;
                        } else {
                                pair.add(new condition(z, arr.get(i + 1), arr.get(i + 2)));
                                i += 3;
                                continue;
                        }
                }
                dbms.delete_into_table(data.get(2), data.get(3), pair);
                return true;
        }
 
        private boolean update() {
                ArrayList<String> arr = split();
                ArrayList<condition> pair = new ArrayList<condition>();
                ArrayList<condition> pair2 = new ArrayList<condition>();
                if (data.size() < 7 || !data.get(3).equalsIgnoreCase("SET"))
                        return false;
 
                if (!dbms.check_table_and_database(data.get(1), data.get(2)))
                        return false;
                int index = 0;
                for (int i = 4; i + 2 < data.size(); i += 3) {
                        if (data.get(i).equalsIgnoreCase("WHERE")) {
                                index = i;
                                break;
                        }
                        if (!data.get(i + 1).equals("="))
                                return false;
                        if (!dbms.check_column_is_valid(data.get(1), data.get(2),
                                        data.get(i)))
                                return false;
                        if (!condition
                                        .check_type(
                                                        dbms.get_colomn_type(data.get(1), data.get(2),
                                                                        data.get(i)), data.get(i + 2)))
                                return false;
                        pair2.add(new condition(data.get(i), "=", data.get(i + 2)));
                }
                if (index == 0)
                        return false;
                for (int i = index + 1; i < data.size(); i++) {
                        if ((data.get(i).equalsIgnoreCase("and") | data.get(i)
                                        .equalsIgnoreCase("or")) & i + 1 == data.size())
                                return false;
                        if (data.get(i).equalsIgnoreCase("and")
                                        | data.get(i).equalsIgnoreCase("or"))
                                continue;
                        if (!(i + 2 < data.size()))
                                return false;
                        if (!(data.get(i + 1).equalsIgnoreCase("=")
                                        | data.get(i + 1).equalsIgnoreCase("<") |data.get(i + 1).equals("<>")| data.get(i+ 1)
                                        .equalsIgnoreCase(">")))
                                return false;
                        if (!dbms.check_column_is_valid(data.get(1), data.get(2),
                                        data.get(i)))
                                return false;
                        if (!condition
                                        .check_type(
                                                        dbms.get_colomn_type(data.get(1), data.get(2),
                                                                        data.get(i)), data.get(i + 2)))
                                return false;
                        if (dbms.get_colomn_type(data.get(1), data.get(2), data.get(i))
                                        .equalsIgnoreCase("string") & !data.get(i + 1).equals("="))
                                return false;
                        i += 2;
                }
 
                for (int i = 0; i < arr.size();) {
                        String z = arr.get(i);
                        if (z.equals("(") | z.equals(")")) {
                                pair.add(new condition(z, "", ""));
                                i++;
                                continue;
                        } else if (z.equalsIgnoreCase("AND")) {
                                pair.add(new condition("*", "", ""));
                                i++;
                                continue;
                        } else if (z.equalsIgnoreCase("or")) {
                                pair.add(new condition("+", "", ""));
                                i++;
                                continue;
                        } else if (z.equalsIgnoreCase("<>")) {
                                pair.add(new condition("!", "", ""));
                                i++;
                                continue;
                        } else {
                                pair.add(new condition(z, arr.get(i + 1), arr.get(i + 2)));
                                i += 3;
                                continue;
                        }
                }
                dbms.update_into_table(data.get(1), data.get(2), pair, pair2);
                return true;
        }
 
        private boolean check_data(String x) {
                if (x.equalsIgnoreCase("string") || x.equalsIgnoreCase("integer")
                                || x.equalsIgnoreCase("double"))
                        return true;
                if (x.equalsIgnoreCase("date") || x.equalsIgnoreCase("boolean"))
                        return true;
                return false;
        }
 
        private boolean create_val() {
                if (data.size() < 3)
                        return false;
                ArrayList<pair_Data> attributes = new ArrayList<pair_Data>();
                if (data.size() == 3) {
                        if (!data.get(1).equalsIgnoreCase("DATABASE"))
                                return false;
                        if (!dbms.check_database(data.get(2))) {
                                dbms.createDB(data.get(2));
                                return true;
                        }
                } else {
                        if (data.size() < 8 | data.size() % 2 == 1
                                        | !data.get(1).equalsIgnoreCase("TABLE"))
                                return false;
                        if (!data.get(3).equalsIgnoreCase("AT")
                                        | !data.get(5).equalsIgnoreCase("WHERE"))
                                return false;
                        if (!dbms.check_database(data.get(4)))
                                return false;
                        if (dbms.check_table_and_database(data.get(4), data.get(2)))
                                return false;
                        for (int i = 6; i < data.size(); i += 2) {
                                if (!check_data(data.get(i + 1)))
                                        return false;
                                pair_Data pair = new pair_Data(data.get(i), data.get(i + 1));
                                attributes.add(pair);
                                for (int j = i + 2; j < data.size(); j++) {
                                        if (data.get(i).equals(data.get(j)))
                                                return false;
                                }
                        }
 
                }
                dbms.createTable(data.get(4), data.get(2), attributes);
                return true;
        }
 
        private boolean insert_eval() {
                ArrayList<pair_Data> atr = new ArrayList<pair_Data>();
                if (data.size() < 6 || !data.get(1).equalsIgnoreCase("INTO"))
                        return false;
                if (!dbms.check_database(data.get(2)))
                        return false;
                if (!dbms.check_table_and_database(data.get(2), data.get(3)))
                        return false;
                int index = 4;
                for (int i = 4; i < data.size(); i++) {
                        if (data.get(i).equalsIgnoreCase("values")) {
                                index = i;
                        }
                }
 
                if (index == 4)
                        return false;
                for (int i = 4, j = index + 1; i < index; i++, j++) {
                        if (!dbms.check_column_is_valid(data.get(2), data.get(3),
                                        data.get(i)))
                                return false;
                        // System.out.println( dbms.get_colomn_type(data.get(2),
                        // data.get(3),data.get(i)) + " ' " +data.get(j) ) ;
 
                        if (!condition
                                        .check_type(
                                                        dbms.get_colomn_type(data.get(2), data.get(3),
                                                                        data.get(i)), data.get(j)))
                                return false;
 
                        atr.add(new pair_Data(data.get(i), data.get(j)));
                }
                // System.out.println(444);
                dbms.insert_into_table(data.get(2), data.get(3), atr);
                return true;
        }
       
        private boolean selection()
        {
                if (!data.get(0).equalsIgnoreCase("select"))
                        return false;
                if (data.size() == 5) {
                        if (!data.get(1).equals("*")
                                        || !data.get(2).equalsIgnoreCase("from"))
                                return false;
                        if (!dbms.check_table_and_database(data.get(3), data.get(4)))
                                return false;
                } else {
                        if (data.size() < 8 || !data.get(1).equalsIgnoreCase("from"))
                                return false;
                        if (!data.get(4).equalsIgnoreCase("where"))
                                return false;
                        if (!dbms.check_table_and_database(data.get(2), data.get(3)))
                                return false;
 
                        for (int i = 5; i + 2 < data.size();) {
                                if ((data.get(i).equalsIgnoreCase("and") | data.get(i)
                                                .equalsIgnoreCase("or")) & !(i + 3 < data.size()))
                                        return false;
                                if (data.get(i).equalsIgnoreCase("and")
                                                | data.get(i).equalsIgnoreCase("or")) {
                                        i++;
                                        continue;
                                }
                                if (!(data.get(i + 1).equals("=") |data.get(i + 1).equals("<>") | data.get(i + 1).equals("<") | data
                                                .get(i + 1).equals(">")))
                                        return false;
                                if (!dbms.check_column_is_valid(data.get(2), data.get(3),
                                                data.get(i)))
                                        return false;
                                if (!condition.check_type(
                                                dbms.get_colomn_type(data.get(2), data.get(3),
                                                                data.get(i)), data.get(i + 2)))
                                        return false;
                                i += 3;
                        }
 
                        ArrayList<String> arr = split();
                        ArrayList<condition> pair = new ArrayList<condition>();
 
                        for (int i = 0; i < arr.size();) {
                                String z = arr.get(i);
                                if (z.equals("(") | z.equals(")")) {
                                        pair.add(new condition(z, "", ""));
                                        i++;
                                        continue;
                                } else if (z.equalsIgnoreCase("AND")) {
                                        pair.add(new condition("*", "", ""));
                                        i++;
                                        continue;
                                } else if (z.equalsIgnoreCase("or")) {
                                        pair.add(new condition("+", "", ""));
                                        i++;
                                        continue;
                                } else if (z.equalsIgnoreCase("<>")) {
                                        pair.add(new condition("!", "", ""));
                                        i++;
                                        continue;
                                } else {
                                        pair.add(new condition(z, arr.get(i + 1), arr.get(i + 2)));
                                        i += 3;
                                        continue;
                                }
                        }
                }
                return true;
 
        }
       
 
}