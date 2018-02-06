package cn.com.intra_mart.common.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
	private static final SimpleDateFormat  simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd") ; 

	public static synchronized String dateToStr1(Date date) {
		if ( date == null ) return "" ;
		return DateUtil.simpleDateFormat.format(date);
	}
}
