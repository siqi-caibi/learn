package link;

import java.util.PriorityQueue;

public class BaseLink {
    public static void main(String[] args) {
        //mergeTwoLists();
        //mergeKLists();
        detectCycle();
    }

    /**
     * 给两个升序，合成一个升序  类似快慢指针
     */
    public static void  mergeTwoLists(){
        ListNode l1=ListNode.init(new int[]{1, 2, 4});
        ListNode l2=ListNode.init(new int[]{1,3,4});
//  虚拟头节点
        ListNode dummy=new ListNode(-1);
        ListNode p=dummy;
        ListNode p1=l1;
        ListNode p2=l2;
        while (p1!=null&&p2!=null){
            if (p1.val<p2.val){
                p.next=p1;
                p1=p1.next;
            }else {
                p.next=p2;
                p2=p2.next;
            }
            //记得外面的也移动
            p=p.next;
        }
        //有一个链表已经跑完了，剩下的链表直接全接上就可以了
        if (p1!=null){
            p.next=p1;
        }
        if (p2!=null){
            p.next=p2;
        }
        System.out.println(dummy);
    }

    /**
     * 一堆 有序合成一个  优先级队列 二叉堆
     */
    private static void  mergeKLists() {
        ListNode[] lists=new ListNode[3];
        ListNode l1=ListNode.init(new int[]{1, 4, 5});
        ListNode l2=ListNode.init(new int[]{1,3,4});
        ListNode l3=ListNode.init(new int[]{ 2, 6});
        lists[0]=l1;
        lists[1]=l2;
        lists[2]=l3;

        //if (lists.length == 0)
        //    return null;
// 虚拟头结点
        ListNode dummy = new ListNode(-1);
        ListNode p = dummy;
// 优先级队列，最⼩堆
        PriorityQueue<ListNode> pq = new PriorityQueue<>(
                lists.length, (a, b)->(a.val - b.val));
// 将 k 个链表的头结点加⼊最⼩堆
        for (ListNode head : lists) {
            if (head != null)
                pq.add(head);
        }
        while (!pq.isEmpty()) {
// 获取最⼩节点，接到结果链表中
            ListNode node = pq.poll();
            p.next = node;
            //把除了最小的节点，在放回 优先级队列
            if (node.next != null) {
                pq.add(node.next);
            }
// p 指针不断前进
            p = p.next;
        }
        //return dummy.next;
        System.out.println(dummy);
    }

    // 返回链表的倒数第 k 个节点  暴力解法是 先循环一次得到总长度，在循环第二次走 k个位置
    private static void findFromEnd() {
        ListNode head=ListNode.init(new int[]{1, 4, 5});
        int k =2;
        ListNode p1 = head;
// p1 先⾛ k 步
        for (int i = 0; i < k; i++) {
            p1 = p1.next;
        }
        ListNode p2 = head;
// p1 和 p2 同时⾛ n - k 步
        while (p1 != null) {
            p2 = p2.next;
            p1 = p1.next;
        }
// p2 现在指向第 n - k 个节点
//        return p2;
        System.out.println(p2);
    }

    /**
     * 找到 中间点。 一个走一步，一个走两步。，快慢指针
     */
    private ListNode middleNode(ListNode head) {
// 快慢指针初始化指向 head
        ListNode slow = head, fast = head;
// 快指针⾛到末尾时停⽌
        while (fast != null && fast.next != null) {
// 慢指针⾛⼀步，快指针⾛两步
            slow = slow.next;
            fast = fast.next.next;
        }
// 慢指针指向中点
        return slow;
    }

    /**
     * 是否成 环  ，接着用 找中点的方法
     * 如果 fast 最终遇到空指针，说明链表中没有环；如果 fast 最终和 slow 相遇，那肯定是 fast 超过了
     * slow ⼀圈，说明链表中含有环。
     * @param head
     * @return
     */
    private boolean hasCycle(ListNode head) {
// 快慢指针初始化指向 head
        ListNode slow = head, fast = head;
// 快指针⾛到末尾时停⽌
        while (fast != null && fast.next != null) {
// 慢指针⾛⼀步，快指针⾛两步
            slow = slow.next;
            fast = fast.next.next;
// 快慢指针相遇，说明含有环  一个走了一圈，。一个走了两圈，此时他们要不都在第一个，要不都在最后一个
            if (slow == fast) {
                return true;
            }
        }
// 不包含环
        return false;
    }

    /**
     * 有环，计算环的起点
     *
     * fast ⼀定⽐ slow 多⾛了 k 步，这多⾛的 k 步其实就是 fast 指针在环⾥转圈圈，所以 k 的值就是环⻓度 的「整数倍」。
     * 假设相遇点距环的起点的距离为 m，那么结合上图的 slow 指针，环的起点距头结点 head 的距离为 k -
     * m，也就是说如果从 head 前进 k - m 步就能到达环起点。 巧的是，如果从相遇点继续前进 k - m 步，也恰好到达环起点。
     * 因为结合上图的 fast 指针，从相遇点开始 ⾛k步可以转回到相遇点，那⾛ k - m 步肯定就⾛到环起点了：
     */

    private static void  detectCycle() {
        ListNode dummy = new ListNode(-1);
        ListNode head=ListNode.init(new int[]{1, 2, 3,4,5});
        dummy.next=head;
        head.next=dummy;

        ListNode fast, slow;
        fast = slow = head;
        while (fast != null && fast.next != null) {
            fast = fast.next.next;
            slow = slow.next;
            if (fast == slow) break;
        }
// 上⾯的代码类似 hasCycle 函数
        if (fast == null || fast.next == null) {
// fast 遇到空指针说明没有环
//            return null;
            throw new RuntimeException("没有环");
        }
// 重新指向头结点
        slow = head;
// 快慢指针同步前进，相交点就是环起点
        while (slow != fast) {
            fast = fast.next;
            slow = slow.next;
        }
        //return slow;
        System.out.println(slow);
    }

    /**
     * 给你输⼊两个链表的头结点 headA 和 headB，这两个链表可能存在相交。 如果相交，你的算法应该返回相交的那个节点；如果没相交，则返回 null。

     这个题直接的想法可能是⽤ HashSet 记录⼀个链表的所有节点，然后和另⼀条链表对⽐，但这就需要额外 的空间。
     如果不⽤额外的空间，只使⽤两个指针，你如何做呢？ 难点在于，由于两条链表的⻓度可能不同，两条链表之间的节点⽆法对应：
     我们可以让 p1 遍历完链表 A 之后开始遍历链表 B，让 p2 遍历完链表 B 之后开始遍历链表 A，这样相 当于「逻辑上」两条链表接在了⼀起。
     如果这样进⾏拼接，就可以让 p1 和 p2 同时进⼊公共部分，也就是同时到达相交节点 c1
     */
    private ListNode getIntersectionNode(ListNode headA, ListNode headB) {
// p1 指向 A 链表头结点，p2 指向 B 链表头结点
        ListNode p1 = headA, p2 = headB;
        while (p1 != p2) {
// p1 ⾛⼀步，如果⾛到 A 链表末尾，转到 B 链表
            if (p1 == null) p1 = headB;
            else p1 = p1.next;
// p2 ⾛⼀步，如果⾛到 B 链表末尾，转到 A 链表
            if (p2 == null) p2 = headA;
            else p2 = p2.next;
        }
        return p1;
    }

    /**
     *反转链表的一部分
     */
}
