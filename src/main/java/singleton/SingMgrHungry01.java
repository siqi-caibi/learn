package singleton;

/**
 * 饿汉式，
 * 缺点：即使实例化类，加载时就会放入内存
 */
public class SingMgrHungry01 {
    private static final SingMgrHungry01 INSTANCE= new SingMgrHungry01();

    private SingMgrHungry01() {
    }

    public static SingMgrHungry01 getInstance(){
        return INSTANCE;
    }


}
