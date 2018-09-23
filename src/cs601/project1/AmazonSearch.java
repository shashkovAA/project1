package cs601.project1;

import java.util.Scanner;


public class AmazonSearch {
	
	private static final String REVIEW_ARG = "-reviews";
	private static final String QA_ARG = "-qa";
	
	private static String reviewsFileName;
	private static String qasFileName;
	
	public static void main(String[] args) {	
				
		System.out.println("Start programm!");
		
		int len = args.length;
		String command;
		String searchTerm,input;
		boolean isExit = false;
		
		
		for (int i = 0; i < args.length; i++) {
			if (REVIEW_ARG.equals(args[i]) && len >= 4)
				reviewsFileName = args[i+1];
			if (QA_ARG.equals(args[i]) && len >= 4)
				qasFileName = args[i+1];	
		}
		
		if (reviewsFileName == null || qasFileName == null){
			System.out.println("There are not enough program arguments specified. Check arguments and try again.");
			System.out.println("Exit program.");
			System.exit(0);
		}
				
		InvertedIndex<Review> iReview = new InvertedIndex<Review>(Review.class,reviewsFileName);	
		iReview.createIndex();
		
		InvertedIndex<QuestionAnswer> iQuestionAnswer = new InvertedIndex<QuestionAnswer>(QuestionAnswer.class,qasFileName);	
		iQuestionAnswer.createIndex();

		Scanner scanner = new Scanner(System.in);
	    
	    while (!isExit) {
	    	System.out.println();
		    System.out.println("Avaliable commands:");
		    System.out.println();
		    System.out.println("1. find <ASIN>");
		    System.out.println("2. reviewsearch <term>");
		    System.out.println("3. qasearch <term>");
		    System.out.println("4. reviewpartialsearch <term>");
		    System.out.println("5. qapartialsearch <term>");
		    System.out.println();
		    System.out.println("0. exit");
		    System.out.println();
		    System.out.print("Select command number: ");
		    
		    command = scanner.next();
		    
		    switch (command) {
		    
				case "1": 
					System.out.print("Enter ASIN : ");
					searchTerm = scanner.next();
					searchTerm = convertSearchTerm(searchTerm);
					
					System.out.println ("Search by ASIN [" + searchTerm + "] in Review:\n");
					iReview.indexedSearchByASIN(searchTerm);
					
					System.out.println ("Search by ASIN [" + searchTerm + "] in QA:\n");
					iQuestionAnswer.indexedSearchByASIN(searchTerm);
					break;
					
				case "2": 
					System.out.print("Enter a search term: ");
					searchTerm = scanner.next();
					searchTerm = convertSearchTerm(searchTerm);
					System.out.println ("Search by term [" + searchTerm + "] in Review text:\n");	
					iReview.indexedSearchByTerm(searchTerm);
					break;
					
				case "3": 
					System.out.print("Enter a search term: ");
					searchTerm = scanner.next();
					searchTerm = convertSearchTerm(searchTerm);
					System.out.println ("Search by term [" + searchTerm + "] in QA text:\n");
					iQuestionAnswer.indexedSearchByTerm(searchTerm);
					break;
					
				case "4": 
					System.out.print("Enter part of term for search: ");
					searchTerm = scanner.next();
					searchTerm = convertSearchTerm(searchTerm);
					System.out.println ("Search by part of term [" + searchTerm + "] in Review text:\n");
					iReview.indexedSearchByPartOfTerm(searchTerm);
					break;
					
				case "5": 
					System.out.print("Enter part of term for search: ");
					searchTerm = scanner.next();
					searchTerm = convertSearchTerm(searchTerm);
					System.out.println ("Search by part of term [" + searchTerm + "] in QA text:\n");
					iQuestionAnswer.indexedSearchByPartOfTerm(searchTerm);
					break;
					
				case "0": 
					isExit = true;
					System.out.println("Exit program.");
					pressAnyKeyToContinue();
					System.exit(0);
					break;
				
				default:
					System.out.print("Invalid input. Try again...");
					System.out.println();
					
		    }
		    
		    System.out.print("Enter 'y' for continue? :"); 		
			input = scanner.next();
			
			if ((!input.equals("y") && !input.equals("Y"))) {
				isExit = true;
				System.out.println("Exit program.");
				pressAnyKeyToContinue();
			}	
	    }
	    
	    scanner.close();	    	    
	}
	/**
	 * Method for convert all  characters of searching term to lower case. 
	 * <p> If {@code term} contains non-alphanumeric characters, they will be deleted. 
	 * The {@code term} will be split into parts and only the first part of the {@code term} 
	 * will be used for searching. 
	 * 
	 * 
	 * @return convertedSearchTerm
	 */
	private static String convertSearchTerm(String term){
		term = (term.split("\\W+"))[0].toLowerCase().trim();
		return term;
	}
	
	private static void pressAnyKeyToContinue() { 
		System.out.println("Press Enter key to close  ...");
	    try{
	    	System.in.read();
	    }
	    catch(Exception e){
	    }  
	 }
}
