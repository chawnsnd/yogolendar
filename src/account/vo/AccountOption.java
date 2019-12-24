package account.vo;

public class AccountOption {

	private int no;
	private int account_no;
	private char hide;
	
	public AccountOption() {
	}

	public AccountOption(int no, int account_no, char hide) {
		this.no = no;
		this.account_no = account_no;
		this.hide = hide;
	}

	public int getNo() {
		return no;
	}

	public void setNo(int no) {
		this.no = no;
	}

	public int getAccount_no() {
		return account_no;
	}

	public void setAccount_no(int account_no) {
		this.account_no = account_no;
	}

	public char getHide() {
		return hide;
	}

	public void setHide(char hide) {
		this.hide = hide;
	}

	@Override
	public String toString() {
		return "AccountOption [no=" + no + ", account_no=" + account_no + ", hide=" + hide + "]";
	}
	
}