package util;

public class DateUtil {

	private static int[] days = { 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };

	public static boolean validationDate(int year, int month, int day) {
		if (((year % 4 == 0) && (year % 100 != 0)) || (year % 400 == 0)) {
			days[1] = 29;
		}
		if (year < 0) {
			return false;
		}
		if (month <= 0 || month > 12) {
			return false;
		}
		if (day > days[month - 1] || day <= 0) {
			return false;
		}
		return true;
	}

	public static boolean validationTime(int hour, int minuite) {
		if (hour < 0 || hour > 24) {
			return false;
		}
		if (minuite < 0 || minuite >= 60) {
			return false;
		}
		return true;
	}

}
