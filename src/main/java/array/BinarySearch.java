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
}
