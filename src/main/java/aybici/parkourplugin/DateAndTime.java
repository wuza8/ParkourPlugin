package aybici.parkourplugin;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateAndTime {
    public String getDateString(long dateLong) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date = new Date(dateLong);
        return formatter.format(date);
    }
}