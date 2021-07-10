import java.io.File;

public class Scantest {
    public static void main(String[] args) {
        Scan_data_csvfile scan_file = new Scan_data_csvfile();
        scan_file.readFolder(new File("/home/gigi/link/soft/upgrade_project_git/zinmark/data"));
        for(File file : scan_file.file_list){
            ScanCSV_to_SQL scan = new ScanCSV_to_SQL(file);
            //scan.insert_data();
            CSVtoDB conectdb = new CSVtoDB("jdbc:postgresql://localhost:5432/zinmark_db", "zinmark", "zinpass",scan.get_table_name(),scan.insert_data(), scan.get_column_name());
        }
    }
}
