package ui.config;

import java.util.Scanner;

import account.dao.AccountDao;
import account.vo.AccountOption;
import ui.YogolanderUi;
import util.LoginSession;
import util.PasswordUtil;

public class AccountUi {
	private AccountDao accountDao = null;
	private Scanner scanner = YogolanderUi.scanner;

	public AccountUi(AccountDao accountDao) {
		this.accountDao = accountDao;
	}

	public void passwd() {
		if (!LoginSession.isLogin())
			return;
		System.out.print("현재 비밀번호 : ");
		String oldPassword = scanner.nextLine();
		String oldPasswordEncrypt = PasswordUtil.encryptSHA256(oldPassword);
		if (!accountDao.validatePassword(LoginSession.getAccount().getNo(), oldPasswordEncrypt)) {
			System.out.println("현재 비밀번호와 일치하지 않습니다.");
			return;
		}
		System.out.print("변경할 비밀번호 : ");
		String newPassword = scanner.nextLine();
		System.out.print("비밀번호 재입력 : ");
		String newPassword2 = scanner.nextLine();
		if (!newPassword.equals(newPassword2)) {
			System.out.println("두 비밀번호가 일치하지 않습니다.");
			return;
		}
		String newPasswordEncrypt = PasswordUtil.encryptSHA256(newPassword);
		if (accountDao.modifyPassword(LoginSession.getAccount().getNo(), newPasswordEncrypt)) {
			System.out.println("비밀번호가 변경되었습니다.");
		} else {
			System.out.println("비밀번호 변경이 실패했습니다.");
		}
	}

	public void hide(String[] opts) {
		if (opts.length == 0) {
			hideInfo();
		} else if (opts.length == 1) {
			modifyHide(opts);
		} else {
			System.out.println("올바른 명령이 아닙니다.");
		}
	}

	private void modifyHide(String[] opts) {
		AccountOption accountOption = accountDao.selectAccountOption(LoginSession.getAccount().getNo());
		accountOption.setHide(opts[0].charAt(0));
		if (accountDao.modifyHide(accountOption)) {
			System.out.println("변경되었습니다.");
		} else {
			System.out.println("변경이 실패하였습니다.");
		}
	}

	private void hideInfo() {
		AccountOption accountOption = accountDao.selectAccountOption(LoginSession.getAccount().getNo());
		char hide = accountOption.getHide();
		if (hide == '1') {
			System.out.println("지난 일정은 숨겨져 있습니다.");
			System.out.println("모든 일정을 출력하려면 hide 0을 입력해주세요.");
		} else {
			System.out.println("모든 일정이 출력됩니다.");
			System.out.println("지난 일정을 숨기려면 hide 1을 입력해주세요.");
		}
	}
}
