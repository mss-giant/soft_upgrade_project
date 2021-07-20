package src;
import src.DB_Setting_tool.*;



import java.io.File;

public class Scantest {
    public static void main(String[] args) {
        //フォルダをスキャン
        //ファイルをスキャン
        //通信確立
        //既存のデータを消す
        //データを入れる
        System.out.println("roading.....");
        Scan_data_csvfile scan_file = new Scan_data_csvfile();
        scan_file.readFolder(new File("/home/gigi/link/soft/upgrade_project_git/zinmark/data"));
        for(File file : scan_file.get_file_list()){
            System.out.println("setting..."+file.getName());
            ScanCSV_to_SQL scan = new ScanCSV_to_SQL(file);
            //scan.insert_data();
            CSVtoDB conectdb = new CSVtoDB("jdbc:postgresql://localhost:5432/zinmark_db", "zinmark", "zinpass",scan.get_table_name(),scan.insert_data(), scan.get_column_name());
            conectdb.connect_SQL("delete");
            conectdb.connect_SQL("insert");
        }
        System.out.println("success");
    }
}
