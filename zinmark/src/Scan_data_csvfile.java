import java.io.File;
import java.util.ArrayList;
//import java.util.LinkedList;

class Scan_data_csvfile {
    //LinkedList<File> file_list = new LinkedList<>();
    ArrayList<File> file_list = new ArrayList<File>();

    /**
     * ディレクトリを再帰的に読む
     * 
     * @param folderPath
     */
    public void readFolder(File dir) {
      File[] files = dir.listFiles();
      if (files == null)
        return;
      for (File file : files) {
        if (!file.exists())
          continue;
        else if (file.isDirectory())
          readFolder(file);
        else if (file.isFile())
          execute(file);
      }
    }
  
    /**
     * ファイルの処理
     * 
     * @param filePath
     */
    public void execute(File file) {
      // ここにやりたい処理を書く
      // System.out.println( file.getPath() );
      if (!file_list.contains(file)) {
        if(file.getName().matches("[-_.!~*\\'()a-zA-Z0-9;\\?:\\@&=+\\$,%#]+csv")){
          file_list.add(file);
        }
      }
    }
  
}
