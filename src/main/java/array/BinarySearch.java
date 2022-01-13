package array;

/**
 * 最常⽤的⼆分查找场景：寻找⼀个数、寻找左侧边界、寻找右侧边界。
 *
 // 函数 f 是关于⾃变量 x 的单调函数
 int f(int x) {
 // ...
 }
 // 主函数，在 f(x) == target 的约束下求 x 的最值
 int solution(int[] nums, int target) {
 if (nums.length == 0) return -1;
 // 问⾃⼰：⾃变量 x 的最⼩值是多少？
 int left = ...;
 // 问⾃⼰：⾃变量 x 的最⼤值是多少？
 int right = ... + 1;

 while (left < right) {
 int mid = left + (right - left) / 2;
 if (f(mid) == target) {
 // 问⾃⼰：题⽬是求左边界还是右边界？
 // ...
 } else if (f(mid) < target) {
 // 问⾃⼰：怎么让 f(x) ⼤⼀点？
 // ...
 } else if (f(mid) > target) {
 // 问⾃⼰：怎么让 f(x) ⼩⼀点？
 // ...
 }
 }
 return left;
 }
 * 计算 mid 时需要防⽌溢出，代码中 left + (right - left) / 2 就和 (left +
 * right) / 2 的结果相同，但是有效防⽌了 left 和 right 太⼤直接相加导致溢出。
 *
 *
 *
 */
public class BinarySearch {
    public static void main(String[] args) {

    }

    /**
     * 珂珂喜欢吃香蕉。这里有 N 堆香蕉，第 i 堆中有 piles[i] 根香蕉。警卫已经离开了，将在 H 小时后回来。
     * 珂可以决定她吃香蕉的速度 K （单位：根/小时）。每个小时，她将会选择一堆香蕉，从中吃掉 K 根。如果这堆香蕉少于 K 根，她将吃掉这堆的所有香蕉，然后这一小时内不会再吃更多的香蕉。
     * 珂珂喜欢慢慢吃，但仍然想在警卫回来前吃掉所有的香蕉。
     * 返回她可以在 H 小时内吃掉所有香蕉的最小速度 K（K 为整数）。
     * 输入：
     * piles = [3,6,7,11], H = 8
     * 输出：
     * 4
     *
     * 输入：
     * piles = [30,11,23,4,20], H = 5
     * 输出：
     * 30
     *
     * 输入：
     * piles = [30,11,23,4,20], H = 6
     * 输出：
     * 23
     *
     * 提示
     *
     * 1 <= piles.length <= 10^4
     * piles.length <= H <= 10^9
     * 1 <= piles[i] <= 10^9
     */
    private void banana(){

    }
    private void bananaFx(){

    }
}
