package thread;

import java.util.concurrent.locks.ReentrantLock;

public class VolatileUse {

    private volatile int num=0;

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public static void main(String[] args) {

        VolatileUse root=new VolatileUse();
        try {
            root.ss();
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("sdfsd ");
        }
        ReentrantLock

    }
    public void ss(){
        Thread two=new Thread(()->{
            try {
                System.out.println(1);
                throw new RuntimeException("55");
            }finally {
                System.out.println("a");
            }
        });

        two.start();
    }
}
