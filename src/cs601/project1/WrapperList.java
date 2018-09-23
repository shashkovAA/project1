package cs601.project1;


import java.util.ArrayList;
/**
 * The {@code WrapperList} class is a wrapper for ArrayList class with the ability 
 * to return blank value for method {@code get(index)} if {@code index} is invalid.
 *
 */
public class WrapperList {
	
	private ArrayList<String> elementList;
	
	public WrapperList(){
		elementList = new ArrayList<String>();
	}
	
	public String get(int index) {
		
		try {
			return elementList.get(index);
		} catch (IndexOutOfBoundsException except) {
			return "";
		}	
	}
	
	public void add(String element){
		elementList.add(element);
	}
	
	public int size(){
		return elementList.size();
	}

	
	
}
