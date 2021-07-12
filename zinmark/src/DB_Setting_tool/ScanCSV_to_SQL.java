package DB_Setting_tool;

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





public class ScanCSV_to_SQL {
    private File scanfFile;                                                         //スキャン対処のファイル　ファイル型で管理
    private HashMap<Integer, HashMap<String, String>> alldata = new HashMap<>();    //ファイル内のすべてのデータを格納　Integer : 行　, MAP<カラム名、データ>
    private String[] column_name_original;                                          //**ファイルに記述されているカラム名をそのまま格納    実際はハッシュコードが違うためなぜか使えない
    private String[] column_name_database;                                          //**データベースに記述されているカラム名を格納　使用していない
    private BufferedReader scanline = null;                                         //ファイルを一行ずつ読み込むためのもの
    private int counter = 0;                                                        //行の数　つまりデータの数を管理
    private String tabel_name;                                                      //データベースのテーブルの名前、ファイルの.csvを抜いた名前でもある
    private ArrayList<String> query_list = new ArrayList<>();                       //データベースにINSERTするためのクエリのリスト　データの数だけある


    //コンストラクタスキャン対象のファイルを受け取る
    //ファイル名から.csvを抜き取りテーブル名を取り出す
    //データのスキャン開始
    public ScanCSV_to_SQL(File file) {
        scanfFile = file;
        this.tabel_name = scanfFile.getName().substring(0, scanfFile.getName().length()-4);
        this.scandata();
    }

    //データを一行ごとにスキャン
    //一行目はカラム名などで「,」区切りでオリジナルカラム名取得　無視してもいい
    //　一行目が終わるとdataflagがtrueになりデータを取得開始
    //一行にあるデータを一度「,」で分割しString[](data)に格納
    //その後カラム名とデータが対応するようにHashMap(newdata)に格納
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

    // column_set : INSERT TABLE (ここのところに入る) VALUE (data1, data2, ..);
    // value_set  : INSERT TABLE (column1, column2,) VALUE (ここに入る);
    //   query    : INSERT TABLE (ここはない) VALUE (data1, data2, ..);
    //alldataの一行目からファイルに記述されている順番でデータを取り出す
    //数字であればそのままクエリとしてvalue_setに入れる
    //文字列であれば''をつけてvalue_setに入れる
    //最後に全部くっつけてクエリにする
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

                        //少数or自然数
                        if (data_value.matches("[+-]?[0-9]+[.]?[0-9]") || data_value.matches("[0-9]+")) {  
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
                        if (data_value.matches("[+-]?[0-9]+[.]?[0-9]") || data_value.matches("[0-9]+")) {  
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

            //クエリ確認用↓
            //System.out.print(tabel_name + " : ");
            //System.out.println(query.toString());
            query_list.add(query.toString());
        }
        return query_list;
    }

    //オリジナルのカラム名を返す　あまり使用していない CSVtoDBで多少使用
    public String[] get_column_name(){
        return column_name_original;
    }

    //テーブルの名前を返す
    public String get_table_name(){
        return this.tabel_name;
    }

}
