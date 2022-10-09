package hot;

import link.ListNode;

import java.awt.*;

public class CodeTop {
    public static void main(String[] args) {
        reverseList();
    }

    /**
     * 反转链表
     * 输入：head = [1,2,3,4,5]
     * 输出：[5,4,3,2,1]
     * 将next指向前一个节点
     */
    private static void reverseList(){
        int[] headArr=new int[]{1,2,3,4,5};
        ListNode head=ListNode.init(headArr);
//将 上一个循环的节点保存下来
        ListNode preHead=null;
//        循环后，将循环的节点和，源数据分开，并保存到 pre中，源数据继续循环
        ListNode cur=head;
        while (cur!=null){
//            1指向2，改为2指向1，并且把2拿出去，由2开始 继续循环
            ListNode next=cur.next;
            cur.next=preHead;
            preHead=cur;
            cur=next;
        }

        System.out.println(preHead);

    }
    /**
     * 最长 无重复字串
     * 输入: s = "abcabcbb"
     * 输出: 3
     * 解释: 因为无重复字符的最长子串是 "abc"，所以其长度为 3。
     */

    private static void longsSubStr(){

    }
}
