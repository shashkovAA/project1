package cs601.project1;

/**The {@code QuestionAnswer} class is dataset, derived from {@code json}-file
 * {@code qa_Cell_Phones_and_Accessories.json}.
 * <p>
 * 
 *
 */
public class QuestionAnswer extends Amazon{
	private String systemId;
	private String questionType;
    private String asin;
    private String answerTime;
    private Long unixTime;
    private String question;
    private String answerType;
    private String answer;

    public String getSystemId() {
		return systemId;
	}
	public void setSystemId(String systemId) {
		this.systemId = systemId;
	}
	
	public String getQuestionType() {
		return questionType;
	}
	
	public String getAsin() {
		return asin;
	}
	
	public String getAnswerTime() {
		return answerTime;
	}
	
	public Long getUnixTime() {
		return unixTime;
	}
	
	public String getQuestion() {
		return question;
	}
	
	public String getAnswerType() {
		return answerType;
	}
	
	public String getAnswer() {
		return answer;
	}

	public String getTextForIndex() {
		return question + " " + answer;
	}

	public void print() {
		
		QAPrintConverter.qaFormattedPrint(this);

	}
	public void printHeader() {
		
		QAPrintConverter.headerPrint();
	}
	


}
