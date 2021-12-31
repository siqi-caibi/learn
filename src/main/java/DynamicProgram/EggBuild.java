package DynamicProgram;

import java.lang.reflect.Type;

public class EggBuild<T> extends CoinAmount{

    private T v;

    public static void main(String[] args) {
        CoinAmount a=new EggBuild<String>();
        Type superclass = a.getClass().getGenericSuperclass();
        System.out.println(superclass.getTypeName());
        System.out.println(a.getClass().getName());
//        System.out.println(climbStair(4));
    }

    /***
     * 假设你正在爬楼梯。需要 n 阶你才能到达楼顶。
     *
     * 每次你可以爬 1 或 2 个台阶。你有多少种不同的方法可以爬到楼顶呢？
     *
     * 注意：给定 n 是一个正整数。
     *
     * 示例 1：
     *
     * 输入： 2
     * 输出： 2
     * 解释： 有两种方法可以爬到楼顶。
     * 1.  1 阶 + 1 阶
     * 2.  2 阶

     */
    public static int climbStair(int n){
//实际就是斐波那契数列  1，2，3，5，8
        return 1;
    }

    private static void z() {
        String a ="";
        // \u000a  a="hello world!";
        System.out.println(a);
    }
}
