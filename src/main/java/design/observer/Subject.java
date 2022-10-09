package design.observer;

import java.util.List;

/**
 * 具体事件 操作
 *
 */
public class Subject {


   private String name;
//   很多观察者都来，观测这个事件
   private List<Observer> observerList;

    public Subject(String name) {
        this.name = name;
    }

    public void addObserver(Observer observer){
        observerList.add(observer);
    }
    public void envent(){
        notifyObserver();
    }

    private void notifyObserver(){
        for (Observer observer : observerList) {
            observer.update(name+"触发通知");
        }
    }
}
