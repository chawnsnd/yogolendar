package account.dao;

import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import account.vo.Account;
import account.vo.AccountOption;
import db.MybatisConfig;

public class AccountDao {

	private SqlSessionFactory factory = MybatisConfig.getSqlSessionFactory();

	public boolean createAccount(Account account) {
		SqlSession session = null;
		int cnt = 0;
		try {
			session = factory.openSession();
			AccountMapper mapper = session.getMapper(AccountMapper.class);
			cnt = mapper.createAccount(account);
			cnt = mapper.createAccountOption(account);
			session.commit();
		} catch (Exception e) {
//			e.printStackTrace();
			System.out.println("동작에 실패했습니다.");
		} finally {
			session.close();
		}
		if (cnt == 1) {
			return true;
		} else {
			return false;
		}
	}

	public boolean validateId(String id) {
		int cnt = 0;
		SqlSession session = null;
		try {
			session = factory.openSession();
			AccountMapper mapper = session.getMapper(AccountMapper.class);
			cnt = mapper.validateId(id);
		} catch (Exception e) {
//			e.printStackTrace();
			System.out.println("동작에 실패했습니다.");
		} finally {
			session.close();
		}
		if (cnt == 0) {
			return true;
		} else {
			return false;
		}
	}

	public boolean validatePassword(int no, String password) {
		Map<String, Object> validatePasswordInfo = new HashMap<>();
		validatePasswordInfo.put("no", no);
		validatePasswordInfo.put("password", password);
		int cnt = 0;

		SqlSession session = null;
		try {
			session = factory.openSession();
			AccountMapper mapper = session.getMapper(AccountMapper.class);
			cnt = mapper.validatePassword(validatePasswordInfo);
		} catch (Exception e) {
//			e.printStackTrace();
			System.out.println("동작에 실패했습니다.");
		} finally {
			session.close();
		}
		if (cnt == 1) {
			return true;
		} else {
			return false;
		}
	}

	public Account loginAccount(String id, String password) {
		Map<String, String> loginInfo = new HashMap<>();
		loginInfo.put("id", id);
		loginInfo.put("password", password);
		Account account = null;

		SqlSession session = null;
		try {
			session = factory.openSession();
			AccountMapper mapper = session.getMapper(AccountMapper.class);
			account = mapper.loginAccount(loginInfo);
		} catch (Exception e) {
//			e.printStackTrace();
			System.out.println("동작에 실패했습니다.");
		} finally {
			session.close();
		}
		return account;
	}
	
	public Account searchAccountById(String id) {
		Account account = null;
		
		SqlSession session = null;
		try {
			session = factory.openSession();
			AccountMapper mapper = session.getMapper(AccountMapper.class);
			account = mapper.searchAccountById(id);
		} catch (Exception e) {
//			e.printStackTrace();
			System.out.println("동작에 실패했습니다.");
		} finally {
			session.close();
		}
		return account;
	}

	public Account searchAccountByNo(int no) {
		Account account = null;
		
		SqlSession session = null;
		try {
			session = factory.openSession();
			AccountMapper mapper = session.getMapper(AccountMapper.class);
			account = mapper.searchAccountByNo(no);
		} catch (Exception e) {
//			e.printStackTrace();
			System.out.println("동작에 실패했습니다.");
		} finally {
			session.close();
		}
		return account;
	}

	public boolean modifyPassword(int no, String password) {
		Map<String, Object> modifyPasswordInfo = new HashMap<>();
		modifyPasswordInfo.put("no", no);
		modifyPasswordInfo.put("password", password);
		int cnt = 0;

		SqlSession session = null;

		try {
			session = factory.openSession();
			AccountMapper mapper = session.getMapper(AccountMapper.class);
			cnt = mapper.modifyPassword(modifyPasswordInfo);
			session.commit();
		} catch (Exception e) {
//			e.printStackTrace();
			System.out.println("동작에 실패했습니다.");
		} finally {
			session.close();
		}
		if (cnt == 1) {
			return true;
		} else {
			return false;
		}
	}
	
	public AccountOption selectAccountOption(int account_no) {
		AccountOption option = null;
		SqlSession session = null;
		try {
			session = factory.openSession();
			AccountMapper mapper = session.getMapper(AccountMapper.class);
			option = mapper.selectAccountOption(account_no);
		} catch (Exception e) {
//			e.printStackTrace();
			System.out.println("동작에 실패했습니다.");
		} finally {
			session.close();
		}
		return option;
	}

	public boolean modifyHide(AccountOption accountOption) {
		int cnt = 0;

		SqlSession session = null;

		try {
			session = factory.openSession();
			AccountMapper mapper = session.getMapper(AccountMapper.class);
			cnt = mapper.modifyHide(accountOption);
			session.commit();
		} catch (Exception e) {
//			e.printStackTrace();
			System.out.println("동작에 실패했습니다.");
		} finally {
			session.close();
		}
		if (cnt == 1) {
			return true;
		} else {
			return false;
		}
	}

	public boolean withdrawAccount(int no, String password) {
		Map<String, Object> withdrawInfo = new HashMap<>();
		withdrawInfo.put("no", no);
		withdrawInfo.put("password", password);
		int cnt = 0;

		SqlSession session = null;

		try {
			session = factory.openSession();
			AccountMapper mapper = session.getMapper(AccountMapper.class);
			cnt = mapper.withdrawAccount(withdrawInfo);
			session.commit();
		} catch (Exception e) {
//			e.printStackTrace();
			System.out.println("동작에 실패했습니다.");
		} finally {
			session.close();
		}
		if (cnt == 1) {
			return true;
		} else {
			return false;
		}
	}

}
