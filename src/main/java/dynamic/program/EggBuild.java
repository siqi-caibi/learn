package dynamic.program;

import java.lang.reflect.Type;

/**
 * 啥叫「⾃顶向下」？注意我们刚才画的递归树（或者说图），
 * 是从上向下延伸，都是从⼀个规模较⼤的原问 题⽐如说 f(20)，向下逐渐分解规模，直到 f(1) 和 f(2) 这两个 base case，然后逐层返回答案，这就叫 「⾃顶向下」。
 * 啥叫「⾃底向上」？反过来，我们直接从最底下，最简单，问题规模最⼩的 f(1) 和 f(2) 开始往上推，直 到推到我们想要的答案 f(20)，这就是动态规划的思路，
 * 这也是为什么动态规划⼀般都脱离了递归，⽽是由 循环迭代完成计算。
 * @param <T>
 */
public class EggBuild<T> {

    private T v;

    public static void main(String[] args) {

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
