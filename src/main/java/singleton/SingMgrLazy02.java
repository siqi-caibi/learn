package singleton;

public class SingMgrLazy02 {
    private SingMgrLazy02() {
    }

//    为什么私有，防止被其他类加载，这样就不会懒汉式了
//    为什么线程安全，实例化的过程一定线程安全
    private static class SingMgrLazy02Hoder{
        private static final SingMgrLazy02 INSTANCE=new SingMgrLazy02();
    }
//为什么懒汉，因为加载外部类的时候内部类不会被加载，。只有真的用内部类才会被加载
    public static SingMgrLazy02 getInstance(){
        return SingMgrLazy02Hoder.INSTANCE;
    }
}
