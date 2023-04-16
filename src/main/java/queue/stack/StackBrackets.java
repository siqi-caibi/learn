package queue.stack;

import java.util.Stack;

/**
 * 括号匹配 使用 栈
 */
public class StackBrackets {

    public static void main(String[] args) {

    }
    /**
     * 括号是否匹配
     * 但实际上直接照搬这种思路是不⾏的，⽐如说只有⼀个括号的情况下 (()) 是合法的，但是多种括号的情况 下， [(]) 显然是不合法的。
     * 我们这道题就⽤⼀个名为 left 的栈代替之前思路中的 left 变量，遇到左括号就⼊栈，遇到右括号就去栈 中寻找最近的左括号，看是否匹配：
     */
    private static boolean isValid(String str) {
        Stack<Character> left=new Stack<>();
//堆里只放左括号
        for (Character c : str.toCharArray()) {
            if (c == '(' || c == '{' || c == '[')
                left.push(c);
            else { // 字符 c 是右括号
                if (!left.empty() && leftOf(c) == left.peek())
                    left.pop();
                else
// 和最近的左括号不匹配
                    return false;
            }
        }
// 是否所有的左括号都被匹配了
        return left.empty();
    }

    private static char leftOf(char c) {
        if (c == '}') return '{';
        if (c == ')') return '(';
        return '[';
    }


    /**
     * 平衡括号串（⼀） 先来个简单的，⼒扣第 921 题「使括号有效的最少添加」
     *
     * ⽐如说输⼊ s = "())("，算法应该返回 2，因为我们⾄少需要插⼊两次把 s 变成 "(())()"，
     */
    //private static int minInsertions(String s) {
    //    int res = 0, need = 0;
    //    for (int i = 0; i < s.length(); i++) {
    //        if (s[i] == '(') {
    //            need += 2;
    //            if (need % 2 == 1) {
    //                res++;
    //                need--;
    //            }
    //        }
    //        if (s[i] == ')') {
    //            need--;
    //            if (need == -1) {
    //                res++;
    //                need = 1;
    //            }
    //        }
    //    }
    //
    //    return res + need;
    //}

}
