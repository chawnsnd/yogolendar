package ui;

import java.util.InputMismatchException;
import java.util.Scanner;

import account.dao.AccountDao;
import calendar.dao.AnniversaryDao;
import calendar.dao.ToDoDao;
import exception.WrongCommandExcption;
import ui.config.ConfigUi;
import util.HelpUtil;
import util.LoginSession;

public class YogolanderUi {
	public static Scanner scanner = new Scanner(System.in);
	public static boolean isConfig = false;
	public ConfigUi configUi = new ConfigUi();
	private LoginUi loginUi = null;
	private CalendarUi calendarUi = null;

	public YogolanderUi() {
		AccountDao accountDao = new AccountDao();
		ToDoDao toDoDao = new ToDoDao();
		AnniversaryDao anniversaryDao = new AnniversaryDao();
		loginUi = new LoginUi(accountDao);
		calendarUi = new CalendarUi(scanner, anniversaryDao, toDoDao, accountDao);
	}

	public void execute() {
		System.out.println("** 명령어 도움말 보기 : help 입력 후 Enter **");
		while (true) {
			System.out.println();
			try {
				if (LoginSession.isLogin()) {
					System.out.println();
					try {
						if (LoginSession.isLogin()) {
							if (isConfig) {
								configUi.config();
								continue;
							} else {
								System.out.print(LoginSession.getAccount().getId() + "> ");
							}
						} else {
							System.out.print("> ");
						}
						String commandStr = scanner.nextLine();
						String[] commandArr = commandStr.split(" ");
						String command = commandArr[0];
						String[] opts = new String[commandArr.length - 1];
						for (int i = 1; i < commandArr.length; i++) {
							opts[i - 1] = commandArr[i];
						}
						if (command.equals("logout")) { // 로그아웃
							loginUi.logout();
							continue;
						}
						command(command, opts);
					} catch (WrongCommandExcption e) {
						e.help();
					} catch (InputMismatchException e) {
						System.out.println("잘못된 입력입니다.");
						scanner.nextLine();
					}

					if (isConfig) {
						configUi.config();
						continue;
					} else {
						System.out.print(LoginSession.getAccount().getId() + "> ");
					}
				} else {
					System.out.print("> ");
				}
				String commandStr = scanner.nextLine();
				String[] commandArr = commandStr.split(" ");
				String command = commandArr[0];
				String[] opts = new String[commandArr.length - 1];
				for (int i = 1; i < commandArr.length; i++) {
					opts[i - 1] = commandArr[i];
				}
				if (command.equals("logout")) {
					System.out.println("이미 로그아웃되어있습니다.");
				}
				command(command, opts);
			} catch (WrongCommandExcption e) {
				e.help();
			} catch (InputMismatchException e) {
				System.out.println("InputMismatch");
				scanner.nextLine();
			}
		}
	}

	private void command(String command, String[] opts) throws WrongCommandExcption, InputMismatchException {
		switch (command) {
		case "logout":
			break;
		case "login": // 로그인
			loginUi.login();
			break;
		case "signup": // 회원가입
			loginUi.signup();
			break;
		case "config": // 설정 진입
			if(LoginSession.isLogin()) {
				isConfig = true;
			}else {
				System.out.println("로그인 되어있지 않습니다.");
			}
			break;
		case "todo":
			calendarUi.todo(opts);
			break;
		case "anniversary":
			calendarUi.anniversary(opts);
			break;
		case "help":
			HelpUtil.MainHelp();
			break;
		default:
			throw new WrongCommandExcption();
		}
	}

}
