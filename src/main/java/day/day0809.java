package day;

import link.ListNode;

import java.util.HashMap;
import java.util.Map;

public class day0809 {

    public static void main(String[] args) {
//        lengthOfLongestSubstring();
//        Lru cache=new Lru(2);
//        cache.put(1,1);
//        cache.put(2,2);
//        System.out.println(cache.get(1));
//        cache.put(3,3);
//        System.out.println(cache.get(2));
//        cache.put(4,4);
//        System.out.println(cache.get(1));
        day04();
    }

    /*
    * 输入：head = [1,2,3,4,5]
输出：[5,4,3,2,1]*/
    private static void reverseList() {
        ListNode head = ListNode.init(new int[]{1, 2, 3, 4, 5});
        ListNode cur = head;
        ListNode per = null;
        while (cur != null) {
            ListNode next = cur.next;
            cur.next = per;
            per = cur;
            cur = next;
        }
        System.out.println(per);
    }

    /*给定一个字符串 s ，请你找出其中不含有重复字符的 最长子串 的长度。
输入: s = "abcabcbb"
输出: 3
解释: 因为无重复字符的最长子串是 "abc"，所以其长度为 3。
*/
    private static void lengthOfLongestSubstring() {
        String s = "abcabcbb";
        char[] sC=s.toCharArray();

        HashMap<Character,Integer> window=new HashMap<>();
        int left=-1;
        int right=-1;
        int result=0;
        while (right< sC.length-1){
            right++;

            Integer num=window.getOrDefault(sC[right],0)+1;
            window.put(sC[right],num);

                while (aleng1End(window)) {
                    left++;
                    result = Math.max(result, right - left);


                    Integer numLet = window.getOrDefault(sC[left], 0);
                    window.put(sC[left], numLet - 1);
                }

        }
        System.out.println(result);
        System.out.println(window);
        System.out.printf("%d  %d%n",left,right );
    }
    private static boolean aleng1End(HashMap<Character,Integer> window){
        for (Integer value : window.values()) {
            if (value>1){
                return true;
            }

        }
        return false;
    }

    private static class Lru{
        public ListNode head;
        public ListNode tail;
        public int size;
        public Map<Integer,ListNode> cache=new HashMap<>();

        public Lru(int size) {
            this.size = size;
            head=null;

        }

        public int get(Integer key){
            ListNode vNode=cache.get(key);
            if (vNode==null){
                return -1;
            }
            moveToHead(vNode);
            return vNode.val;
        }

        public void put(Integer key,Integer val){
            ListNode vNode=cache.get(key);
            ListNode newNode=new ListNode(val);
            if (vNode==null){

                cache.put(key,newNode);
                if (cache.size()<size){
                    addToHead(newNode);
                }else {
//                    cache.remove (key)  这里本来，listNode里面存cache中的key，然后把对应cache删掉的
                    newNode.prev=tail.prev;
                    tail.prev.next=newNode;
                    tail=newNode;
                }
            }else {
                cache.put(key,newNode);
                moveToHead(newNode);
            }




         }
         public void addToHead(ListNode cur){
            if (head==null){
                head=cur;
                tail=cur;
                head.next=tail;
                tail.prev=head;
            }else {
                cur.prev = null;
                cur.next = head;
            }
        }

        public void moveToHead(ListNode cur){
            if (cur==head){
                return;
            }
            ListNode cPre=cur.prev;
            cPre.next=cur.next;

            cur.prev=null;
            cur.next=head;
        }

    }


    /*输入：head = [1,2,3,4,5], k = 2
输出：[2,1,4,3,5]*/
    private static void day04(){
        int k=2;
        ListNode head = ListNode.init(new int[]{1, 2, 3, 4, 5});

        ListNode result=new ListNode();
        result.next=head;
        ListNode pre=result;
        ListNode end=result;
        s:while (head.next!=null){


            for (int i=0;i<k;i++){
                if (end.next==null){
                    break s;
                }
                end=end.next;

            }


            //先记录下end.next,方便后面链接链表
            ListNode next=end.next;
            //然后断开链表
            end.next=null;
            //记录下要翻转链表的头节点
            ListNode start=pre.next;
            //翻转链表,pre.next指向翻转后的链表。1->2 变成2->1。 dummy->2->1
            pre.next=redv04(start);
            //翻转后头节点变到最后。通过.next把断开的链表重新链接。
            start.next=next;
            //将pre换成下次要翻转的链表的头结点的上一个节点。即start
            pre=start;
            //翻转结束，将end置为下次要翻转的链表的头结点的上一个节点。即start
            end=start;

        }
        System.out.println(result);
    }
    private static ListNode redv04(ListNode child){
//        ListNode head=new ListNode();

        ListNode pre=null;
        while (child!=null){

            ListNode next=child.next;
            child.next=pre;
            pre=child;
            child=next;
        }
        return pre;
    }
}
