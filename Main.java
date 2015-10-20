import java.util.*;
 
 //  (initial state)                // 0
 //  (head parsing)                 // 1
 //  (Var and Clause parsing)       // 2
 //  (variable state parsing)       // 3
 //  (Exception or Error state)     // 4
 //  (Calculate stage)              // 5
 //  (finish)                       // 6
 

 public class Main {
     
     //this takes argument from arraylist, parses them into 2D array of Int
     public static int[][] parser(ArrayList<String[]> argument) {        
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
     
     //SAT Solver
     public static boolean formulaCalc(boolean[] variables, int[][] klauses) {

             boolean inner = false;
             boolean outer = true;
              
             for (int k = 0; (k < klauses.length) ; k++) {
                 for (int j = 0 ; j < klauses[k].length; j++) {
                     if (klauses[k][j] > 0) {
                         inner = (inner || variables[klauses[k][j]-1]);
                     } else {
                         inner = (inner || (!variables[Math.abs(klauses[k][j])-1]));
                     }
                 }
                 outer = (outer && inner);
                 inner = false;         
             }
             return outer;                  
     }
     
     //Recursive Method
     public static boolean[] updateVariable(int ind, boolean[] inputVar) {

             if (inputVar[ind] == false) {
                 inputVar[ind] = true;
             } else if (inputVar[ind] == true) {
                 inputVar[ind] = false;
                 updateVariable(ind + 1, inputVar);
             }
             return inputVar;
     }
   
     public static void main(String[] args) { 
         
         Scanner scanner = new Scanner(System.in);
         
         String expression;
         String eof = "EOF";      
         int parserState = 1; // Let's start from head parsing 
         int[] varclause = new int[2];
         int clauseNumber = 0;
         boolean[] booleanVariables = null;
         
         ArrayList<String> inputs = new ArrayList<String>();        //for problem header parsing
         ArrayList<String> varClauses = new ArrayList<String>();    //for #of var and clause
         ArrayList<String> clauses = new ArrayList<String>();       //clauses and formulas
         ArrayList<String[]> clauseFormula = new ArrayList<String[]>();
         
         while ((!(expression = scanner.nextLine()).equals(eof)) && (parserState != 6)) {
             
             switch (parserState) { // State machine switch
             case 0:                // Initial state
                 System.out.println("Waiting");
                 break;
                 
             case 1: //header parsing
                 if (expression.regionMatches(0, "Test", 0, "Test".length()) 
                         || expression.regionMatches(0, "Problem", 0, "Problem".length())) {
                     inputs.addAll(Arrays.asList(expression.split("\\s*,\\s*")));
                 }
                 parserState = 2; 
                 break;
                 
             case 2: //# of var and clauses parsing
                 varClauses.addAll(Arrays.asList(expression.split("\\s*,\\s*")));
                 String[] parts = expression.split(" ");                
                 varclause[0] = Integer.parseInt(parts[0]); //# of var
                 varclause[1] = Integer.parseInt(parts[1]); //# of clauses
                 booleanVariables = new boolean[varclause[0]]; // creating boolean variables
                 parserState = 3;
                 break;
                 
             case 3: //clauses parsing
                 if (clauseNumber < varclause[1]) {
                     clauseNumber++;
                     clauses.addAll(Arrays.asList(expression.split("\\s*,\\s*")));
                     
                     clauseFormula.add(expression.split(" "));

                 } else { parserState = 5;} //Clause collecting done                
                 break; 
                 
             case 4: //One of the input format error state.
                 parserState = 6;
                 break;
                 
             case 5: //SAT Solver
                 try {
                     boolean probe = false;
                     while (!probe) {
                         booleanVariables = updateVariable(0, booleanVariables);
                         probe = formulaCalc(booleanVariables, parser(clauseFormula));
                     }                 
                     if (probe == true) {
                         System.out.print(inputs.get(0) + ": ");
                         System.out.print(varclause[0]);
                         System.out.print(" Variable(s) ");
                         System.out.print(varclause[1]);
                         System.out.println(" Clause(s)");
                         System.out.println("Satisfiable");
                         for (boolean var: booleanVariables) {
                             System.out.print(var);
                             System.out.print(" ");
                         }
                         System.out.println();
                     }             
                     //System.out.println(probe);                
                     //System.out.println(Arrays.toString(booleanVariables)); 
                     parserState = 6;
                     break;
                 } catch (ArrayIndexOutOfBoundsException e) {
                     System.out.print(inputs.get(0) + ": ");
                     System.out.print(varclause[0]);
                     System.out.print(" Variable(s) ");
                     System.out.print(varclause[1]);
                     System.out.println(" Clause(s)");
                     System.out.println("Unsatisfiable");
                 }
             case 6: 
                 System.out.println("Leaving the program"); 
                 break;
             default:
                 System.out.println("DEFAULT : Leaving the program");
                 break;
             }                 
         }  
         scanner.close();
    }
}