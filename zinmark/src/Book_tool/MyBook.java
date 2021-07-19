package src.Book_tool;

import java.util.ArrayList;
import java.util.Comparator;

public class MyBook implements Comparable<MyBook>{
    //new new new new new new new 
    private int book_id;                //bookid
    private String my_page;
    private String book_name;
    //private int number_reference_book;

    private double book_score;
    private double book_score_0_1;

    private double word_score;
    private double word_score_0_1;

    private ArrayList<MyBook> reference_book; 
    private ArrayList<MyBook> referenced_to_me;
    private ArrayList<String> word_list;


    public MyBook(int id, String book_name,String page_name){
        this.book_id = id;
        this.book_name = book_name;
        this.my_page = page_name;
        this.book_score = 1.0;
        this.book_score_0_1 = 0.0;
        this.word_score = 0.0;
        this.word_score_0_1 = 0.0;
        this.reference_book = new ArrayList<MyBook>();
        this.referenced_to_me = new ArrayList<MyBook>();
        this.word_list = new ArrayList<String>();
    }    


    //new new new
    public int get_book_id(){
        return this.book_id;
    }

    public String get_book_name(){
        return this.book_name;
    }

    public void replace_book_name(String book_name){
        this.book_name = book_name;
    }

    public String get_my_page(){
        return this.my_page;
    }

    public void replace_my_page(String my_page){
        this.my_page = my_page;
    }

    public int get_num_reference(){
        return this.reference_book.size();
    }


    public void set_book_score(double book_score){
        this.book_score = book_score;
    }

    public double get_book_score(){
        return this.book_score;
    }


    public void set_word_score(double word_score){
        this.word_score = word_score;
    }

    public double get_word_score(){
        return this.word_score;
    }

    //calculate from 0 to 1, book_score and word_score

    public void set_book_score_0_1(double book_score_0_1){
        this.book_score_0_1 = book_score_0_1;
    }

    public double get_book_score_0_1(){
        return this.book_score_0_1;
    }

    public void set_word_score_0_1(double word_score_0_1){
        this.word_score_0_1 = word_score_0_1;
    }

    public double get_word_score_0_1(){
        return this.word_score_0_1;
    }

    public double get_all_score(){
        return this.book_score_0_1 + this.word_score_0_1;
    }


    // ArrayList

    public void set_reference_book(MyBook refer_book){
        if(!this.reference_book.contains(refer_book)){
            this.reference_book.add(refer_book);
        }
    }

    public ArrayList<MyBook> get_reference_book(){
        return this.reference_book;
    }


    public void set_referenced_to_me(MyBook refer_to_me){
        if(!this.referenced_to_me.contains(refer_to_me)){
            this.referenced_to_me.add(refer_to_me);
        }
    }

    public ArrayList<MyBook> get_refernced_to_me(){
        return this.referenced_to_me;
    }


    public void set_word(String word){
        if(!this.word_list.contains(word)){
            this.word_list.add(word);
        }
    }

    public boolean check_has_word(String word){
        return word_list.contains(word);
    }

    public ArrayList<String> get_book_has_word_list(){
        return this.word_list;
    }

    @Override
    public int compareTo(MyBook other_book){
        if(this.get_all_score() > other_book.get_all_score()){
            return -1;
        }
        else if(this.get_all_score() < other_book.get_all_score()){
            return 1;
        }
        else{
            return 0;
        }
    }


    public void disp_book_information(){
        System.out.println();
        System.out.println("+---------------------------------------------------------------------+");
        System.out.println("Book ID : "+this.book_id+"     Book Name : "+this.book_name+"     Book url : "+this.my_page);
        //System.out.println(".......................................................................");
        System.out.println("Reference lists");
        for(MyBook ref_book : reference_book){
            System.out.println("Book ID : "+ref_book.book_id+"     Book Name : "+ref_book.book_name+"     Book url : "+ref_book.my_page);
        }
        //System.out.println(".......................................................................");
        System.out.println("word list");
        for(String word : word_list){
            System.out.print(" "+word);
        }
        System.out.println();
        System.out.println("+---------------------------------------------------------------------+");
    }

    public void remove_reference_reference_to_me(MyBook remove_book){
        if(reference_book.contains(remove_book)){
            this.reference_book.remove(remove_book);
        }
        if(referenced_to_me.contains(remove_book)){
            this.referenced_to_me.remove(remove_book);
        }
    }

}
