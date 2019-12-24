package exception;

public class WrongCommandExcption extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String command = "";
	
	public WrongCommandExcption() {
	}
	
	public WrongCommandExcption(String command) {
		this.command = command;
	}

	public void help() {
		System.out.println("사용할 수 없는 명령어입니다.");		
		System.out.println("-- help");
//		if(this.command.equals("command")) {
//			System.out.println("-- help");
//		}else {
//			System.out.println("-- help "+this.command);
//			
//		}
	}
}
