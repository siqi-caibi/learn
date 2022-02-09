package queue.stack;

import java.util.Stack;

/**
 * ，先进后出的逻辑顺序，符合某些问题的特点，⽐如说函数调⽤栈。 单调栈实际上就是栈，
 * 只是利⽤了⼀些巧妙的逻辑，使得每次新元素⼊栈后，栈内的元素都保持有序（单调 递增或单调递减）。
 */
public class StackMonotonic {
    public static void main(String[] args) {
        //nextGreaterElement();
        //dailyTemperatures();
        nextGreaterElements();
    }
    /**
     * 给你⼀个数组 nums，请你返回⼀个等⻓的结果数组，结果数组中对应索引存储着下⼀个更⼤元素，如果没有 更⼤的元素，就存 -1。
     * ⽐如说，输⼊⼀个数组 nums = [2,1,2,4,3]，你返回数组 [4,2,4,-1,-1]。
     * 解释：第⼀个 2 后⾯⽐ 2 ⼤的数是 4; 1 后⾯⽐ 1 ⼤的数是 2；第⼆个 2 后⾯⽐ 2 ⼤的数是 4; 4 后⾯没有⽐ 4 ⼤的数，填 -1；3 后
     * ⾯没有⽐ 3 ⼤的数，填 -1。
     */
    private static void nextGreaterElement(){
        int[] nums={2,1,2,4,3};
        int[] res=new int[nums.length];
        Stack<Integer> stack=new Stack<>();
        for (int i = nums.length-1; i >= 0; i--) {
            while (!stack.empty()&&nums[i]>=stack.peek()){
                stack.pop();
            }
            res[i]=stack.empty()?-1:stack.peek();
            stack.push(nums[i]);
        }
        System.out.println(res);
    }
    /**
     * ⼒扣第 739 题「每⽇温度」： 给你⼀个数组 T，这个数组存放的是近⼏天的天⽓⽓温，你返回⼀个等⻓的数组，计算：对于每⼀天，你还 要⾄少等多少天才能等到⼀个更暖和的⽓温；如果等不到那⼀天，填 0。
     * ⽐如说给你输⼊ T = [73,74,75,71,69,76]，你返回 [1,1,3,2,1,0]。
     */
    private static void  dailyTemperatures(){
        int[] nums={73,74,75,71,69,76};
        int[] res=new int[nums.length];
        // 这⾥放元素索引，⽽不是元素
        Stack<Integer> stack=new Stack<>();
        for (int i = nums.length-1; i >= 0; i--) {
// 将索引⼊栈，⽽不是元素
            while (!stack.empty()&&nums[i]>=nums[stack.peek()]){
                stack.pop();
            }
            // 将索引⼊栈，⽽不是元素
            res[i]=stack.isEmpty()?0:stack.peek()-i;
            stack.push(i);
        }

        System.out.println(res);
    }
    /**
     * 现在假设给你的数组是个环形的，如何处理？⼒扣第 503 题「下⼀个更⼤元 素 II」就是这个问题：
     * ⽐如输⼊⼀个数组 [2,1,2,4,3]，你返回数组 [4,2,4,-1,4]。拥有了环形属性，最后⼀个元素 3 绕了⼀ 圈后找到了⽐⾃⼰⼤的元素 4。
     */
    //⼀般是通过 % 运算符求模（余数），来获得环形特效：
    //但难点在于，⽐如输⼊是 [2,1,2,4,3]，对于最后⼀个元素 3， 如何找到元素 4 作为 Next Greater Number。 对于这种需求，常⽤套路就是将数组⻓度翻倍
    private static void cycle(){
        int[] arr = {1,2,3,4,5};
        int n = arr.length, index = 0;
        while (true) {
            System.out.println(arr[index % n]);
            index++;
        }
    }
    private static void  nextGreaterElements(){
        int[] nums={2,1,2,4,3};
        int[] res=new int[nums.length];
        Stack<Integer> stack=new Stack<>();
        //假装数组翻倍
        for (int i = nums.length*2-1; i >= 0; i--) {
            while (!stack.empty()&&nums[i%nums.length]>=stack.peek()){
                stack.pop();
            }
            res[i%nums.length]=stack.empty()?-1:stack.peek();
            stack.push(nums[i%nums.length]);
        }
        System.out.println(res);
    }
}

