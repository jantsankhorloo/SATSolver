import java.util.*;
	 
	 //  (initial state)                // 0
	 //  (head parsing)                 // 1
	 //  (Var and Clause parsing)       // 2
	 //  (variable state parsing)       // 3
	 //  (Exception or Error state)     // 4
	 //  (Calculate stage)              // 5
	 //  (finish)                       // 6
	 
public class Test {
	     
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
	     
	    //Formula Solver
	    public static boolean formulaCalc(boolean[] variables, int[][] klauses) {

	             boolean inner = false;
	             boolean outer = true;
	              
	             for (int k = 0; (k < klauses.length); k++) {
	                 for (int j = 0; j < klauses[k].length; j++) {
	                     if (klauses[k][j] > 0) {
	                         inner = (inner || variables[klauses[k][j] - 1]);
	                     } else {
	                         inner = (inner || (!variables[Math.abs(klauses[k][j]) - 1]));
	                     }
	                 }
	                 outer = (outer && inner);
	                 inner = false;         
	             }
	             return outer;                  
	     }
	     
	    //RECURSIVE METHOD HERE
	    //This is where I exhaustively
	    //search for different boolean arrangements
	    public static boolean[] updateVariable(int index, boolean[] inputVar) {
	        if (inputVar[index] == false) {
	            inputVar[index] = true;
	        } else if (inputVar[index] == true) {
	            inputVar[index] = false;
	            updateVariable(index + 1, inputVar);
	        }
	        return inputVar;
	    }
	   
	    //Main Method
	    public static void main(String[] args) { 	         
	         Scanner scanner = new Scanner(System.in);	         
	         
	         String finalResult = "";         
	         String expression = "";
	         String eof = "EOF";      
	         
	         int parserState = 1; // Let's start from head parsing 
	         int[] varclause = new int[2];
	         int clauseNumber = 0;
	         int nameindex = 0;
	         
	         boolean[] booleanVariables = null;
         
	         ArrayList<String> inputs = new ArrayList<String>();        //for problem header parsing
	         ArrayList<String> clauses = new ArrayList<String>();       //clauses and formulas
	         ArrayList<String[]> clauseFormula = new ArrayList<String[]>();
	         
	         if (!(expression = scanner.nextLine()).equals(eof)) {
	             switch (parserState) { // State machine switch
	             case 0:                // Initial state
	                 System.out.println("Waiting");
	                 break;
	                 
	             case 1: //header parsing	                	 	           	                 
	            	 inputs.addAll(Arrays.asList(expression.split("\\s*,\\s*")));	                 
	            	 clauseNumber = 0;
	            	 clauses.clear();
	                 clauseFormula.clear();
	                 varclause = new int[2];
	            	 parserState = 2; 
	                 break;
	                 
	             case 2: //# of var and clauses parsing	      
	            	 String[] parts = expression.split(" ");  
	                 varclause[0] = Integer.parseInt(parts[0]); //# of var
	                 varclause[1] = Integer.parseInt(parts[1]); //# of clauses
	                 //System.out.println(varclause[0]);
	                 //System.out.println(varclause[1]);
	                 booleanVariables = new boolean[varclause[0]]; // creating boolean variables
	                 parserState = 3;
	                 break;
	                 
	             case 3: //clauses parsing           	 
		            	 if (clauseNumber < varclause[1]) {    
			            	 clauseNumber++;
			            	 //System.out.println("clauseNumber: " + clauseNumber + " varclause: " + varclause[1]);
			                 clauses.addAll(Arrays.asList(expression.split("\\s*,\\s*")));  
			                 String[] temps = expression.split(" ");
			                 //System.out.println(Arrays.toString(temps));
			                 clauseFormula.add(temps);
			             } else { 
		                	 parserState = 5;		               
		                 } //Clause collecting done 
		            	 break;
	                 
	             case 5: //SAT Solver
	                 try {
	                     boolean probe = false;
	                     while (!probe) {
	                         booleanVariables = updateVariable(0, booleanVariables);
	                         probe = formulaCalc(booleanVariables, parser(clauseFormula));
	                     }                 
	                     if (probe == true) {
	                         finalResult += (inputs.get(nameindex++) + ": " + varclause[0] 
	                        		 + " Variable(s) " + varclause[1] + " Clause(s)" + "\n"
	                        		 + "Satisfiable" + "\n");
	                    	 
	                         for (boolean var: booleanVariables) {
	                             finalResult += (var + " ");
	                         }
	                         finalResult += "\n";
	                         //System.out.println(finalResult);
	                     }             
	                     parserState = 1;
	                     break;
	                 } catch (ArrayIndexOutOfBoundsException e) {
	                     finalResult += (inputs.get(nameindex++) + ": " + varclause[0] 
	                    		     + " Variable(s) " + varclause[1] 
	                    		     + " Clause(s)" + "\n" + "Unsatisfiable" + "\n");
	                     //System.out.println(finalResult);
	                     parserState = 1;
	                     break;
	                 }
	                 
	             default:
	                 System.out.println("DEFAULT: Leaving the program");
	                 break;
	             }                 
	         }  
	         scanner.close();
	         System.out.println(finalResult);
	    }	    
}
	
