package com.medlinker.dentist.uac.api.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.google.common.base.Preconditions;

/**
 * <p>
 * Title: <font color=red></font> Description: Copyright: Copyright (c) 2002
 * Company: www.myniko.com
 * 
 * @author <font color=red>niko </font>
 * @version 1.0
 */
public class DateTimeUtil {

	private static final Calendar rightNow = Calendar.getInstance();

	private static final String format1 = "yyyy-MM-dd HH:mm:ss";

	private static final String format2 = "yyyy-MM-dd";

	private static final String format3 = "yyyyMMddHHmmss";
	
	private static final String format4 = "yyyy";

	private static final SimpleDateFormat dateFormat3 = new SimpleDateFormat(
			format3);

	private static final SimpleDateFormat dateFormat1 = new SimpleDateFormat(
			format1);

	private static final SimpleDateFormat dateFormat2 = new SimpleDateFormat(
			format2);
	
	private static final SimpleDateFormat dateFormat4 = new SimpleDateFormat(
			format4);

	public DateTimeUtil() {
	}
	public static String getNowDate(String format){
		SimpleDateFormat dateFormat = new SimpleDateFormat(
				format);
		return dateFormat.format(new Date());
	}
	public static Date parseDate(String time) {
		try {
			return dateFormat2.parse(time);
		} catch (Exception ex) {
		}
		return null;
	}
	public static long getTimeStamp(String time) throws ParseException {
		return dateFormat2.parse(time).getTime();
	}

	public static long getTimeStamp(String time, int format)
			throws ParseException {
		if (format == 1) {
			return dateFormat1.parse(time).getTime();
		} else if (format == 2) {
			return dateFormat2.parse(time).getTime();
		} else if (format == 3) {
			return dateFormat3.parse(time).getTime();
		} else {
			return dateFormat2.parse(time).getTime();
		}
	}

	public static String getTime(long amp) throws ParseException {
		return dateFormat2.format(new Date(amp));
	}

	public static String getTime(Date date) {
		return dateFormat2.format(date);
	}

	public static String getTime(long timeStamp, int format){
		if (format == 1) {
			return dateFormat1.format(new Date(timeStamp));
		} else if (format == 2) {
			return dateFormat2.format(new Date(timeStamp));
		} else {
			return dateFormat2.format(new Date(timeStamp));
		}
	}

	public static long getTime(int days) {
		Calendar now = Calendar.getInstance();
		now.add(Calendar.DAY_OF_YEAR, -(days));
		return now.getTimeInMillis();
	}

	public static List getToday(long time) throws ParseException {
		List list = new ArrayList();

		String date = dateFormat2.format(time);
		String end = date + " 24:00:00";
		String begin = date + " 00:00:00";

		list.add(0, dateFormat1.parse(begin).getTime());
		list.add(1, dateFormat1.parse(end).getTime());

		return list;
	}

	public static List getTodayForString() {
		List list = new ArrayList();
		Calendar now = Calendar.getInstance();
		String date = dateFormat2.format(now.getTime());
		String end = date + " 24:00:00";
		String begin = date + " 00:00:00";
		list.add(0, begin);
		list.add(1, end);
		return list;
	}

	public static List getHours() throws ParseException {
		List list = new ArrayList();
		String date = dateFormat2.format(System.currentTimeMillis());

		String h0 = date + " 00:00:00";
		String h1 = date + " 01:00:00";
		String h2 = date + " 02:00:00";
		String h3 = date + " 03:00:00";
		String h4 = date + " 04:00:00";
		String h5 = date + " 05:00:00";
		String h6 = date + " 06:00:00";
		String h7 = date + " 07:00:00";
		String h8 = date + " 08:00:00";
		String h9 = date + " 09:00:00";
		String h10 = date + " 10:00:00";
		String h11 = date + " 11:00:00";
		String h12 = date + " 12:00:00";
		String h13 = date + " 13:00:00";
		String h14 = date + " 14:00:00";
		String h15 = date + " 15:00:00";
		String h16 = date + " 16:00:00";
		String h17 = date + " 17:00:00";
		String h18 = date + " 18:00:00";
		String h19 = date + " 19:00:00";
		String h20 = date + " 20:00:00";
		String h21 = date + " 21:00:00";
		String h22 = date + " 22:00:00";
		String h23 = date + " 23:00:00";
		String h24 = date + " 24:00:00";

		list.add(0, dateFormat1.parse(h0).getTime());
		list.add(1, dateFormat1.parse(h1).getTime());
		list.add(2, dateFormat1.parse(h2).getTime());
		list.add(3, dateFormat1.parse(h3).getTime());
		list.add(4, dateFormat1.parse(h4).getTime());
		list.add(5, dateFormat1.parse(h5).getTime());
		list.add(6, dateFormat1.parse(h6).getTime());
		list.add(7, dateFormat1.parse(h7).getTime());
		list.add(8, dateFormat1.parse(h8).getTime());
		list.add(9, dateFormat1.parse(h9).getTime());
		list.add(10, dateFormat1.parse(h10).getTime());
		list.add(11, dateFormat1.parse(h11).getTime());
		list.add(12, dateFormat1.parse(h12).getTime());
		list.add(13, dateFormat1.parse(h13).getTime());
		list.add(14, dateFormat1.parse(h14).getTime());
		list.add(15, dateFormat1.parse(h15).getTime());
		list.add(16, dateFormat1.parse(h16).getTime());
		list.add(17, dateFormat1.parse(h17).getTime());
		list.add(18, dateFormat1.parse(h18).getTime());
		list.add(19, dateFormat1.parse(h19).getTime());
		list.add(20, dateFormat1.parse(h20).getTime());
		list.add(21, dateFormat1.parse(h21).getTime());
		list.add(22, dateFormat1.parse(h22).getTime());
		list.add(23, dateFormat1.parse(h23).getTime());
		list.add(24, dateFormat1.parse(h24).getTime());
		return list;
	}

	public static List getWorkTime() throws ParseException {
		List list = new ArrayList();

		String begin = dateFormat2.format(getTime(1)) + " 12:00:00";
		String end = dateFormat2.format(getTime(0)) + " 12:00:00";

		list.add(0, dateFormat1.parse(begin).getTime());
		list.add(1, dateFormat1.parse(end).getTime());

		return list;
	}
	
	public static List getMonthDay() throws ParseException {
		List list = new ArrayList();

		String day_end = dateFormat2.format(System.currentTimeMillis());
		String day_endtemp = day_end.substring(8, 10);
		String day_temp = day_end.substring(0, 8);
		int dayendtemp = 0;
		dayendtemp = Integer.parseInt(day_endtemp);
		for (int i = 1; i <= dayendtemp; i++) {
			list.add(day_temp + i);
		}

		return list;
	}

	public static List getMonthBeginandEnd() throws ParseException {
		List list = new ArrayList();
		String day_end = dateFormat2.format(System.currentTimeMillis());
		String month_temp = day_end.substring(0, 8);
		month_temp = month_temp + "1 00:00:00";
		day_end = day_end + " 24:00:00";
		list.add(month_temp);
		list.add(day_end);
		// System.out.println(DateTimeUtil.getTimeStamp(month_temp, 1));
		return list;
	}
	
	/**
	 * 获取对应的毫秒区间(前闭后开)
	 * @param year 指定年
	 * @param month 指定月
	 * @return 单位（毫秒）
	 */
	public static long[] getMonthRangeInMillis(int year, int month) {
		Preconditions.checkArgument(year > 0, "Invalid year argument: %s", year);
		Preconditions.checkArgument(month >= 1 & month <= 12, "Invalid month argument: %s", month);
		
		long[] longPair = new long[2];
		
		DateFormat formatter = new SimpleDateFormat("yyyy-MM");

		try {
			String beginOfMonth = year + "-" + month;
			Date date = formatter.parse(beginOfMonth);
			longPair[0] = date.getTime();
			
			String beginOfNextMonth = (month == 12 ? (year + 1) + "-1" : year + "-" + (month + 1));
			date = formatter.parse(beginOfNextMonth);
			longPair[1] = date.getTime();
		} catch (ParseException pe) {
			throw new RuntimeException("SimpleDateFormat将字符串转换成日期异常: " + year + "_" + month, pe.getCause());
		}
		
		return longPair;
	}
	
	/**
	 * 获取对应的毫秒区间(前闭后开)
	 * @param year 指定年
	 * @param month 指定月
	 * @return 单位（秒）
	 */
	public static long[] getMonthRangeInSeconds(int year, int month) {
		long[] longPair = getMonthRangeInMillis(year, month);
		longPair[0] = longPair[0] / 1000;
		longPair[1] = longPair[1] / 1000;
		return longPair;
	}
	
	public static List getMonthBeginandEnd(Date date){
		List list = new ArrayList();
		Calendar   ca   =   Calendar.getInstance();   
	    ca.setTime(date);   //   someDate   为你要获取的那个月的时间   
		ca.set(Calendar.DAY_OF_MONTH,1);   
		Date   firstDate   =   ca.getTime();   
		ca.add(Calendar.MONTH,   1);   
	    ca.add(Calendar.DAY_OF_MONTH,-1);   
		Date   lastDate   =   ca.getTime();   
		
		String first = dateFormat2.format(firstDate)+" 00:00:00";
		String end = dateFormat2.format(lastDate)+" 24:00:00";
		list.add(0,first);
		list.add(1,end);
		return list;
	}

	public static List getYearBeginandEnd() throws ParseException {
		List list = new ArrayList();
		String day_end = dateFormat2.format(System.currentTimeMillis());
		String month_temp = day_end.substring(0, 5);
		month_temp = month_temp + "01-01 00:00:00";
		day_end = day_end + " 24:00:00";
		list.add(month_temp);
		list.add(day_end);

		return list;
	}
	

	public static List getYearMonth() {
		List list = new ArrayList();

		String month_end = dateFormat2.format(System.currentTimeMillis());
		String month_endtemp = month_end.substring(5, 7);

		String month_temp = month_end.substring(0, 5);

		int monthendtemp = 0;
		monthendtemp = Integer.parseInt(month_endtemp);
		for (int i = 1; i <= monthendtemp; i++) {
			System.out.println(month_temp + i + "-15");
			list.add(month_temp + i + "-15");
		}

		return list;
	}
	 public static int getYear(){
	    	Calendar cal = Calendar.getInstance();
	    	return Integer.parseInt(dateFormat4.format(cal.getTime()));
	}
	
	public static List getYearMonth(int year) {
		List list = new ArrayList();
		if(DateTimeUtil.getYear()==year){
		return DateTimeUtil.getYearMonth();
		}
		else{
			for(int i=1; i<=12; i ++){
				list.add(year +"-"+ i + "-15");
			}
		return list;
		}
	}
	public static Date convertDate(Integer temp) throws ParseException{
		if(temp== null){
			return new Date();
		}else{
			String month_end = dateFormat2.format(System.currentTimeMillis());
			String month_endtemp = month_end.substring(5, 7);
		//	int result = Integer.parseInt(month_endtemp)+(int)temp;
			String month_temp = month_end.substring(0, 5);
			String timeresult =month_temp+temp+"-15";
			
			
			return dateFormat2.parse(timeresult);
		}
		 
	}
	
	public static boolean isSameMonth(long date1, long date2){
		Calendar c1 = Calendar.getInstance();
		Calendar c2 = Calendar.getInstance();
		c1.setTimeInMillis(date1);
		c2.setTimeInMillis(date2);
		return (c1.get(Calendar.YEAR) == c2.get(Calendar.YEAR) && c1
				.get(Calendar.MONTH) == c2.get(Calendar.MONTH)); 
	}
	public static Date  getDate(String str){
		try {
			return dateFormat2.parse(str);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	public static Integer getMonthnum(){
		String month_end = dateFormat2.format(System.currentTimeMillis());
		String month_endtemp = month_end.substring(5, 7);
		return Integer.parseInt(month_endtemp);
	}
	public static List getMonthTime(long time) throws ParseException {
		List list = new ArrayList();
		Calendar ca = Calendar.getInstance();
		ca.setTimeInMillis(time); // time 为你要获取的那个月的时间
		ca.set(Calendar.DAY_OF_MONTH, 1);
		Date firstDate = ca.getTime();

		list.add(0, DateTimeUtil.getTimeStamp(dateFormat2.format(firstDate)
				.toString()));
		ca.add(Calendar.MONTH, 1);
		ca.add(Calendar.DAY_OF_MONTH, -1);
		Date lastDate = ca.getTime();
		list.add(1, DateTimeUtil.getTimeStamp(dateFormat2.format(lastDate)
				.toString()));
		return list;
	}

	public static void main(String[] argv) {
		try {
			
			//System.out.println(DateTimeUtil.getYear()==2010);
			System.out.println(DateTimeUtil.getMonthBeginandEnd());
			//System.out.println(DateTimeUtil.getMonthBeginandEnd().get(1));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

	}

	public static String formatTimestamp(Object date) {
		try {
			return (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date));

		} catch (Exception e) {
			return "";
		}

	}

	public static String timeZone(long time, long zone) {
		long hour = 3600000;
		if (zone == 1) {
			return formatTimestamp(time - 20 * hour) + "(GMT -12:00)";
		} else if (zone == 2) {
			return formatTimestamp(time - 19 * hour) + "(GMT -11:00)";
		} else if (zone == 3) {
			return formatTimestamp(time - 18 * hour) + "(GMT -10:00)";
		} else if (zone == 4) {
			return formatTimestamp(time - 17 * hour) + "(GMT -9:00)";
		} else if (zone == 5) {
			return formatTimestamp(time - 16 * hour) + "(GMT -8:00)";
		} else if (zone == 6) {
			return formatTimestamp(time - 15 * hour) + "(GMT -7:00)";
		} else if (zone == 7) {
			return formatTimestamp(time - 14 * hour) + "(GMT -6:00)";
		} else if (zone == 8) {
			return formatTimestamp(time - 13 * hour) + "(GMT -5:00)";
		} else if (zone == 9) {
			return formatTimestamp(time - 12 * hour) + "(GMT -4:00)";
		} else if (zone == 10) {
			return formatTimestamp(time - 11.5 * hour) + "(GMT -3:30)";
		} else if (zone == 11) {
			return formatTimestamp(time - 11 * hour) + "(GMT -3:00)";
		} else if (zone == 12) {
			return formatTimestamp(time - 10 * hour) + "(GMT -2:00)";
		} else if (zone == 13) {
			return formatTimestamp(time - 9 * hour) + "(GMT -1:00)";
		} else if (zone == 14) {
			return formatTimestamp(time - 8 * hour) + "(GMT)";
		} else if (zone == 15) {
			return formatTimestamp(time - 7 * hour) + "(GMT +01:00)";
		} else if (zone == 16) {
			return formatTimestamp(time - 6 * hour) + "(GMT +02:00)";
		} else if (zone == 17) {
			return formatTimestamp(time - 5 * hour) + "(GMT +03:00)";
		} else if (zone == 18) {
			return formatTimestamp(time - 4.5 * hour) + "(GMT +03:30)";
		} else if (zone == 19) {
			return formatTimestamp(time - 4 * hour) + "(GMT +04:00)";
		} else if (zone == 20) {
			return formatTimestamp(time - 3.5 * hour) + "(GMT +04:30)";
		} else if (zone == 21) {
			return formatTimestamp(time - 3 * hour) + "(GMT +05:00)";
		} else if (zone == 22) {
			return formatTimestamp(time - 2.5 * hour) + "(GMT +05:30)";
		} else if (zone == 23) {
			return formatTimestamp(time - 2.25 * hour) + "(GMT +05:45)";
		} else if (zone == 24) {
			return formatTimestamp(time - 2 * hour) + "(GMT +06:00)";
		} else if (zone == 25) {
			return formatTimestamp(time - 1.5 * hour) + "(GMT +06:30)";
		} else if (zone == 26) {
			return formatTimestamp(time - 1 * hour) + "(GMT +07:00)";
		} else if (zone == 27) {
			return formatTimestamp(time) + "(GMT +08:00)";
		} else if (zone == 28) {
			return formatTimestamp(time + 1 * hour) + "(GMT +09:00)";
		} else if (zone == 29) {
			return formatTimestamp(time + 1.5 * hour) + "(GMT +09:30)";
		} else if (zone == 30) {
			return formatTimestamp(time + 2 * hour) + "(GMT +10:00)";
		} else if (zone == 31) {
			return formatTimestamp(time + 3 * hour) + "(GMT +11:00)";
		} else if (zone == 32) {
			return formatTimestamp(time + 4 * hour) + "(GMT +12:00)";
		} else {
			return "";
		}
	}
	/**
	 * 毫秒时间戳转换为日期字符串
	 * @param millis
	 * @return
	 */
	public static String parseDateByLong(long millis) {
		String dateStr = null;
		try {
			Date date = new Date(millis);
			dateStr = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return dateStr;
	}
	public static String getFormatTime(Date d){
		if(d == null) return "";
		
		return dateFormat2.format(d);
	}
}