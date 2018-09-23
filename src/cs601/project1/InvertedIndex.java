package cs601.project1;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import java.util.HashMap;
import java.util.TreeMap;

import com.google.gson.Gson;

/** The {@code InvertedIndex} class holds data from datasets Review or QA.
 *  The {@code InvertedIndex} contains methods for bilding an inverted indexes used for searching documents.
 * @author Shashkov A.
 *
 * @param <T>
 */


public class InvertedIndex <T extends Amazon>{
	
	private Class<T> TYPE;
	
	private String jsonFileName;
	private ArrayList<T> objListFromjsonRecords = new ArrayList<T>();
	
	private TreeMap<String, T> systemIdMap;
	private HashMap<String, OccurenceObjList> indexASINMap;
	private TreeMap<String, OccurenceObjList> indexTermMap; 
	
	private long timeMs;
	private int wordLength = 2;
	int count = 0;
	private boolean debug = true;

/** The {@code InvertedIndex} class constructor. 
 * 
 * @param type - Class Type of dataset. 
 * @param jsonFileName - name of json file.
 */
	public InvertedIndex(Class<T> type, String jsonFileName) {
		
		TYPE = type;
		this.jsonFileName = jsonFileName;	
		
		readJsonFile();		
		//printObjectList(objListFromjsonRecords);
	}
	
	/**Method {@code readJsonFile()} converts data from Json file to ArrayList of datasets (Review or QA).  
	 * <p>
	 */
	private void readJsonFile() {
		
		String readline;
		Gson gson = new Gson();
		timeMs = System.currentTimeMillis();
		
		try (BufferedReader buffer = new BufferedReader(
				new InputStreamReader(new FileInputStream(jsonFileName),"ISO-8859-1"));) {
					while ((readline = buffer.readLine()) != null) {
					if (readline.contains("\\x")) {
						readline = removeBackSlashSymFromString(readline);										
					}	
					objListFromjsonRecords.add(gson.fromJson(readline,TYPE));
		    	}

		} catch (FileNotFoundException evt) {
		    System.out.println(evt.getMessage());
		    System.exit(0);
		} catch (IOException ev) {
			 System.out.println(ev.getMessage());
			 System.exit(0);
		}
		if (debug )
			printTime("readJsonFile()",TYPE,  timeMs);
	}

	public String removeBackSlashSymFromString(String readline) {
		char [] chrArray = readline.toCharArray();

		for (int k = 1; k <= chrArray.length; k++)
			if (chrArray[k-1]=='\\' && chrArray[k]=='x')
				chrArray[k-1] = ' ';
		
		readline = String.valueOf(chrArray);
		return readline;
	}	
	
	
	/**Method {@code createIndex} creates indexMaps used for quick searching documents. 
	 * <p>
	 */
	public void createIndex () {
		
		createSystemIdMap(objListFromjsonRecords);
		createIndexMapForFindByASIN();
		createIndexMapForFindByTerm();
		
		//System.out.println("-------------indexMap--------------");
		//printIndexMap();	
		
		convertValuesOfIndexMapForFindByTerm();
		
		//System.out.println("-------------ConvertedIndexMap--------------");
		//printIndexMap();	
	}
	/**Method {@code createSystemIdMap} creates {@code systemIdMap} object instance. The {@code systemIdMap} is HashMap, where
	 * <p> <b>key</b> - SystemID
	 * <p> <b>value</b> - dataset object (Review or QA).
	 * 
	 * @param list
	 */
	private void createSystemIdMap(ArrayList<T> list) {
		
		String systemId;
		T listElement;
		
		timeMs = System.currentTimeMillis();
		
		systemIdMap = new TreeMap<String, T>();
		
		for (int i=0; i<objListFromjsonRecords.size(); i++) {
			
			listElement = objListFromjsonRecords.get(i);
			systemId = String.valueOf(i+1);
			
			listElement.setSystemId(systemId);
			
			systemIdMap.put(systemId, listElement);
		}
		if (debug)
			printTime("createSystemIdMap()", TYPE, timeMs);
	}

	/**The method {@code createIndexMapForFindByASIN} creates {@code indexASINMap} object instance. The {@code indexASINMap} is HashMap, where 
	 * <p> <b>key</b> - ASIN
	 * <p> <b>value</b> - {@link OccurenceObjList} of SystemID
	 * 
	 */
	private void createIndexMapForFindByASIN() {
		
		String asin, systemId;
		T listElement;
		OccurenceObjList systemIdList;
		
		timeMs = System.currentTimeMillis();
		
		indexASINMap = new HashMap<String, OccurenceObjList>();
			
		for (int i = 0; i < objListFromjsonRecords.size(); i++) {
			
			listElement = objListFromjsonRecords.get(i);
			systemId = listElement.getSystemId();		
					
			asin = listElement.getAsin().toLowerCase().trim();
			
			if (!indexASINMap.containsKey(asin)){
				indexASINMap.put(asin, new OccurenceObjList(systemId));
			} else {
				systemIdList = indexASINMap.get(asin);
				systemIdList.add(systemId);
				indexASINMap.replace(asin, systemIdList);
			}				
		}
		if (debug)
			printTime("createIndexMapForFindByASIN()", TYPE, timeMs);
		}
	/**The method {@code createIndexMapForFindByTerm} creates {@code indexTermMap} object instance.  The {@code indexTermMap} is TreeMap object, where
	 * <p> <b>key</b> - term
	 * <p> <b>value</b> - {@link OccurenceObjList} of SystemID
	 * <p>
	 */
	private void createIndexMapForFindByTerm() {
		
		String systemId;
		ArrayList<String> termList;
		OccurenceObjList systemIdList;
		
		timeMs = System.currentTimeMillis();
		
		indexTermMap = new TreeMap<String, OccurenceObjList>();
		
		for (T listElement : objListFromjsonRecords) {
					
			systemId = listElement.getSystemId();
			
			termList = getListOfTermsFromText(listElement.getTextForIndex());

			for (String term : termList) {
				
				if (!indexTermMap.containsKey(term)){
					indexTermMap.put(term, new OccurenceObjList(systemId));
				} else {
					systemIdList = indexTermMap.get(term);
					systemIdList.add(systemId);
					indexTermMap.replace(term, systemIdList);
				}			
			}				
		}
		if (debug)
			printTime("createIndexMapForFindByTerm()", TYPE, timeMs);
	}
	/** The method {@code getListOfTermsFromText} converts text string to ArrayList of words
	 * 
	 * @param text
	 * @return ArrayList of words
	 */
	private ArrayList<String> getListOfTermsFromText(String text) {
		
		String [] termArray = text.split("\\W+");
		ArrayList<String> termList= new ArrayList<String>();
		
		for (int i = 0; i < termArray.length; i++) {
			if (termArray[i].trim().length() >= wordLength ) {
				termList.add(termArray[i].toLowerCase().trim());
			}	
		}
		return termList;
	}
	/**The method  converts for {@link OccurenceObjList} of SystemID {@code indexTermMap}
	 * 
	 */
	private void convertValuesOfIndexMapForFindByTerm() {
		
		OccurenceObjList list;
		
		timeMs = System.currentTimeMillis();
		
		for (HashMap.Entry<String, OccurenceObjList> entry : indexTermMap.entrySet()) {
		    
			list = entry.getValue();	
			ListConverter.convertOccurenceObjList(list);
			indexTermMap.replace(entry.getKey(), list);
		}
		if (debug)
			printTime("convertValuesOfIndexMapForFindByTerm()",TYPE, timeMs);
	}
	
	/** The method {@code indexedSearchByASIN} used for print documents for searching ASIN 
	 * 
	 * @param searchASIN
	 */
	public void indexedSearchByASIN(String searchASIN) {
		
		int length;
		count = 0;
		
		timeMs = System.currentTimeMillis();

		if (indexASINMap.containsKey(searchASIN)) {
			
			length = indexASINMap.get(searchASIN).size();
			
			// Print Header method from first finding instance Object
			systemIdMap.get(indexASINMap.get(searchASIN).get(0)).printHeader();
		
			for (int i = 0; i < length; i++) {
				systemIdMap.get(indexASINMap.get(searchASIN).get(i)).print();
				count++;
			}
			System.out.println("Found " + count + " record(s).");
			System.out.println();
			
			if (debug)
				printTime("search by ASIN",TYPE, timeMs);
			
		} else System.out.println("Nothing found!");	
	}
	/**The method {@code indexedSearchByTerm} used for print documents for searching term
	 * 
	 * @param searchTerm
	 */
	public void indexedSearchByTerm(String searchTerm) {
		
		int length;
		count = 0;
		
		timeMs = System.currentTimeMillis();

		if (indexTermMap.containsKey(searchTerm)) {
			
			length = indexTermMap.get(searchTerm).size();
			// Print Header method from first finding instance Object
			systemIdMap.get(indexTermMap.get(searchTerm).get(0)).printHeader();
		
			for (int i = 0; i < length; i++) {
				systemIdMap.get(indexTermMap.get(searchTerm).get(i)).print();
				count++;
			}
			System.out.println("Found " + count + " record(s).");
			System.out.println();
			
			if (debug) 
				printTime("search by term",TYPE, timeMs);
			
		} else System.out.println("Nothing found!");	
		
	}
	/**The method {@code indexedSearchByTerm} used for print documents for searching part of term
	 * 
	 * @param searchPartTerm
	 */
	public void indexedSearchByPartOfTerm(String searchPartTerm) {
		
		int length;
		count = 0;
		String indexedTerm;
		
		timeMs = System.currentTimeMillis();
				
		for (HashMap.Entry<String, OccurenceObjList> entry : indexTermMap.entrySet()) {
		    
			indexedTerm = entry.getKey();
			if (indexedTerm.contains(searchPartTerm)) {
				
				length = indexTermMap.get(indexedTerm).size();
				
				for (int i = 0; i < length; i++) {
					systemIdMap.get(indexTermMap.get(indexedTerm).get(i)).print();
					count++;
				}
			} 				
		}
		
		if (count == 0)
			System.out.println("Nothing found!");
		else 
			System.out.println("Found " + count + " record(s).");
		
		if (debug)
			printTime("partial search by term", TYPE, timeMs);	
	}
		
	private void printIndexMap() {
		
		for (HashMap.Entry<String, OccurenceObjList> entry : indexTermMap.entrySet()) {
			System.out.print(entry.getKey()+ " : " );
			entry.getValue().print();
		}
	}	
		
	private void printObjectList(ArrayList<T> jsonRecordsList2) {
		
        for (int i=0; i<jsonRecordsList2.size(); i++) {
        	jsonRecordsList2.get(i).print();
        }
        System.out.println();	
	}
	
	private void printStringList(ArrayList<String> list) {

        for (int i=0; i<list.size(); i++) {
        	System.out.println(list.get(i));
        }	
	}
	private void printTime(String method, Class<T> type, long startTimeMs){
		System.out.println("Run method "+ method +" for object " + type + " at " + (System.currentTimeMillis() - timeMs) + " ms.");					
	}

}
