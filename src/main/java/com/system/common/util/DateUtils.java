package com.system.common.util;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.alibaba.fastjson.JSON;


public class DateUtils {

	
	  /**
     * 获取指定日期的一天小时集合yyyyMMddHH
    **/
    public static List<String> getOneDayHourList(Date date){
        List<String> hourList = new ArrayList<String>();
        SimpleDateFormat fmt = new SimpleDateFormat("yyyyMMdd");
        String dateString = fmt.format(date);
        for(int i = 0; i < 24; i++) {
            String hour = String.valueOf(i);
            if(i < 10){
                hour = "0" + hour;
            }
            hourList.add(dateString + hour);
        }
        return hourList;
    }
/**
     * 获取指定日期的前N天日期
    **/
public static Date getBeforeDayDate(Date date, int beforeDay)
    {
        Calendar a = Calendar.getInstance();
        a.setTime(date);
        a.add(Calendar.DATE, -beforeDay);
        return a.getTime();
    }


public static void main(String[] args) {
	System.out.println(JSON.toJSONString(getOneDayHourList(getBeforeDayDate(new Date(),1))));
}


}
