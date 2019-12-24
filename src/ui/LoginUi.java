package ui;

import java.util.Scanner;

import account.dao.AccountDao;
import account.vo.Account;
import util.LoginSession;
import util.PasswordUtil;

public class LoginUi {
	private AccountDao accountDao = null;
	private Scanner scanner = YogolanderUi.scanner;

	public LoginUi(AccountDao accountDao) {
		this.accountDao = accountDao;
	}

	public void login() {
		if(LoginSession.isLogin()) {
			System.out.println("이미 로그인 되어있습니다.");
			return;
		}
		System.out.print("아이디 : ");
		String id = scanner.nextLine();
		System.out.print("비밀번호 : ");
		String password = scanner.nextLine();
		String encryptedPW = PasswordUtil.encryptSHA256(password);
		Account loginAccount = accountDao.loginAccount(id, encryptedPW);
		if (loginAccount == null) {
			System.out.println("로그인에 실패하였습니다.");
		} else {
			LoginSession.login(loginAccount);
			System.out.println(loginAccount.getName() + "님 반갑습니다.");
		}
	}

	public void logout() {
		if (!LoginSession.isLogin()) {
			System.out.println("이미 로그아웃되어있습니다.");
		} else {
			LoginSession.logout();
			System.out.println("로그아웃 되었습니다.");
		}
	}

	public void signup() {
		if (!LoginSession.isLogin()) {
			String id = null;
			while (true) {
				System.out.print("아이디 : ");
				id = scanner.nextLine();
				if (accountDao.validateId(id))
					break;
				else
					System.out.println("이미 가입된 아이디입니다.");
			}
			System.out.print("비밀번호 : ");
			String password = scanner.nextLine();
			String encryptedPW = PasswordUtil.encryptSHA256(password);
			System.out.print("이름 : ");
			String name = scanner.nextLine();
			if(id.equals("") || password.equals("") || name.equals("")) {
				System.out.println("모든 항목을 입력해주세요.");
				return;
			}
			Account account = new Account();
			account.setId(id);
			account.setPassword(encryptedPW);
			account.setName(name);
			if (accountDao.createAccount(account)) {
				System.out.println("가입에 성공하였습니다.");
			} else {
				System.out.println("가입에 실패하였습니다.");
			}
		} else {
			System.out.println("먼저 로그아웃 해주세요.");
		}
	}

	public void withdraw() {
		System.out.print("비밀번호 : ");
		String password = scanner.nextLine();
		String encryptedPW = PasswordUtil.encryptSHA256(password);
		if (!accountDao.validatePassword(LoginSession.getAccount().getNo(), encryptedPW)) {
			System.out.println("비밀번호가 일치하지 않습니다.");
		}
		if (accountDao.withdrawAccount(LoginSession.getAccount().getNo(), encryptedPW)) {
			System.out.println("성공적으로 탈퇴되었습니다");
			LoginSession.logout();
			YogolanderUi.isConfig = false;
		}
	}
}
