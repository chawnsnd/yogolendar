package calendar.vo;

public class ToDo {

	private int no;
	private int allday;
	private String from_date;
	private String to_date;
	private String title;
	private String content;
	private int account_no;


	public ToDo() {
	}

	public ToDo(int no, int allday, String from_date, String to_date, String title, String content, int account_no) {
		super();
		this.no = no;
		this.allday = allday;
		this.from_date = from_date;
		this.to_date = to_date;
		this.title = title;
		this.content = content;
		this.account_no = account_no;
	}

	public int getNo() {
		return no;
	}

	public void setNo(int no) {
		this.no = no;
	}

	public int getAllday() {
		return allday;
	}

	public void setAllday(int allday) {
		this.allday = allday;
	}

	public String getFrom_date() {
		return from_date;
	}

	public void setFrom_date(String from_date) {
		this.from_date = from_date;
	}


	public String getTo_date() {
		return to_date;
	}

	public void setTo_date(String to_date) {
		this.to_date = to_date;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	public int getAccont_no() {
		return account_no;
	}

	public void setAccont_no(int account_no) {
		this.account_no = account_no;
	}
	@Override
	public String toString() {
		return "ToDo [no=" + no + ", allday=" + allday + ", from_date=" + from_date + ", to_date=" + to_date
				+ ", title=" + title + ", content=" + content + ", account_no=" + account_no + "]";
	}
}
