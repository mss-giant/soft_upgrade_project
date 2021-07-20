import java.io.*;
import java.util.*;
import java.nio.file.*;
import java.nio.charset.*;
import java.util.Random;
import java.util.Scanner;
import java.util.Map;
import java.util.HashMap;

public class geneLocation {
  static String str_in;
  static int[][] check;

  public static void main(String[] args) {
    String fileName = "location.csv";
    String wordId_urlId = "word_id,url_id";
    int N;
    int U;
    String n;

    try {
      Scanner scan = new Scanner(System.in);
      System.out.println("Please input value (ex: 1)");
      System.out.println("input number of words and number of user id  like : 50 1");

      N = scan.nextInt();
      U = scan.nextInt();
      n = Integer.toString(N);

      check = new int[N + 1][N + 1];
      for (int i = 0; i <= N; i++) {
        for (int j = 0; j <= N; j++) {
          check[i][j] = 0;
        }
      }

      List<String> lines = new ArrayList<String>();
      lines.add(wordId_urlId);
      // rand(N, lines);
      rand2(N, U, lines);

      Path file = Paths.get(fileName);
      Files.write(file, lines, StandardCharsets.UTF_8);
    } catch (IOException e) {
      System.out.println("An error occurred.");
      e.printStackTrace();
    }
  }

  static void rand(int N, List<String> lines) {
    Random rand = new Random();
    int word_id;
    int url_id;
    int count = 0;
    while (count < N) {
      word_id = rand.nextInt(N) + 1;
      url_id = rand.nextInt(N) + 1;
      if (check[word_id][url_id] == 0) {
        str_in = Integer.toString(word_id) + "," + Integer.toString(url_id);
        lines.add(str_in);
        check[word_id][url_id] = 1;
        count++;
      }
    }
  }

  static void rand2(int word, int user, List<String> lines){
    Random ram_w = new Random();
    Random ram_u = new Random();
    Random num_data = new Random();
    //int data_num = num_data.nextInt(1000);
    int data_num = 10000;
    //int[][] same_check = new int[word*user][word*user];
    Map<Integer,ArrayList<Integer>> same_check2 = new HashMap<Integer,ArrayList<Integer>>();
    if(true){
      for(int i=0;i<data_num;i++){
        int word_id = ram_w.nextInt(word)+1;
        int user_id = ram_u.nextInt(user)+1;
        if(same_check2.containsKey(user_id)){
          if(!same_check2.get(user_id).contains(word_id)){
            String data_set = Integer.toString(word_id) +","+Integer.toString(user_id);
            same_check2.get(user_id).add(word_id);
            lines.add(data_set);
          }
        }
        else{
          ArrayList<Integer> d = new ArrayList<>();
          d.add(word_id);
          same_check2.put(user_id, d);
        }
      }
    }
    else{
      for(int i=0;i<100;i++){
        int word_id = ram_w.nextInt(word)+1;
        int user_id = ram_u.nextInt(user)+1;
        if(same_check2.containsKey(user_id)){
          if(!same_check2.get(user_id).contains(word_id)){
            String data_set = Integer.toString(word_id) +","+Integer.toString(user_id);
            lines.add(data_set);
          }
        }
        else{
          ArrayList<Integer> d = new ArrayList<>();
          d.add(word_id);
          same_check2.put(user_id, d);
        }
        
      }
    }


  }
}
