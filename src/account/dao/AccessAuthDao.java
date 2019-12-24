package account.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import account.vo.AccessAuth;
import db.MybatisConfig;

public class AccessAuthDao {

	private SqlSessionFactory factory = MybatisConfig.getSqlSessionFactory();

	public boolean grantAccess(int client_no, int server_no, String todo, String anniversary) {
		SqlSession session = null;
		Map<String, Object> grantInfo = new HashMap<>();
		grantInfo.put("client_no", client_no);
		grantInfo.put("server_no", server_no);
		grantInfo.put("todo", todo.charAt(0));
		grantInfo.put("anniversary", anniversary.charAt(0));
		
		int cnt = 0;
		try {
			session = factory.openSession();
			AccessAuthMapper mapper = session.getMapper(AccessAuthMapper.class);
			cnt = mapper.grantAccess(grantInfo);
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

	public List<AccessAuth> myClientsList(int server_no) {
		List<AccessAuth> myClientsList = null;
		
		SqlSession session = null;
		try {
			session = factory.openSession();
			AccessAuthMapper mapper = session.getMapper(AccessAuthMapper.class);
			myClientsList = mapper.myClientsList(server_no);
		} catch (Exception e) {
//			e.printStackTrace();
			System.out.println("동작에 실패했습니다.");
		} finally {
			session.close();
		}
		return myClientsList;
	}

	public List<AccessAuth> myServersList(int client_no) {
		List<AccessAuth> myServersList = null;
		
		SqlSession session = null;
		try {
			session = factory.openSession();
			AccessAuthMapper mapper = session.getMapper(AccessAuthMapper.class);
			myServersList = mapper.myServersList(client_no);
		} catch (Exception e) {
//			e.printStackTrace();
			System.out.println("동작에 실패했습니다.");
		} finally {
			session.close();
		}
		return myServersList;
	}
	

	public boolean modifyAccessOption(int client_no, int server_no, String todo, String anniversary) {
		Map<String, Object> modifyOptionInfo = new HashMap<>();
		modifyOptionInfo.put("client_no", client_no);
		modifyOptionInfo.put("server_no", server_no);
		modifyOptionInfo.put("todo", todo.charAt(0));
		modifyOptionInfo.put("anniversary", anniversary.charAt(0));
		int cnt = 0;
		
		SqlSession session = null;
		try {
			session = factory.openSession();
			AccessAuthMapper mapper = session.getMapper(AccessAuthMapper.class);
			cnt = mapper.modifyAccessOption(modifyOptionInfo);
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

	public boolean modifyAccessOptionAll(int server_no, String todo, String anniversary) {
		Map<String, Object> modifyOptionInfo = new HashMap<>();
		modifyOptionInfo.put("server_no", server_no);
		modifyOptionInfo.put("todo", todo.charAt(0));
		modifyOptionInfo.put("anniversary", anniversary.charAt(0));
		int cnt = 0;
		
		SqlSession session = null;
		try {
			session = factory.openSession();
			AccessAuthMapper mapper = session.getMapper(AccessAuthMapper.class);
			cnt = mapper.modifyAccessOptionAll(modifyOptionInfo);
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

	public boolean deleteAccess(int server_no, int client_no) {
		Map<String, Integer> deleteInfo = new HashMap<>();
		deleteInfo.put("server_no", server_no);
		deleteInfo.put("client_no", client_no);
		int cnt = 0;
		
		SqlSession session = null;
		try {
			session = factory.openSession();
			AccessAuthMapper mapper = session.getMapper(AccessAuthMapper.class);
			cnt = mapper.deleteAccess(deleteInfo);
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
