//S1803445
package org.me.gcu.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DateParser {
    Date date;
    String pattern = "E, dd MMM yyyy HH:mm:ss";
    SimpleDateFormat simpleDateFormat =new SimpleDateFormat(pattern, new Locale("en", "UK"));

    public Date parse(String data){
        try {
            date = simpleDateFormat.parse(data);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public Date format(Calendar calendar){
        date = this.parse(simpleDateFormat.format(calendar.getTime()));
        return date;
    }

    public Date formatMillis(Long data){
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(data);
        date = this.format(calendar);
        return date;
    }
}
