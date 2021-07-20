import java.io.*;
import java.util.*;
import java.nio.file.*;
import java.nio.charset.*;
import java.util.Random;
import java.util.Scanner;

public class geneScore {
  static String str_in;
  static int[][] check;

  public static void main(String[] args){
    String fileName = "score.csv";
    String userId_bookId_score = "user_id,book_id,score";
    int N, M, E;
    String n, m, e;

    try{
      Scanner scan = new Scanner(System.in);
      System.out.println("ユーザーid範囲, 本id範囲, データ数(ex: 1 1 1)");

      //ユーザー数
      N = scan.nextInt();
      //本の数
      M = scan.nextInt();
      //出力するデータ数
      E = scan.nextInt();

      n = Integer.toString(N);
      m = Integer.toString(M);
      e = Integer.toString(E);

      //ユーザーが再度同じ本を評価しないようcheck
      check = new int[N+M][N+M];
      for(int i=0; i<N+M; i++){
        for(int j=0; j<N+M; j++){
          check[i][j] = 0;
        }
      }

      List<String> lines = new ArrayList<String>();
      lines.add(userId_bookId_score);
      rand(N,M,E,lines);

      //csvファイル生成
      Path file = Paths.get(fileName);
      Files.write(file, lines, StandardCharsets.UTF_8);
    }catch(IOException ioe){
      System.out.println("An error occurred");
      ioe.printStackTrace();
    }
  }

  //ランダムな値の生成
  static void rand(int N, int M, int E, List<String> lines){
    Random rand = new Random();
    int user_id;
    int book_id;
    double score;
    String str_score;
    int count = 0;

    while(count < E){
      //ユーザーをランダムに選択
      user_id = rand.nextInt(N)+1;
      //本をランダムに選択
      book_id = rand.nextInt(M)+1;
      //まだユーサーがその本を評価していない場合,スコアを決定
      if(check[user_id][book_id] == 0){
        score = rand.nextDouble()*5;
        str_score = Double.toString(Math.floor(score*10)/10);
        str_in = Integer.toString(user_id) + "," + Integer.toString(book_id) + "," + str_score;
        lines.add(str_in);
        check[user_id][book_id] = 1;
        count++;
      }

    }
  }
}
