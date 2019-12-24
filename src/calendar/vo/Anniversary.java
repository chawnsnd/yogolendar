package calendar.vo;

public class Anniversary {

	private int no;
	private int month;
	private int day;
	private String title;
	private String kind;
	private int account_no;

	public Anniversary() {
	}

	public Anniversary(int no, int month, int day, String title, String kind, int account_no) {
		super();
		this.no = no;
		this.month = month;
		this.day = day;
		this.title = title;
		this.kind = kind;
		this.account_no = account_no;
	}

	public int getNo() {
		return no;
	}

	public void setNo(int no) {
		this.no = no;
	}

	public int getMonth() {
		return month;
	}

	public void setMonth(int month) {
		this.month = month;
	}

	public int getDay() {
		return day;
	}

	public void setDay(int day) {
		this.day = day;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getKind() {
		return kind;
	}

	public void setKind(String kind) {
		this.kind = kind;
	}

	public int getAccount_no() {
		return account_no;
	}

	public void setAccount_no(int account_no) {
		this.account_no = account_no;
	}
}
