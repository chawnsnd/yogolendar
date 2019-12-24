package account.dao;

import java.util.ArrayList;
import java.util.Map;

import account.vo.AccessAuth;

public interface AccessAuthMapper {
	
	public int grantAccess(Map<String, Object> grantInfo);
	
	public ArrayList<AccessAuth> myClientsList(int server_no);

	public ArrayList<AccessAuth> myServersList(int client_no);
	
	public int modifyAccessOption(Map<String, Object> modifyOptionInfo);

	public int modifyAccessOptionAll(Map<String, Object> modifyOptionInfo);

	public int deleteAccess(Map<String, Integer> deleteInfo);
}
