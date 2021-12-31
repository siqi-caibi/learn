//package proxy.javassist;
//
//
//import javassist.*;
//import javassist.Modifier;
//import org.apache.commons.lang3.StringUtils;
//import org.apache.commons.lang3.reflect.FieldUtils;
//
//
//import java.lang.reflect.*;
//import java.util.*;
//import java.util.concurrent.ConcurrentHashMap;
//import java.util.concurrent.atomic.AtomicInteger;
//
//public class ClassBuilder {
//
//    private static final AtomicInteger ATOMIC_INTEGER = new AtomicInteger(0);
//    private static final Map<String, Class<?>> CLASS_MAP = new ConcurrentHashMap<>(16);
//
//    public static Object getProxy(InvocationHandler obj, Class<?>[] interfaces, ClassLoader classLoader) throws Exception {
//        boolean allIsInterface = Arrays.stream(interfaces).allMatch(Class::isInterface);
//        if (!allIsInterface) {
//            throw new RuntimeException("interfaces is not all interface");
//        }
//        Class<?> clazz;
//        String classKey = createClassKey(interfaces);
//        if (CLASS_MAP.containsKey(classKey)) {
//            clazz = CLASS_MAP.get(classKey);
//        } else {
//            clazz = createdClass(obj, interfaces, classLoader);
//            CLASS_MAP.put(classKey, clazz);
//        }
//        Constructor<?> constructor = clazz.getConstructor(InvocationHandler.class);
//        return constructor.newInstance(obj);
//    }
//
//    private static Class<?> createdClass(InvocationHandler obj, Class<?>[] interfaces, ClassLoader classLoader) throws Exception {
//        ClassPool classPool = ClassPool.getDefault();
//        int index = getIndex();
//        CtClass ctClass = classPool.makeClass("com.demo.proxy.Proxy" + index);
//        for (Class<?> clazz : interfaces) {
//            ctClass.addInterface(classPool.get(clazz.getName()));
//        }
//        Set<String> keys = new HashSet<>();
//        Method[] methods = Arrays.stream(interfaces).flatMap(clazz -> Arrays.stream(clazz.getMethods())).toArray(Method[]::new);
//        ctClass.addField(CtField.make("private java.lang.reflect.InvocationHandler h;", ctClass));
//        int j = 1;
//        List<Method> methodList = new ArrayList<>();
//        for (Method method : methods) {
//            String key = createdMethodKey(method);
//            if (keys.contains(key)) {
//                continue;
//            }
//            keys.add(key);
//            int i = 1;
//            List<String> list = new ArrayList<>(method.getParameterTypes().length);
//            List<String> args1 = new ArrayList<>(method.getParameterTypes().length);
//            for (Class<?> a : method.getParameterTypes()) {
//                list.add(String.format("%s args%s", a.getName(), i));
//                args1.add("args" + i);
//                i++;
//            }
//            String methodStr = String.format("public %s %s(%s) throws Exception", method.getReturnType().getName(), method.getName(), String.join(",", list))
//                    + String.format("{\n" +
//                    "        try {\n" +
//                    "            return %sh.invoke(this, m1, %s);\n" +
//                    "        } catch (RuntimeException var3) {\n" +
//                    "            throw var3;\n" +
//                    "        } catch (Throwable var4) {\n" +
//                    "            throw new java.lang.reflect.UndeclaredThrowableException(var4);\n" +
//                    "        }\n" +
//                    "    }", "void".equals(method.getReturnType().getName()) ? "" : "(" + method.getReturnType().getName() + ")", StringUtils.isBlank(String.join(",", args1)) ? "null" : "new Object[]{" + String.join(",", args1) + "}");
//            ctClass.addField(CtField.make(String.format("private static java.lang.reflect.Method m%s;", j), ctClass));
//            ctClass.addMethod(CtMethod.make(methodStr, ctClass));
//            j++;
//            methodList.add(method);
//        }
//        CtConstructor constructor = new CtConstructor(new CtClass[]{classPool.get(InvocationHandler.class.getName())}, ctClass);
//        constructor.setModifiers(Modifier.PUBLIC);
//        constructor.setBody("{this.h= $1;}");
//        ctClass.addConstructor(constructor);
//        Class<?> proxyClazz = ctClass.toClass(classLoader, null);
//        int i = 1;
//        for (Method method : methodList) {
//            Field declaredField = proxyClazz.getDeclaredField(String.format("m%s", i++));
//            declaredField.setAccessible(true);
//            FieldUtils.writeStaticField(declaredField, proxyClazz.getMethod(method.getName(), method.getParameterTypes()));
//        }
//        return proxyClazz;
//    }
//
//    private static String createClassKey(Class<?>[] interfaces) {
//        StringBuilder sb = new StringBuilder();
//        for (Class<?> a : interfaces) {
//            sb.append(String.format("%s/", a.getName()));
//        }
//        return sb.toString();
//    }
//
//    private static String createdMethodKey(Method method) {
//        StringBuilder sb = new StringBuilder();
//        sb.append(method.getName()).append("/");
//        for (Class<?> a : method.getParameterTypes()) {
//            sb.append(String.format("%s/", a.getName()));
//        }
//        return sb.toString();
//    }
//
//    private static int getIndex() {
//        for (; ; ) {
//            int i = ATOMIC_INTEGER.get();
//            if (ATOMIC_INTEGER.compareAndSet(i, i + 1)) {
//                return i + 1;
//            }
//        }
//    }
//}
