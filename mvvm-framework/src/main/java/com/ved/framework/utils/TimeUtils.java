package com.ved.framework.utils;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Context;
import android.text.TextUtils;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

@SuppressWarnings("deprecation")
public class TimeUtils {

    // ///s 代表Simple日期格式：yyyy-MM-dd
    // ///f 代表Full日期格式：yyyy-MM-dd hh:mm:ss

    public static final SimpleDateFormat ss_format = new SimpleDateFormat("MM-dd");
    public static final SimpleDateFormat s_format = new SimpleDateFormat("yyyy-MM-dd");
    public static final SimpleDateFormat f_format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static long s_str_2_long(String dateString) {
        try {
            Date d = s_format.parse(dateString);
            return d.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static long dateToLong(Date date) {
        return date.getTime();
    }

    public static Date longToDate(long currentTime) throws ParseException {
        return longToDate(currentTime,"yyyy-MM-dd HH:mm:ss");
    }

    public static Date longToDate(long currentTime, String formatType)
            throws ParseException {
        Date dateOld = new Date(currentTime); // 根据long类型的毫秒数生命一个date类型的时间
        String sDateTime = dateToString(dateOld, formatType); // 把date类型的时间转换为string
        Date date = stringToDate(sDateTime, formatType); // 把String类型转换为Date类型
        return date;
    }

    public static long stringToLong(String strTime, String formatType)
            throws ParseException {
        Date date = stringToDate(strTime, formatType); // String类型转成date类型
        if (date == null) {
            return 0;
        } else {
            long currentTime = dateToLong(date); // date类型转成long类型
            return currentTime;
        }
    }

    public static Date stringToDate(String strTime, String formatType)
            throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat(formatType);
        Date date = null;
        date = formatter.parse(strTime);
        return date;
    }

    public static String longToString(long currentTime, String formatType)
            throws ParseException {
        Date date = longToDate(currentTime, formatType); // long类型转成Date类型
        String strTime = dateToString(date, formatType); // date类型转成String
        return strTime;
    }

    public static String dateToString(Date data, String formatType) {
        return new SimpleDateFormat(formatType).format(data);
    }

    public static String dateToStr(Date date, SimpleDateFormat format) {//可根据需要自行截取数据显示
        return format.format(date);
    }

    public static long f_str_2_long(String dateString) {
        try {
            Date d = f_format.parse(dateString);
            return d.getTime() / 1000;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static long f_str_2_long(String dateString, SimpleDateFormat f_format) {
        try {
            Date d = f_format.parse(dateString);
            return d.getTime() / 1000;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static String ss_long_2_str(long timestamp) {
        return ss_format.format(new Date(timestamp));
    }

    public static String f_long_2_str(String timestamp) {
        return f_format.format(new Date(Long.parseLong(timestamp)*1000));
    }

    public static String s_long_2_str(long timestamp) {
        return s_format.format(new Date(timestamp));
    }

    public static String f_long_2_str(long timestamp) {
        return f_format.format(new Date(timestamp));
    }

    public static String f_long_2_str(long timestamp, SimpleDateFormat f_format) {
        return f_format.format(new Date(timestamp));
    }

    /**
     * 获取字符串时间的年份
     *
     * @param dateString 格式为yyyy-MM-ss，或者yyyy-MM-dd HH:mm:ss
     * @return
     */
    public static int getYear(String dateString) {
        try {
            Date d = s_format.parse(dateString);
            return d.getYear() + 1900;// 年份是基于格林威治时间，所以加上1900
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 获取字符串时间的月份
     *
     * @param dateString 格式为yyyy-MM-ss，或者yyyy-MM-dd hh:mm:ss
     * @return
     */
    public static int getMonth(String dateString) {
        try {
            Date d = s_format.parse(dateString);
            return d.getMonth();// 月份从0-11
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 获取字符串时间的天
     *
     * @param dateString 格式为yyyy-MM-ss，或者yyyy-MM-dd hh:mm:ss
     * @return
     */
    public static int getDayOfMonth(String dateString) {
        try {
            Date d = s_format.parse(dateString);
            return d.getDate();
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return 0;
    }

    public static int getHours(String timeString) {
        SimpleDateFormat formart = new SimpleDateFormat("HH:mm:ss");
        try {
            Date date = formart.parse(timeString);
            return date.getHours();
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return 0;
    }

    public static int getMinutes(String timeString) {
        SimpleDateFormat formart = new SimpleDateFormat("HH:mm:ss");
        try {
            Date date = formart.parse(timeString);
            return date.getMinutes();
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return 0;
    }

    public static int getSeconds(String timeString) {
        SimpleDateFormat formart = new SimpleDateFormat("HH:mm:ss");
        try {
            Date date = formart.parse(timeString);
            return date.getSeconds();
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return 0;
    }

    public static String getCurrentTime() {
        return f_format.format(new Date(System.currentTimeMillis()));
    }

    /**
     * 在当前时间上加上多少毫秒，返回这个时间
     *
     * @param mask
     * @return
     */
    public static String getCurrentTimeMask(long mask) {
        return f_format.format(new Date(System.currentTimeMillis() + mask));
    }

    // /////////////////////以上是通用的，下面为特殊需求的////////////////////////
    // /**
    // * 时间戳转换日期格式
    // *
    // * @param timestamp
    // * 单位秒
    // * @return
    // */
    // public static String getCurrentTime(long timestamp) {
    // SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    // return f.format(new Date(timestamp * 1000));
    // }

    /**
     * 获取精简的日期
     *
     * @param time
     * @return
     */
    public static String getSimpleDate(String time) {
        SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = f_format.parse(time);
            return formater.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * @param time
     * @return
     */
    public static String getSimpleDateTime(String time) {
        SimpleDateFormat formater = new SimpleDateFormat("yy-MM-dd HH:mm");
        Date date = null;
        try {
            date = f_format.parse(time);
            return formater.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return "";
        }
    }

    public static String getSimpleTime(String time) {
        SimpleDateFormat formater = new SimpleDateFormat("HH:mm");
        Date date = null;
        try {
            date = f_format.parse(time);
            return formater.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return "";
        }
    }

    public static String getChatSimpleDate(String time) {
        SimpleDateFormat formater = new SimpleDateFormat("yy-MM-dd");
        Date date = null;
        try {
            date = f_format.parse(time);
            return formater.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return "";
        }
    }

    public static String getTimeHM(String time) {
        SimpleDateFormat formater = new SimpleDateFormat("HH:mm");
        Date date = null;
        try {
            date = f_format.parse(time);
            return formater.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return "";
        }
    }

    public static String getTimeMMdd(long time) {
        SimpleDateFormat formater = new SimpleDateFormat("MM-dd");
        return formater.format(time * 1000);

    }

    public static SimpleDateFormat friendly_format1 = new SimpleDateFormat("HH:mm");
    public static SimpleDateFormat friendly_format2 = new SimpleDateFormat("MM-dd HH:mm");

    public static String sk_time_friendly_format2(long time) {
        return friendly_format2.format(new Date(time * 1000));
    }

    public static String sk_time_s_long_2_str(long time) {
        return s_long_2_str(time * 1000);
    }


    public static String sk_time_ss_long_2_str(long time) {
        return ss_long_2_str(time * 1000);
    }

    public static long sk_time_s_str_2_long(String dateString) {
        return s_str_2_long(dateString) / 1000;
    }

    public static long sk_time_current_time() {
        return System.currentTimeMillis() / 1000;
    }

    private static SimpleDateFormat hm_formater = new SimpleDateFormat("HH:mm");


    private static SimpleDateFormat hm_formater12 = new SimpleDateFormat("aa hh:mm ");

    public static String sk_time_long_to_hm_str(long time) {
        try {
            return hm_formater.format(new Date(time * 1000));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String sk_time_long_to_hm_str12(long time) {
        try {
            return hm_formater12.format(new Date(time * 1000));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String sk_time_long_to_chat_time_str(Context mContext,long time) {
        String date1 = sk_time_s_long_2_str(time);
        String date2 = sk_time_s_long_2_str(System.currentTimeMillis() / 1000);
        if (date1.compareToIgnoreCase(date2) == 0) {// 是同一天
            if(is24Hh(mContext)){
                return sk_time_long_to_hm_str(time);
            }else{
                return sk_time_long_to_hm_str12(time);
            }

        } else {
            if(is24Hh(mContext)){
                return long_to_yMdHm_str(time * 1000);
            }else{
                return long_to_yMdHm_st12r(time * 1000);
            }

        }
    }


    /**
     * @param mContext
     * @return
     * @since 是否24小时制 true  是
     */
    public static Boolean is24Hh(Context mContext){
        ContentResolver cv = mContext.getContentResolver();
        String strTimeFormat = android.provider.Settings.System.getString(cv,
                android.provider.Settings.System.TIME_12_24);
        if(strTimeFormat !=null && strTimeFormat.equals("24")){
            return true;
        }
        return false;
    }

    public static final SimpleDateFormat sk_format_1 = new SimpleDateFormat("yyyy/MM/dd HH:mm");

    public static final SimpleDateFormat sk_format_12 = new SimpleDateFormat("yyyy/MM/dd aa hh:mm");

    // 日期加小时的字符串
    public static String long_to_yMdHm_str(long time) {
        return sk_format_1.format(new Date(time));
    }



    // 日期加小时的字符串
    public static String long_to_yMdHm_st12r(long time) {
        return sk_format_12.format(new Date(time));
    }


    public static long sk_time_yMdHm_str_to_long(String time) {
        try {
            return sk_format_1.parse(time).getTime() / 1000;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static int yMdHm_getYear(String dateString) {
        try {
            Date d = sk_format_1.parse(dateString);
            return d.getYear() + 1900;// 年份是基于格林威治时间，所以加上1900
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static int yMdHm_getMonth(String dateString) {
        try {
            Date d = sk_format_1.parse(dateString);
            return d.getMonth();// 月份从0-11
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static int yMdHm_getDayOfMonth(String dateString) {
        try {
            Date d = sk_format_1.parse(dateString);
            return d.getDate();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static int yMdHm_getHours(String timeString) {
        try {
            Date date = sk_format_1.parse(timeString);
            return date.getHours();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static int yMdHm_getMinutes(String timeString) {
        try {
            Date date = sk_format_1.parse(timeString);
            return date.getMinutes();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * @param textView
     * @param time     时间戳/1000
     * @return
     */
    public static long getSpecialBeginTime(TextView textView, long time) {
        long currentTime = System.currentTimeMillis() / 1000;
        if (time > currentTime) {
            time = currentTime;
        }
        textView.setText(sk_time_s_long_2_str(time));
        return time;
    }

    @SuppressLint("SimpleDateFormat")
    public static String getFormatDateMsg(long time){
        String callDate = TransitionTime.getDate(time);
        String todayData = TransitionTime.getTodayData();
        SimpleDateFormat f = null;
        if (TextUtils.equals(todayData, callDate)) {//今天
            f = new SimpleDateFormat("HH:mm");
        }else {
            f = new SimpleDateFormat("yyyy/MM/dd");
        }
        return f.format(time);
    }

    /**
     * 时间格式化为今天，昨天，前天   年-月-日
     *
     * @param time
     */
    @SuppressLint("SimpleDateFormat")
    public static String setFormatDate(long time) {
        time *= 1000;
        //获取拨打电话的日期
        String callDate = TransitionTime.getDate(time);
        //获取今天的日期
        String todayData = TransitionTime.getTodayData();
        //获取昨天的日期
        String yesData = TransitionTime.getYesData();
        //获取前天的日期
        String beforeYesterday = TransitionTime.getBeforeYesterdayData();

        SimpleDateFormat f = null;

        if (TextUtils.equals(todayData, callDate)) {//今天
            return "今天";
        } else if (TextUtils.equals(yesData, callDate)) {//昨天
            return "昨天";
        } else if (TextUtils.equals(beforeYesterday, callDate)) {//前天
            return "前天";
        } else {
            // 月日 : 时分
            f = new SimpleDateFormat("yyyy-MM-dd");
            return f.format(time);
        }
    }

    /**
     * 字符串转为时间格式
     *
     * @return
     */
    public static String strToTime(double str) {
        long time = Long.parseLong(StringUtils.removeDecimal(String.valueOf(str)));
        StringBuilder stringBuilder = new StringBuilder();
        if (time < 60) {
            stringBuilder.append("00:");
            if (time < 10) {
                stringBuilder.append("0");
            }
            stringBuilder.append(time);
            return stringBuilder.toString();
        } else if (time < 3600) {
            return getBranch(time, stringBuilder);
        } else {
            if (time / 3600 < 10) {
                stringBuilder.append("0");
            }
            stringBuilder.append(time / 3600);
            stringBuilder.append(":");
            return getBranch(time % 3600, stringBuilder);
        }
    }

    //获取分秒的时间格式
    private static String getBranch(long time, StringBuilder stringBuilder) {
        if (time / 60 < 10) {
            stringBuilder.append("0");
        }
        stringBuilder.append(time / 60);
        stringBuilder.append(":");
        if (time % 60 < 10) {
            stringBuilder.append("0");
        }
        stringBuilder.append(time % 60);
        return stringBuilder.toString();
    }

    @SuppressLint("SimpleDateFormat")
    public static String timeToStr(Context context,long time){
        String callDate = TransitionTime.getDate(time);
        String todayData = TransitionTime.getTodayData();
        SimpleDateFormat f = null;
        long currentTimeMillis = System.currentTimeMillis();
        if (TextUtils.equals(todayData, callDate)) {//今天
            if (android.text.format.DateFormat.is24HourFormat(context)){
                f = new SimpleDateFormat("HH:mm");
            }else {
                f = new SimpleDateFormat("a hh:mm");
            }
            return f.format(time);
        }else {
            if (android.text.format.DateFormat.is24HourFormat(context)){
                f = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            }else {
                f = new SimpleDateFormat("yyyy-MM-dd a hh:mm");
            }
            String dateStr = f.format(time);
            String currentStr = f.format(currentTimeMillis);
            if (TextUtils.equals(currentStr.substring(0, 5), dateStr.substring(0, 5))) {
                dateStr = dateStr.substring(5);
            }
            return dateStr;
        }
    }

    @SuppressLint("SimpleDateFormat")
    public static boolean compare(String time,String time2){
        if (time.contains(":")) {
            int t1;
            int t2;
            String s1 = time.split(":")[0];
            String s2 = time.split(":")[1];
            if (s1.startsWith("0")){
                t1 = StringUtils.parseInt(s1.substring(1));
            }else {
                t1 = StringUtils.parseInt(s1);
            }
            if (s2.startsWith("0")){
                t2 = StringUtils.parseInt(s2.substring(1));
            }else {
                t2 = StringUtils.parseInt(s2);
            }

            int t3;
            int t4;
            String s3 = time2.split(":")[0];
            String s4 = time2.split(":")[1];
            if (s3.startsWith("0")){
                t3 = StringUtils.parseInt(s3.substring(1));
            }else {
                t3 = StringUtils.parseInt(s3);
            }
            if (s4.startsWith("0")){
                t4 = StringUtils.parseInt(s4.substring(1));
            }else {
                t4 = StringUtils.parseInt(s4);
            }
            if (t3 == t1){
                return t4 > t2;
            }else {
                return t3 > t1;
            }
        } else {
            return false;
        }
    }

    /**
     * 比较时分在当前时间前后
     * @param time      MM:dd
     * @return     true  当前时间大于设定时间
     */
    @SuppressLint("SimpleDateFormat")
    public static boolean compare(String time){
        return compare(time,TimeUtils.f_long_2_str(System.currentTimeMillis(),new SimpleDateFormat("HH:mm")));
    }

    /**
     * 取得当月天数
     * */
    public static int getCurrentMonthLastDay()
    {
        Calendar a = Calendar.getInstance();
        a.set(Calendar.DATE, 1);//把日期设置为当月第一天
        a.roll(Calendar.DATE, -1);//日期回滚一天，也就是最后一天
        return a.get(Calendar.DATE);
    }

    /**
     * 取得上月天数
     * */
    @SuppressLint("SimpleDateFormat")
    public static int getUpMonthLastDay(){
        String currentTime = com.ved.framework.utils.TimeUtils.f_long_2_str(System.currentTimeMillis(),
                new SimpleDateFormat("yyyy-MM"));
        int year = StringUtils.parseInt(currentTime.split("-")[0]);
        String month = currentTime.split("-")[1];
        int m;
        if (month.startsWith("0")){
            m = StringUtils.parseInt(month.substring(1));
        }else {
            m = StringUtils.parseInt(month);
        }
        if (m == 1){
            year--;
            m = 12;
        }else {
            m--;
        }
        return getMonthLastDay(year,m);
    }

    /**
     * 获得指定月的天数
     * */
    public static int getMonthLastDay(int year, int month)
    {
        Calendar a = Calendar.getInstance();
        a.set(Calendar.YEAR, year);
        a.set(Calendar.MONTH, month - 1);
        a.set(Calendar.DATE, 1);//把日期设置为当月第一天
        a.roll(Calendar.DATE, -1);//日期回滚一天，也就是最后一天
        return a.get(Calendar.DATE);
    }

    //是否为本月
    public static boolean equalsMonth(String m1,String m2){
        int cMonth;
        if (m1.startsWith("0")){
            cMonth = StringUtils.parseInt(m1.substring(1));
        }else {
            cMonth = StringUtils.parseInt(m1);
        }
        int month;
        if (m2.startsWith("0")){
            month = StringUtils.parseInt(m2.substring(1));
        }else {
            month = StringUtils.parseInt(m2);
        }
        return cMonth == month;
    }

    //秒转为小时，并保留一位小数
    public static double getHour(int mis){
        DecimalFormat df = new DecimalFormat("#.#");
        return Double.parseDouble(df.format(mis/3600.0d));
    }
}
