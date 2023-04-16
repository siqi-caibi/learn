package link;

public class ListNode {
    public Integer val;
   public ListNode next;
    public ListNode prev;

    public ListNode() {
    }

    public ListNode(Integer val) {
        this.val = val;
    }

    public ListNode(int val, ListNode next) {
        this.val = val;
        this.next = next;
    }

    @Override
    public String toString() {
        return "ListNode{" +
                "val=" + val +
                ", next=" + next +
                '}';
    }

    public static ListNode init(int[] array){
        ListNode headNode=new ListNode(array[0]);
        ListNode copyNode=headNode;
        for (int i = 1; i < array.length; i++) {
            ListNode iNode=new ListNode(array[i]);
            copyNode.next=iNode;
            copyNode=iNode;
        }
        return headNode;
    }
}
