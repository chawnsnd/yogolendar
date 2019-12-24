package calendar.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import calendar.vo.Anniversary;
import calendar.vo.ToDo;

public interface CalendarMapper {
	
	//Todo
	public int insertTodo(ToDo todo);
	public ArrayList<ToDo> selectTodo(Map<String, Object> map);
	public ToDo readTodo(Map<String, Object> map);
	public int deleteTodo(int ent);
	public ArrayList<ToDo> searchTodo(HashMap<String, Object> map);
	public int updateTodo(ToDo todo);

	
	//Anniversary
	public int insertAnniversary(Anniversary anniversary);
	public ArrayList<Anniversary> selectAnniversary(Map<String, Object> map);
	public Anniversary readAnniversary(Map<String, Object> map);
	public int deleteAnniversary(int ent);
	public ArrayList<Anniversary> searchAnniversary(HashMap<String, Object> map);
	public int updateAnniversary(Anniversary anniversary);

}
