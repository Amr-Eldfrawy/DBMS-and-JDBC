package DBMS;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.locks.Condition;
 
public class DBMS implements DBMSInterface {
        protected static String PATH;
        protected static int counter;
        protected static boolean check;
        protected static  ArrayList<ArrayList<String> > changedtable ;
        public DBMS() {
                // TODO Auto-generated constructor stub
                counter = 0;
                check=false;
                changedtable=new ArrayList<ArrayList<String>>();
 
        }
 
        public void createDB(String name) {
                // TODO Auto-generated method stub
                new Database(name);
                // amr
        }
 
        protected void selectAll(String DB, String name) {
        	Table temp = new Table(DB, name);
        	select(DB , name , new ArrayList<condition>());
        	check=true;
        }
 
        protected void select(String DB, String name, ArrayList<condition> pair) {
                check=true;
                Table temp = new Table(DB, name);
                temp.view(pair);
 
        }
 
        @Override
        public void createTable(String DB, String name, ArrayList<pair_Data> e) {
                // TODO Auto-generated method stub
                try {
                        new Table(DB, name, e);
                } catch (IOException e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                }
                // amr
 
        }
 
        public void insert_into_table(String DB, String table,
                        ArrayList<pair_Data> data) {
                check=false;
                // TODO Auto-generated method stub
                System.out.println();
                Table temp = new Table(DB, table);
 
                ArrayList<String> s = new ArrayList<String>();
                for (int i = 0; i < temp.colNames.size(); i++)
                        s.add("");
 
                for (int i = 0; i < data.size(); i++) {
                        for (int j = 0; j < temp.colNames.size(); j++)
                                if (data.get(i).data_1.equals(temp.colNames.get(j).data_1))
                                        s.set(j, data.get(i).data_2);
                }
 
                /*
                 * the following check is used to make sure that the insertion has the
                 * same format of the selected table for example if the table has two
                 * attributes name and age . if the inserted data is only age we
                 * shouldn't accept this . or if the inserted data is name , age and
                 * type . we also shouldn't accept this .
                 */
 
                /*
                 * you also have the choice to change this action towards this problem
                 * it is up to you . but for now we handled it as follows .
                 */
 
                int countEmptyString = 0;
 
                for (int i = 0; i < s.size(); i++)
                        if (s.get(i).equals(""))
                                countEmptyString++;
 
                if (countEmptyString != 0 || data.size() > temp.colNames.size()) {
                        System.out
                                        .println("Insertion failed . the data you are trying to insert doesn't match the the selected table");
                } else {
                        check=false;
                        temp.insert(s);
                }
 
                //temp.tableView();
                /*
                 * the old part left as a comment in case of emergency :D
                 */
 
                /*
                 *
                 * for (int i = 0; i < data.size(); i++){ for (int j = 0; j <
                 * temp.colNames.size(); j++) if
                 * (data.get(i).data_2.equals(temp.colNames.get(j).data_1)) s.set(j,
                 * data.get(i).data_1); }
                 *
                 * temp.insert(s);
                 */
 
        }
 
        @Override
        public void delete_into_table(String DB, String table,
                        ArrayList<condition> Con) {
                // TODO Auto-generated method stub
                check=false;
                Table temp = new Table(DB, table);
 
                temp.delete(Con);
 
        }
 
        @Override
        public void select_into_table(String DB, String name,
                        ArrayList<condition> Con) {
                // TODO Auto-generated method stub
                check=true
                                ;
 
                Table temp = new Table(DB, name);
 
                temp.view(Con);
 
        }
 
        @Override
        public boolean check_database(String DB) {
                // TODO Auto-generated method stub
 
                if (new File(DBMS.PATH + "\\" + DB).exists() == false)
                        return false;
 
                return true;
        }
 
        @Override
        public boolean check_table_and_database(String database_name,
                        String table_name) {
                // TODO Auto-generated method stub
                if (check_database(database_name) == false)
                        return false;
 
                if (new File(DBMS.PATH + "\\" + database_name + "\\" + table_name
                                + ".xml").exists() == false)
                        return false;
 
                return true;
 
        }
 
        @Override
        public String get_colomn_type(String DB, String table, String colNumber) {
 
                Table temp = new Table(DB, table); // fo2 ma3mola new Table (table, DB);
                int o = temp.getIndex(colNumber);
                if (o >= 0)
                        return temp.getColomnType(o);
                System.out.println();
                return colNumber;
 
                // method i made in
                // insert into test from foe id 12 name amr // table class
 
        }
 
        @Override
        public boolean check_column_is_valid(String DB, String table,
                        String columnName) {
                Table temp = new Table(DB, table); // fo2 ma3mola new Table(table, DB);
                                                                                        // please check it.
 
                if (check_table_and_database(DB, table)) {
                        if (temp.isThereCol(columnName)) {
                                return true;
                        } else
                                return false;
                } else
                        return false;
        }
 
        @Override
        public void update_into_table(String DB, String table,
                        ArrayList<condition> Con, ArrayList<condition> newData) {
                // TODO Auto-generated method stub
                check=false;
                Table temp = new Table(DB, table);
                temp.update(Con, newData);
                //temp.tableView();
 
        }
 
        public void TableView(String databas_name, String table_name) {
               
                Table temp = new Table(databas_name, table_name);
                //temp.tableView();
 
        }
 
        public void setpath(String s) {
                PATH = s;
 
        }
        public boolean getcheck()
        {
                return check;
        }
 
        public int getcounter() {
                return counter;
        }
 
 
        public ArrayList<ArrayList<String>> getable() {
                return changedtable ;
        }
}