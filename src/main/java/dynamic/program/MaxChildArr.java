package dynamic.program;

import java.util.Arrays;
import java.util.Comparator;


public class MaxChildArr {
    public static void main(String[] args) {
//        minFallingPathSum();
//        lengthOfLI();
//        maxSubArray();
//        longestCommonSubsequence();
        erase();
    }


    /**
     * 最大 连续 子序列
     * 给定一个整数数组 nums，找到一个具有最大和的连续子数组（子数组最少包含一个元素），返回其最大和。
     * 示例 1：
     * 输入：nums = [-2,1,-3,4,-1,2,1,-5,4]
     * 输出：6
     * 解释：连续子数组[4,-1,2,1] 的和最大，为6 。
     *
     * 我⾸先想到的是滑动窗⼝算法，因为我们前⽂说过嘛，滑动窗⼝算法就是专⻔处理 ⼦串/⼦数组问题的，这⾥不就是⼦数组问题么？
     * 但是，稍加分析就发现，这道题还不能⽤滑动窗⼝算法，因为数组中的数字可以是负数。 滑动窗⼝算法⽆⾮就是双指针形成的窗⼝扫描整个数组/⼦串，
     * 但关键是，你得清楚地知道什么时候应该移动 右侧指针来扩⼤窗⼝，什么时候移动左侧指针来减⼩窗⼝。
     * ⽽对于这道题⽬，你想想，当窗⼝扩⼤的时候可能遇到负数，窗⼝中的值也就可能增加也可能减少，
     * 这种情 况下不知道什么时机去收缩左侧窗⼝，也就⽆法求出「最⼤⼦数组和」。
     */
    private static void maxSubArray(){
        int[] arr={-2,1,-3,4,-1,2,1,-5,4};
        int[] dp=new int[arr.length];
        dp[0]=arr[0];
        for (int i = 1; i < arr.length; i++) {
            dp[i]=Math.max(arr[i],dp[i-1]+arr[i]);
        }
        for (int i : dp) {
            System.out.println(i);
        }

    }
    private static void lengthOfLI(){
        int[] arr={4,2,3,1,3,4};
        int[] dp=new int[arr.length];
        for (int i=0;i< arr.length;i++){
            dp[i]=1;
            int curNum=0;
            for (int j=0;j<i;j++){
                if (arr[i]>arr[j]&&arr[j]>curNum){
                    curNum=arr[j];
                    dp[i]=dp[i]+1;
                }
            }
        }
        for (int i : dp) {
            System.out.println(i);
        }
    }

    /**
     * 最大 连续 递增子序列
     * 给你一个整数数组 nums ，找到其中最长严格递增子序列的长度。
     *
     * 子序列是由数组派生而来的序列，删除（或不删除）数组中的元素而不改变其余元素的顺序。例如，[3,6,2,7] 是数组 [0,3,1,6,2,2,7] 的子序列。
     *
     *
     * 示例 1：
     *
     * 输入：nums = [10,9,2,5,3,7,101,18]
     * 输出：4
     * 解释：最长递增子序列是 [2,3,7,101]，因此长度为 4 。
     *
     * @param nums
     * @return
     */
    static int lengthOfLIS(int[] nums) {
        int[] dp = new int[nums.length];
        // base case：dp 数组全都初始化为 1
        Arrays.fill(dp, 1);
        for (int i = 0; i < nums.length; i++) {
            for (int j = 0; j < i; j++) {
                if (nums[i] > nums[j])
                    //                    为了知道数组是否是 递增的，如果 j小于 i，例子原数组  1，2 ，和1，3
                    dp[i] = Math.max(dp[i], dp[j] + 1);
            }
        }

        int res = 0;
        for (int j : dp) {
            res = Math.max(res, j);
        }
        return res;
    }

    /**
     * 给你一个 n x n 的 方形 整数数组matrix ，请你找出并返回通过 matrix 的下降路径 的 最小和 。
     *
     * 下降路径 可以从第一行中的任何元素开始，并从每一行中选择一个元素。
     * 在下一行选择的元素和当前行所选元素最多相隔一列（即位于正下方或者沿对角线向左或者向右的第一个元素）。
     * 具体来说，位置 (row, col) 的下一个元素应当是 (row + 1, col - 1)、(row + 1, col) 或者 (row + 1, col + 1) 。
     *输入：matrix = [[2,1,3],[6,5,4],[7,8,9]]
     * 输出：13
     * 解释：如图所示，为和最小的两条下降路径
     *
     * 4 走不到 7上，不能用贪心
     */
    private static void minFallingPathSum(){
        int[][] matrix = {{2,1,3},{6,5,4},{7,8,9}};
        int[][] memo=new int[matrix.length][matrix[0].length];
        for (int i = 0; i < matrix.length; i++) {
            Arrays.fill(memo[i], 9998);
        }

        for (int j = 0; j < matrix[0].length; j++) {
            System.out.println(minFallingPathSumDp(matrix,matrix.length-1,j,memo));
        }

    }

    private static int minFallingPathSumDp(int[][] matrix,int i,int j,int[][] memo){

//        1 越界检查
        if (i<0||j<0||i>matrix.length-1||j>matrix[0].length-1){
            return 9999;
        }
        // 2、base case
        if (i == 0) {
            return matrix[0][j];
        }
//        3 备忘录
        if (memo[i][j]!=9998){
            return memo[i][j];
        }
//        4 dp公式
        memo[i][j]=matrix[i][j]+Math.min(minFallingPathSumDp(matrix,i-1,j,memo),
                Math.min(minFallingPathSumDp(matrix,i-1,j-1,memo), minFallingPathSumDp(matrix,i-1,j+1,memo)));
        return memo[i][j];

    }
    /**
     * 最长公共子序列（Longest Common Subsequence，简称 LCS）
     *
     * 输入: str1 = "babcde", str2 = "ace"
     * 输出: 3
     * 解释: 最长公共子序列是 "ace"，它的长度是 3
     */
    private static void longestCommonSubsequence(){
        char[] str1="abcde".toCharArray();
        char[] str2="ace".toCharArray();
        int[][] dp=new int[str2.length+1][str1.length+1];

        for (int i = 1; i < str2.length+1; i++) {
            for (int j = 1; j < str1.length+1; j++) {
                int max= Math.max(dp[i - 1][j], dp[i][j - 1]);
                if (str2[i-1]==str1[j-1]){
                    dp[i][j] = max+1;
                }else {
                    dp[i][j] = max;
                }
            }
        }
        System.out.println(dp[str2.length+1][str1.length+1]);

    }
/**
 * 给定一个区间的集合 intervals ，其中 intervals[i] = [starti, endi] 。返回 需要移除区间的最小数量，使剩余区间互不重叠 。
 *
 *
 * 示例 1:
 *
 * 输入: intervals = [[1,2],[2,3],[3,4],[1,3]]
 * 输出: 1
 * 解释: 移除 [1,3] 后，剩下的区间没有重叠。
 * 示例 2:
 *
 * 输入: intervals = [ [1,2], [1,2], [1,2] ]
 * 输出: 2
 * 解释: 你需要移除两个 [1,2] 来使剩下的区间没有重叠。
 */
    private static void  erase(){
        int[][] intervals = {{1,2},{2,3},{3,4},{1,3}};
        Arrays.sort(intervals, new Comparator<int[]>() {
            public int compare(int[] interval1, int[] interval2) {
                return interval1[0] - interval2[0];
            }
        });

        int n = intervals.length;
        int[] dp = new int[n];
        Arrays.fill(dp, 1);

        for (int i = 1; i < intervals.length; i++) {
            for (int j = 0; j < i; j++) {
                if ( intervals[i][0]>=intervals[j][1]){
                    dp[i]=Math.max(dp[i],dp[j]+1);
                }
            }
        }
        System.out.println();
    }
}
