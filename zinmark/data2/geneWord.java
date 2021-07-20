import java.io.*;
import java.util.*;
import java.nio.file.*;
import java.nio.charset.*;
import java.util.Random;
import java.util.Scanner;
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

public class geneWord {
  static String str_in;
  static String[] alph = {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z",
                          "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};

  
  public static void main(String[] args){
    String fileName = "word.csv";
    String id_word = "id,word";
    int N, M;
    String n;
    try{
      Scanner scan = new Scanner(System.in);
      //System.out.println("word数, 最長文字列 (ex: 2 2)");
      System.out.println("word数");
      N = scan.nextInt();
      //M = scan.nextInt();
      n = Integer.toString(N);
      M = 1;
      List<String> lines = new ArrayList<String>();
      lines.add(id_word);
      rand(N,M,lines);

      Path file = Paths.get(fileName);
      Files.write(file, lines, StandardCharsets.UTF_8);

    }catch(IOException e){
      System.out.println("An error occurred.");
      e.printStackTrace();
    }
  }

  static void rand(int N, int M, List<String> lines){
    Random rand = new Random();
    int num_make;
    String num;
    String word;
    for(int i=1; i<=N; i++){
      num = Integer.toString(i);
      num_make = rand.nextInt(M)+1;
      //word = makeword(num_make);
      word = makeword2();
      lines.add(num + "," + word);
    }
  }

  //ランダム文字列生成
  static String makeword(int num_make){
    Random rand = new Random();
    int num_get;
    String word = null;
    for(int i=0; i<num_make; i++){
      num_get = rand.nextInt(51)+1;
      if(i == 0){
        int num_rand_adj = rand.nextInt(num_get);
        word = alph[num_get - num_rand_adj-1];
      }
      else word = word + alph[num_get];
    }
    return word;
  }

  static String makeword2(){
    Random rand = new Random();
    int randam = rand.nextInt(3000);
    String word = scan_words().get(randam);
    return word;
  }

  static ArrayList<String> scan_words(){
    File scanfile = new File("words.txt");
    ArrayList<String> data = new ArrayList<String>();
    try {
      BufferedReader scanline = new BufferedReader(new InputStreamReader(new FileInputStream(scanfile),"UTF-8"));
      String line;
      while((line = scanline.readLine()) != null){
        data.add(line);
      }
    } catch (Exception e) {
      //TODO: handle exception
    }
    return data;
  }
}
