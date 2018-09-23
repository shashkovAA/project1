package cs601.project1;

/**
 * The abstract parent class for objects derived from {@code json}-files 
 * that define the minimum set of methods to be implemented.
 * <p>
 * 
 * @author Shashkov A.
 *
 */
public abstract class Amazon {
		
	public abstract String getSystemId(); 

	public abstract void setSystemId(String systemId); 
	
	public abstract String getTextForIndex();
		
	public abstract String getAsin();
	
	public abstract void print();
	
	public abstract void printHeader();
		
	
}
