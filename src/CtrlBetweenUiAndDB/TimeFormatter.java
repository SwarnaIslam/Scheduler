package CtrlBetweenUiAndDB;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.TimeZone;

public class TimeFormatter {
    public static String timeFormatting(String inTime,String duHour,String duMin,String duSec){
        String strtHour=inTime.substring(0,2);
        String strtMin=inTime.substring(3);

        long bSecond=Long.parseLong(strtHour)*3600+Long.parseLong(strtMin)*60;
        long eSecond=Long.parseLong(duHour)*3600+Long.parseLong(duMin)*60+Long.parseLong(duSec)+bSecond;

        SimpleDateFormat formatter = new SimpleDateFormat("hh:mm a");
        formatter.setTimeZone(TimeZone.getTimeZone("Bangladesh/Dhaka"));

        String initialTime = formatter.format(new Time(bSecond * 1000L));
        String endTime = formatter.format(new Time(eSecond * 1000L));

        return initialTime + " to " + endTime;
    }
}
