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
//        minimumWindowSubstring();
        permutationInString();
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
        }else {
            System.out.println(s.substring(start, end));
        }
    }
    /**给你两个字符串 s1 和 s2 ，写一个函数来判断 s2 是否包含 s1 的排列。如果是，返回 true ；否则，返回 false 。
     换句话说，s1 的排列之一是 s2 的 子串 。
     示例 1：
     输入：s1 = "ab" s2 = "eidbaooo"
     输出：true
     解释：s2 包含 s1 的排列之一 ("ba").
     示例 2：
     输入：s1= "ab" s2 = "eidboaoo"
     输出：false
     * 这种题⽬，是明显的滑动窗⼝算法，相当给你⼀个 S 和⼀个 T，请问你 S 中是否存在⼀个⼦串，包含 T 中所 有字符且不包含其他字符？
     */
    private static void permutationInString(){
        String s="eidboaoo";
        String t="ab";
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
            char sChar=sourceArray[right];
            right++;
            if (need.containsKey(sChar)){
                window.put(sChar,window.getOrDefault(sChar,0)+1);
                if (window.get(sChar).equals(need.get(sChar))) {
                    amount ++;
                }
            }
            /**
             * 参考上题的逻辑代码，但是我们需要更精一步
             */
//            if(amount==t.length()) {
//                while (amount == t.length()) {
//                    char leftChar = sourceArray[left];
//                    start = left;
//                    end = right;
//                    left++;
//                    if (need.containsKey(leftChar)) {
//
//                        if (window.get(leftChar).equals(need.get(leftChar))) {
//                            amount--;
//                        }
////                    后改值，如果先改了，就没法做上面的判断了
//                        window.put(leftChar, window.getOrDefault(leftChar, 0) - 1);
//                    }
//                }
//                if (end-start<1){
//                    System.out.println(" ");
//                }else {
//                    System.out.println(s.substring(start,end));
//                }
////                由于没有顺序，只要匹配长度就可以了
//                if (end-start==t.length()){
//                    System.out.println("有匹配的");
//                }else {
//                    System.out.println("没有");
//                }
//
//            }
            /**
             * 优化
             * 1、本题移动 left 缩⼩窗⼝的时机是窗⼝⼤⼩⼤于 t.size() 时，应为排列嘛，显然⻓度应该是⼀样的。
             * 2、当发现 valid == need.size() 时，就说明窗⼝中就是⼀个合法的排列，所以⽴即返回 true。 ⾄于如何处理窗⼝的扩⼤和缩⼩，和最⼩覆盖⼦串完全相同。
             */
//            因为是 2个空格的滑动窗口，所以窗口大于2，就要右移。长度固定，所以不需要左移



        }


    }
}
