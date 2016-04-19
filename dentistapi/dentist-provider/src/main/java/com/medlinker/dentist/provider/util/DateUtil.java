package com.medlinker.dentist.provider.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * 涉及日期相关的处理函数
 *
 */
public class DateUtil{

	/**
	 * 根据出生日期计算年龄
	 * 
	 * @param csrq
	 *            　出生日期
	 * @return
	 * @throws Exception
	 * @author 武红斌
	 */
	public static int getAge(String sysdate,String csrq) throws Exception {
		Date birthDay = praseDate(csrq);
		Date now=praseDate(sysdate);
		Calendar cal = Calendar.getInstance();
        cal.setTime(now);
		if (cal.before(birthDay)) {
			throw new IllegalArgumentException("出生日期异常!");
		}
		int yearNow = cal.get(Calendar.YEAR);
		int monthNow = cal.get(Calendar.MONTH);
		int dayOfMonthNow = cal.get(Calendar.DAY_OF_MONTH);
		cal.setTime(birthDay);

		int yearBirth = cal.get(Calendar.YEAR);
		int monthBirth = cal.get(Calendar.MONTH);
		int dayOfMonthBirth = cal.get(Calendar.DAY_OF_MONTH);

		int age = yearNow - yearBirth;

		if (monthNow <= monthBirth) {
			if (monthNow == monthBirth) {
				if (dayOfMonthNow < dayOfMonthBirth) {
					age--;
				} else {
				}
			} else {
				age--;
			}
		} else {
		}

		return age;
	}
	/**
	 * 将YYYY-mm-dd的字符串转化为日期对象
	 * @param dateString YYYY-mm-dd的字符串
	 * @return 日期对象,格式不正确返回NULL
	 */
	public static java.sql.Date praseDate(String dateString){
		return praseDate(dateString,"yyyy-MM-dd");
	}
	
	public static java.sql.Date praseDate(String dateString,String format){
		SimpleDateFormat formatter=new SimpleDateFormat(format);
		Date d=null;
		if(dateString==null||dateString.equals("")||dateString.toLowerCase().equals("null")){ return null; }
		try{
			d=formatter.parse(dateString);
		}catch(ParseException e){
			e.printStackTrace();
		}
		return new java.sql.Date(d.getTime());
	}
	
	/**
	 * 格式化db取出的日期时间为yyyy-MM-dd
	 * 
	 * @param strDateTime
	 *            String
	 * @return String
	 */
	public static String formatDate(String strDateTime) {
		if("--".equals(strDateTime)){
			return strDateTime;
		}
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		if (strDateTime != null && !strDateTime.equals("")) {
			try {
				strDateTime = format.format(format.parse(strDateTime));
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		} else {
			strDateTime = "";
		}
		return strDateTime;
	}
	/**
	 * 格式化db取出的日期时间为yyyy-MM-dd
	 * 
	 * @param strDateTime
	 *            String
	 * @return String
	 */
	public static String formatDateMinute(String strDateTime) {
		if("--".equals(strDateTime)){
			return strDateTime;
		}
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		if (strDateTime != null && !strDateTime.equals("")) {
			try {
				strDateTime = format.format(format.parse(strDateTime));
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		} else {
			strDateTime = "";
		}
		return strDateTime;
	}
	/**
	 * 日期加年
	 * @param strDateTiem
	 * @param i
	 * @return
	 */
	public String addYears(String strDateTiem,int i){
		String result="",temp="";
		try{
			strDateTiem = formatDate(strDateTiem);
			temp = strDateTiem.substring(0,4);
			temp= Integer.parseInt(temp)+i+"";
			result = temp+strDateTiem.substring(4);
		}catch (Exception ex) {
			ex.printStackTrace();
		}
		return result;
	}
	
	/**
	 * 将时间字符串转化为时间对象
	 * @param dateString yyyy-MM-dd HH:mm:ss或yyyy-MM-dd HH:mm或YYYY-mm-dd格式的字符串
	 * @return 时间对象，格式不正确返回NULL
	 */	
	public static java.sql.Timestamp praseTimestamp(String dateString){
		String format="yyyy-MM-dd HH:mm:ss";
		if(dateString.trim().length()>=17){
			format="yyyy-MM-dd HH:mm:ss";
		}else if(dateString.trim().length()>=14){
			format="yyyy-MM-dd HH:mm";
		}else if(dateString.trim().length()>=10){
			format="yyyy-MM-dd";
		}else{
			return null;
		}
		SimpleDateFormat formatter=new SimpleDateFormat(format);
		Date d=null;
		try{
			d=formatter.parse(dateString);
		}catch(ParseException e){
			e.printStackTrace();
		}
		return new java.sql.Timestamp(d.getTime());
	}
	
	/**
	 * 将日期对象转化为yyy-mm-dd的字符串
	 * @param curTime 日期对象
	 * @return 日期字符串
	 */		
	public static String formatDate(Date curTime){
		if(curTime==null){
			return " ";
		}else{
			SimpleDateFormat formatter=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			return formatter.format(curTime);
		}
	}	
	/**
	 * 将日期对象转化为指定日期格式的的字符串
	 * @param curTime 日期对象
	 * @return 日期字符串
	 */		
	public static String formatDate(Date curTime,String format){
		if(curTime==null){
			return " ";
		}else{
			SimpleDateFormat formatter=new SimpleDateFormat(format);
			return formatter.format(curTime);
		}
	}	
	/**
	 * 使用默认解析格式解析传入日期的字串，返回yyyy-MM-dd的固定格式
	 * @param curTimeStr 日期字符串，含有 yyyy-MM-dd 信息，如 2009-12-29 00:00:00
	 * @param format     需要返回的格式形式，一般为yyyy-MM-dd 
	 * @return
	 */
	public static String formatDate(String curTimeStr,String format){
		if("--".equals(curTimeStr)){
			return curTimeStr;
		}
		if(curTimeStr==null){
			return " ";
		}else if(curTimeStr.equals("")){
			return " ";
		}else{
			Date curTime=praseDate(curTimeStr,"yyyy-MM-dd");
			SimpleDateFormat formatter=new SimpleDateFormat(format);
			return formatter.format(curTime);
		}
	}	
	/**
	 * 使用指定解析格式解析传入日期字串，并返回为指定的格式
	 * @param curTimeStr  日期字符串，含有 yyyy-MM-dd 信息，如 2009-12-29 00:00:00
	 * @param praseFormat  指定解析格式，一般为yyyy-MM-dd 
	 * @param format       需要返回的格式形式，一般为yyyy-MM-dd，yyyy'年'MM'月'dd'日，yyyy'年'MM'月'dd'日'HH'时'mm'分'等
	 * @return
	 */
	public static String formatDate(String curTimeStr,String praseFormat,String format){
		if("--".equals(curTimeStr)){
			return curTimeStr;
		}
		if(curTimeStr==null){
			return " ";
		}else{
			Date curTime=praseDate(curTimeStr,praseFormat);
			SimpleDateFormat formatter=new SimpleDateFormat(format);
			return formatter.format(curTime);
		}
	}
	
	
	/**
	 * 使用默认解析格式解析传入日期的字串，返回yyyy-MM-dd hh24:mi:ss.0的固定格式
	 * @param curTimeStr 日期字符串，含有 yyyy-MM-dd yyyy-MM-dd hh:mm:ss信息，如 2009-12-29 00:00:00
	 * @return
	 */
	public static String formatDateTime(String curTimeStr){
		if("--".equals(curTimeStr)){
			return curTimeStr;
		}
		String strTmp=curTimeStr;
		try {
			if (curTimeStr!=null && !curTimeStr.equals("")&& !curTimeStr.equals(" ")){
				if (curTimeStr.length()==10){
					strTmp=formatDate(curTimeStr,"yyyy-MM-dd","yyyy-MM-dd");
				}else{
					if(curTimeStr.length()==16){
						strTmp=formatDate(curTimeStr,"yyyy-MM-dd HH:mm","yyyy-MM-dd HH:mm");
					}else{
						strTmp=formatDate(curTimeStr,"yyyy-MM-dd HH:mm:ss","yyyy-MM-dd HH:mm:ss");
					}	
				}
				if (strTmp!=null && strTmp.length()==19 && strTmp.endsWith("00:00:00")){
					strTmp=strTmp.substring(0, 10);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return strTmp;
	}	
	
	/**
	 * 将英文格式日期字符串转换为日期中文字符串
	 * @param timestr 英文格式字符串 yyyy-MM-dd HH:mm或YYYY-mm-dd格式的字符串
	 * @return 中文格式日期字符串
	 */			
	public static String formatDateZh(String timestr){
		String format_mm="yyyy'年'MM'月'dd'日'HH'时'mm'分'";
		String format_dd="yyyy'年'MM'月'dd'日'";
		String result=timestr;
		try{
			Date curTime=null;
			String format=format_dd;
			if(timestr.length()>10){
				curTime=praseTimestamp(timestr);
				format=format_mm;
			}else{
				curTime=praseDate(timestr);
			}
			SimpleDateFormat formatter=new SimpleDateFormat(format);
			result=formatter.format(curTime);
		}catch(Exception e){
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * 将英文格式日期字符串转换为日期中文字符串 
	 * @param timestr 英文格式字符串 yyyy-MM-dd HH:mm或YYYY-mm-dd格式的字符串
	 * @return 中文格式日期字符串
	 * @author xuxiaodong 
	 * 主要用于法律文书打印，剔除多余的0
	 */			
	public static String formatDateZh_print(String timestr){
		String format_mm="yyyy'年'M'月'd'日'H'时'm'分'";
		String format_dd="yyyy'年'M'月'd'日'";
		String result=timestr;
		try{
			Date curTime=null;
			String format=format_dd;
			if(timestr.length()>10){
				curTime=praseTimestamp(timestr);
				format=format_mm;
			}else{
				curTime=praseDate(timestr);
			}
			SimpleDateFormat formatter=new SimpleDateFormat(format);
			result=formatter.format(curTime);
		}catch(Exception e){
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * 将英文格式日期字符串转换为日期中文字符串 
	 * @param timestr 英文格式字符串YYYY-mm-dd格式的字符串
	 * @return 中文格式日期字符串
	 * @author xuxiaodong 
	 * 主要用于法律文书打印，剔除多余的0
	 */			
	public static String formatDateZh1_print(String timestr){
		String format_dd="yyyy'年'M'月'd'日'";
		String result=timestr;
		try{
			Date curTime=null;
			String format=format_dd;
			curTime=praseDate(timestr);
			SimpleDateFormat formatter=new SimpleDateFormat(format);
			result=formatter.format(curTime);
		}catch(Exception e){
			e.printStackTrace();
		}
		return result;
	}
	
	
	public static String formatDateTimeStr(java.util.Calendar calendar){
		String result = (new   SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(calendar.getTime());
		return result;
	}
	public static void main(String a[]){
		/*
		 * String d=""; d="2008-09-03"; System.out.println(d+" ->
		 * "+formatDateZh(d)); d="2008-09-03 23:12"; System.out.println(d+" ->
		 * "+formatDateZh(d)); d="2008-09-03 23:12:23"; System.out.println(d+" ->
		 * "+formatDateZh(d)); d="2008-09-03 11:12:23"; System.out.println(d+" ->
		 * "+formatDateZh(d)); d="2008-09-03 "; System.out.println(d+" ->
		 * "+formatDateZh(d)); d=null; System.out.println(d+" -> "+formatDateZh(d));
		 * d=""; System.out.println(d+" -> "+formatDateZh(d)); d=" ";
		 * System.out.println(d+" -> "+formatDateZh(d)); d=" ";
		 * System.out.println(d+" -> "+formatDateZh(d));
		 */
		/*
		System.out.println(praseTimestamp("2009-3-10 19:09"));
		System.out.println(praseTimestamp("2008-12-11 12:30:30"));
		System.out.println(praseTimestamp("2008-12-11 12:30:30.0"));
		System.out.println(praseTimestamp("2008-12-11"));
		
		System.out.println(formatDate("2009-12-29 19:09","yyyy-MM-dd"));
		System.out.println(formatDate("2009-12-29 00:00:00","yyyy-MM-dd"));
		System.out.println(formatDate("2009-12-29","yyyy-MM-dd"));
		System.out.println(formatDate("2009-12-29 00:00:00","yyyy-MM-dd","yyyy'年'MM'月'dd'日'"));
		
		Timestamp ts=praseTimestamp("");
		System.out.println(ts);
		if(ts==null){
			System.out.println("ts is null");
		}
		ts=praseTimestamp("2008");
		System.out.println(ts);
		if(ts==null){
			System.out.println("ts is null");
		}
		ts=praseTimestamp("xxxx");
		System.out.println(ts);
		if(ts==null){
			System.out.println("ts is null");
		}
		*/
		String sfzmhm = "身份证号码";
		System.out.println(sfzmhm.indexOf("身份证号码"));
		//System.out.println("19"+sfzmhm.substring(6,8)+"-"+sfzmhm.substring(8,10)+"-"+sfzmhm.substring(10,12));	
		//System.out.println(sfzmhm.substring(14,15));
	}
	
    /**
     * 比较两个日期的天数差
     * @param dateStr String
     * @return Date
     * @author chiva.s
     */
    public static long compareDate(String s1,String s2) {
    	long DAY = 24L * 60L * 60L * 1000L; 
    	Date d1 = praseDate(s1);    
    	Date d2 = praseDate(s2);
    	return ( d2.getTime() - d1.getTime() ) / DAY ;
    }
    
    /**
     * 特定日期加特定天数
     * @param nDate String      形如 'yyyy-MM-dd' 的字符串
     * @param nNumberOfDay int  天数
     * @return String
     */
    public static String addDay(String nDate, int nNumberOfDay) {
      String a[] = nDate.split("-");
      SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
      GregorianCalendar gc = new GregorianCalendar(Integer.parseInt(a[0]),
                                                   Integer.parseInt(a[1]) - 1,
                                                   Integer.parseInt(a[2]));
      gc.add(Calendar.DATE, nNumberOfDay);
      return formatter.format(gc.getTime());
    }
    public static String getSystime(){
    	 Date dt=new Date();//如果不需要格式,可直接用dt,dt就是当前系统时间
       DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置显示格式
       String nowTime="";
      nowTime= df.format(dt);//用DateFormat的format()方法在dt中获取并以yyyy/MM/dd HH:mm:ss格式显示
      return nowTime;
    }

	public static String numtochinese(String str) throws Exception{
		String[] chinese={"0","一","二","三","四","五","六","七","八","九"};
		String strResult="";
		// 将单个数字转成中文.
		int slen=str.length();
		strResult="";
		for(int i=0;i<slen;i++){
			strResult=strResult+chinese[Integer.parseInt(str.charAt(i)+"")];
		}
		return strResult;
	}

	public static String numToCharacter(String str) throws Exception{
		String date="";
		String mmChar="";
		String ddChar="";
		if(str==null||str.length()<10){
			return date;
		}else{
			String mm=str.substring(5,7);
			String dd=str.substring(8,10);
			if(mm.substring(0,1).equals("0")){
				mm=mm.substring(1,2);
			}
			if(mm.length()==2){
				if(mm.substring(0,1).equals("1")){
					if(mm.substring(1,2).equals("0")){
						mmChar="十";
					}else{
						mmChar="十"+numtochinese(mm.substring(1,2));
					}
				}else{
					if(mm.substring(1,2).equals("0"))
						mmChar=numtochinese(mm.substring(0,1))+"十";
					else mmChar=numtochinese(mm.substring(0,1))+"十"+numtochinese(mm.substring(1,2));
				}
			}else{
				mmChar=numtochinese(mm.substring(0,1));
			}
			if(dd.substring(0,1).equals("0")){
				dd=dd.substring(1,2);
			}
			if(dd.length()==2){
				if(dd.substring(0,1).equals("1")){
					if(dd.substring(1,2).equals("0")){
						ddChar="十";
					}else{
						ddChar="十"+numtochinese(dd.substring(1,2));
					}
				}else{
					if(dd.substring(1,2).equals("0"))
						ddChar=numtochinese(dd.substring(0,1))+"十";
					else ddChar=numtochinese(dd.substring(0,1))+"十"+numtochinese(dd.substring(1,2));
				}

			}else{
				ddChar=numtochinese(dd.substring(0,1));
			}
			return numtochinese(str.substring(0,4))+"年"+mmChar+"月"+ddChar+"日";

		}
	}

	// 根据传进来的年份月份日生成相应的中文
	public static String numToCharacterPar(String str) throws Exception{
		String mmChar="";
		if(str.length()==4){
			return numtochinese(str);
		}else if(str.length()==2){
			if(str.substring(0,1).equals("0")){
				str=str.substring(1,2);
			}
			if(str.length()==2){
				if(str.substring(0,1).equals("1")){
					if(str.substring(1,2).equals("0")){
						mmChar="十";
					}else{
						mmChar="十"+numtochinese(str.substring(1,2));
					}
				}else{
					if(str.substring(1,2).equals("0"))
						mmChar=numtochinese(str.substring(0,1))+"十";
					else mmChar=numtochinese(str.substring(0,1))+"十"+numtochinese(str.substring(1,2));
				}
			}else{
				mmChar=numtochinese(str.substring(0,1));
			}
			return mmChar;
		}else if(str.length()==10){
			return numToCharacter(str);
		}else return "";
	}

	// 根据时间返回带年月日的格式
	public static String getNyr(String date) throws Exception{
		if(date.length()==10){ // 类似2005-02-01
			return date.substring(0,4)+"年"+date.substring(5,7)+"月"+date.substring(8,10)+"日";
		}else if(date.length()==16){ // 类似2005-02-01 01:01
			return date.substring(0,4)+"年"+date.substring(5,7)+"月"+date.substring(8,10)+"日"+date.substring(11,13)+"时"+date.substring(14,16)+"分";
		}else if(date.length()==18){ // 18位身份证号
			return date.substring(6,10)+"年"+date.substring(10,12)+"月"+date.substring(12,14)+"日";
		}else if(date.length()==15){ // 15位身份证号
			return "19"+date.substring(6,8)+"年"+date.substring(8,10)+"月"+date.substring(10,12)+"日";
		}else{
			return "";
		}
	}
}
