package calendar.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import calendar.vo.Anniversary;
import db.MybatisConfig;

public class AnniversaryDao {
	
	private SqlSessionFactory factory = MybatisConfig.getSqlSessionFactory();
	
	public boolean insertAnniversary(Anniversary anniversary) {
		SqlSession session = null;
		int result = 0;
		
		try {
			session = factory.openSession();
			CalendarMapper mapper = session.getMapper(CalendarMapper.class);
			result = mapper.insertAnniversary(anniversary);
			session.commit();
		} catch (Exception e) {
//			e.printStackTrace();
			System.out.println("동작에 실패했습니다.");
		} finally {
			session.close();
		}
		
		if(result==0) return false;
		return true;
	}
	
    public ArrayList<Anniversary> selectAnniversary(int account_no) {
        SqlSession session = null;
        ArrayList<Anniversary> list = null;
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("account_no", account_no);
        try {
            session = factory.openSession();
            CalendarMapper mapper = session.getMapper(CalendarMapper.class);
            list = mapper.selectAnniversary(map);
            
        } catch (Exception e) {
//            e.printStackTrace();
        	System.out.println("동작에 실패했습니다.");
        } finally {
            session.close();
        }
        
        return list;
    }
    
    public Anniversary readAnniversary(int ent, int account_no) {
        SqlSession session = null;
        Anniversary anniversary = null;
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("no", ent);
        map.put("account_no", account_no);
        try {
            session = factory.openSession();
            CalendarMapper mapper = session.getMapper(CalendarMapper.class);
            anniversary = mapper.readAnniversary(map);
            session.commit();
            
        } catch (Exception e) {
//            e.printStackTrace();
        	System.out.println("동작에 실패했습니다.");
        } finally {
            session.close();
        }
        
        return anniversary;
    }
    
    public boolean deleteAnniversary(int ent) {
        SqlSession session = null;
        int cnt = 0;
        
        try {
            session = factory.openSession();
            CalendarMapper mapper = session.getMapper(CalendarMapper.class);
            cnt = mapper.deleteAnniversary(ent);
            session.commit();
            
        } catch (Exception e) {
//            e.printStackTrace();
        	System.out.println("동작에 실패했습니다.");
        } finally {
            session.close();
        }
        
        if(cnt == 0) return false;
        return true;
    }
    
	public ArrayList<Anniversary> searchAnniversary(int col, String word, int account_no) {
		SqlSession session = null;
		ArrayList<Anniversary> list = null;
		
		HashMap<String, Object> map = null;
		
		try {
			session = factory.openSession();
			CalendarMapper mapper = session.getMapper(CalendarMapper.class);
			map = new HashMap<>();
			map.put("column", col);
			map.put("words", word);
			map.put("account_no", account_no);
			list = mapper.searchAnniversary(map);
			
		} catch (Exception e) {
//			e.printStackTrace();
			System.out.println("동작에 실패했습니다.");
		} finally {
			session.close();
		}
		return list;
	}
	
	public boolean updateAnniversary(Anniversary anniversary) {
		SqlSession session = null;
		int result = 0;
		
		try {
			session = factory.openSession();
			CalendarMapper mapper = session.getMapper(CalendarMapper.class);
			result = mapper.updateAnniversary(anniversary);
			session.commit();
		} catch (Exception e) {
//			e.printStackTrace();
			System.out.println("동작에 실패했습니다.");
		} finally {
			session.close();
		}
		if(result==0) return false;
		return true;
	}
}
