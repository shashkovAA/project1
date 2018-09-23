package cs601.project1;

import java.util.ArrayList;
/**
 * The {@code OccurenceObjList} class  is a wrapper for ArrayList with the ability 
 * to use the addition directly of one element  in a class constructor.
 *
 */
public class OccurenceObjList {
	
	private ArrayList<String> elementList;
	
	public OccurenceObjList(){
		elementList = new ArrayList<String>();
	}
	
	public OccurenceObjList(String element){
		elementList = new ArrayList<String>();
		elementList.add(element);
	}
	
	public String get(int index) {
		return elementList.get(index);
	}
	
	public void add(String element){
		elementList.add(element);
	}
	
	public int size(){
		return elementList.size();
	}
	
	public void clear() {
		elementList.clear();
	}
	
	public void print() {

		for (int i=0; i<elementList.size(); i++) {
			System.out.print(elementList.get(i) + " ");
		}
		System.out.println();
	}
	
}
