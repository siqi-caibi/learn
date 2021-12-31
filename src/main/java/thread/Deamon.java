package thread;



/**
 * 连接使用 守护线程，因为如果连接断了，就没必要继续执行用户的其他的操作了
 */
public class Deamon {

    static class DeamonTestRunnable implements Runnable{
        public void run(){
            try {
                for (int i = 0; i < 7; i++) {
                    Thread.sleep(1000);
                    System.out.println("deamon out"+i);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    static class UserTestRunnable implements Runnable{
        public void run(){
            try {
//                守护线程
                Thread threadDea=new Thread(new DeamonTestRunnable());
                threadDea.setDaemon(true);
                threadDea.start();
                for (int i = 0; i < 7; i++) {
                    Thread.sleep(500);
                    System.out.println("user out"+i);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) throws Exception {
        Thread threadUsr=new Thread(new UserTestRunnable());
        threadUsr.start();


    }
}
