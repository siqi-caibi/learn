package design.observer;

public class Test {
    public static void main(String[] args) {
        Subject subjectMilk=new Subject("牛奶");
        Observer observer1=new Observer("1 号");
        Observer observer2=new Observer("2 号");
        subjectMilk.addObserver(observer1);
        subjectMilk.addObserver(observer2);

        subjectMilk.envent();
    }
}
