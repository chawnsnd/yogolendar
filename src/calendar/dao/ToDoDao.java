package calendar.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import calendar.vo.ToDo;
import db.MybatisConfig;

public class ToDoDao {
	
	private SqlSessionFactory factory = MybatisConfig.getSqlSessionFactory();
	
	public boolean insertTodo(ToDo todo) {
		SqlSession session = null;
		int result = 0;
		
		try {
			session = factory.openSession();
			CalendarMapper mapper = session.getMapper(CalendarMapper.class);
			result = mapper.insertTodo(todo);
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
	
    public ArrayList<ToDo> selectTodo(int account_no, char hide) {
        SqlSession session = null;
        ArrayList<ToDo> list = null;
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("account_no", account_no);
        map.put("hide", hide);
        try {
            session = factory.openSession();
            CalendarMapper mapper = session.getMapper(CalendarMapper.class);
            list = mapper.selectTodo(map);
            
        } catch (Exception e) {
//            e.printStackTrace();
        	System.out.println("동작에 실패했습니다.");
        } finally {
            session.close();
        }
        
        return list;
    }
    
    public ToDo readTodo(int ent, int account_no, char hide) {
        SqlSession session = null;
        ToDo todo = null;
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("no", ent);
        map.put("account_no", account_no);
        map.put("hide", hide);
        try {
            session = factory.openSession();
            CalendarMapper mapper = session.getMapper(CalendarMapper.class);
            todo = mapper.readTodo(map);
            session.commit();
            
        } catch (Exception e) {
//            e.printStackTrace();
        	System.out.println("동작에 실패했습니다.");
        } finally {
            session.close();
        }
        
        return todo;
    }
    
    public boolean deleteTodo(int ent) {
        SqlSession session = null;
        int cnt = 0;
        
        try {
            session = factory.openSession();
            CalendarMapper mapper = session.getMapper(CalendarMapper.class);
            cnt = mapper.deleteTodo(ent);
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
    
	public ArrayList<ToDo> searchTodo(int col, String word, int account_no, char hide) {
		SqlSession session = null;
		ArrayList<ToDo> list = null;
		
		HashMap<String, Object> map = null;
		
		try {
			session = factory.openSession();
			CalendarMapper mapper = session.getMapper(CalendarMapper.class);
			map = new HashMap<>();
			map.put("column", col);
			map.put("words", word);
			map.put("account_no", account_no);
			map.put("hide", hide);
			list = mapper.searchTodo(map);
			
		} catch (Exception e) {
//			e.printStackTrace();
			System.out.println("동작에 실패했습니다.");
		} finally {
			session.close();
		}
		return list;
	}
	
	public boolean updateTodo(ToDo todo) {
		SqlSession session = null;
		int result = 0;
		
		try {
			session = factory.openSession();
			CalendarMapper mapper = session.getMapper(CalendarMapper.class);
			result = mapper.updateTodo(todo);
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
