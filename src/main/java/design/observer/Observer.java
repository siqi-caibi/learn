package design.observer;

/**
 * 观察者
 */
public class Observer {

    private String name;

    public Observer(String name) {
        this.name = name;
    }

    public void update(String log){
        System.out.println(log);
    }
}
