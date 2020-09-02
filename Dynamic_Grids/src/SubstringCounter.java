/**
 * Class that implements the interface Combiner and defines a specific type of operation executed on 
 * two operands by the combine method of such interface.
 * 
 * @author Sergio Delgado
 * @version 1.0
 *
 */
public class SubstringCounter implements Combiner<String, String, Integer>
{
	/**
	 * Counts how many times operand2 occurs in operand1 as a substring. Returns the count.
	 * O(NM) where N is the length of operand1 and M is the length of operand2.
	 * @param operand1 String to be traversed in order to find occurrences of a substring.
	 * @param operand2 Substring whose occurrences on operand1 will be counted.
	 * @return int value. Number of times that operand2 was found in operand1.
	 */
	public Integer combine(String operand1, String operand2)
	{
		int result = 0;
		
		// Traversing operand1 one character at a time.
		for (int i=0; i<operand1.length(); i++)
		{
			if(operand1.startsWith(operand2,i))
				result++;
		}
		
		return result;
	}

	
	/**
	 * Main method that executes a test on the performance of the class SubstringCounter.
	 * @param args Command line arguments, which are not necessary for this class.
	 */
	public static void main(String[] args){
		SubstringCounter sc = new SubstringCounter();
		if (sc.combine("abab","ab") == 2 && sc.combine("aa","aab") == 0
			&& sc.combine("23232","232") == 2 
			&& sc.combine("helloabchelloddefzdfjhello","hello")==3) {
			System.out.println("Yay 1");
		}

	}
}