package util;

import account.vo.Account;

public class LoginSession {
	private static Account account;
	private static boolean isLogin;

	public static void login(Account accountt) {
		account = accountt;
		isLogin = true;
	}

	public static void logout() {
		account = null;
		isLogin = false;
	}

	public static boolean isLogin() {
		return isLogin;
	}

	public static Account getAccount() {
		if (account == null) {
			System.out.println("로그인 되어있지 않습니다.");
			return null;
		} else {
			return account;
		}
	}
}
