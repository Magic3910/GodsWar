package godswar.godswar.utils.base;

import java.util.Calendar;

public class Seasons {

	private static final Calendar calendar = Calendar.getInstance();

	static {
		calendar.setTimeInMillis(System.currentTimeMillis());
	}

	private Seasons() {}

	/**
	 * 크리스마스 시즌: 12월 18일 ~ 12월 31일
	 * @return  크리스마스 시즌일 경우 true, 아닐 경우 false
	 */
	public static boolean isChristmas() {
		if (calendar.get(Calendar.MONTH) != Calendar.DECEMBER) return false;
		final int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
		return dayOfMonth >= 18;
	}

	/**
	 * 연말연시 시즌: 12월 1일 ~ 1월 1일
	 * @return  크리스마스 시즌일 경우 true, 아닐 경우 false
	 */
	public static boolean isFestive() {
		return calendar.get(Calendar.MONTH) == Calendar.DECEMBER || (calendar.get(Calendar.MONTH) == Calendar.JANUARY && calendar.get(Calendar.DAY_OF_MONTH) == 1);
	}

}
