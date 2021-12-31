package DynamicProgram;

import java.util.Arrays;

/**
 * 给定一个整数数组 nums ，找到一个具有最大和的连续子数组（子数组最少包含一个元素），返回其最大和。
 *
 *  
 *
 * 示例 1：
 *
 * 输入：nums = [-2,1,-3,4,-1,2,1,-5,4]
 * 输出：6
 * 解释：连续子数组 [4,-1,2,1] 的和最大，为 6 。
 *
 * 作者：力扣 (LeetCode)
 * 链接：https://leetcode-cn.com/leetbook/read/top-interview-questions-easy/xn3cg3/
 * 来源：力扣（LeetCode）
 * 著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。
 */
public class MaxChildArr {
    public static void main(String[] args) {
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
}
