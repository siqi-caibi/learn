package array;

/**
 * 最常⽤的⼆分查找场景：寻找⼀个数、寻找左侧边界、寻找右侧边界。按照给定的顺序装运,单调递增或递减
 *
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
//        banana();
        shipWithinDays();
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
    private static void banana(){
        int[] plies={30,11,23,4,20};
        int h=6;
        /**
         * 珂珂吃香蕉的速度最小是多少？多大是多少？
         *
         * 显然，最小速度应该是 1，最大速度是piles数组中元素的最大值，因为每小时最多吃一堆香蕉，胃口再大也白搭嘛。
         *
         * 这里可以有两种选择，要么你用一个 for 循环去遍历piles数组，计算最大值，要么你看题目给的约束，piles中的元素取值范围是多少，然后给right初始化一个取值范围之外的值。
         *
         * 我选择第二种，题目说了1 <= piles[i] <= 10^9，那么我就可以确定二分搜索的区间边界：
         */
        int left=1;
        int right=30;

        while (left<right){
            int mid=left+(right -left)/2;
            if (bananaFx(mid,plies)==h){
                right=mid;
            }else if(bananaFx(mid,plies)<h){
                right=mid;
            }else {
                left=mid+1;
            }
        }
        System.out.println(left);
    }
    private static int  bananaFx(int x,int[] plies){
        int y=0;
        for (int ply : plies) {
            float hours=(float) ply/(float) x;
            y=y+(int) Math.ceil(hours);
        }
       return y;
    }
    /**
     * 传送带上的包裹必须在 days 天内从一个港口运送到另一个港口。
     *
     * 传送带上的第 i 个包裹的重量为 weights[i]。每一天，我们都会按给出重量（weights）的顺序往传送带上装载包裹。我们装载的重量不会超过船的最大运载重量。
     *
     * 返回能在 days 天内将传送带上的所有包裹送达的船的最低运载能力。
     *
     *  
     *
     * 示例 1：
     *
     * 输入：weights = [1,2,3,4,5,6,7,8,9,10], days = 5
     * 输出：15
     * 解释：
     * 船舶最低载重 15 就能够在 5 天内送达所有包裹，如下所示：
     * 第 1 天：1, 2, 3, 4, 5
     * 第 2 天：6, 7
     * 第 3 天：8
     * 第 4 天：9
     * 第 5 天：10
     *
     * 请注意，货物必须按照给定的顺序装运，因此使用载重能力为 14 的船舶并将包装分成 (2, 3, 4, 5), (1, 6, 7), (8), (9), (10) 是不允许的。
     * 示例 2：
     *
     * 输入：weights = [3,2,2,4,1,4], days = 3
     * 输出：6
     * 解释：
     * 船舶最低载重 6 就能够在 3 天内送达所有包裹，如下所示：
     * 第 1 天：3, 2
     * 第 2 天：2, 4
     * 第 3 天：1, 4
     * 示例 3：
     *
     * 输入：weights = [1,2,3,1,1], D = 4
     * 输出：3
     * 解释：
     * 第 1 天：1
     * 第 2 天：2
     * 第 3 天：3
     * 第 4 天：1, 1
     *  
     *
     * 提示：
     *
     * 1 <= days <= weights.length <= 5 * 104
     * 1 <= weights[i] <= 500
     *
     */
    public static void shipWithinDays(){
        int[] plies={3,2,2,4,1,4};
        int h=3;

        int left=1;
        int right=10;
        /**
         * 显然，船的最小载重应该是weights数组中元素的最大值，因为每次至少得装一件货物走，不能说装不下嘛。
         *
         * 最大载重显然就是weights数组所有元素之和，也就是一次把所有货物都装走。
         */
        for (int ply : plies) {
            right=right+ply;
        }

        while (left<right){
            int mid=left+(right -left)/2;
            if (shipWithinDaysFx(mid,plies)==h){
                right=mid;
            }else if(shipWithinDaysFx(mid,plies)<h){
                right=mid;
            }else {
                left=mid+1;
            }
        }
        System.out.println(left);
    }

    public static int shipWithinDaysFx(int x,int[] weights){
        int y=0;
        int hour=0;
        for (int ply : weights) {
//            float hours=(float) ply/(float) x;
//            y=y+hours;
            hour=hour+ply;
            if (hour>x){
                y=y+1;
                hour=ply;
            }
        }
        return y+1;
    }
}
