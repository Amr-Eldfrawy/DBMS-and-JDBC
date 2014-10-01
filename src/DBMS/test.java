package DBMS;
 
import DBMS.DBMS;
import DBMS.Table;
import junit.framework.TestCase;
 
public class test extends TestCase {
        public void testCheck_database() {
                DBMS s = new DBMS();
                assertEquals(s.check_database("ba"), false);
        }
 
        public void testCheck_table() {
                DBMS s = new DBMS();
 
                assertEquals(s.check_table_and_database("b", "rr"), false);
 
        }
 
        public void testCheck_colum() {
                DBMS s = new DBMS();
                assertEquals(s.check_column_is_valid("AMR", "ttt", "age"), false);
 
        }
 
        public void check_postifix() {
                Table s = new Table("AMR", "ttt");
                assertEquals(s.evaluateExp("(1*0)"), false);
 
        }
 
        public void check_index() {
                Table s = new Table("AMR", "ttt");
                assertEquals(s.getIndex("age"), -1);
        }
 
}
