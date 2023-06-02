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

        1 2 3 4 5 6 7 8
 */
public class SlidingWindow {

    public static void main(String[] args) throws Exception{

//        minimumWindowSubstring();
//        permutationInString();
//        findAllAnagramsInString();
//        longestSubstringWithoutRepeatingCharacters();


    }
    private void formula (){
        String source="source";
        String compare="compare";
        HashMap<Character,Integer> need=new HashMap<>();
        HashMap<Character,Integer> window=new HashMap<>();
//        构建需求map
        for (char c : compare.toCharArray()) {
            need.put(c,1);
        }

        char[] sourceArray=source.toCharArray();
        int left=0;
        int right=0;
        int amount=0;
        while (right<sourceArray.length){
//            取出数组内容
            char sChar=sourceArray[right];
//            右移
            right++;
//           `````  进⾏窗⼝内数据的⼀系列更新

//            判断是否需要左指针右移
            while (amount==compare.length()){
//和 右指针是对称操作
                char leftChar=sourceArray[left];
                left++;
            }
        }
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
       String s="ADOBAECODEBANC";
       String t="ABBC";
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
        String s="eidboaboo";
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
//            一直在划
            /**
             * 优化
             * 1、本题移动 left 缩⼩窗⼝的时机是窗⼝⼤⼩⼤于 t.size() 时，应为排列嘛，显然⻓度应该是⼀样的。
             * 2、当发现 valid == need.size() 时，就说明窗⼝中就是⼀个合法的排列，所以⽴即返回 true。 ⾄于如何处理窗⼝的扩⼤和缩⼩，和最⼩覆盖⼦串完全相同。
             */
//            因为是 2个空格的滑动窗口，所以窗口大于2，就要右移。长度固定，所以不需要左移
            while (right-left>=t.length()){
                if (amount==t.length()){
                    System.out.println("有匹配的");
                    System.out.println(s.substring(left,right));
                }else {
                    System.out.println("no 有匹配的");
                }
                char leftChar=sourceArray[left];
                left++;
                if (need.containsKey(leftChar)){
                    if (window.get(leftChar).equals(need.get(leftChar))) {
                        amount --;
                    }
                    window.put(leftChar,window.getOrDefault(leftChar,0)-1);
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
        }
    }

    private static void permutationInString2() {
        String s = "eidboaboo";
        String t = "ab";
        HashMap<Character,Integer> need=new HashMap<>();
        HashMap<Character,Integer> window=new HashMap<>();
        for (char c : t.toCharArray()) {
            need.put(c,1);
        }
        char[] chars=s.toCharArray();
        int left=0;
        int right=0;
        while (right<= chars.length-1){
            char rChar=chars[right];
            if (need.containsKey(rChar)){
                window.put(rChar,window.getOrDefault(rChar,1));
            }
            right++;

            while (right-left>=t.length()){

            }
        }

    }

    /**
     *找所有字⺟异位词
     *
     * 给定两个字符串s和 p，找到s中所有p的异位词的子串，返回这些子串的起始索引。不考虑答案输出的顺序。
     * 异位词 指由相同字母重排列形成的字符串（包括相同的字符串）。
     * 示例1:
     * 输入: s = "cbaebabacd", p = "abc"
     * 输出: [0,6]
     * 解释:
     * 起始索引等于 0 的子串是 "cba", 它是 "abc" 的异位词。
     * 起始索引等于 6 的子串是 "bac", 它是 "abc" 的异位词。
     * 示例 2:
     * 输入: s = "abab", p = "ab"
     * 输出: [0,1,2]
     * 解释:
     * 起始索引等于 0 的子串是 "ab", 它是 "ab" 的异位词。
     * 起始索引等于 1 的子串是 "ba", 它是 "ab" 的异位词。
     * 起始索引等于 2 的子串是 "ab", 它是 "ab" 的异位词。
     */
    private static void findAllAnagramsInString(){
//        String s="cbaebabacd";
//        String t="abc";
        String s="abab";
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
            Character sChar=sourceArray[right];
            right++;
            if (need.containsKey(sChar)){
                window.put(sChar,window.getOrDefault(sChar,0)+1);
                if (window.get(sChar).equals(need.get(sChar))){
                    amount++;
                }
            }
            while (right-left>=t.length()){

                if (amount==t.length()){
                    System.out.println(s.substring(left,right));
                    System.out.println(" have a result ,it is left = "+left);
                }else {
                    System.out.println(" move a  left");
                }
                Character leftChar=sourceArray[left];
                left++;
                if (need.containsKey(leftChar)){
                    if (window.get(leftChar).equals(need.get(leftChar))){
                        amount--;
                    }
                    window.put(leftChar,window.get(leftChar)-1);
                }
            }
        }
    }

    /**
     * 给定一个字符串 s ，请你找出其中不含有重复字符的最长子串的长度。
     * 示例1:
     * 输入: s = "abcabcbb"
     * 输出: 3
     * 解释: 因为无重复字符的最长子串是 "abc"，所以其长度为 3。
     * 示例 2:
     * 输入: s = "bbbbb"
     * 输出: 1
     * 解释: 因为无重复字符的最长子串是 "b"，所以其长度为 1。
     * 示例 3:
     * 输入: s = "pwwkew"
     * 输出: 3
     * 解释: 因为无重复字符的最长子串是"wke"，所以其长度为 3。
     *    请注意，你的答案必须是 子串 的长度，"pwke"是一个子序列，不是子串。
     * 示例 4:
     * 输入: s = ""
     * 输出: 0
     */
    private static void longestSubstringWithoutRepeatingCharacters(){
        String s="pwwkew";
        HashMap<Character,Integer> window=new HashMap<>();
        char[] sourceArray=s.toCharArray();
        int left=0;
        int right=0;
        int start=0;
        int end =0;
        int needValue=1;
        int mapValue=0;
        int amount=0;
        while (right<sourceArray.length){
            Character sChar=sourceArray[right];
            right++;

            amount=window.getOrDefault(sChar,0)+1;
            window.put(sChar, amount);


            while (amount>needValue){
                System.out.println(s.substring(left,right-1));
                System.out.println(" have a result ,it is length = "+(right-1-left));

                Character leftChar=sourceArray[left];
                left++;
                mapValue=window.get(leftChar);
                if (mapValue>needValue){
                    amount--;
                }
                window.put(leftChar,mapValue-1);
            }
        }

    }
}
