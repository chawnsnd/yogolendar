package ui.config;

import java.util.List;
import java.util.Scanner;

import account.dao.AccessAuthDao;
import account.dao.AccountDao;
import account.vo.AccessAuth;
import account.vo.Account;
import exception.WrongCommandExcption;
import ui.YogolanderUi;
import util.LoginSession;

public class AuthUi {
	
	private AccessAuthDao accessAuthDao = new AccessAuthDao();
	private AccountDao accountDao = null;
	private Scanner scanner = YogolanderUi.scanner;

	public AuthUi(AccessAuthDao accessAuthDao, AccountDao accountDao) {
		this.accessAuthDao = accessAuthDao;
		this.accountDao = accountDao;
	}

	public void auth(String[] opts) throws WrongCommandExcption {
		if(opts.length == 0) {
			myClients();
		}else if(opts.length == 1) {
			if(opts[0].equals("grant")) {
				grant();
			}else if(opts[0].equals("modify")) {
				config();
			}else if(opts[0].equals("remove")) {
				remove();
			}else {				
				throw new WrongCommandExcption("auth");
			}
		}else {
			throw new WrongCommandExcption("auth");
		}
	}

	private void remove() {
		System.out.print("권한을 뺏을 아이디를 입력해주세요. : ");
		String client_id = scanner.nextLine();
		Account client = accountDao.searchAccountById(client_id);
		if(client == null) {
			System.out.println("존재하지 않는 아이디입니다.");
			return;
		}
		List<AccessAuth> clientsList = accessAuthDao.myClientsList(LoginSession.getAccount().getNo());
		boolean inList = false;
		for (AccessAuth accessAuth : clientsList) {
			if(accessAuth.getClient_no() == client.getNo()) {
				inList = true;
				break;
			}
		}
		if(!inList) {
			System.out.println("권한을 부여받지 않은 아이디입니다.");
			return;
		}
		if(accessAuthDao.deleteAccess(LoginSession.getAccount().getNo(), client.getNo())) {
			System.out.println("권한을 뺏었습니다.");
		}else {
			System.out.println("권한뺏기에 실패했습니다.");
		}
	}

	private void grant() {
		System.out.print("권한을 부여할 아이디를 입력해주세요. : ");
		String client_id = scanner.nextLine();
		List<AccessAuth> myClientsList = accessAuthDao.myClientsList(LoginSession.getAccount().getNo());
		Account client = accountDao.searchAccountById(client_id);
		if(client == null) {
			System.out.println("존재하지 않는 아이디입니다.");
			return;
		} else if(client.getNo() == LoginSession.getAccount().getNo()) {
			System.out.println("본인의 아이디입니다.");
			return;
		}
		for (AccessAuth accessAuth : myClientsList) {
			if(accessAuth.getClient_no() == client.getNo()) {
				System.out.println("이미 권한이 부여된 아이디입니다.");
				return;
			}
		}
		System.out.println(client.getName() + "(" + client.getId() + ") 님에게 권한을 부여합니다.");
		if (accessAuthDao.grantAccess(client.getNo(), LoginSession.getAccount().getNo(), "1", "1")) {
			System.out.println(client.getName()+"님께 권한을 부여했습니다.");
		} else {
			System.out.println("권한 부여에 실패했습니다.");
		}
	}

	private void config() {
		System.out.print("(1. 전체수정    2. 한사람만 수정) : ");
		String menu = scanner.nextLine();
		switch (menu) {
		case "1":
			configAll();
			break;
		case "2":
			configOne();
			break;
		default:
			System.out.println("올바르지 않은 명령어입니다.");
			return;
		}
	}

	private void configOne() {
		System.out.print("권한을 수정할 아이디를 입력해주세요. : ");
		String client_id = scanner.nextLine();
		Account client = accountDao.searchAccountById(client_id);
		if (client == null) {
			System.out.println("존재하지 않는 아이디 입니다.");
			return;
		}
		List<AccessAuth> clientsList = accessAuthDao.myClientsList(LoginSession.getAccount().getNo());
		boolean inList = false;
		for (AccessAuth accessAuth : clientsList) {
			if(accessAuth.getClient_no() == client.getNo()) {
				inList = true;
				break;
			}
		}
		if(!inList) {
			System.out.println("권한을 부여받지 않은 아이디입니다.");
			return;
		}
		System.out.print("todo를 조회할 권한을 주시겠습니까? (0 or 1): ");
		String ttodo = scanner.nextLine();
		System.out.print("anniversary를 조회할 권한을 주시겠습니까? (0 or 1): ");
		String aanniversary = scanner.nextLine();
		if (accessAuthDao.modifyAccessOption(client.getNo(), LoginSession.getAccount().getNo(), ttodo, aanniversary)) {
			System.out.println(client.getName()+"님의 권한을 변경했습니다.");
		} else {
			System.out.println("권한 변경에 실패했습니다.");
		}
	}
	
	private void configAll() {
		System.out.print("todo를 조회할 권한을 주시겠습니까? (0 or 1): ");
		String todo = scanner.nextLine();
		System.out.print("anniversary를 조회할 권한을 주시겠습니까? (0 or 1): ");
		String anniversary = scanner.nextLine();
		if(accessAuthDao.modifyAccessOptionAll(LoginSession.getAccount().getNo(), todo, anniversary)) {
			System.out.println("전체 권한을 변경했습니다.");
		}else {
			System.out.println("권한 변경에 실패했습니다.");
		}
	}

	private void myClients() {
		List<AccessAuth> myClientsList = accessAuthDao.myClientsList(LoginSession.getAccount().getNo());
		if (myClientsList.size() == 0) {
			System.out.println("정보가 없습니다.");
			return;
		}
		System.out.printf("아이디\t\t이름\t\tTODO\t\tANNIVERSARY");
		System.out.println();
		for (AccessAuth client : myClientsList) {
			System.out.print(accountDao.searchAccountByNo(client.getClient_no()).getId());
			System.out.printf("\t\t");
			System.out.print(accountDao.searchAccountByNo(client.getClient_no()).getName());
			System.out.printf("\t\t");
			System.out.print(client.getTodo());
			System.out.printf("\t\t");
			System.out.print(client.getAnniversary());
			System.out.println();
		}
	}

	public void subscribe(String[] opts) {
		if(opts.length == 0) {
			myServers();
		}else if(opts.length == 1) {
			if(opts[0].equals("remove")) {
				removeSubscribe();
			}else {				
				System.out.println("올바른 명령이 아닙니다.");
			}
		}else {
			System.out.println("올바른 명령이 아닙니다.");
		}
	}
	
	public void myServers() {
		List<AccessAuth> myServersList = accessAuthDao.myServersList(LoginSession.getAccount().getNo());
		if (myServersList.size() == 0) {
			System.out.println("정보가 없습니다.");
			return;
		}
		System.out.printf("아이디\t\t이름\t\tTODO\t\tANNIVERSARY");
		System.out.println();
		for (AccessAuth server : myServersList) {
			System.out.print(accountDao.searchAccountByNo(server.getServer_no()).getId());
			System.out.printf("\t\t");
			System.out.print(accountDao.searchAccountByNo(server.getServer_no()).getName());
			System.out.printf("\t\t");
			System.out.print(server.getTodo());
			System.out.printf("\t\t");
			System.out.print(server.getAnniversary());
			System.out.println();
		}
	}
	
	private void removeSubscribe() {
		System.out.print("구독을 해제할 아이디를 입력해주세요. : ");
		String server_id = scanner.nextLine();
		Account server = accountDao.searchAccountById(server_id);
		if(server == null) {
			System.out.println("존재하지 않는 아이디입니다.");
			return;
		}
		List<AccessAuth> serversList = accessAuthDao.myServersList(LoginSession.getAccount().getNo());
		boolean inList = false;
		for (AccessAuth accessAuth : serversList) {
			if(accessAuth.getClient_no() == LoginSession.getAccount().getNo()) {
				inList = true;
				break;
			}
		}
		if(!inList) {
			System.out.println("구독중이지 않은 아이디입니다.");
			return;
		}
		if(accessAuthDao.deleteAccess(server.getNo(), LoginSession.getAccount().getNo())) {
			System.out.println("구독을 해지했습니다.");
		}else {
			System.out.println("구독해지에 실패했습니다.");
		}
	}
}
