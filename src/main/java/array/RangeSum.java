package array;

import java.util.*;

/**
 * 快速 频繁 的 获取，数组内范围的值
 * 是原始数组不会被修 改的情况下，频繁查询某个区间的累加和。
 */
public class RangeSum {
    

    /**
     *  求一个数组范围内的和  从 i到 j，包含 i  j，i < j
     */
    private static void getArraySumRang(){
//        int[] array={-1,5,-4,4,6,7};
        int[] array={1,2,3,0,6};

//        int[] arraySum=new int[array.length+1];
//        for (int i = 0; i < array.length; i++) {
//
//            arraySum[i+1]=array[i]+arraySum[i];
//        }
        int[] arraySum=new int[array.length];
        for (int i = 0; i < array.length; i++) {
            int leftArray=0;
            if (i>0){
                leftArray=arraySum[i-1];
            }
            arraySum[i]=array[i]+leftArray;
        }

        int begin=2;
        int end=5;
//        2 指的是普通人习惯上的 2，对于数据来说，是从0开始，所以 减一
        int rangSum=arraySum[end-1]-arraySum[begin-1];
//        System.out.println(rangSum);

    }

    /**
     * 连续子序列和为 sum和 子集数量
     * 入参是 前缀和
     * 关于mp.put(0, 1); 这一行的作用就是为了应对 nums[0] +nums[1] + ... + nums[i] == k 的情况的, 也
     * 就是从下标 0 累加到下标 i, 举个例子说明, 如数组 [1, 2, 3, 6],
     * 那么这个数组的累加和数组为 [1, 3, 6, 12] 如果 k = 6, 假如map中没有预先 put 一个 (0, 1) ,
     * 如果此时我们来到了累加和为 6 的位置, 这时map中的情况是 (1, 1), (3, 1),
     * 而 mp.containsKey(pre - k) , 这时 pre - k 也就是 6 - 6 = 0,
     * 因为 map 中没有 (0, 1) 所以 count 的值没有加一,
     * 其实这个时候我们就是忽略了从下标 0 累加到下标 i 等于 k 的情况,
     * 我们仅仅是统计了从下标大于 0 到某个位置等于 k 的所有答案,
     *
     * 至于为什么是 count += mp.get(pre - k); 呢 ? 举个例子: k = 6, 数组 [1, 2, 3, 0, 6]
     * 累加和为: [1, 3, 6, 6, 12], 明显答案应该是 4, 当我们来到第一个累加和为 6 的位置上时, pre - k = 0,
     * 也就是说从下标 0 到当前位置的累加和是一个答案, 当来到第二个 6 的位置上时,
     * 也就是说从下标 0 到当前位置的累加和是一个答案, 而当来到 12 位置上时, pre - k = 6,
     * 也就是说从累加和为 6 的子数组的后一个位置到当前位置也是满足条件的答案,
     * 而累加和为 6 的子数组只有一个吗 ? 不 ! 这个例子中他有两个,
     * 所以 count 是 加 mp.get(pre - k);, 而不是加 1,
     */
    private static void maxChildArraySum(){
        int[] arraySum={1,2,3,0,6};
        int sum=6;
        int count=0;
//        前缀和的 和
        int perSum=0;

        HashMap<Integer,Integer> sumMap=new LinkedHashMap<>();
//        如果差值就是 sum本身。比如 【1，2，3】 差值为三的话，数组本身有个3，所以初始值为0的，要加一
        sumMap.put(0,1);

        for (int i = 0; i < arraySum.length; i++) {
//            此处是前缀和
            perSum=arraySum[i]+perSum;

//            如果子数组和 ，之间的差值。等于 sum
            if (sumMap.containsKey(perSum-sum)){
//                不是 加一，某一个区间内，多个
//                比如多一个12 ，就是多了2个，本身有2个，所以是 2+2而不是2+1
                count=count+sumMap.get(perSum-sum);
            }
//            k是必须的，区分差值用
            sumMap.put(perSum,sumMap.getOrDefault(perSum,0)+1);

        }

        System.out.println(sumMap.toString());
        System.out.println(count);
    }


    /**
     * 同上，此方法的二维数组版
     * 1  2   3    4
     * 5  6   7    8
     * 9  10  11   12
     */
    private static void getArraySumRangTwoDimensionalArray(){
//        初始化数组
        int initial=1;
        int[][] array=new int[3][4];
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array[0].length; j++) {
                array[i][j]=initial++;
            }
        }
//初始化 数组中每个点 到  （0，0）的和
        int[][] arraySum=new int[3][4];
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array[0].length; j++) {

                int arrayICumulative=0;
                int arrayJCumulative=0;
                int arrayIJCumulative=0;
                if (i-1>=0){
                    arrayICumulative=arraySum[i- 1][j];
                    if (j-1>=0) {
                        arrayIJCumulative = arraySum[i- 1][j-1];
                    }
                }
                if (j-1>=0){
                    arrayJCumulative=arraySum[i][j - 1];
                }

                arraySum[i][j]=arrayJCumulative+array[i][j]+arrayICumulative-arrayIJCumulative;

            }
        }
//        剩下的需要自定义 比如（0，1） 到 （1，3）
        System.out.println(arraySum[1][3]-arraySum[1][0]);
    }
    
    
    public static void main(String[] args) {
//        getArraySumRang();
//        getArraySumRangTwoDimensionalArray();
//        maxChildArraySum();
//        sum();
    }

    private static void sum(){
//        例子1
//        int[] arraySum={1,2,3,0,6};
//        int target=6;
//  例子 2
        int[] arraySum={1,6,0,8};
        int target=7;
        Arrays.sort(arraySum);
        int  curSum=0;
        int[]  result={-1,-1,-1,-1,-1};
        int resultIndex=0;
        int minIndex=0;
        for (int i = arraySum.length - 1; i >= 0; i--) {
            if (arraySum[i]>target){
                minIndex=i;
                continue;
            }
            curSum=curSum+arraySum[i];
            if (curSum<target) {
                result[resultIndex] = arraySum[i];
            }else if(curSum==target){
                result[resultIndex] = arraySum[i];
                break;
            }
            else {
                result[resultIndex] = arraySum[0];
                break;
            }
            resultIndex++;
        }
        if (result[0]==-1){
            result[minIndex]=arraySum[minIndex];
        }
        System.out.println(curSum);
        System.out.println(Arrays.toString(result));
    }

}
