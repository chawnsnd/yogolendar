package account.dao;

import java.util.Map;

import account.vo.Account;
import account.vo.AccountOption;

public interface AccountMapper {
	
	public int createAccount(Account account);
	
	public int createAccountOption(Account account);
	
	public AccountOption selectAccountOption(int account_no);

	public int validateId(String id);
	
	public int validatePassword(Map<String, Object> validatePasswordInfo);
	
	public Account loginAccount(Map<String, String> loginInfo);
	
	public Account searchAccountById(String id);

	public Account searchAccountByNo(int no);
	
	public int modifyPassword(Map<String, Object> modifyPasswordInfo);

	public int modifyHide(AccountOption accountOption);
	
	public int withdrawAccount(Map<String, Object> withdrawInfo);

}
