package ui.config;

import java.util.Scanner;

import account.dao.AccessAuthDao;
import account.dao.AccountDao;
import exception.WrongCommandExcption;
import ui.LoginUi;
import ui.YogolanderUi;
import util.HelpUtil;
import util.LoginSession;

public class ConfigUi {
	private Scanner scanner = YogolanderUi.scanner;
	private LoginUi loginUi = null;
	private AccountUi accountUi = null;
	private AuthUi authUi = null;

	public ConfigUi() {
		AccountDao accountDao = new AccountDao();
		AccessAuthDao accessAuthDao = new AccessAuthDao();
		loginUi = new LoginUi(accountDao);
		accountUi = new AccountUi(accountDao);
		authUi = new AuthUi(accessAuthDao, accountDao);
	}

	public void config() throws WrongCommandExcption {
		while (true) {
			
			if (LoginSession.isLogin()) {
				System.out.print(LoginSession.getAccount().getId() + "(config)> ");
			} else {
				System.out.println("로그인 되어있지 않습니다.");
				return;
			}
			String commandStr = scanner.nextLine();
			String[] commandArr = commandStr.split(" ");
			String command = commandArr[0];
			if (command.equals("exit")) {
				YogolanderUi.isConfig = false;
				return;
			}
			String[] opts = new String[commandArr.length - 1];
			for (int i = 1; i < commandArr.length; i++) {
				opts[i - 1] = commandArr[i];
			}
			if(command.equals("withdraw")){ // 회원탈퇴
				loginUi.withdraw();
				return;
			}
			command(command, opts);
			System.out.println();
			
		}
	}

	private void command(String command, String[] opts) throws WrongCommandExcption {
		switch (command) {
		case "passwd": // 비밀번호 변경
			accountUi.passwd();
			break;
		case "hide": // 숨김설정 변경
			accountUi.hide(opts);
			break;
		case "auth": // 권한부여
			authUi.auth(opts);
			break;
		case "subscribe": // 팔로잉목록
			authUi.subscribe(opts);
			break;
		case "help":
			HelpUtil.MainHelp();
			break;
		default:
			throw new WrongCommandExcption("config");
		}
	}

}
