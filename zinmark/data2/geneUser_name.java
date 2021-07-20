import java.io.*;
import java.util.*;
import java.nio.file.*;
import java.nio.charset.*;
import java.util.Random;
import java.util.Scanner;

public class geneUser_name {
  static String str_in;
  static String[] alph = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};

  public static void main(String[] args){
    String fileName = "user_name.csv";
    String id_name = "id,name";
    int N, M;
    String n;

    try{
      Scanner scan = new Scanner(System.in);
      System.out.println("名前数, 最長文字列 (ex: 1 1)");

      N = scan.nextInt();
      M = scan.nextInt();
      n = Integer.toString(N);

      List<String> lines = new ArrayList<String>();
      lines.add(id_name);
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
    String user_name;
    for(int i=1; i<=N; i++){
      num = Integer.toString(i);
      num_make = rand.nextInt(M)+1;
      user_name = makeuser_name(num_make);
      lines.add(num + "," + user_name);
    }
  }

  //ランダム文字列生成
  static String makeuser_name(int num_make){
    Random rand = new Random();
    int num_get;
    String user_name = null;
    for(int i=0; i<num_make; i++){
      num_get = rand.nextInt(25)+1;
      if(i == 0) {
        int num_rand_adj = rand.nextInt(num_get);
        user_name = alph[num_get - num_rand_adj-1];
      }
      else user_name = user_name + alph[num_get];
    }
    return user_name;
  }
}
