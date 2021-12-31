package singleton;

public class SingMgrLazy01 {
//    防止语句重拍，会导致返回null
    private static volatile SingMgrLazy01 INSTANCE;

    private SingMgrLazy01() {
    }

    public static SingMgrLazy01 getInstance(){
//        为什么需要第一次，因为大部分情况都直接判断后跳过，不走内部锁逻辑
        if (INSTANCE==null){
            synchronized (SingMgrLazy01.class) {
//                为什么第二次，因为加锁也会让所有线程都跑i一次，不判断会重复new
                if (INSTANCE==null) {
                    INSTANCE = new SingMgrLazy01();
                }
            }
        }
        return INSTANCE;
    }
}
