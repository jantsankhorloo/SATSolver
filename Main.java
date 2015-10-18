import java.io.*;
import java.util.*;

public class Main {
    
    public int[][] parser() {
        return null;
        
    }
    static boolean isInt(String s) {
        try { 
            Integer.parseInt(s); 
            return true; 
        } catch(NumberFormatException er) { 
            return false; 
        }
    }
    
    public static void main(String[] args) { 
        Scanner scanner = new Scanner(System.in);
        
        String expression = "";
        String finish = "done";
        
        ArrayList<String> inputs = new ArrayList<String>();
        
        while (!(expression = scanner.nextLine()).equals(finish)) {
            System.out.println(expression); 
            
            inputs.add(expression);
        }  
        System.out.println(inputs);
        
        String[][] array = new String[10][1];
        System.out.println(array);
        int i = 0;
        for (String input: inputs) {
            System.out.println(input);
            array[i][0] = input;
            i++;
            System.out.println(array[i][0]);
            
            if (!isInt(input)) {
                //System.out.print(input);
                //System.out.print(":");
            }
            
        }
        System.out.println(array);
        
    }
    
}