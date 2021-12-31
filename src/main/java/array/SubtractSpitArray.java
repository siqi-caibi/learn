package array;


import java.util.Arrays;


/**
 * 差分数组的主要适⽤场景是频繁对原始数组的 某个区间的元素进⾏增减。
 */
public class SubtractSpitArray {

    private int[] sourceArray;
    private int[] diffArray;

    public SubtractSpitArray(int[] sourceArray) {
        this.sourceArray = sourceArray;
        buildDiffArray();
    }
    private void buildDiffArray(){
        diffArray=new int[sourceArray.length];
        diffArray[0]=sourceArray[0];
        for (int i = 1; i < sourceArray.length; i++) {
            diffArray[i]=sourceArray[i]-sourceArray[i-1];
        }
    }

    private void diffArray(int begin , int end , int addNumber){
            diffArray[begin]=diffArray[begin]+addNumber;
            int endIndex=end+1;
            //例子 0 0 0 ，如果都加3 ，那么只能在 第四位减三，不然就变成错误的 3 3 0
//        我们没有第四位，所以 大于3位，就不操作了
            if (endIndex<diffArray.length){
                diffArray[endIndex]=diffArray[endIndex]-addNumber;
            }

    }

    private int[] getResult(){
        int[] result=new int[diffArray.length];
        result[0]=diffArray[0];
        for (int i = 1; i < diffArray.length; i++) {
            result[i]=result[i-1]+diffArray[i];
        }
        return result;
    }


    public static void main(String[] args) {
        rangeAddition();
    }

    /**
     * 假设你有一个长度为 n 的数组，初始情况下所有的数字均为 0，你将会被给出 k 个更新的操作。
     * 其中，每个操作会被表示为一个三元组：[startIndex, endIndex, inc]，你需要将子数组 A[startIndex ... endIndex]
     * （包括 startIndex 和 endIndex）增加 inc。
     * 请你返回 k 次操作后的数组。
     * 输入: length = 5, updates = [[1,3,2],[2,4,3],[0,2,-2]]
     * 输出: [-2,0,3,5,3]
     */

       private static void rangeAddition(){
            int[] initArray={0,0,0,0,0};
            SubtractSpitArray spitArray=new SubtractSpitArray(initArray);
            int[][] updates= {{1,3,2},{2,4,3},{0,2,-2}};
           for (int[] update : updates) {
               spitArray.diffArray(update[0],update[1],update[2]);
               System.out.println(Arrays.toString(spitArray.getResult()));
           }
       }
       /*
       相似题

        你是⼀个开公交⻋的司机，公交⻋的最⼤载客量为 capacity，沿途要经过若⼲⻋站，给你⼀份乘客⾏程表
int[][] trips，其中 trips[i] = [num, start, end] 代表着有 num 个旅客要从站点 start 上⻋，
到站点 end 下⻋，请你计算是否能够⼀次把所有旅客运送完毕（不能超过最⼤载客量 capacity）。
        */
}
