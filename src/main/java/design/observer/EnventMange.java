package design.observer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 中央事件管理器
 * 所有事件都推送到这里，然后通知订阅者
 */
public class EnventMange {
    private String name;
    private Map<Subject, List<Observer>> enventHnadler=new HashMap<>();


    public void addOberver(Subject key, List<Observer> val){
        enventHnadler.put(key, val);
    }

    public void publishEnvent(Subject key,String log){
        List<Observer> suberList=enventHnadler.get(key);
        for (Observer observer : suberList) {
            observer.update(log);
        }
    }
}
