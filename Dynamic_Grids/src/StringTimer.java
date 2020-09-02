/**
 * Class that implements the interface Combiner and defines a specific type of operation executed on 
 * two operands by the combine method of such interface.
 * 
 * @author Sergio Delgado
 * @version 1.0
 *
 */
public class StringTimer implements Combiner<String, Integer, String>
{
	
	/**
	 * Returns a string as a repetition of the original string operand1 the number of repeats is
	 * specified by integer operand2. Returns empty string if operand2 is 0 or negative.
	 * O(NL) where N is the value of operand2 and L is the length of operand1
	 * @param operand1 String to be repeated a number of times into a new string.
	 * @param operand2 Number of times that operand1 will be repeated.
	 * @return String value which is the result of repeating operand1 a number of times (operand2).
	 */
	public String combine(String operand1, Integer operand2)
	{
		
		String result = "";
		
		for (int i=0; i<operand2; i++)
			result = result.concat(operand1);
		
		return result;	
	}
	

	/**
	 * Main method that executes a test on the performance of the class StringTimer.
	 * @param args Command line arguments, which are not necessary for this class.
	 */
	public static void main(String[] args){
		StringTimer st = new StringTimer();
		if (st.combine("a",1).equals("a") && st.combine("ab",3).equals("ababab")
			&& st.combine("abc",-1).equals("")) {
			System.out.println("Yay 1");
		}

	}
}
