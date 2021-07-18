package src.Command_tool;



import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.regex.Pattern;

public class Command_Scanner {
    private boolean display_user_info_call;
    private boolean display_book_info_call;

    private boolean rec_user_call;
    private boolean rec_user_book_call;
    private boolean rec_book_call;

    private boolean add_user_call;
    private boolean add_user_book_score_call;
    private boolean add_user_friend_call;
    private boolean add_book_call;
    private boolean add_book_word_call;

    private boolean update_user_name_call;
    private boolean update_user_book_score_call;    //eval
    private boolean update_book_name_call;

    private boolean delete_user_call;
    private boolean delete_book_call;

    private boolean search_mode_call;
    private boolean exit_call;
    private boolean command_found;


    private Map<String, String> command_input_data = new HashMap<String, String>();
    private ArrayList<String> input_words_list = new ArrayList<String>();


    public Command_Scanner() {

    }

    public void scan_command() {
        reset_all_call();
        System.out.println("please input command and option");
        System.out.println("**if you don't know command and option, you can input help command.**");
        Scanner input = new Scanner(System.in);
        String inputed_command = input.nextLine();
        String[] input_command_split = inputed_command.split("\\s+");
        // System.out.println(input_command_split[0]);
        if (input_command_split[0].equals("display")) {
            command_found = true;
            display_command_controller(input_command_split);
        }
        if (input_command_split[0].equals("rec")) {
            command_found = true;
            rec_command_controller(input_command_split);
        }
        if (input_command_split[0].equals("add")) {
            command_found = true;
            add_command_controller(input_command_split);
        }
        if (input_command_split[0].equals("update")) {
            command_found = true;
            update_command_controller(input_command_split);
        }
        if (input_command_split[0].equals("delete")) {
            command_found = true;
            delete_command_controller(input_command_split);
        }
        if (input_command_split[0].equals("search")) {
            command_found = true;
            search_mode_controller(input_command_split);
        }
        if (input_command_split[0].equals("help")) {
            command_found = true;
            disp_help_command_list();
        }
        if (input_command_split[0].equals("exit")) {
            command_found = true;
            this.exit_call = true;
        }
        if (!command_found) {
            System.out.println("error : not found command");
        }
    }

    public void reset_all_call() {
        display_user_info_call = false;
        display_book_info_call = false;

        rec_user_call = false;
        rec_user_book_call = false;
        rec_book_call = false;

        add_user_call = false;
        add_user_book_score_call = false;
        add_user_friend_call=false;
        add_book_call = false;
        add_book_word_call = false;

        update_user_name_call = false;
        update_user_book_score_call = false;
        update_book_name_call = false;

        delete_user_call = false;
        delete_book_call = false;
        
        search_mode_call = false;
        exit_call = false;
        command_found = false;
        command_input_data.clear();
    }



    // display command
    public void display_command_controller(String[] command) {
        if(command.length==3 && command[1].equals("-u")){
            if(command[2].matches("^[0-9]+$")){
                this.display_user_info_call=true;
                command_input_data.put("user_id", command[2]);
            }
            else{
                System.out.println("error : please input integer");
            }
        }
        else if(command.length==3 && command[1].equals("-b")){
            if(command[2].matches("^[0-9]+$")){
                this.display_book_info_call = true;
                command_input_data.put("book_id", command[2]);
            }
            else{
                System.out.println("error : please input integer");
            }
        }
        else{
            System.out.println("error : sytax error or not found option");
        }
    }

    public void rec_command_controller(String[] command) {
        if(command.length==3 && command[1].equals("-u")){
            if(command[2].matches("^[0-9]+$")){
                this.rec_user_call=true;
                command_input_data.put("user_id", command[2]);
            }
            else{
                System.out.println("error : please input integer");
            }
        }
        else if(command.length==5 && command[1].equals("-u") && command[3].equals("-b")){
            if(command[2].matches("^[0-9]+$") && command[4].matches("^[0-9]+$")){
                this.rec_user_book_call=true;
                command_input_data.put("user_id", command[2]);
                command_input_data.put("book_id", command[4]);  
            }
            else{
                System.out.println("error : please input integer");
            }
            
        }
        else if(command.length==2 && command[1].equals("-b")){
            this.rec_book_call = true;
        }
        else{
            System.out.println("error : sytax error or not found option");
        }
    }

    public void add_command_controller(String[] command) {
        if(command.length==3 && command[1].equals("-u")){
            if(command[2].matches("^[A-Za-z0-9]+$")){
                this.add_user_call = true;
                command_input_data.put("user_name", command[2]);
                System.out.println("Created!"+" User name is : "+command[2]);
            }
            else{
                System.out.println("error : please input alphabet of number");
            }
        }
        else if(command.length==7 && command[1].equals("-u") && command[3].equals("-b") && command[5].equals("-s")){
            if(command[2].matches("^[0-9]+$") && command[4].matches("^[0-9]+$") && command[6].matches("^-?(0|[1-9]\\d*)(\\.\\d+|)$")){
                this.add_user_book_score_call = true;
                command_input_data.put("user_id", command[2]);
                command_input_data.put("book_id", command[4]);
                command_input_data.put("score", command[6]);
                System.out.println("add new score  user_id : " + command[2] + "  book_id : "+command[4]+" score : "+command[6]);
            }
            else{
                System.out.println("error : please input correct value");
            }
        }
        else if(command.length==5 && command[1].equals("-u") && command[3].equals("-f")){
            if(command[2].matches("^[0-9]+$") && command[4].matches("^[0-9]+$")){
                this.add_user_friend_call = true;
                command_input_data.put("user_id", command[2]);
                command_input_data.put("user_id", command[4]);
            }
        }
        else if (command.length==4 && command[1].equals("-b")){
            if(command[3].matches("^[A-Za-z0-9]+(.html)$")){
                this.add_book_call=true;
                command_input_data.put("book_name", command[2]);
                command_input_data.put("url", command[3]);
                System.out.println("Created!"+" Book name is : "+command[2]);
                System.out.println("Book url is : "+command[3]);
            }
            else{
                System.out.println("error : please input correct URL");
            }
        }
        else if(command.length==5 && command[1].equals("-b") && command[3].equals("-w")){
            if(command[2].matches("^[0-9]+$") && command[4].matches("^[0-9]+$")){
                this.add_book_word_call=true;
                command_input_data.put("book_id", command[2]);
                command_input_data.put("word_id", command[4]);
            }
            else{
                System.out.println("error : please input integer");
            }
        }
        else{
            System.out.println("syntax error or not found option");
        }
    }

    public void update_command_controller(String[] command) {
        if(command.length==4  &&  command[1].equals("-u")){
            if(command[2].matches("^[0-9]+$") && command[3].matches("^[A-Za-z0-9]+$")){
                this.update_user_name_call=true;
                command_input_data.put("user_id", command[2]);
                command_input_data.put("user_name", command[3]);
                System.out.println("updated"+" New User name is : "+command[3]);
            }
            else{
                System.out.println("error : please input integer and alphabet or number");
            }
        }
        else if(command.length==7 && command[1].equals("-u") && command[3].equals("-b") && command[5].equals("-s")){
            if(command[2].matches("^[0-9]+$") && command[4].matches("^[0-9]+$") && command[6].matches("^-?(0|[1-9]\\d*)(\\.\\d+|)$")){
                this.update_user_book_score_call =true;
                command_input_data.put("user_id", command[2]);
                command_input_data.put("book_id", command[4]);
                command_input_data.put("score", command[6]);
                System.out.println("updated"+" New score is : "+command[6]+" book id is "+command[4]);
            }
            else{
                System.out.println("error : please input integer and integer and double");
            }
        }
        else if(command.length==4 && command[1].equals("-b")){
            if(command[2].matches("^[0-9]+$") && command[3].matches("^[A-Za-z0-9]+$")){
                this.update_book_name_call = true;
                command_input_data.put("book_id", command[2]);
                command_input_data.put("book_name", command[3]);
                System.out.println("updated"+" New Book name is : "+command[3]+ " book id is "+command[2]);
            }
            else{
                System.out.println("error : please input integer and alphabet or number");
            }
        }
        else{
            System.out.println("syntax error or not found option");
        }
    }

    public void delete_command_controller(String[] command) {
        if(command.length==3 && command[1].equals("-u")){
            if(command[2].matches("^[0-9]+$")){
                this.delete_user_call = true;
                command_input_data.put("user_id", command[2]);
            }
            else{
                System.out.println("error : please input integer");
            }
        }
        else if(command.length==3 && command[1].equals("-b")){
            if(command[2].matches("^[0-9]+$")){
                this.delete_book_call = true;
                command_input_data.put("book_id", command[2]);
            }
            else{
                System.out.println("error : please input integer");
            }
        }
        else{
            System.out.println("syntax error or not found option");
        }
    }

    public void search_mode_controller(String[] command) {
        this.search_mode_call = true;
        System.out.println("please input search word");
        Scanner scanwords = new Scanner(System.in);
        String[] input_words = scanwords.nextLine().split("\\s+");
        Set<String> word_set = new HashSet<String>();
        word_set.addAll(Arrays.asList(input_words));
        input_words_list.addAll(word_set);
    }



    public boolean get_display_user_info_call() {
        return this.display_user_info_call;
    }

    public boolean get_display_book_info_call() {
        return this.display_book_info_call;
    }






    public boolean get_rec_user_call(){
        return this.rec_user_call;
    }

    public boolean get_rec_user_book_call(){
        return this.rec_user_book_call;
    }

    public boolean get_rec_book_call(){
        return this.rec_book_call;
    }


    // add command
    public boolean get_add_user_call() {
        return this.add_user_call;
    }

    public boolean get_add_user_book_score_call(){
        return this.add_user_book_score_call;
    }

    public boolean get_add_user_friend_call(){
        return this.add_user_friend_call;
    }

    public boolean get_add_book_call() {
        return this.add_book_call;
    }

    public boolean get_add_book_word_call(){
        return this.add_book_word_call;
    }


    // update command
    public boolean get_update_user_name_call() {
        return this.update_user_name_call;
    }

    public boolean get_update_user_book_score_call(){
        return this.update_user_book_score_call;
    }

    public boolean get_update_book_name_call(){
        return this.update_book_name_call;
    }


    // delete command
    public boolean get_delete_user_call() {
        return this.delete_user_call;
    }

    public boolean get_delete_book_call(){
        return this.delete_book_call;
    }

    public boolean get_search_mode_call(){
        return this.search_mode_call;
    }

    
    // exit command
    public boolean get_exit_call() {
        return this.exit_call;
    }

    public Map<String, String> get_command_data() {
        return this.command_input_data;
    }

    public ArrayList<String> get_input_words(){
        return this.input_words_list;
    }


    public void disp_help_command_list(){
        System.out.println("********************command list********************");
        System.out.printf("%-50s"," display -u [user_id]");
        System.out.print(" : ");
        System.out.println("display user information");
        System.out.printf("%-50s"," display -b [book_id]");
        System.out.print(" : ");
        System.out.println("display book book information");
        System.out.printf("%-50s"," rec -u [user_id]");
        System.out.print(" : ");
        System.out.println("display recommendation score and all book for user");
        System.out.printf("%-50s"," rec -u [user_id] -b [book_id]");
        System.out.print(" : ");
        System.out.println("display recommendation score for user");
        System.out.printf("%-50s"," rec -b");
        System.out.print(" : ");
        System.out.println("display recommendation book");
        System.out.printf("%-50s"," add -u [user_name]");
        System.out.print(" : ");
        System.out.println("add user");
        System.out.printf("%-50s"," add -u [user_id] -b [book_id] -s [new_score]");
        System.out.print(" : ");
        System.out.println("add user book score (old command eval)");
        System.out.printf("%-50s"," add -u [user_id] -f [user_id]");
        System.out.print(" : ");
        System.out.println("add user friend");
        System.out.printf("%-50s"," add -b [book_name] [book_url]");
        System.out.print(" : ");
        System.out.println("add book");
        System.out.printf("%-50s"," add -b [book_id] -w [word_id]");
        System.out.print(" : ");
        System.out.println("add book word");
        System.out.printf("%-50s"," update -u [user_id] [new_user_name]");
        System.out.print(" : ");
        System.out.println("update user name");
        System.out.printf("%-50s"," update -u [user_id] -b [book_id] -s [new_score]");
        System.out.print(" : ");
        System.out.println("update user book score (old command name is \"eval\")");
        System.out.printf("%-50s"," update -b [book_id] [new_book_name]");
        System.out.print(" : ");
        System.out.println("update book name");
        System.out.printf("%-50s"," delete -u [user_id]");
        System.out.print(" : ");
        System.out.println("delete user");
        System.out.printf("%-50s"," delete -b [book_id]");
        System.out.print(" : ");
        System.out.println("delete book");
        System.out.printf("%-50s"," search");
        System.out.print(" : ");
        System.out.println("input word and display recommendation book");
        System.out.printf("%-50s"," exit");
        System.out.print(" : ");
        System.out.println(" finish ");
    }

}
