package ui;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Scanner;

import account.dao.AccountDao;
import account.vo.AccountOption;
import calendar.dao.AnniversaryDao;
import calendar.dao.ToDoDao;
import calendar.vo.Anniversary;
import calendar.vo.ToDo;
import exception.WrongCommandExcption;
import util.DateUtil;
import util.LoginSession;

public class CalendarUi {

	private Scanner sc = null;

	private AnniversaryDao anniversaryDao = new AnniversaryDao();
	private ToDoDao toDodao = null;
	private AccountDao accountDao = null;
	private DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	
	public CalendarUi(Scanner sc, AnniversaryDao anniversaryDao, ToDoDao todoDao, AccountDao accountDao) {
		this.sc = sc;
		this.anniversaryDao = anniversaryDao;
		this.toDodao = todoDao;
		this.accountDao = accountDao;
	}

	// ToDO 관리
	public void todo(String[] opts) throws WrongCommandExcption {
		if(!LoginSession.isLogin()) {
			System.out.println("로그인 되어있지 않습니다.");
			return;
		}
		if (opts.length == 0) {
			selectToDO();
		} else if (opts.length == 1) {
			if (opts[0].equals("enroll")) {
				insertToDO();
			} else if (opts[0].equals("search")) {
				searchToDO();
			} else if (opts[0].equals("modify")) {
				updateToDO();
			} else if (opts[0].equals("remove")) {
				deleteToDO();
			} else if (opts[0].equals("read")) {
				readToDO();
			} else {
				throw new WrongCommandExcption("todo");
			}
		} else {
			throw new WrongCommandExcption("todo");
		}
	}

	// TODO 등록
	private void insertToDO(){

		int fromDateDayYear;
		int toDateDayYear;
		int toDateDayMonth;
		int toDateDayDay;
		int fromDateDayDay;
		int fromDateDayMonth;
		int fromDateTimeHour;
		int fromDateTimeMin;
		int toDateTimeHour;
		int toDateTimeMin;

		System.out.println("하루종일? 1. 네 // 0. 아니오");
		System.out.print("선택 : ");
		int allDay = sc.nextInt();
		sc.nextLine();
		if (allDay != 1 && allDay != 0) {
			System.out.println("올바르지 않은 입력입니다.");
			return;
		}
		System.out.print("등록 - 시작일 년 입력: ");
		fromDateDayYear = sc.nextInt();
		System.out.print("등록 - 시작일 월 입력 : ");
		fromDateDayMonth = sc.nextInt();
		System.out.print("등록 - 시작일 일 입력 : ");
		fromDateDayDay = sc.nextInt();
		if (allDay == 0) {
			System.out.print("등록 - 시작 시간 시 입력 : ");
			fromDateTimeHour = sc.nextInt();
			System.out.print("등록 - 시작 시간 분 입력 : ");
			fromDateTimeMin = sc.nextInt();
		} else {
			fromDateTimeHour = 0;
			fromDateTimeMin = 0;
		}
		sc.nextLine();
		if (!DateUtil.validationDate(fromDateDayYear, fromDateDayMonth, fromDateDayDay)) {
			System.out.println("올바른 날짜를 입력해주세요.");
			System.out.println();
			return;
		}
		if (!DateUtil.validationTime(fromDateTimeHour, fromDateTimeMin)) {
			System.out.println("올바른 시간을 입력해주세요.");
			System.out.println();
			return;
		}

		System.out.println("");
		System.out.print("등록 - 종료일 년 입력 : ");
		toDateDayYear = sc.nextInt();
		System.out.print("등록 - 종료일 월 입력 :");
		toDateDayMonth = sc.nextInt();
		System.out.print("등록 - 종료일 일 입력:");
		toDateDayDay = sc.nextInt();
		if (allDay == 0) {
			System.out.print("등록 - 종료 시간 시 입력 : ");
			toDateTimeHour = sc.nextInt();
			System.out.print("등록 - 종료 시간 분 입력 : ");
			toDateTimeMin = sc.nextInt();
		} else {
			toDateTimeHour = 23;
			toDateTimeMin = 59;
		}
		sc.nextLine();
		if (!DateUtil.validationDate(toDateDayYear, toDateDayMonth, toDateDayDay)) {
			System.out.println("올바른 날짜를 입력해주세요.");
			System.out.println();
			return;
		}
		if (!DateUtil.validationTime(toDateTimeHour, toDateTimeMin)) {
			System.out.println("올바른 시간을 입력해주세요.");
			System.out.println();
			return;
		}

		Calendar fromCal = Calendar.getInstance();
		fromCal.set(fromDateDayYear, fromDateDayMonth - 1, fromDateDayDay, fromDateTimeHour, fromDateTimeMin);

		Calendar toCal = Calendar.getInstance();
		toCal.set(toDateDayYear, toDateDayMonth - 1, toDateDayDay, toDateTimeHour, toDateTimeMin);

		if (fromCal.compareTo(toCal) == 1) {
			System.out.println("종료일이 시작일보다 먼저 설정되었습니다.");
			System.out.println();
			return;
		}

		String fromDate = dateFormat.format(fromCal.getTime());
		String toDate = dateFormat.format(toCal.getTime());
		System.out.println("");
		System.out.print("등록 - 제목 : ");
		String title = sc.nextLine();
		System.out.print("등록 - 상세내용 : ");
		String content = sc.nextLine();

		ToDo todo = new ToDo();
		todo.setAllday(allDay);
		todo.setFrom_date(fromDate);
		todo.setTo_date(toDate);
		todo.setTitle(title);
		todo.setContent(content);
		todo.setAccont_no(LoginSession.getAccount().getNo());

		boolean result = toDodao.insertTodo(todo);

		if (result) {
			System.out.println("ToDO를 등록 하였습니다.");
		} else {
			System.out.println("X ToDO를 등록하지 못했습니다.");
		}

	}

	// TODO 전체 조회
	private void selectToDO() {
		AccountOption selectAccountOption = accountDao.selectAccountOption(LoginSession.getAccount().getNo());
		ArrayList<ToDo> list = toDodao.selectTodo(LoginSession.getAccount().getNo(), selectAccountOption.getHide());

		if (list == null || list.size() == 0) {
			System.out.println("등록된 ToDO가 없습니다.");
			return;
		} else {
			System.out.println(
					"ToDO 번호  |  시작일  |  종료일  |  제목  |  등록자");
			for (ToDo todo : list) {
				System.out.print(todo.getNo());
				System.out.print("  |  " + todo.getFrom_date().substring(0, 16));
				System.out.print("  |  " + todo.getTo_date().substring(0, 16));
				System.out.print("  |  " + todo.getTitle());
				System.out.print("  |  " + accountDao.searchAccountByNo(todo.getAccont_no()).getName());
				System.out.println();
			}
		}
	}

	// TODO 1개 보기
	private void readToDO() {
		selectToDO();
		ToDo todo = null;
		System.out.print("확인할 ToDO 번호 입력  : ");
		int ent = sc.nextInt();
		sc.nextLine();

		char hide = accountDao.selectAccountOption(LoginSession.getAccount().getNo()).getHide();
		todo = toDodao.readTodo(ent, LoginSession.getAccount().getNo(), hide);
		if (todo == null) {
			System.out.println(ent + "번으로 등록된 ToDO가 없습니다.");
			return;
		}
		System.out.println("");
		System.out.println("Todo 번호	: " + todo.getNo());
		System.out.println(todo.getFrom_date().substring(0, 16) + " 부터 " + todo.getTo_date().substring(0, 16) + " 까지");
		System.out.println("할 일은 : " + todo.getTitle());
		System.out.println("상세 내용 :" + todo.getContent());
		System.out.println("------------------------------------");
	}

	// TODO 삭제
	private void deleteToDO() {
		selectToDO();
		System.out.print("삭제할 ToDO 번호 입력 : ");
		int ent = 0;
		ent = sc.nextInt();
		sc.nextLine();
		ToDo todo = toDodao.readTodo(ent, LoginSession.getAccount().getNo(), '0');
		if (todo == null) {
			System.out.println("등록된 ToDO가 없습니다.");
			return;
		} else if (todo.getAccont_no() != LoginSession.getAccount().getNo()) {
			System.out.println("해당 ToDO는 삭제할 수 없습니다.");
			return;
		}
		boolean result = toDodao.deleteTodo(ent);

		if (result) {
			System.out.println(ent + "번 ToDO가 삭제 되었습니다.");
			return;
		} else {
			System.out.println(ent + "번 ToDO삭제가 실패하였습니다.");
		}
	}

	// TODO 검색
	private void searchToDO() {

		int col = 0;
		String word = null;
		System.out.println("검색 조건 : 1.날짜  2.제목  3.상세내용");
		System.out.print("선택 : ");
		col = sc.nextInt();
		sc.nextLine();
		if (col == 1) {
			System.out.print("년도 : ");
			int year = sc.nextInt();
			System.out.print("월 : ");
			int month = sc.nextInt();
			System.out.print("일 : ");
			int day = sc.nextInt();
			sc.nextLine();
			Calendar calendar = Calendar.getInstance();
			calendar.set(year, month - 1, day);
			word = dateFormat.format(calendar.getTime());
		} else if (col == 2 || col == 3) {
			System.out.print("검색어 : ");
			word = sc.nextLine();
		} else {
			System.out.println("올바르지 않은 입력입니다.");
		}
		char hide = accountDao.selectAccountOption(LoginSession.getAccount().getNo()).getHide();
		ArrayList<ToDo> list = toDodao.searchTodo(col, word, LoginSession.getAccount().getNo(), hide);

		if (list == null || list.size() == 0) {
			System.out.println("등록된 ToDO가 없습니다.");
			return;
		} else {
			System.out.println(
					"ToDO 번호  |  시작일  |  종료일  |  제목  |  등록자");
			for (ToDo todo : list) {
				System.out.print(todo.getNo());
				System.out.print("  |  " + todo.getFrom_date());
				System.out.print("  |  " + todo.getTo_date());
				System.out.print("  |  " + todo.getTitle());
				System.out.print("  |  " + accountDao.searchAccountByNo(todo.getAccont_no()).getName());
				System.out.println();
			}
		}
	}

	// TODO 업데이트
	private void updateToDO() {
		selectToDO();
		int fromDateDayYear;
		int toDateDayYear;
		int toDateDayMonth;
		int toDateDayDay;
		int fromDateDayDay;
		int fromDateDayMonth;
		int fromDateTimeHour;
		int fromDateTimeMin;
		int toDateTimeHour;
		int toDateTimeMin;
		int no;

		System.out.print("TODO 번호 : ");
		no = sc.nextInt();
		sc.nextLine();
		ToDo todo = toDodao.readTodo(no, LoginSession.getAccount().getNo(), '0');
		if (todo == null) {
			System.out.println("등록된 ToDO가 없습니다.");
			return;
		} else if (todo.getAccont_no() != LoginSession.getAccount().getNo()) {
			System.out.println("해당 ToDO는 수정할 수 없습니다.");
			return;
		}

		System.out.println("1. 종일/시간    2. 시작일    3. 종료일    4. 제목    5. 내용    6. 전체수정    7. 나가기");
		System.out.print("선택 : ");
		int ent;
		ent = sc.nextInt();
		sc.nextLine();
		if (ent == 7) {
			return;
		} else if (ent == 1) {
			System.out.println("수정-하루종일? 1. 네 // 0. 아니오");
			System.out.print("입력 : ");
			int allDay = sc.nextInt();
			sc.nextLine();
			todo.setAllday(allDay);
		} else if (ent == 2) {
			System.out.print("수정- 시작일 년 입력 : ");
			fromDateDayYear = sc.nextInt();
			System.out.print("수정- 시작일 월 입력 : ");
			fromDateDayMonth = sc.nextInt();
			System.out.print("수정- 시작일 일 입력 : ");
			fromDateDayDay = sc.nextInt();
			if (todo.getAllday() == 0) {
				System.out.print("수정 - 시작 시간 시 입력 :");
				fromDateTimeHour = sc.nextInt();
				System.out.print("수정 - 시작 시간 분 입력 :");
				fromDateTimeMin = sc.nextInt();
			} else {
				fromDateTimeHour = 0;
				fromDateTimeMin = 0;
			}
			sc.nextLine();
			if (!DateUtil.validationDate(fromDateDayYear, fromDateDayMonth, fromDateDayDay)) {
				System.out.println("올바른 날짜를 입력해주세요.");
				System.out.println();
				return;
			}
			if (!DateUtil.validationTime(fromDateTimeHour, fromDateTimeMin)) {
				System.out.println("올바른 시간을 입력해주세요.");
				System.out.println();
				return;
			}
			Calendar fromCal = Calendar.getInstance();
			Calendar toCal = Calendar.getInstance();
			fromCal.set(fromDateDayYear, fromDateDayMonth - 1, fromDateDayDay, fromDateTimeHour, fromDateTimeMin);
			try {
				toCal.setTime(dateFormat.parse(todo.getTo_date()));
			} catch (ParseException e) {
				System.out.println("시작일을 변경할 수 없습니다.");
			}
			if (fromCal.compareTo(toCal) == 1) {
				System.out.println("시작일이 종료일 이후에 입력되었습니다.");
				System.out.println();
				return;
			}
			String fromDate = dateFormat.format(fromCal.getTime());
			todo.setFrom_date(fromDate);
		} else if (ent == 3) {
			System.out.println("");
			System.out.print("수정- 종료일 년 입력 : ");
			toDateDayYear = sc.nextInt();
			System.out.print("수정- 종료일 월 입력 : ");
			toDateDayMonth = sc.nextInt();
			System.out.print("수정- 종료일 일 입력 : ");
			toDateDayDay = sc.nextInt();
			if (todo.getAllday() == 0) {
				System.out.print("수정 - 종료 시간 시 입력 :");
				toDateTimeHour = sc.nextInt();
				System.out.print("수정 - 종료 시간 분 입력 :");
				toDateTimeMin = sc.nextInt();
			} else {
				toDateTimeHour = 23;
				toDateTimeMin = 59;
			}
			sc.nextLine();
			if (!DateUtil.validationDate(toDateDayYear, toDateDayMonth, toDateDayDay)) {
				System.out.println("올바른 날짜를 입력해주세요.");
				System.out.println();
				return;
			}
			if (!DateUtil.validationTime(toDateTimeHour, toDateTimeMin)) {
				System.out.println("올바른 시간을 입력해주세요.");
				System.out.println();
				return;
			}
			Calendar fromCal = Calendar.getInstance();
			Calendar toCal = Calendar.getInstance();
			toCal.set(toDateDayYear, toDateDayMonth - 1, toDateDayDay, toDateTimeHour, toDateTimeMin);
			try {
				fromCal.setTime(dateFormat.parse(todo.getFrom_date()));
			} catch (ParseException e) {
				System.out.println("종료일을 변경할 수 없습니다.");
			}
			if (fromCal.compareTo(toCal) == 1) {
				System.out.println("종료일이 시작일 이전에 입력되었습니다.");
				System.out.println();
				return;
			}
			String toDate = dateFormat.format(toCal.getTime());
			todo.setTo_date(toDate);
		} else if (ent == 4) {
			System.out.print("수정 - 제목 : ");
			String title = sc.nextLine();
			todo.setTitle(title);
		} else if (ent == 5) {
			System.out.print("수정 - 상세내용 : ");
			String content = sc.nextLine();
			todo.setContent(content);
		} else if (ent == 6) {
			System.out.println("종일? 1. 네 // 0. 아니오");
			System.out.print("선택 : ");
			int allDay = sc.nextInt();
			sc.nextLine();

			System.out.print("수정- 시작일 년 입력 : ");
			fromDateDayYear = sc.nextInt();
			System.out.print("수정- 시작일 월 입력 : ");
			fromDateDayMonth = sc.nextInt();
			System.out.print("수정- 시작일 일 입력 : ");
			fromDateDayDay = sc.nextInt();
			if (allDay == 0) {
				System.out.print("수정 - 시작 시간 시 입력 :");
				fromDateTimeHour = sc.nextInt();
				System.out.print("수정 - 시작 시간 분 입력 :");
				fromDateTimeMin = sc.nextInt();
			} else {
				fromDateTimeHour = 0;
				fromDateTimeMin = 0;
			}
			if (!DateUtil.validationDate(fromDateDayYear, fromDateDayMonth, fromDateDayDay)) {
				System.out.println("올바른 날짜를 입력해주세요.");
				System.out.println();
				return;
			}
			if (!DateUtil.validationTime(fromDateTimeHour, fromDateTimeMin)) {
				System.out.println("올바른 시간을 입력해주세요.");
				System.out.println();
				return;
			}

			System.out.println("");
			System.out.print("수정- 종료일 년 입력 : ");
			toDateDayYear = sc.nextInt();
			System.out.print("수정- 종료일 월 입력 : ");
			toDateDayMonth = sc.nextInt();
			System.out.print("수정- 종료일 일 입력 : ");
			toDateDayDay = sc.nextInt();
			if (todo.getAllday() == 0) {
				System.out.print("수정 - 종료 시간 시 입력 :");
				toDateTimeHour = sc.nextInt();
				System.out.print("수정 - 종료 시간 분 입력 :");
				toDateTimeMin = sc.nextInt();
			} else {
				toDateTimeHour = 23;
				toDateTimeMin = 59;
			}
			if (!DateUtil.validationDate(toDateDayYear, toDateDayMonth, toDateDayDay)) {
				System.out.println("올바른 날짜를 입력해주세요.");
				System.out.println();
				return;
			}
			if (!DateUtil.validationTime(toDateTimeHour, toDateTimeMin)) {
				System.out.println("올바른 시간을 입력해주세요.");
				System.out.println();
				return;
			}
			sc.nextLine();

			Calendar fromCal = Calendar.getInstance();
			fromCal.set(fromDateDayYear, fromDateDayMonth - 1, fromDateDayDay, fromDateTimeHour, fromDateTimeMin);

			Calendar toCal = Calendar.getInstance();
			toCal.set(toDateDayYear, toDateDayMonth - 1, toDateDayDay, toDateTimeHour, toDateTimeMin);

			if (fromCal.compareTo(toCal) == 1) {
				System.out.println("종료일이 시작일보다 먼저 설정되었습니다.");
				System.out.println();
				return;
			}
			String fromDate = dateFormat.format(fromCal.getTime());

			String toDate = dateFormat.format(toCal.getTime());

			System.out.println("");
			System.out.print("수정 - 제목 : ");
			String title = sc.nextLine();
			System.out.print("수정 - 상세내용 : ");
			String content = sc.nextLine();

			todo.setAllday(allDay);
			todo.setFrom_date(fromDate);
			todo.setTo_date(toDate);
			todo.setTitle(title);
			todo.setContent(content);
		}
		boolean result = toDodao.updateTodo(todo);

		if (result) {
			System.out.println("ToDO를 수정 하였습니다.");
		} else {
			System.out.println("ToDO를 수정 하지못했습니다.");
		}
	}

	// Anniversary 관리
	public void anniversary(String[] opts) throws WrongCommandExcption {
		if(!LoginSession.isLogin()) {
			System.out.println("로그인 되어있지 않습니다.");
			return;
		}
		if (opts.length == 0) {
			selectAnniversary();
		} else if (opts.length == 1) {
			if (opts[0].equals("enroll")) {
				insertAnniversary();
			} else if (opts[0].equals("search")) {
				searchAnniversary();
			} else if (opts[0].equals("modify")) {
				updateAnniversary();
			} else if (opts[0].equals("remove")) {
				deleteAnniversary();
			} else if (opts[0].equals("read")) {
				readAnniversary();
			} else {
				throw new WrongCommandExcption("anniversary");
			}
		} else {
			throw new WrongCommandExcption("anniversary");
		}
	}

	// Anniversary 등록
	private void insertAnniversary() {
		int month, day, ent;
		String title, kind;
		System.out.println("");
		System.out.println(" - 기념일 입력 - ");
		System.out.println("");
		System.out.print("기념일 월 입력: ");
		month = sc.nextInt();
		sc.nextLine();
		System.out.print("기념일 일 입력: ");
		day = sc.nextInt();
		sc.nextLine();
		if (!DateUtil.validationDate(2019, month, day)) {
			System.out.println("올바른 날짜를 입력해주세요.");
			return;
		}
		System.out.print("기념일 제목 입력: ");
		title = sc.nextLine();
		System.out.println("1. HOLIDAY / 2.BIRTHDAY ");
		System.out.print("입력: ");
		ent = sc.nextInt();
		sc.nextLine();
		if (ent == 1) {
			kind = "HOLIDAY";
		} else if (ent == 2) {
			kind = "BIRTHDAY";
		} else {
			System.out.println("올바르지 않은 입력입니다.");
			return;
		}
		Anniversary anniversary = new Anniversary();
		anniversary.setMonth(month);
		anniversary.setDay(day);
		anniversary.setTitle(title);
		anniversary.setKind(kind);
		anniversary.setAccount_no(LoginSession.getAccount().getNo());
		boolean result = anniversaryDao.insertAnniversary(anniversary);

		if (result) {
			System.out.println("기념일 등록을 등록하였습니다.");
		} else {
			System.out.println("X 기념일을 등록하지 못했습니다.");
		}
	}

	// Anniversary 전체 조회
	private void selectAnniversary() {
		ArrayList<Anniversary> list = anniversaryDao.selectAnniversary(LoginSession.getAccount().getNo());

		if (list == null || list.size() == 0) {
			System.out.println("등록된 기념일이 없습니다.");
			return;
		} else {
			System.out.println("기념일 번호  |  월  |  일  |  종류  |  제목  |  등록자");
			for (Anniversary anniversary : list) {
				System.out.print(anniversary.getNo());
				System.out.print("  |  " + anniversary.getMonth());
				System.out.print("  |  " + anniversary.getDay());
				System.out.print("  |  " + anniversary.getKind());
				System.out.print("  |  " + anniversary.getTitle());
				System.out.print("  |  " + accountDao.searchAccountByNo(anniversary.getAccount_no()).getName());
				System.out.println();
			}
		}
	}

	// Anniversary 1개 보기
	private void readAnniversary() {
		selectAnniversary();
		Anniversary anniversary = null;
		System.out.print("확인할 기념일 번호 입력  : ");
		int ent = 0;
		ent = sc.nextInt();
		sc.nextLine();
		anniversary = anniversaryDao.readAnniversary(ent, LoginSession.getAccount().getNo());
		if (anniversary == null) {
			System.out.println("해당 기념일 번호의 기념일이 없습니다.");
			return;
		}
		System.out.println("");
		System.out.println("일정 번호	: " + anniversary.getNo());
		System.out.println("기념일 제목 : " + anniversary.getTitle());
		System.out.print("해당 기념일은	: " + anniversary.getMonth() + "월   " + anniversary.getDay() + "일이며, ");
		System.out.println(anniversary.getKind() + "입니다.");
		System.out.println("------------------------------------");
	}

	// Anniversary 삭제
	private void deleteAnniversary() {
		selectAnniversary();
		int ent = 0;
		System.out.print("기념일 번호 입력 : ");
		ent = sc.nextInt();
		sc.nextLine();

		Anniversary anniversary = anniversaryDao.readAnniversary(ent, LoginSession.getAccount().getNo());
		if(anniversary == null) {
			System.out.println("등록된 기념일이 없습니다.");
			return;
		}
		
		if (anniversary.getAccount_no() != LoginSession.getAccount().getNo()) {
			System.out.println("해당 기념일을 삭제할 수 없습니다.");
			return;
		}
		boolean result = anniversaryDao.deleteAnniversary(ent);

		if (result) {
			System.out.println(ent + "번 기념일이 삭제 되었습니다.");
		} else {
			System.out.println(ent + "번 기념일 삭제가 실패하였습니다.");
		}
	}

	// Anniversary 검색
	private void searchAnniversary() {
		int col = 0;
		String word = null;
		System.out.println("기념일 검색 조건 : 1.월  2.종류  3.기념일 제목");
		System.out.println("선택 : ");
		col = sc.nextInt();
		if (col == 1) {
			System.out.print("년 : ");
			int year = sc.nextInt();
			System.out.print("월 : ");
			int month = sc.nextInt();
			System.out.print("일 : ");
			int day = sc.nextInt();
			Calendar calendar = Calendar.getInstance();
			calendar.set(year, month - 1, day);
			word = dateFormat.format(calendar.getTime());
		} else if (col == 2 || col == 3) {
			System.out.print("검색어 : ");
			word = sc.nextLine();
		} else {
			System.out.println("올바르지 않은 입력입니다.");
		}
		System.out.print("선택 : ");
		col = sc.nextInt();
		System.out.print("검색어 : ");
		sc.nextLine();
		word = sc.nextLine();
		sc.nextLine();
		ArrayList<Anniversary> list = anniversaryDao.searchAnniversary(col, word, LoginSession.getAccount().getNo());

		if (list == null || list.size() == 0) {
			System.out.println("검색 결과가 없습니다.");
			return;
		} else {
			for (Anniversary anniversary : list) {
				System.out.println("");
				System.out.println("일정 번호	: " + anniversary.getNo());
				System.out.println("기념일 제목 : " + anniversary.getTitle());
				System.out.print("해당 기념일은 " + anniversary.getMonth() + "월   " + anniversary.getDay() + "일");
				System.out.println(" is " + anniversary.getKind());
				System.out.println("------------------------------------");
			}
		}
	}

	// Anniversary 업데이트
	private void updateAnniversary() {
		selectAnniversary();
		System.out.print("기념일 번호 입력  : ");
		int month, day, n = 0;
		String title;

		n = sc.nextInt();
		sc.nextLine();

		Anniversary anniversary = anniversaryDao.readAnniversary(n, LoginSession.getAccount().getNo());
		if (anniversary == null) {
			System.out.println("등록되지 않은 기념일입니다.");
		} else if (anniversary.getAccount_no() != LoginSession.getAccount().getNo()) {
			System.out.println("해당 기념일을 변경할 수 없습니다.");
			return;
		}

		System.out.println("1. 월    2. 일    3. 제목    4. 종류    5. 전체수정    6. 나가기");
		System.out.print("선택 : ");
		int ent = 0;
		ent = sc.nextInt();
		sc.nextLine();
		if (ent == 6) {
			return;
		} else if (ent == 1) {
			System.out.print("수정 - 월 입력 : ");
			month = sc.nextInt();
			sc.nextLine();
			if (month <= 0 || month > 12) {
				System.out.println("올바른 월을 입력해주세요");
				return;
			}
			anniversary.setMonth(month);
		} else if (ent == 2) {
			System.out.print("수정 - 일 입력	: ");
			day = sc.nextInt();
			sc.nextLine();
			if (!DateUtil.validationDate(2019, anniversary.getMonth(), day)) {
				System.out.println("올바른 일을 입력해주세요");
				return;
			}
			anniversary.setDay(day);
		} else if (ent == 3) {
			System.out.print("수정 - 제목 입력	: ");
			title = sc.nextLine();
			anniversary.setTitle(title);
		} else if (ent == 4) {
			System.out.println("종류 : 1. HOLIDAY / 2.BIRTHDAY ");
			System.out.print("선택 : ");
			ent = sc.nextInt();
			sc.nextLine();
			String kind = anniversary.getKind();
			if (ent == 1) {
				kind = "HOLIDAY";
			} else if (ent == 2) {
				kind = "BIRTHDAY";
			} else {
				System.out.println("올바르지 않은 종류입니다.");
				return;
			}
			anniversary.setKind(kind);
		} else if (ent == 5) {
			System.out.print("수정 - 월 입력 : ");
			month = sc.nextInt();
			sc.nextLine();
			if (month <= 0 || month > 12) {
				System.out.println("올바른 월을 입력해주세요");
				return;
			}
			System.out.print("수정 - 일 입력: ");
			day = sc.nextInt();
			sc.nextLine();
			if (!DateUtil.validationDate(2019, anniversary.getMonth(), day)) {
				System.out.println("올바른 일을 입력해주세요");
				return;
			}
			System.out.print("수정 - 제목 입력 : ");
			title = sc.nextLine();
			System.out.println("종류: 1. HOLIDAY / 2.BIRTHDAY ");
			System.out.print("선택: ");
			ent = sc.nextInt();
			sc.nextLine();
			String kind = anniversary.getKind();
			if (ent == 1) {
				kind = "HOLIDAY";
			} else if (ent == 2) {
				kind = "BIRTHDAY";
			} else {
				System.out.println("올바르지 않은 종류입니다.");
				return;
			}

			anniversary.setMonth(month);
			anniversary.setDay(day);
			anniversary.setTitle(title);
			anniversary.setKind(kind);
		} else {
			System.out.println("올바르지 않은 입력입니다.");
			return;
		}
		boolean result = anniversaryDao.updateAnniversary(anniversary);

		if (result) {
			System.out.println("기념일 수정이 완료 되었습니다.");
		} else {
			System.out.println("기념일 수정이 실패 하였습니다.");
		}
	}
}
