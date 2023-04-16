package treeSem;



public class TreeNode {

    public int value;
    public TreeNode left;
    public TreeNode right;
    public TreeNode next;


    public TreeNode(int value) {
        this.value = value;
    }

    public TreeNode init(int root,int left,int right){
        TreeNode rootNode=new TreeNode(root);
        TreeNode leftNode=new TreeNode(left);
        TreeNode rightNode=new TreeNode(right);
        rootNode.left=leftNode;
        rootNode.right=rightNode;
        return rootNode;
    }
}
