package interesting;


import java.text.SimpleDateFormat;
import java.util.Date;


public class Time {
    public static void main(String[] args) throws Exception {
        SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String str="1900-01-01 08:05:42";
        String str2="1900-01-01 08:05:43";
        Date date=sf.parse(str);
        Date date2=sf.parse(str2);
        long timeLong=date.getTime()/1000;
        long timeLong2=date2.getTime()/1000;
        System.out.println(timeLong2-timeLong);
        System.out.println(sf.format(sf.parse("1900-01-01 08:00:00")));
    }
}
