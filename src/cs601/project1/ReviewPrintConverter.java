package cs601.project1;

import java.util.Arrays;

/**The {@code ReviewPrintConverter} class provides static methods for formatted output  
 * Review objects fields in table view. 
 * <p>
 *
 */
public class ReviewPrintConverter {

	//private static WrapperList out1 = new WrapperList();
	private static WrapperList out2 = new WrapperList();
	private static WrapperList out3 = new WrapperList();
	private static WrapperList out4 = new WrapperList();
	//private static WrapperList out5 = new WrapperList();
	private static WrapperList out6 = new WrapperList();
	
	//private static int lenFieldReviewerID = 15;
	private static int lenFieldASIN = 12;
	private static int lenFieldReviewerName = 12;
	private static int lenFieldReviewText = 48;
	//private static int lenFieldSummary = 20;
	private static int lenFieldReviewTime = 8;
	
	
	
	public static void reviewFormattedPrint(Review review) {
		
		//out1 = wrap(review.getReviewerID(),lenFieldReviewerID);

		out2 = wrap(review.getAsin(),lenFieldASIN);

		out3 = wrap(review.getReviewerName(),lenFieldReviewerName);

		out4 = wrap(review.getReviewText(),lenFieldReviewText);

		//out5 = wrap(review.getSummary(),lenFieldSummary);

		out6 = wrap(review.getReviewTime(),lenFieldReviewTime);
		
		
		int[] data = {/*out1.size(),*/ out2.size(), out3.size(), out4.size(), /*out5.size(),*/ out6.size()};
		Arrays.sort(data);
		int max_size = data[3];
		
		for (int i = 0; i < max_size; i++) {

			System.out.format("%-"+ /*lenFieldReviewerID + "s%-"+*/ lenFieldASIN + "s%-" + lenFieldReviewerName+ "s%-"+ 
					lenFieldReviewText + "s%-" +  /*lenFieldSummary + "s%-" +*/ lenFieldReviewTime + "s", 
					/*out1.get(i),*/ out2.get(i), out3.get(i), out4.get(i), /*out5.get(i),*/ out6.get(i));
			System.out.println();
				
		}	
		System.out.println(printDash());
	}
	
	public static void headerPrint() {
		
		System.out.format("%-"+ /*lenFieldReviewerID + "s%-"+*/ lenFieldASIN + "s%-" + lenFieldReviewerName+ "s%-"+ 
				lenFieldReviewText + "s%-" +  /*lenFieldSummary + "s%-" +*/ lenFieldReviewTime + "s", 
				/*"Reviewer ID",*/ "ASIN", "Name", "Review Text", /*"Summary",*/ "Date");
		System.out.println();
		System.out.println(printDash());	
	}
	
	public static WrapperList wrap(String longString, int length) {
		
		int partsString;
		int MAX_WIDTH = length;	
		
		WrapperList outLines = new WrapperList();
		
		if (longString == null) {
			outLines.add("");
			return outLines;
		}
						
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
	
	public static void reviewUnFormattedPrint(Review review) {
		
		System.out.println(review.getAsin() + " " + review.getReviewerName()+ " " + review.getReviewText()+ " " +review.getReviewTime());
	}
	
	private static String printDash() {
		
		StringBuffer sb = new StringBuffer();
		int dashLength = /*lenFieldReviewerID +*/ lenFieldASIN +lenFieldReviewerName + lenFieldReviewText + /*lenFieldSummary +*/ lenFieldReviewTime;
		for (int i = 0; i<dashLength; i++)
			sb.append("-");
		return sb.toString();
	}	
}
