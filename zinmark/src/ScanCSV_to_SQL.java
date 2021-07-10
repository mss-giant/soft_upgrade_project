import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;





class ScanCSV_to_SQL {
    private File scanfFile;
    private HashMap<Integer, HashMap<String, String>> alldata = new HashMap<>();
    private String[] column_name_original;
    private List<String> column_name;
    private String[] column_name_database;
    private BufferedReader scanline = null;
    private int counter = 0;
    private String tabel_name;
    private ArrayList<String> query_list = new ArrayList<>();

    ScanCSV_to_SQL(File file) {
        scanfFile = file;
        this.tabel_name = scanfFile.getName().substring(0, scanfFile.getName().length()-4);
        this.scandata();
    }

    public void scandata() {
        try {
            scanline = new BufferedReader(new InputStreamReader(new FileInputStream(scanfFile), "UTF-8"));
            String line;
            Boolean dataflag = false;
            while ((line = scanline.readLine()) != null) {
                if (dataflag) {
                    counter++;
                    String[] data = new String[column_name_original.length];
                    HashMap<String, String> newdata = new HashMap<>();
                    data = line.split(",", -1);

                    for (int i = 0; i < column_name_original.length; i++) {
                        newdata.put(column_name_original[i], data[i]);
                    }
                    alldata.put(counter, newdata);

                } else {
                    column_name_original = line.split(",",-1);
                    dataflag = true;
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public ArrayList<String> insert_data() {
        for (Integer j : alldata.keySet()) {
            StringBuilder column_set = new StringBuilder();
            StringBuilder value_set = new StringBuilder();
            StringBuilder query = new StringBuilder();

            for (int i = 0; i < column_name_original.length; i++) {
                String data_value = alldata.get(j).get(column_name_original[i]);
                if (data_value.length() != 0) {
                    if (column_set.length() != 0) {
                        column_set.append(",");
                        column_set.append("\"");
                        column_set.append(column_name_original[i]);
                        column_set.append("\"");

                        if (data_value.matches("[0-9]{1,5}")) {  
                            value_set.append(",");
                            value_set.append(alldata.get(j).get(column_name_original[i]));
                        } else {
                            value_set.append(",");
                            value_set.append("\'");
                            value_set.append(alldata.get(j).get(column_name_original[i]));
                            value_set.append("\'");
                        }
                    } else {
                        column_set.append("\"");
                        column_set.append(column_name_original[i]);
                        column_set.append("\"");
                        if (data_value.matches("[0-9]{1,5}")) {  
                            value_set.append(alldata.get(j).get(column_name_original[i]));
                        } else {
                            value_set.append("\'");
                            value_set.append(alldata.get(j).get(column_name_original[i]));
                            value_set.append("\'");
                        }
                        
                    }
                }
            }
            query.append("INSERT INTO ");
            //query.append("\"");
            query.append(tabel_name);
            //query.append("\"");
            //query.append(" (");
            //query.append(column_set);
            //query.append(") ");
            query.append(" VALUES (");
            query.append(value_set);
            query.append(");");
            //System.out.println(query.toString());
            query_list.add(query.toString());
        }
        return query_list;
    }

    public String[] get_column_name(){
        return column_name_original;
    }

    public String get_table_name(){
        return this.tabel_name;
    }

}
