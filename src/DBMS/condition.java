package DBMS;
public class condition {
        String data_1;
        String operator;
        String data_2;
 
        public condition(String s1, String s2, String s3) {
                data_1 = s1;
                operator = s2;
                data_2 = s3;
        }
 
        public boolean check_condition_and_types(String database, String table,
                        DBMS dbms) {
                if (!dbms.check_column_is_valid(database, table, data_1)) {
                        operator = "-1";
                        return false;
                }
                String type = dbms.get_colomn_type(database, table, data_1);
                if (!check_type(type, data_2)) {
                        operator = "-1";
                        return false;
                }
                return true;
        }
 
        public static boolean check_type(String type, String data) {
                if (type.equals("integer")) {
                        try {
                                Integer.parseInt(data);
                                return true;
                        } catch (NumberFormatException e) {
                                return false;
                        }
                } else if (type.equals("double")) {
                        try {
                                Double.parseDouble(data);
                                return true;
                        } catch (NumberFormatException e) {
                                return false;
                        }
                } else if (type.equals("string"))
                        return true;
                else if (type.equals("date")) {
                         return new Date(data).validate();
                } else if (type.equalsIgnoreCase("boolean")) {
                        BooleanDatabase.check(data);
                }
 
                return false;
        }
 
        public String get_operator() {
                return operator;
        }
}