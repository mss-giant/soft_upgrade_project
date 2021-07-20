import java.io.*;
import java.util.*;
import java.nio.file.*;
import java.nio.charset.*;
import java.util.Random;
import java.util.Scanner;

public class geneFriend {
  static String str_in;
  static int[][] check;

  public static void main(String[] args) {
    String fileName = "friend.csv";
    String userA_userB = "user_a,user_b";
    int N;
    String n;

    try{
      Scanner scan = new Scanner(System.in);
      System.out.println("Please input value (ex: 1)");

      N = scan.nextInt();
      n = Integer.toString(N);

      check = new int[N+1][N+1];
      for(int i=0; i<=N; i++){
        for(int j=0; j<=N; j++){
          check[i][j] = 0;
        }
      }

      List<String> lines = new ArrayList<String>();
      lines.add(userA_userB);
      rand(N, lines);

      Path file = Paths.get(fileName);
      Files.write(file, lines, StandardCharsets.UTF_8);
    }catch(IOException e){
      System.out.println("An error occurred.");
      e.printStackTrace();
    }
  }

  static void rand(int N, List<String> lines){
    Random rand = new Random();
    int user_a;
    int user_b;
    int count = 0;
    while(count < N){
      user_a = rand.nextInt(N)+1;
      user_b = rand.nextInt(N/2)+1*2;
      if(check[user_a][user_b] == 0 && user_a != user_b){
        str_in = Integer.toString(user_a) + "," + Integer.toString(user_b);
        lines.add(str_in);
        check[user_a][user_b] = 1;
        count++;
      }
    }
  }
}
