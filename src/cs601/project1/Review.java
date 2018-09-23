package cs601.project1;

import java.util.ArrayList;

/**The {@code Review} class is dataset, derived from {@code json}-file
 * {@code Cell_Phones_and_Accessories_5.json}
 * <p>
 * 
 *
 */
public class Review extends  Amazon{
    private String systemId;
	private String reviewerID;
    private String asin;
    private String reviewerName;
    private String reviewText;
    private Double overall;
    private String summary;
    private Long unixReviewTime;
    private String reviewTime;

    
    public String getSystemId() {
		return systemId;
	}

	public void setSystemId(String systemId) {
		this.systemId = systemId;
	}

	public String getReviewerID() {
		return reviewerID;
	}

	public String getAsin() {
		return asin;
	}

	public String getReviewerName() {
		return reviewerName;
	}

	public String getReviewText() {
		return reviewText;
	}

	public Double getOverall() {
		return overall;
	}

	public String getSummary() {
		return summary;
	}

	public Long getUnixReviewTime() {
		return unixReviewTime;
	}

	public String getReviewTime() {
		return reviewTime;
	}
	
	public void setTextForIndex(ArrayList<String> fieldsList) {
		
	}
	public String getTextForIndex() {
		return reviewText;
	}

	public void print() {
		
		ReviewPrintConverter.reviewFormattedPrint(this);
		//ReviewPrintConverter.reviewUnFormattedPrint(this);
		
	}
	
	public void printHeader() {
		ReviewPrintConverter.headerPrint();

	}
	
	
        
   }
