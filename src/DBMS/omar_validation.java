package DBMS;
import java.util.ArrayList;
 
public class omar_validation {
//      public static void main(String[] args) {
//              ArrayList<String> ans;
//              String input = "SELECT FROM DBMS, TESTex where city = 'alexandria' and depratement ='c s e d' and age <> 17";
//              input = "       INSERT INTO DBMS, test (column1,column2,column3) VALUES (value1,value2,value3)";
//              input = "UPDATE table_name SET column1=value1,column2=value2 WHERE date=23/8/2013";
//            
//              ans = chk(input);
//
//              for (int i = 0; i < ans.size(); i++) {
//                      System.out.println(ans.get(i));
//              }
//
//      }
 
        static ArrayList<String> chk(String in) {
                ArrayList<String> data = new ArrayList<String>();
                String build = "";
                char[] xyz = in.toCharArray();
                for (int i = 0; i < xyz.length; i++) {
                        char x = xyz[i];
                        if ((x > 64 & x < 91) | (x > 96 & x < 123) | (x > 47 & x < 58) | x=='_' | x=='/')
                                build = build + x;
                        else {
 
                                if ((x == ' ' | x == ','| x==')'| x=='(') && build.length() > 0) {
                                        data.add(build);
                                        build = "";
                                } else if (x == '=' | x == '<' | x == '>') {
                                       
                                        if (build.length() > 0)
                                                data.add(build);
                                       
                                       
                                        if (i + 1 < xyz.length) {
                                                if (xyz[i] == '<' & xyz[i + 1] == '>'){
                                                        data.add("<>");
                                                        i++;
                                                }
                                                else {
                                                        data.add(x + "");
                                                }
                                        }
                                        build = "";
 
                                } else if (x == '\'') {
                                        build = "";
                                        for (int j = i + 1; j < xyz.length; j++) {
                                                i = j;
                                                x = xyz[j];
                                                if (x == '\'') {
                                                        data.add(build);
                                                        break;
                                                }
                                                build = build + x;
                                        }
                                        build = "";
                                } else if (x == '*')
                                        data.add("*");
                        }
                }
                if (build.length() > 0)
                        data.add(build);
 
                return data;
        }
       
        static ArrayList<String> chk2(String in) {
                ArrayList<String> data = new ArrayList<String>();
                String build = "";
                char[] xyz = in.toCharArray();
                for (int i = 0; i < xyz.length; i++) {
                        char x = xyz[i];
                        if ((x > 64 & x < 91) | (x > 96 & x < 123) | (x > 47 & x < 58) | x=='_' | x=='/' )
                                build = build + x;
                        else if( x==')'| x=='(')
                        {
                                if(build.length()>0) data.add(build);
                                data.add(x+"");
                                build = "";
                        }
                        else {
 
                                if ((x == ' ' | x == ',') && build.length() > 0) {
                                        data.add(build);
                                        build = "";
                                } else if (x == '=' | x == '<' | x == '>') {
                                       
                                        if (build.length() > 0)
                                                data.add(build);
                                       
                                       
                                        if (i + 1 < xyz.length) {
                                                if (xyz[i] == '<' & xyz[i + 1] == '>'){
                                                        data.add("<>");
                                                        i++;
                                                }
                                                else {
                                                        data.add(x + "");
                                                }
                                        }
                                        build = "";
 
                                } else if (x == '\'') {
                                        build = "";
                                        for (int j = i + 1; j < xyz.length; j++) {
                                                i = j;
                                                x = xyz[j];
                                                if (x == '\'') {
                                                        data.add(build);
                                                        break;
                                                }
                                                build = build + x;
                                        }
                                        build = "";
                                } else if (x == '*')
                                        data.add("*");
                        }
                }
                if (build.length() > 0)
                        data.add(build);
 
                return data;
        }
 
}
