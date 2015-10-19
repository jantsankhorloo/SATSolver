import java.io.*;
import java.util.*;
 
 //comment
 //There are two stages
 //test 1
 //var clause
 //varx vary
 //...
 //test 2
 //...
 //Question is, how will the parser know it's end of input?
 //-End Of File? EOF , for current terminal mockup it will be EOF chars
 
 /*
 Test 1
 5 3
 2 3
 -1 3
 5 2
 Test 2
 ...
 */
 //((parserState == 2) && !(expression.matches(".")))
 
 //State machine states
 // O (initial state)                // 0
 // A (head parsing)                 // 1
 // B (Var and Clause parsing)       // 2
 // C (variable state parsing)       // 3
 // E (Exception or Error state)     // 4
 // K (Calculate stage)              // 5
 // F (finish)                       // 6
 

 public class Main {
     
     public static int[][] parser(ArrayList<String[]> argument) { //this takes argument from arraylist
         int[][] result = new int[argument.size()][];       
         int j = 0;
         for (String[] arg: argument) {
             int[] temp = new int[arg.length];
             for (int i = 0; i < arg.length; i++) {              
                temp[i] = Integer.parseInt(arg[i]);
             }
             result[j] = temp;
             j++;
         }
         return result;      
     }
     
     public static boolean[][] booleanParser(int[][] argument) { //takes the return value from parser() method
         
         boolean[][] booleanResult = new boolean[argument.length][];
         
         for (int i = 0; i < argument.length; i++) {
             boolean[] temper = new boolean[argument[i].length];
             for (int j = 0; j < argument[i].length; j++) {
                 if (argument[i][j] < 0) {
                     temper[j] = false;
                 }
                 else { temper[j] = true;}               
             }
             booleanResult[i] = temper;
         }
         return booleanResult;       
     }
     
     public static boolean[] firstSolver(boolean[][] booleanArg) {//takes input from booleanParser
         
         boolean[] answer = new boolean[booleanArg.length];
         //boolean result = false;
         
         for (int i = 0; i < booleanArg.length; i++) {
             boolean temp = booleanArg[i][0];
             for (int j = 1; j < booleanArg[i].length; j++) {
                 temp = (temp || booleanArg[i][j]);
             }
             answer[i] = temp;
         }
         return answer;
     }
     
     public static boolean secondSolver(boolean[] booleanArguments) {
         
         boolean temp = booleanArguments[0];
         
         for (int i = 0; i < booleanArguments.length; i++) {
             temp = (temp && booleanArguments[i]);
         }
         return temp;
     }

     public static void main(String[] args) { 
         Scanner scanner = new Scanner(System.in);
         
         String expression;// = "";
         String finish = "eof"; //dot EOF
         String testNumber = "";
         
         int parserState = 1; // Let's start from head parsing 
         int testNumberTrack = 1;
         int i = 0;
         
         ArrayList<String> inputs = new ArrayList<String>(); 
         ArrayList<String> varClauses = new ArrayList<String>(); 
         //ArrayList<String> variables = new ArrayList<String>(); // saving variables
         ArrayList<String> clauses = new ArrayList<String>(); // saving clauses
         //String[2] varClaus;///= "0","0";//string.split("-")
         ArrayList<String> variableNumbers = new ArrayList<String>();
         ArrayList<String> clauseNumbers = new ArrayList<String>();
         
         //String[][] clauseFormula = new String[10][];
         ArrayList<String[]> clauseFormula = new ArrayList<String[]>();
         
         while ( (!(expression = scanner.nextLine()).equals(finish)) && (parserState != 6) ) {

             switch (parserState) { // State machine switch
             case 0://Initial state Does nothing much
                 System.out.println("Waiting");
                 break;
             case 1: 
                 if (expression.regionMatches(0, "Test", 0, "Test".length()) 
                         || expression.regionMatches(0, "Problem", 0, "Problem".length())) {
                     inputs.addAll(Arrays.asList(expression.split("\\s*,\\s*")));
                 }
                 System.out.println(inputs.toString());
                 parserState = 2; 
                 break;
             case 2: 
                 varClauses.addAll(Arrays.asList(expression.split("\\s*,\\s*")));
                 String[] parts = expression.split(" ");
                 int[] varclause = new int[2]; 
                 varclause[0] = Integer.parseInt(parts[0]); //# of var
                 varclause[1] = Integer.parseInt(parts[1]); //# of clauses
                 System.out.println(Arrays.toString(varclause));
                 parserState = 3;
                 break;
             case 3: 
                 clauses.addAll(Arrays.asList(expression.split("\\s*,\\s*")));
                 
                 System.out.println(clauses.toString());
                 
                 clauseFormula.add(expression.split(" "));
                 
                 //System.out.println(Arrays.toString((clauseFormula.get(i))));
                 i++;
                 System.out.println(Arrays.deepToString(parser(clauseFormula)));
                 System.out.println(Arrays.deepToString(booleanParser(parser(clauseFormula))));
                 System.out.println(Arrays.toString(firstSolver(booleanParser(parser(clauseFormula)))));
                 System.out.println(secondSolver(firstSolver(booleanParser(parser(clauseFormula)))));
                 break;
             case 4: 
             //One of the input format error state.
                 parserState = 6;
                 break;
             case 5: 
             //Calculate state;
                 break;
             case 6: 
                 System.out.println("Error occured leaving the program"); 
                 break;
             default:
                 System.out.println("DEFAULT : Error occured leaving the program");
                 break;
             }
                  
         }  
         scanner.close();
    }//end of static main
}//end of Main Class