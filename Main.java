import java.io.*;
import java.util.*;

public class Main {
    
    public int[][] parser() {
        return null;
        
    }
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        String expression = "";
        String finish = "done";
        
        
        ArrayList<String> inputs = new ArrayList<String>();
        
        ArrayList<String> clauses = new ArrayList<String>();
        
        
        while (!(expression = scanner.nextLine()).equals(finish)) {
            System.out.println(expression);
            
            for (int i = 0; i < expression.length(); i++) {
                
                if (Character.isAlphabetic(expression.charAt(i))) {
                    inputs.add(expression);
                    break;
                }
                if (Character.isDigit(expression.charAt(i))) {
                    clauses.add(expression);
                    break;
                }
            }
            
            
            //inputs.add(expression);
        }
        System.out.println(inputs);
        System.out.println(clauses);
        
        ArrayList<String> clausesSplit = new ArrayList<String>();
        
        String[][] splitt = new String[4][1];
        int i = 0;
        for (String clause: clauses) {
            splitt[i] = clause.split("\\s+");
            System.out.println(splitt);
            i++;
        }
        
        
        
        
        scanner.close();
        
    }
    
}
