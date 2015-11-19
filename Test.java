import java.util.*;

//   Finite State Machine Model
//  (head parsing)                 // 1
//  (Var and Clause parsing)       // 2
//  (variable state parsing)       // 3
//  (Calculate stage)              // 4
	 
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
	        String finalResult = "";  //to concatenate outputs for each test case and display it at the very end       
	        String expression = "";   //for taking in inputs from STDIN
	        String eof = "EOF";       //used for earlier stages of the program for testing
	         						  //for testing in Eclipse environment
	        int parserState = 1;      // Let's start from head parsing 
	        int[] varclause = new int[2];
	        int clauseNumber = 0;
	        int nameindex = 0;        //this is for getting the test case names 
	         
	        boolean[] booleanVariables = null;
	        boolean run = true;  	   //flag variable for ending the program in Ecliple environment
         
	        ArrayList<String> inputs = new ArrayList<String>();        //for problem header parsing
	        ArrayList<String> clauses = new ArrayList<String>();       //clauses and formulas
	        ArrayList<String[]> clauseFormula = new ArrayList<String[]>();
	         
	         //while (run) { 			    //if wanted to test the code in Eclipse uncomment this part
	        while (scanner.hasNextLine()) { //and comment this part
	        	switch (parserState) { // State machine switch
		            case 1: //header parsing	
		            	expression = scanner.nextLine();
		            	//if (expression.equals(eof)) {   //if wanted to try this in Ecliple
		            	//	run = false;				  //uncomment this section
		            	//}
		            	inputs.addAll(Arrays.asList(expression.split("\\s*,\\s*")));	                 
		            	clauseNumber = 0;   //reset for each test case
		            	clauses.clear();    
		                clauseFormula.clear();
		                varclause = new int[2];
		            	parserState = 2; 
		                break;
		                 
		            case 2: //# of var and clauses parsing	 
		            	expression = scanner.nextLine();
		            	String[] parts = expression.split(" ");  
		                varclause[0] = Integer.parseInt(parts[0]); //# of var
		                varclause[1] = Integer.parseInt(parts[1]); //# of clauses
		                booleanVariables = new boolean[varclause[0]]; // creating boolean variables
		                parserState = 3;
		                break;
		                 
		            case 3: //clauses parsing  	            	 
			            if (clauseNumber < varclause[1]) {  
			            	expression = scanner.nextLine();
				            clauseNumber++;
				            clauses.addAll(Arrays.asList(expression.split("\\s*,\\s*")));  
				            String[] temps = expression.split(" ");
				            clauseFormula.add(temps);
				        } else { 
			                parserState = 4;		               
			            } 
			            break;
		                 
		            case 4: //SAT Solver
		                try {
		                    //Satisfiablity exhaustive search
		                    boolean probe = false;
		                    while (!probe) {
		                        booleanVariables = updateVariable(0, booleanVariables);
		                        probe = formulaCalc(booleanVariables, parser(clauseFormula));
		                    }   
		                    //output parsing              
		                    if (probe == true) {
		                        finalResult += (inputs.get(nameindex++) + ": " + varclause[0] 
		                        		+ " Variable(s) " + varclause[1] + " Clause(s)" + "\n"
		                        		+ "Satisfiable" + "\n");
		                    	 
		                        for (boolean var: booleanVariables) {
		                            finalResult += (var + " ");
		                        }
		                        finalResult += "\n";
		                    }             
		                    parserState = 1;
		                    break;
		                } catch (ArrayIndexOutOfBoundsException e) { 		//while trying every configuration, 
		                    finalResult += (inputs.get(nameindex++) + ": "  //it reaches the end all the possible values, thus out of bound	
		                    		    + varclause[0] + " Variable(s) " + varclause[1] 
		                    		    + " Clause(s)" + "\n" + "Unsatisfiable" + "\n");
		                    parserState = 1;
		                    break;
		                }
		                 
		            default:
		                System.out.println("DEFAULT: Leaving the program");
		                break;
		            }                 
	        }  
	        finalResult += (inputs.get(nameindex++) + ": " + varclause[0]  //parsing the very last output
            	 + " Variable(s) " + varclause[1] + " Clause(s)" + "\n"
            	 + "Satisfiable" + "\n");
	        scanner.close();
	        System.out.println(finalResult);
	    }	    
}
	
