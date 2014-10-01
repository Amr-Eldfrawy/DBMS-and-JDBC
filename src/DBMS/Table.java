package DBMS;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Stack;
 
import javax.swing.text.TabExpander;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
 
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
 
public class Table {
        private String name;
        private String parentDB;
        private ArrayList<ArrayList<String>> table;
        protected ArrayList<pair_Data> colNames;
        private File file;
 
        // ----------------------------------------------------
        private DocumentBuilderFactory docFactory;
        private DocumentBuilder docBuilder; // these members are for the saving and
                                                                                // loading process
 
        private Document doc;
 
        // ----------------------------------------------------
 
        // String URL;
 
        // public Table(String parentDB, String name, ArrayList<pair_Data> e)
        // throws IOException {
        // this.name = name;
        // this.parentDB = parentDB;
        // colNames = new ArrayList<>();
        // colNames = e;
        // table = new ArrayList<ArrayList<String>>();// editable
        //
        // file = new File(DBMS.PATH + "\\" + parentDB + "\\" + name + ".xml");
        // file.createNewFile();
        // save();
        //
        // }
 
        public Table(String parentDB, String name, ArrayList<pair_Data> e)
                        throws IOException {
                this.name = name;
                this.parentDB = parentDB;
                colNames = new ArrayList<pair_Data>();
                colNames = e;
                table = new ArrayList<ArrayList<String>>();// editable
                DBMS.counter = 0;
                //DBMS.check = false;
                System.out.print(DBMS.PATH);
                file = new File(DBMS.PATH + "\\" + parentDB + "\\" + name + ".xml");
                file.createNewFile();
                String Filename1 = DBMS.PATH + "\\" + parentDB + "\\" + name + ".xml";
                String Filename2 = DBMS.PATH + "\\" + parentDB + "\\" + name + ".dtd";
 
                save();
                DTDGenerator generateDTD = new DTDGenerator();
                generateDTD.run(Filename1);
                generateDTD.printDTD(Filename2);
 
                generateDTD.run(Filename1);
                generateDTD.printDTD(Filename2);
 
        }
 
        // ///////////////////////////////////////////////////////////////////////
 
        public Table(String database_name, String Table_name) {
                name = Table_name;
                parentDB = database_name;
                colNames = new ArrayList<pair_Data>();
                DBMS.counter = 0;
                //DBMS.check = false;
                System.out.print(DBMS.PATH);
                table = new ArrayList<ArrayList<String>>();// editable
                file = new File(DBMS.PATH + "\\" + parentDB + "\\" + name + ".xml");
                String Filename = DBMS.PATH + "\\" + parentDB + "\\" + name + ".xml";
 
                try {
                        if (DTDGenerator.validateXMLFile(Filename)) {
 
                                // System.out.println(" 11 file loaded successfully !");
 
                                load();
 
                        } else {
                                System.out.println("corrupted file . loading failed !");
                        }
//              } catch (ParserConfigurationException | SAXException | IOException e) {
                } catch(Exception e){
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                }
 
                // load();
 
        }
 
        // ///////////////////////////////////////////////////////////////////////
 
        protected boolean validate() {
                /*
                 * 2D array is filled with data from xml file you are requried to match
                 * the array<String>Colnames which determine the dataType of each colum
                 * with the 2D grid
                 *
                 * el mo3eeed 7ay3'ayyar fe cell mo3aianna fe xml file mafrood ene check
                 * en kol cell fe nafs data type el mafrrood tkoonn fehha :D
                 *
                 *
                 * tab3an tree data types mawgodeen fe Group Assupmtions . txt
                 */
                int n = table.get(0).size();
                int m = table.size();
 
                for (int i = 0; i < n; i++) {
                        String p = colNames.get(i).data_2;
                        for (int j = 0; j < m; j++) {
                                String s = table.get(j).get(i);
 
                                if (p.equals("integer")) {
                                        try {
                                                Integer.parseInt(s);
                                        } catch (Exception e) {
                                                return false;
                                        }
 
                                } else if (p.equals("double")) {
                                        try {
                                                Double.parseDouble(s);
                                        } catch (Exception e) {
                                                return false;
                                        }
                                }
                        }
                }
                return true;
        }
 
        // ///////////////////////////////////////////////////////////////////////
 
        public void save() {
                /*
                 * in this function you have to fill the xml file which has the the Path
                 * = C:\\Directory\\parentBD\\name with the 2D grid table above (table)
                 *
                 *
                 * note: if cell in 2D array has empty cell you should not push its row
                 * in the xml file
                 *
                 *
                 *
                 * the previous comments are written by amr el defrawy this method is
                 * responsible of saving the contents of the table to an xml file .
                 */
                docFactory = DocumentBuilderFactory.newInstance();
                try {
                        docBuilder = docFactory.newDocumentBuilder();
                        doc = docBuilder.newDocument();
 
                        Element root = doc.createElement("table");
                        doc.appendChild(root);
 
                        for (int i = 0; i < colNames.size(); i++) {
                                Element current = createNodeFromColNames(i);
                                root.appendChild(current);
                        }
 
                        for (int i = 0; i < table.size(); i++) {
 
                                Element currentRow = createNodeForXML(i);
                                root.appendChild(currentRow);
 
                        }
 
                        TransformerFactory transformerFactory = TransformerFactory
                                        .newInstance();
                        Transformer transformer = transformerFactory.newTransformer();
                        DOMSource source = new DOMSource(doc);
                        StreamResult result = new StreamResult(file);
 
                        // Output to console for testing
                        // StreamResult result = new StreamResult(System.out);
                        transformer.transform(source, result);
                        System.out.println("File saved!");
 
                } catch (ParserConfigurationException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                } catch (TransformerConfigurationException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                } catch (TransformerException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                }
 
        }
 
        // ///////////////////////////////////////////////////////////////////////
 
        public void load() {
                // TODO Auto-generated method stub
                /*
                 * fill the 2D grid (tabel) from the xml file note : this funtion will
                 * not be called if and only if the the table_name and database_name are
                 * given .
                 *
                 * you are given 2 paremeters(tabel_name , Database_name) and your task
                 * to fil the 2D array from xml file which has the Path =
                 * C:\\Directory\\parentBD\\name
                 */
 
                /*
                 * the previous comments are by amr el defrawy this function is
                 * responsible for loading an existing xml file and parsing its contents
                 * into the table
                 */
 
                try {
                        docFactory = DocumentBuilderFactory.newInstance();
                        docBuilder = docFactory.newDocumentBuilder();
                        Document doc = docBuilder.parse(file);
                        doc.getDocumentElement().normalize();
 
                        NodeList allattributes = doc.getElementsByTagName("attr");
                        for (int i = 0; i < allattributes.getLength(); i++) {
                                org.w3c.dom.Node curr = allattributes.item(i);
 
                                if (curr.getNodeType() == Node.ELEMENT_NODE) {
                                        Element current = (Element) curr;
                                        String id = current.getElementsByTagName("myid").item(0)
                                                        .getTextContent();
                                        String type = current.getElementsByTagName("type").item(0)
                                                        .getTextContent();
                                        pair_Data newcol = new pair_Data(id, type);
                                        colNames.add(newcol);
                                }
 
                        }
 
                        NodeList allElements = doc.getElementsByTagName("row");
 
                        for (int i = 0; i < allElements.getLength(); i++) {
                                org.w3c.dom.Node currentNode = allElements.item(i);
 
                                if (currentNode.getNodeType() == Node.ELEMENT_NODE) {
 
                                        Element currentElement = (Element) currentNode;
                                        ArrayList<String> dataOfcurrentElement = new ArrayList<String>();
                                        for (int j = 0; j < colNames.size(); j++) {
                                                String currentAttribute = colNames.get(j).get_Data_1();
                                                String currentValue = currentElement
                                                                .getElementsByTagName(currentAttribute).item(0)
                                                                .getTextContent();
                                                dataOfcurrentElement.add(currentValue);
 
                                        }
                                        table.add(dataOfcurrentElement);
 
                                }
                        }
 
                } catch (Exception e) {
                        e.printStackTrace();
                }
 
        }
 
        // ///////////////////////////////////////////////////////////////////////
        protected void insert(ArrayList<String> newRow) {
                //DBMS.check = false;
                DBMS.counter=1;
                table.add(newRow);
 
                save();
 
                }
 
        // /////////////////////////////////////////////////////////////////////////////////////////////
        private static String toPostfix(String str) {
 
                String ans = "";
                Stack<Character> S = new Stack<Character>();
                for (int i = 0; i < str.length(); i++) {
                        char top = str.charAt(i);
                        // simple char
                        if (top == '1' || top == '0') {
                                ans += top;
                                continue;
 
                        }
                        // ( * , + , ! , ( , ) )
                        else {
                                if (top == ')') {
 
                                        while (!S.empty() && S.peek() != '(')
                                                ans += S.pop();
                                        S.pop();
 
                                } else if (S.isEmpty()) {
                                        S.push(top);
                                        continue;
                                } else {
                                        if (top == '(') {
                                                S.push(top);
                                                continue;
                                        }
 
                                        while (!S.empty() && higherPrecedence(top, S.peek()))
                                                ans += S.pop();
                                        S.push(top);
                                }
                        }
 
                }
                while (!S.empty())
                        ans += S.pop();
 
                return ans;
 
        }
 
        private static boolean higherPrecedence(char s, Character peek) {
                // TODO Auto-generated method stub
                if (peek == '(')
                        return false;
                return peek <= s;
        }
 
        // ////////////////////////////////////////////////////////////////////////////////////////////
        protected boolean evaluateExp(String postfixexp) {
                // TODO Auto-generated method stub
 
                Stack<Boolean> S = new Stack<Boolean>();
                char[] Ar = new char[postfixexp.length()];
                Ar = postfixexp.toCharArray();
                for (int i = 0; i < Ar.length; i++)
                        Ar[i] = postfixexp.charAt(i);
 
                // postfix evaluation
                // ex: 10*1+ >> ab*c+
 
                for (int i = 0; i < Ar.length; i++) {
 
                        if (Ar[i] == '1')
                                S.push(true);
                        else if (Ar[i] == '0')
                                S.push(false);
                        else {
                                if (Ar[i] == '*') {
                                        boolean first = S.pop(), second = S.pop();
                                        S.push(first & second);
                                } else if (Ar[i] == '+') {
                                        boolean first = S.pop(), second = S.pop();
                                        S.push(first | second);
 
                                }
                                // must be not
                                else {
                                        boolean first = S.pop();
                                        S.push(!first);
 
                                }
                        }
                }
                return S.peek();
        }
 
        // /////////////////////////////////////////////////////////////////////////////////////////////
 
        protected void tableView() {
                for (int i = 0; i < colNames.size(); i++) {
                        System.out.print(colNames.get(i).data_1 + " ");
                }
                System.out.println();
                // insert into test from foe name ahmed id 14
                for (int i = 0; i < colNames.size(); i++) {
                        System.out.print(colNames.get(i).data_2 + " ");
                }
                System.out.println();
 
                for (int i = 0; i < table.size(); i++) {
                        for (int j = 0; j < table.get(i).size(); j++) {
                                System.out.print(table.get(i).get(j) + "    ");
 
                        }
                        System.out.println();
                }
        }
        
    	

        protected void view(ArrayList<condition> e) {
                        // TODO Auto-generated method stubzz
                //      DBMS.check = true;
                        DBMS.counter = 0;
                        DBMS.changedtable=new ArrayList<ArrayList<String>>();
                        ArrayList<ArrayList<String>> ans = new ArrayList<ArrayList<String>>();
                        if(e.size()==0)
                        {
                               
                                for (int i = 0; i < table.size(); i++)
                                                ans.add(table.get(i));
                               
                        }
                        else
                                for (int i = 0; i < table.size(); i++) {
                                if (truecondition(e, table.get(i)))
                                        ans.add(table.get(i));
                        }
                        // tableView(ans);
                        if (ans.size() > 0) {
                                ArrayList<String> g = new ArrayList<String>();
         
                                for (int i = 0; i < colNames.size(); i++) {
                                        String str = "";
                                        str += colNames.get(i).data_1 + "," + colNames.get(i).data_2;
                                        g.add(str);
         
                                }
                                ArrayList<ArrayList<String>> q = new ArrayList<ArrayList<String>>();
                                q.add(g);
                                for (int i = 0; i < ans.size(); i++)
                                        q.add(ans.get(i));
                               
                                DBMS.changedtable = q;
         
                        }
                }



     
    // ///////////////////////////////////////////////////////////////////////
 
        // ///////////////////////////////////////////////////////////////////////
 
//      protected void tableView(ArrayList<ArrayList<String>> ans) {
//
//              for (int i = 0; i < colNames.size(); i++)
//                      System.out.print(colNames.get(i).data_1 + " ");
//              System.out.println();
//              for (int i = 0; i < colNames.size(); i++)
//                      System.out.print(colNames.get(i).data_2 + " ");
//              System.out.println();
//
//              for (int i = 0; i < ans.size(); i++) {
//                      for (int j = 0; j < ans.get(i).size(); j++) {
//                              System.out.print(ans.get(i).get(j) + "  ");
//                      }
//                      System.out.println();
//              }
//      }
 
        // ///////////////////////////////////////////////////////////////////////
 
        private boolean Judge(String a, String op, String b) {
                if (a.length() == 0 || b.length() == 0)
                        return false;
                // System.out.println(a + " " + op + " " + b );
                boolean isInteger1 = isInt(a);
                boolean isInteger2 = isInt(a);
                if (isInteger1 == isInteger2) {
                        if (isInteger1 == true) {
                                if (op.compareTo("=") == 0) {
                                        // System.out.println(a + " " + b +"  " + a==b );
                                        return Integer.parseInt(a) == Integer.parseInt(b);
                                } else if (op.compareTo(">") == 0) {
                                        // System.out.println(a + " " + b);
                                        return Integer.parseInt(a) > Integer.parseInt(b);
                                } else if (op.compareTo("<") == 0)
                                        return Integer.parseInt(a) < Integer.parseInt(b);
 
                        } else {
                                if (op.compareTo("=") == 0)
                                        return a.compareTo(b) == 0;
                                else if (op.compareTo(">") == 0)
                                        return a.compareTo(b) > 0;
 
                                else if (op.compareTo("<") == 0)
                                        return a.compareTo(b) < 0;
 
                        }
 
                }
 
                return false;
        }
 
        // ///////////////////////////////////////////////////////////////////////
 
        private boolean isInt(String a) {
                // TODO Auto-generated method stub
                for (int i = 0; i < a.length(); i++)
                        if ((a.charAt(i) >= 'a' && a.charAt(i) <= 'z')
                                        || (a.charAt(i) >= 'A' && a.charAt(i) <= 'Z'))
                                return false;
 
                return true;
 
        }
 
        // ///////////////////////////////////////////////////////////////////////
 
        protected void update(ArrayList<condition> e, ArrayList<condition> g) {
               
                // new Update
                DBMS.counter = 0;
                for (int i = 0; i < table.size(); i++) {
                        if (truecondition(e, table.get(i))) {
                                table.set(i, setrow(g, table.get(i)));
                                DBMS.counter++;
 
                        }
                }
 
                save();
 
                /*
                 * int Col = getIndex(e.data_1); int Col2 = getIndex(g.data_1);
                 *
                 * counter = 0; tableView(); if (Col == -1) return;
                 * ArrayList<ArrayList<String>> t = new ArrayList<>(); for (int i = 0; i
                 * < table.size(); i++) { boolean can = false; if
                 * (Judge(table.get(i).get(Col), e.operator, e.data_2)) {
                 * table.get(i).set(Col2, g.data_2); counter++; }
                 *
                 * // System.out.println(Judge(table.get(i).get(Col), e.operator, //
                 * e.data_2) + " " + table.get(i).get(Col)+" " + e.operator + " " //
                 * +e.data_2); if (table.get(i).get(Col2).compareTo("") == 0) can =
                 * true;
                 *
                 * if (can == false) t.add(table.get(i)); } table.clear(); table = t;
                 * tableView();
                 *
                 * save();
                 */
        }
 
        // ///////////////////////////////////////////////////////////////////////
 
        private ArrayList<String> setrow(ArrayList<condition> g,
                        ArrayList<String> arrayList) {
                // TODO Auto-generated method stub
 
                for (int i = 0; i < g.size(); i++) {
                        condition s = g.get(i);
                        int f = getIndex(s.data_1);
                        arrayList.set(f, s.data_2);
                }
 
                return arrayList;
 
        }
 
        private boolean truecondition(ArrayList<condition> e,
                        ArrayList<String> arrayList) {
                // TODO Auto-generated method stub
                String exp = "";
                for (int i = 0; i < e.size(); i++) {
                        condition s = e.get(i);
                        int f = getIndex(s.data_1);
                        if (f != -1) {
                                boolean ans = false;
                                if (Judge(arrayList.get(f), s.operator, s.data_2))
                                        ans = true;
 
                                if (ans == true)
                                        exp += "1";
                                else
                                        exp += "0";
 
                        } else
                                exp += s.data_1;
 
                }
                if (evaluateExp(toPostfix(exp)) == true)
                        return true;
 
                return false;
        }
 
        // ///////////////////////////////////////////////////////////////////////
        protected int getIndex(String data_1) {
                // TODO Auto-generated method stub
                for (int i = 0; i < colNames.size(); i++)
                        if (data_1.equals(colNames.get(i).data_1) == true)
                                return i;
                return -1;
        }
 
        // ///////////////////////////////////////////////////////////////////////
 
        protected void delete(ArrayList<condition> e) {
                //DBMS.check = false;
                DBMS.counter = 0;
                for (int i = 0; i < table.size(); i++)
                        if (truecondition(e, table.get(i))) {
                                table.set(i, new ArrayList<String>());
                                DBMS.counter++;
                        }
                ArrayList<ArrayList<String>> t = new ArrayList<ArrayList<String>>();
                for (int i = 0; i < table.size(); i++)
                        if (table.get(i).size() != 0)
                                t.add(table.get(i));
                table = t;
 
                save();
                // tableView();
        }
 
        /*
         *
         * this method creates a node for each row in the table and returns this
         * node to the function that saves the table to the xml file
         */
 
        private Element createNodeForXML(int index) {
 
                Element newRow = doc.createElement("row");
                newRow.setAttribute("ID", String.valueOf(index));
                for (int i = 0; i < colNames.size(); i++) {
                        String currentColumn = colNames.get(i).get_Data_1();
                        Element child = doc.createElement(currentColumn);
                        child.appendChild(doc.createTextNode(table.get(index).get(i)));
                        newRow.appendChild(child);
                }
                return newRow;
        }
 
        private Element createNodeFromColNames(int index) {
 
                Element newColName = doc.createElement("attr");
                Element ID = doc.createElement("myid");
                ID.appendChild(doc.createTextNode(colNames.get(index).get_Data_1()));
                newColName.appendChild(ID);
 
                Element type = doc.createElement("type");
                type.appendChild(doc.createTextNode(colNames.get(index).get_Data_2()));
                newColName.appendChild(type);
 
                return newColName;
        }
 
        // ///////////////////////////////////////////////////////////////////////
 
        public String getColomnType(int i) {
 
                return colNames.get(i).data_2;
        }
 
        public boolean isThereCol(String name) {
                for (int i = 0; i < colNames.size(); i++) {
                        if (colNames.get(i).data_1.equals(name)) {
                                return true;
                        }
                }
                return false;
        }
 
        // ///////////////////////////////////////////////////////////////////////
 

 
}