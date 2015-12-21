/**
 * @author hty
 * @time 上午9:39:37
 * @date 2014年12月5日
 */
package com.beyondsphere.util;

import java.net.URLEncoder;
import java.security.MessageDigest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONObject;

public class Utilities {
	// get random mac
		public static String randomMac() {
			String macAddr = "00:16:3e";
			int min = 0x00;
			int max = 0x7f;
			Random random = new Random(System.currentTimeMillis());
			int addr = random.nextInt(max - min + 1) + min;
			String addrStr = String.format("%02x", addr);
			macAddr += (":" + addrStr);
			max = 0xff;
			for (int i = 0; i < 2; i++) {
				addr = random.nextInt(max - min + 1) + min;
				addrStr = String.format("%02x", addr);
				macAddr += (":" + addrStr);
			}
			return macAddr;
		}


	// 格式化日期和时间
	public static String formatTime2String(Date dateTime,String formatString) {
		SimpleDateFormat sdf = new SimpleDateFormat(formatString);
		return sdf.format(dateTime);
	}
	
	/**
	 * 获取前n月的第一天
	 * @return date
	 */
	public static Date getFirstDayOfMonth(int n){
        //获取前月的第一天
        Calendar cal_1=Calendar.getInstance();//获取当前日期
        cal_1.add(Calendar.MONTH, -n);
        cal_1.set(Calendar.DAY_OF_MONTH,1);//设置为1号,当前日期既为本月第一天
        cal_1.set(Calendar.HOUR_OF_DAY,0);
        cal_1.set(Calendar.MINUTE,0);
        cal_1.set(Calendar.SECOND,0);
        return cal_1.getTime();
	}

	// 将时间戳转换成日期和时间
	public static String Timestamp2DateTime(long timestamp) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateTime = format.format(timestamp);
		return dateTime;
	}

	// 将日期和时间转换成时间戳
	public static long DateTime2Timestamp(String dateTime) throws Exception {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = format.parse(dateTime);
		return date.getTime();
	}

	public static Date Timestamp2Date(long timestamp) throws ParseException {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = format.parse(Utilities.Timestamp2DateTime(timestamp));
		return date;
	}
	
	//字符串转换成日期
	public static Date stringToDate(String dateString){
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date date = null;
		try {
			if (dateString!=null&&dateString.length()!=0) {
				date = format.parse(dateString);
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}
	
	// 格式化日期和时间
	public static String formatTime(Date dateTime) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.format(dateTime);
	}

	public static Date String2Month(String month) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");
		Date date = null;
		try {
			date = format.parse(month);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

	// 保留小数点后两位的小数
	public static double Round2(double value) {
		return (double) (Math.round(value * 100) / 100.0);
	}

	public static Date AddMinuteForDate(Date date, int minute) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.MINUTE, minute);
		return calendar.getTime();
	}

	public static Date AddMonthForDate(Date date, int month) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.MONTH, 1);
		return calendar.getTime();
	}

	public final static String MD5(String pwd) {
		// 用于加密的字符
		char md5String[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
				'A', 'B', 'C', 'D', 'E', 'F' };
		try {
			// 使用平台的默认字符集将此 String 编码为 byte序列，并将结果存储到一个新的 byte数组中
			byte[] btInput = pwd.getBytes();

			// 获得指定摘要算法的 MessageDigest对象，此处为MD5
			// MessageDigest类为应用程序提供信息摘要算法的功能，如 MD5 或 SHA 算法。
			// 信息摘要是安全的单向哈希函数，它接收任意大小的数据，并输出固定长度的哈希值。
			MessageDigest mdInst = MessageDigest.getInstance("MD5");

			// MessageDigest对象通过使用 update方法处理数据， 使用指定的byte数组更新摘要
			mdInst.update(btInput);

			// 摘要更新之后，通过调用digest（）执行哈希计算，获得密文
			byte[] md = mdInst.digest();

			// 把密文转换成十六进制的字符串形式
			int j = md.length;
			char str[] = new char[j * 2];
			int k = 0;
			for (int i = 0; i < j; i++) { // i = 0
				byte byte0 = md[i]; // 95
				str[k++] = md5String[byte0 >>> 4 & 0xf]; // 5
				str[k++] = md5String[byte0 & 0xf]; // F
			}

			// 返回经过加密后的字符串
			return new String(str);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static String dateToUsed(Date createDate) {
		Date current = new Date();
		long elapse = current.getTime() - createDate.getTime();
		long day = elapse / (24 * 60 * 60 * 1000);
		long hour = (elapse / (60 * 60 * 1000) - day * 24);
		long min = ((elapse / (60 * 1000)) - day * 24 * 60 - hour * 60);
		if (day > 0) {
			return day + " 天";
		} else if (hour > 0) {
			return hour + " 小时";
		} else if (min > 0) {
			return min + " 分钟";
		} else {
			return "<１分钟";
		}
	}
	
	public static String timeToUsed(Date createDate) {
//		Date currnt = new Date();
//		long elapse = currnt.getTime() - createDate.getTime();
//		long day = elapse / (24 * 60 * 60 * 1000);
//		long hour = (elapse / (60 * 60 * 1000) - day * 24);
//		long min = ((elapse / (60 * 1000)) - day * 24 * 60 - hour * 60);
//		return day + "天" + hour + "小时" + min + "分钟";
		Date currnt = new Date();
		double elapse = currnt.getTime() - createDate.getTime();
		return (long) Math.ceil(elapse / (60*1000*60*24))+"天";
	}
	
	public static String timeToUsed(Date createDate, Date endDate) {
//		long elapse = endDate.getTime() - createDate.getTime();
//		long day = elapse / (24 * 60 * 60 * 1000);
//		long hour = (elapse / (60 * 60 * 1000) - day * 24);
//		long min = ((elapse / (60 * 1000)) - day * 24 * 60 - hour * 60);
//		return day + "天" + hour + "小时" + min + "分钟";
		double elapse = endDate.getTime() - createDate.getTime();
		return (long) Math.ceil(elapse / (60*1000*60*24))+"天";
	}

	public static int timeElapse(Date startTime, Date endTime) {
		long elapse = endTime.getTime() - startTime.getTime();
		int elapseInt = 0;
		if (elapse > 0) {
			elapseInt = (int) elapse / 1000;
		}
		return elapseInt;
	}

	public static String getSummary(String input) {
		if (input.length() > 10) {
			return input.substring(0, 10) + "...";
		} else {
			return input;
		}
	}
	
	public static JSONObject createLogInfo(String key, String value) {
		JSONObject logInfo = new JSONObject();
		try {
			logInfo.put("key", URLEncoder.encode(key, "utf-8"));
			logInfo.put("value",
					URLEncoder.encode(value, "utf-8").replace("+", "%20"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return logInfo;
	}

	public static String encodeText(String name) {
		if (name == null || name == "" || name.length() == 0) {
			return "";
		}
		String result = "";
		try {
			result = URLEncoder.encode(name, "utf-8").replace("+", "%20");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public static boolean hasForbiddenChar(String str) {
		Pattern pattern = Pattern
				.compile("[`~!$%^&#*()+|\\\\\\]\\[\\]\\{\\}:;'\\,<>?]+");
		Matcher m = pattern.matcher(str);
		return m.find();
	}
	public static String stickyToSuccess(String msg) {
		return "<div class='alert alert-success'>" + msg + "</div>";
	}

	public static String stickyToInfo(String msg) {
		return "<div class='alert alert-info'>" + msg + "</div>";
	}

	public static String stickyToError(String msg) {
		return "<div class='alert alert-danger'>" + msg + "</div>";
	}

	public static String stickyToWarning(String msg) {
		return "<div class='alert alert-warning'>" + msg + "</div>";
	}
	
	public static long timeToUsedMin(Date createDate, Date endDate){
		long elapse = endDate.getTime() - createDate.getTime();
		return elapse / (60*1000);
	}
	
	public static long timeToUsedDay(Date createDate, Date endDate){
		double elapse = endDate.getTime() - createDate.getTime();
		return (long) Math.ceil(elapse / (60*1000*60*24));
	}
	
	public static Date daySub(int currentDay,int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(currentDay, day);
        Date startDate = calendar.getTime();
        return startDate;
    }
	
	/**
	 * 日期差     
	 * @param date1 <Date>
	 * @param date2 <Date>
	 * @param type <int>  0月   1年
	 * @return 
	 */
	public static int getDateSpace(Date date1, Date date2,int type){
		int result = 0;
		Calendar c1 = Calendar.getInstance();
		Calendar c2 = Calendar.getInstance();

		c1.setTime(date1);
		c2.setTime(date2);
		
		if(type==0){
			result = c2.get(Calendar.MONTH) - c1.get(Calendar.MONTH)+(c2.get(Calendar.YEAR) - c1.get(Calendar.YEAR))*12;
		}else if(type==1){
			result = c2.get(Calendar.YEAR) - c1.get(Calendar.YEAR);
		}
		return result == 0 ? 1 : Math.abs(result);
	}
	/**
	 * 日期加天 
	 * @param date <Date>
	 * @param day <int>
	 * @return 
	 */
	public static Date AddDayForDate(Date date, int day) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DAY_OF_MONTH, day);
		return calendar.getTime();
	}
}
