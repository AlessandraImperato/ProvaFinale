package it.alessandra.provafinale.Utils;

import java.io.Serializable;
import java.text.Format;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by User on 14/12/2017.
 */

public class SettingDate implements Serializable{

    public static Date formatToDate(String dateString){ // trasformo la data da stringa a Date
        String data = dateString.replace('"',' ');
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy", Locale.ITALY);
        return format.parse(dateString,new ParsePosition(0));
    }

    public static String formatToString(Date date){
        Format format = new SimpleDateFormat("dd/MM/yyyy", Locale.ITALY);
        return format.format(date);
    }
}
