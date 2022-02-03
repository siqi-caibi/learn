package interesting;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;


public class Time {
    public static void main(String[] args) throws Exception {


        ThreadPoolExecutor executor = new ThreadPoolExecutor(5, 10, 200, TimeUnit.MILLISECONDS,
                new ArrayBlockingQueue<Runnable>(5),new ThreadFactory() {
            public Thread newThread(Runnable r) {
                Thread thread = new Thread(r);
//                thread.setDaemon(true);
                thread.setName("守护线程 "+ thread.getId());
                return thread;
            }
        });
        Thread shutDown=new Thread(){
            @Override
            public void run() {
                System.out.println(" shutDown");
                executor.shutdownNow();
            }
        };

        Runtime.getRuntime().addShutdownHook(shutDown);

        executor.execute(()->{
                try {
                    System.out.println(Thread.currentThread().getName()+" ,线程池中线程数目："+executor.getPoolSize()+"，队列中等待执行的任务数目："+
                            executor.getQueue().size()+"，已执行玩别的任务数目："+executor.getCompletedTaskCount());
                    Thread.sleep(10000);

                }catch (Exception e){
                    System.out.println(" 线程中断 ");
                }finally {
                    System.out.println(" 线程 FINA ");
                }

            });
        System.exit(0);
    }

//    private static void fibo() {
//        for (int counter = 0; counter <= 60; counter++){
//            Long cur=System.currentTimeMillis();
//            System.out.printf("Fibonacci of %d is: %d\n", counter, fibonacci(counter));
//            Long newC=System.currentTimeMillis();
//            System.out.println((newC-cur)/1000);
//        }
//    }
//
//    public static long fibonacci(long number) {
//        if ((number == 0) || (number == 1))
//            return number;
//        else
//            return fibonacci(number - 1) + fibonacci(number - 2);
//    }
//
//    private static void timer() throws ParseException {
//        SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        String str="1900-01-01 08:05:42";
//        String str2="1900-01-01 08:05:43";
//        Date date=sf.parse(str);
//        Date date2=sf.parse(str2);
//        long timeLong=date.getTime()/1000;
//        long timeLong2=date2.getTime()/1000;
//        System.out.println(timeLong2-timeLong);
//        System.out.println(sf.format(sf.parse("1900-01-01 08:00:00")));
//    }
}
