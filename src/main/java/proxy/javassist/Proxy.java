//package proxy.javassist;
//
//
//import java.lang.reflect.InvocationHandler;
//
//public class Proxy {
//    public static Object getProxy(InvocationHandler obj, Class<?>[] interfaces, ClassLoader classLoader) throws Exception {
//        return ClassBuilder.getProxy(obj, interfaces, classLoader);
//    }
//
//    public static void main(String[] args) throws Exception {
////        Object proxy = getProxy((o, method, arg) -> {
////            System.out.println(method);
////            for (Object s : arg) {
////                System.out.println(s);
////            }
////            return "2";
////        }, new Class[]{InterfacesDemo.class, InterfacesDemo2.class}, Thread.currentThread().getContextClassLoader());
////        proxy = getProxy((o, method, arg) -> {
////            System.out.println(method);
////            for (Object s : arg) {
////                System.out.println(s);
////            }
////            return "2";
////        }, new Class[]{InterfacesDemo.class, InterfacesDemo2.class}, Thread.currentThread().getContextClassLoader());
////        ((InterfacesDemo) proxy).a("1");
////        ((InterfacesDemo2) proxy).a("1", "2");
//        final String a="a";
//        final String b="b";
//        add(a, b);
//    }
//
//    public static void add(String a, String b) {
//        String ab= a + b;
//        System.out.println(ab);
//        a="22";
//        b="663";
//        if (ab.equals("22663")){
//            throw new RuntimeException("stop");
//        }
//        Proxy.add(a,b);
//
//    }
//}
