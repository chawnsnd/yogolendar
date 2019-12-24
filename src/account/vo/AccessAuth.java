package account.vo;

public class AccessAuth {

	private int no;
	private int client_no;
	private int server_no;
	private char todo;
	private char anniversary;
	
	public AccessAuth() {
	}

	public AccessAuth(int no, int client_no, int server_no, char todo, char anniversary) {
		this.no = no;
		this.client_no = client_no;
		this.server_no = server_no;
		this.todo = todo;
		this.anniversary = anniversary;
	}

	public int getNo() {
		return no;
	}

	public void setNo(int no) {
		this.no = no;
	}

	public int getClient_no() {
		return client_no;
	}

	public void setClient_no(int client_no) {
		this.client_no = client_no;
	}

	public int getServer_no() {
		return server_no;
	}

	public void setServer_no(int server_no) {
		this.server_no = server_no;
	}

	public char getTodo() {
		return todo;
	}

	public void setTodo(char todo) {
		this.todo = todo;
	}

	public char getAnniversary() {
		return anniversary;
	}

	public void setAnniversary(char anniversary) {
		this.anniversary = anniversary;
	}

	@Override
	public String toString() {
		return "AccessAuth [no=" + no + ", client_no=" + client_no + ", server_no=" + server_no + ", todo=" + todo
				+ ", anniversary=" + anniversary + "]";
	}
	
	
}
