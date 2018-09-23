package cs601.project1;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

/**The {@code ListConverter} class contains a static method {@code convertOccurenceObjList}
 * that calculates the number of identical ArrayList elements, deletes duplicates, and sorts the elements, 
 * depending on the number of repetitions in the desceding order.
 * 
 *
 */
public class ListConverter {
	

	private static List<Entry<String, Integer>> listTemp;
	
	
	public static void convertOccurenceObjList(OccurenceObjList list) {
			
		HashMap<String, Integer> hMap = createMapFromListWithUnsortedElements(list);
		
		list.clear();
		
		createListFromMapWithDescedingElementsCount(hMap);
        
        for (int i = listTemp.size()-1; i >= 0; i--)       	
        	list.add(listTemp.get(i).getKey());	
        
    	listTemp.clear();
   		
	}
	private static HashMap<String, Integer> createMapFromListWithUnsortedElements(OccurenceObjList list) {
		HashMap<String,Integer> elementsMap = new HashMap<String,Integer>();
		String systemId;
		
		for (int i=0; i<list.size(); i++) {
			
			systemId = list.get(i);
			
			if (elementsMap.containsKey(systemId))
				elementsMap.replace(systemId, (elementsMap.get(systemId) + 1));	
		 	else
			elementsMap.put(systemId, 1);
		}
		return elementsMap;
	}
	
	private static void createListFromMapWithDescedingElementsCount(HashMap<String, Integer> hMap) {
		listTemp = new ArrayList<>(hMap.entrySet());
		listTemp.sort(Entry.comparingByValue());
	}
	

	private static void printList(ArrayList<String> list) {
		System.out.println();
        for (int i=0; i<list.size(); i++) {
        	System.out.println(list.get(i));
        }
		
	}

}
