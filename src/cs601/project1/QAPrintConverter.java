package cs601.project1;

import java.util.Arrays;

/**The {@code QAPrintConverter} class provides static methods for formatted output  
 * QA objects fields in table view. 
 * <p>
 *
 */
public class QAPrintConverter {
	private static WrapperList out1 = new WrapperList();
	private static WrapperList out2 = new WrapperList();
	private static WrapperList out3 = new WrapperList();
	private static WrapperList out4 = new WrapperList();

	private static int lenFieldASIN = 12;
	private static int lenQuestion = 30;
	private static int lenAnswer = 30;
	private static int lenAnswerTime = 8;
	
	public static void qaFormattedPrint(QuestionAnswer qa) {
		
		out1 = wrap(qa.getAsin(),lenFieldASIN);

		out2 = wrap(qa.getQuestion(),lenQuestion);

		out3 = wrap(qa.getAnswer(),lenAnswer);

		out4 = wrap(qa.getAnswerTime(),lenAnswerTime);

		int[] data = {out1.size(), out2.size(), out3.size(), out4.size()};
		Arrays.sort(data);
		int max_size = data[3];
		
		for (int i = 0; i < max_size; i++) {

			System.out.format("%-"+ lenFieldASIN + "s%-"+ lenQuestion + "s%-" + lenAnswer+ "s%-"+ 
					lenAnswerTime+ "s", out1.get(i), out2.get(i), out3.get(i), out4.get(i));
			System.out.println();		
		}	
		System.out.println(printDash());
	}
	
	/**Method for split long text string into elements of {@link WrapperList} object.
     * 
	 * 
	 * @param longString - string of text
	 * @param length - max length of string element in returned WrapperList object.
	 * @return <b>WrapperList</b> object
	 */
	public static WrapperList wrap(String longString, int length) {
		
		int partsString;
		int MAX_WIDTH = length;
		
		WrapperList outLines = new WrapperList();
		String[] splittedString = longString.split(" ");
	    String lineString = "";
	    	    
	    if (longString.length() <= MAX_WIDTH ) {
	    	outLines.add(longString);
	    	return outLines;
	    } else {
	    
		    for (int i = 0; i < splittedString.length; i++) {
		        if (lineString.isEmpty()) {
		            lineString += splittedString[i] + " ";
		        } else if (lineString.length() + splittedString[i].length() < MAX_WIDTH) {
		            lineString += splittedString[i] + " ";
		        } else {
		        	outLines.add(lineString);
		            lineString = splittedString[i] + " ";
		            
		            //Split long string to parts on max field length
		            partsString = (int)lineString.length()/MAX_WIDTH;
		            if (partsString >= 1) {
		            	for (int j = 0; j < partsString; j++ ) {
		            		
		            		outLines.add(lineString.substring(0, MAX_WIDTH - 1));
		            		lineString = lineString.substring(MAX_WIDTH);

		            	}
		            }
		        }
		    }
		   outLines.add(lineString);
		   return outLines;
	    }    
	}
	
	public static void headerPrint() {
		
		System.out.format("%-"+ lenFieldASIN + "s%-"+ lenQuestion + "s%-" + lenAnswer+ "s%-"+ 
				lenAnswerTime + "s", "ASIN", "Question", "Answer", "Date");
		System.out.println();
		System.out.println(printDash());
		
	}	
	private static String printDash() {
		
		StringBuffer sb = new StringBuffer();
		int dashLength = lenFieldASIN + lenQuestion + lenAnswer + lenAnswerTime;
		for (int i = 0; i<dashLength; i++)
			sb.append("-");
		return sb.toString();
	}	
}
