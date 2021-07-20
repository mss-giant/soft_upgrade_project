import java.io.*;
import java.util.*;
import java.nio.file.*;
import java.nio.charset.*;
import java.util.Random;
import java.util.Scanner;

public class geneLink {
  static String str_in;
  static int[][] check;

  public static void main(String[] args) {
    String fileName = "link.csv";
    String source_target = "source,target";
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
      lines.add(source_target);
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
    int sourse;
    int target;
    int count = 0;
    while(count < N){
      sourse = rand.nextInt(N)+1;
      target = rand.nextInt(N)+1;
      if(check[sourse][target] == 0){
        str_in = Integer.toString(sourse) + "," + Integer.toString(target);
        lines.add(str_in);
        check[sourse][target] = 1;
        count++;
      }
    }
  }
}
