package exhaustively;

/**
 * 深度优先
 * DFS 算法和回溯算法可以说是师出同⻔ 本质上就是⼀种暴⼒穷举算法。
 * 你只需要思考 3 个问题：
 * 1、路径：也就是已经做出的选择。
 * 2、选择列表：也就是你当前可以做的选择。
 * 3、结束条件：也就是到达决策树底层，⽆法再做选择的条件。
 *
 */
public class DepthFirstSearch {

    public static void main(String[] args) {
        canPartitionKSubsets();
    }

    /**
     * 给你输⼊⼀个数组 nums 和⼀个正整数 k，请你判断 nums 是否能够被平分为元素和相同的 k 个⼦集
     *
     * 输入： nums = [4, 3, 2, 3, 5, 2, 1], k = 4
     * 输出： True
     * 说明： 有可能将其分成 4 个子集（5），（1,4），（2,3），（2,3）等于总和。
     */
    private static void canPartitionKSubsets(){
        int[] nums={4, 3, 2, 3, 5, 2, 1};
        int k =4;
        int sum=0;
        for (int num : nums) {
            sum=sum+num;
        }
        int target=sum/k;
        int[] buckets=new int[k];
        System.out.println(canPartitionKSubsetFx(buckets,nums,0,target));
//        System.out.println(canPartitionKSubsetBucketFx(buckets,nums,0,target));
    }

    private static boolean canPartitionKSubsetFx(int[] buckets,int[] nums,int index,int target){
//        递归结束的条件
        if (nums.length==index){
            for (int i = 0; i < buckets.length; i++) {
                if (buckets[i]!=target){
                    return false;
                }
            }
            return true;
        }
        for (int i = 0; i < buckets.length; i++) {
//            满足需求的条件
            if (buckets[i]+nums[index]>target){
                System.out.println(buckets[i]+nums[index]);
                continue;
            }
//            做出选择
            buckets[i]=buckets[i]+nums[index];
//            剩余的数字  继续选择
            if (canPartitionKSubsetFx(buckets, nums, index+1, target)){
                return true;
            }
//            撤销选择
            buckets[i]=buckets[i]-nums[index];
        }
        return false;
    }

//    private static boolean canPartitionKSubsetBucketFx(int[] buckets,int[] nums,int index,int target) {
////        递归结束的条件
//        if (buckets.length == index) {
//            for (int i = 0; i < buckets.length; i++) {
//                if (buckets[index] != target) {
//                    return false;
//                }
//            }
//            return true;
//        }
//
//        for (int i = 0; i < nums.length; i++) {
//
//            if (buckets[index]+nums[i]>target){
//                index++;
//                continue;
//            }
//            buckets[index]=buckets[index]+nums[i];
//            if (canPartitionKSubsetBucketFx(buckets, nums, index, target)){
//                return true;
//            }
//
//            buckets[index]=buckets[index]-nums[i];
//        }
//        return false;
//    }
}
