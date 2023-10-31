package HomeBtnWindow;

import com.jfoenix.controls.JFXButton;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Locale;

public class RenameWeekBtn {
    public static int i=0;
    public static String[] weekDays={"Saturday","Sunday","Monday","Tuesday","Wednesday","Thursday","Friday"};
    public static void rename(JFXButton button1,JFXButton button2,
                              JFXButton button3,JFXButton button4,
                              JFXButton button5,JFXButton button6,
                              JFXButton button7)throws IOException{

        LocalDate day = LocalDate.now();
        DateTimeFormatter format = DateTimeFormatter.ofPattern("EEEE", Locale.getDefault());
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");

        String d=day.format(format);
        while(d.compareTo(weekDays[i])!=0)
            i++;

        Calendar cal = Calendar.getInstance();
        System.out.println("Date = "+ cal.getTime());

        button1.setText(weekDays[(i++)%7]+"(Today)");
        cal.add(Calendar.DATE, 0);
        button1.setId(format1.format(cal.getTime()));

        button2.setText(weekDays[(i++)%7]);
        cal.add(Calendar.DATE, 1);
        button2.setId(format1.format(cal.getTime()));

        button3.setText(weekDays[(i++)%7]);
        cal.add(Calendar.DATE, 1);
        button3.setId(format1.format(cal.getTime()));

        button4.setText(weekDays[(i++)%7]);
        cal.add(Calendar.DATE, 1);
        button4.setId(format1.format(cal.getTime()));

        button5.setText(weekDays[(i++)%7]);
        cal.add(Calendar.DATE, 1);
        button5.setId(format1.format(cal.getTime()));

        button6.setText(weekDays[(i++)%7]);
        cal.add(Calendar.DATE, 1);
        button6.setId(format1.format(cal.getTime()));

        button7.setText(weekDays[(i++)%7]);
        cal.add(Calendar.DATE, 1);
        button7.setId(format1.format(cal.getTime()));
        i=0;
    }
}
