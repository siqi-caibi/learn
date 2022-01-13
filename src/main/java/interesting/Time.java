package interesting;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class Time {
    public static void main(String[] args) throws Exception {

        for (int counter = 0; counter <= 60; counter++){
            Long cur=System.currentTimeMillis();
            System.out.printf("Fibonacci of %d is: %d\n", counter, fibonacci(counter));
            Long newC=System.currentTimeMillis();
            System.out.println((newC-cur)/1000);
        }
    }
    public static long fibonacci(long number) {
        if ((number == 0) || (number == 1))
            return number;
        else
            return fibonacci(number - 1) + fibonacci(number - 2);
    }

    private static void timer() throws ParseException {
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
