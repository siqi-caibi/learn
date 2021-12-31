package array;

import java.util.HashMap;

/**
void slidingWindow(string s, string t) {
        unordered_map<char, int> need, window;
        for (char c : t)
         need.put(c, map.getOrDefault(c, 0) +1);
        int left = 0, right = 0;
        int valid = 0;
        while (right < s.size()) {
        // c 是将移⼊窗⼝的字符
        char c = s[right];
        // 右移窗⼝
        right++;
        // 进⾏窗⼝内数据的⼀系列更新
        ...
        /*** debug 输出的位置
        printf("window: [%d, %d)\n", left, right);
        // 判断左侧窗⼝是否要收缩
        while (window needs shrink) {
        // d 是将移出窗⼝的字符
        char d = s[left];
        // 左移窗⼝
        left++;
        // 进⾏窗⼝内数据的⼀系列更新
        ...
        }
        }
        }
 因为滑动窗⼝很多时候都是在处理字符串相关的问题，
 */
public class SlidingWindow {

    public static void main(String[] args) {
        minimumWindowSubstring();
    }

    /**
     * 最⼩覆盖⼦串
     * 给你一个字符串 S、一个字符串 T，请在字符串 S 里面找出：包含 T 所有字母的最小子串。
     * 示例：
     * 输入: S = "ADOBECODEBANC", T = "ABC"
     * 输出: "BANC"
     * 说明：
     * 如果 S 中不存这样的子串，则返回空字符串 ""。
     * 如果 S 中存在这样的子串，我们保证它是唯一的答案。
     */
    private static void minimumWindowSubstring(){
       String s="ADOBECODEBANC";
       String t="ABC";
       HashMap<Character,Integer> need=new HashMap<>();
        HashMap<Character,Integer> window=new HashMap<>();

        for (char c : t.toCharArray()) {
            need.put(c,1);
        }

        char[] sourceArray=s.toCharArray();
        int left=0;
        int right=0;
        int start=0;
        int end =0;
        int amount=0;
        while (right<sourceArray.length){
//            先取后加
            char sChar=sourceArray[right];
            right++;
            if (need.containsKey(sChar)){
//                必须先操作 window不然空指针
                window.put(sChar,window.getOrDefault(sChar,0)+1);
                if (window.get(sChar).equals(need.get(sChar))) {
                    amount ++;
                }
            }
            while (amount==t.length()){
//和 右指针是对称操作
//                先取后加
                char leftChar=sourceArray[left];
                start=left;
                end=right;
                left++;
                if (need.containsKey(leftChar)){

                    if (window.get(leftChar).equals(need.get(leftChar))) {
                        amount --;
                    }
//                    后改值，如果先改了，就没法做上面的判断了
                    window.put(leftChar,window.getOrDefault(leftChar,0)-1);
                }
            }
        }
        if (end-start<1){
            System.out.println(" ");
        }
        System.out.println(s.substring(start,end));
    }
}
