package link;

import java.io.InputStreamReader;

class SendValue{

    public String str="6";
    private static int a;
    public static void main(String[] args) {

        Runnable runnable=new Runnable() {
            @Override
            public void run() {
                System.out.println("dsg");
            }
        };
        Thread thread=new Thread(runnable);
        thread.run();
    }
    public void change(String str) {
        str="10";
    }
    protected void change(InputStreamReader str){
        //return "20";
    }
}
class SwitchTest{//1
    public static void main(String[] args) {//2
        System.out.println("value="+switchit(4));//3
    }//4
    public static int switchit(int x) {
        int j=1;
        switch (x) {
            default:j++;
            case 1:j++;
            case 2:j++;
            case 3:j++;
            case 4:j++;
            case 5:j++;

        }
        return j+x;
    }
}
class TestDemo{
    private int count;
    public static void main(String[] args) {
        TestDemo test=new TestDemo(88);
        System.out.println(test.count);
    }
    TestDemo(int a) {
        count=a;
    }
}


class X{
    static {
        System.out.println("sde");
    }
     Y y=new Y();
    public X(){
        System.out.print("X");
    }
}
class Y{
    public Y(){
        System.out.println("Y");
    }
}
 class Z extends X{
     static {
         System.out.println("3532");
     }
      Y y=new Y();
    public Z(){
        System.out.print("Z");
    }
    public static void main(String[] args) {
        new Z();
        new Z();
    }
}

public class Final {

    public int div(int a, int b) {

        try {

            return a / b;

        }catch(NullPointerException e){

            System.out.println("ArithmeticException");

        }

        catch (ArithmeticException e) {

            System.out.println("ArithmeticException");

        } finally {

            System.out.println("finally");

        }

        return 0;
    }

    public static void main(String[] args) {

        Final demo = new Final();

        System.out.println(demo.div(9, 0));

    }
}

