import java.io.*;
import java.util.*;
import java.nio.file.*;
import java.nio.charset.*;
import java.util.Random;
import java.util.Scanner;

public class geneUrl {
  static String str_in;
  static String[] alph = {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z",
  "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};

  public static void main(String[] args){
    String fileName = "url.csv";
    String id_name_url = "id,name,url";
    int N, M;
    String n;

    try{
      Scanner scan = new Scanner(System.in);
      System.out.println("URL数(name数もこの値と同値), 最長文字列 (ex: 1 1)");

      //URL数(name数もこの数と同値)
      N = scan.nextInt();
      //最長文字列
      M = scan.nextInt();
      n = Integer.toString(N);

      List<String> lines = new ArrayList<String>();
      lines.add(id_name_url);
      rand(N,M,lines);

      //.csvファイル作成
      Path file = Paths.get(fileName);
      Files.write(file, lines, StandardCharsets.UTF_8);

    }catch(IOException e){
      System.out.println("An error occurred.");
      e.printStackTrace();
    }
  }

  //.csvファイルに.html追加
  static void rand(int N, int M, List<String> lines){
    Random rand = new Random();
    int num_make;
    String num;
    String name;
    String url;
    for(int i=1; i<=N; i++){
      num = Integer.toString(i);
      num_make = rand.nextInt(M)+1;
      name = makeStr(num_make);
      num_make = rand.nextInt(M)+1;
      url = makeStr(num_make);
      lines.add(num + "," + name + "," + url + ".html");
    }
  }

  //ランダム文字列生成
  static String makeStr(int num_make){
    Random rand = new Random();
    int num_get;
    String str = null;
    for(int i=0; i<num_make; i++){
      num_get = rand.nextInt(51)+1;
      if(i == 0) {
        int num_rand_adj = rand.nextInt(num_get);
        str = alph[num_get - num_rand_adj-1];
      }
      else str = str + alph[num_get];
    }
    return str;
  }
}
