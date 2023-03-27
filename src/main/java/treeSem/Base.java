package treeSem;

import java.util.*;

public class Base {
    public static void main(String[] args) {
        iterates();
//        invert();
//        buildTree();
//        findDuplicateSubtrees();
    }

    /**
     *                       1
     *                      2  3
     *                    4  5
     *                  6  7
     *                    8  9

     */
    public static TreeNode iteratesInit(){
        TreeNode rootNode=new TreeNode(1);
        TreeNode leftNode=new TreeNode(2);
        TreeNode rightNode=new TreeNode(3);
        rootNode.left=leftNode;
        rootNode.right=rightNode;

        TreeNode node4=new TreeNode(4);
        TreeNode node5=new TreeNode(5);
        leftNode.left=node4;
        leftNode.right=node5;

        TreeNode node6=new TreeNode(6);
        TreeNode node7=new TreeNode(7);
        node4.left=node6;
        node4.right=node7;

        TreeNode node8=new TreeNode(8);
        TreeNode node9=new TreeNode(9);
        node7.left=node8;
        node7.right=node9;
        return rootNode;
    }

    private static void iterates(){
        TreeNode root=iteratesInit();
//        beforeIterate(root);
//            midIterate(root);
//        rightIterate(root);
        levelOut(root);
//        System.out.println(count(root));
    }

    /**
     * 计算有多少个节点
     * @param root
     * @return
     */
    private static int count(TreeNode root) {
        if (root == null) {
            return 0;
        }
        // 先算出左右子树有多少节点
        int left = count(root.left);
        int right = count(root.right);
        /* 后序遍历代码位置 */
        // 加上自己，就是整棵二叉树的节点数
        int res = left + right + 1;
        return res;
    }

    /**
     * 前序遍历  中左右
     * @param node
     */
    private static void beforeIterate(TreeNode node){
        if (node==null){
            return;
        }
        System.out.println(node.value);
        beforeIterate(node.left);
        beforeIterate(node.right);
    }

    /**
     * 左中右
     * @param node
     */
    private static void midIterate(TreeNode node){
        if (node==null){
            return;
        }

        midIterate(node.left);
        System.out.println(node.value);
        midIterate(node.right);
    }

    /**
     * 左右中
     * @param node
     */
    private static void rightIterate(TreeNode node){
        if (node==null){
            return;
        }

        rightIterate(node.left);
        rightIterate(node.right);

        System.out.println(node.value);
    }
    /**
     * 层序遍历
     */
    private static void levelOut(TreeNode node){
        if (node==null){
            return;
        }
        Queue<TreeNode> queue=new LinkedList<>();
        queue.offer(node);
        List<List<Integer>> valList=new ArrayList<>();

        while (!queue.isEmpty()){
//            注意是所有值的左右，不是一个节点的左右
            List<Integer> val=new ArrayList<>();
//            注意这里是  queue一直变化，要把size拿出去
            int size= queue.size();;
            for (int i = 0; i < size; i++) {
                TreeNode nodeCur=queue.poll();
                val.add(nodeCur.value);

                if (nodeCur.left!=null){
                    queue.offer(nodeCur.left);
                }
                if (nodeCur.right!=null){
                    queue.offer(nodeCur.right);
                }
            }
            valList.add(val);


        }
        for (List<Integer> integers : valList) {
            System.out.println(integers);
        }
    }


    /**
     *             4
     *           2  7
     *         1 3  6  9

     */
    public static TreeNode invertInit(){
        TreeNode rootNode=new TreeNode(4);
        TreeNode node2=new TreeNode(2);
        TreeNode node7=new TreeNode(7);
        rootNode.left=node2;
        rootNode.right=node7;

        TreeNode node1=new TreeNode(1);
        TreeNode node3=new TreeNode(3);
        node2.left=node1;
        node2.right=node3;

        TreeNode node6=new TreeNode(6);
        TreeNode node9=new TreeNode(9);
        node7.left=node6;
        node7.right=node9;

        return rootNode;
    }
    /**
     * 翻转二叉树
     */
    private static void invert(){
        TreeNode root=invertInit();
        beforeInvertIterate(root);
        System.out.println();
    }

    private static void beforeInvertIterate(TreeNode node){
        if (node==null){
            return;
        }

        TreeNode empty=node.left;;
        node.left=node.right;
        node.right=empty;
        beforeInvertIterate(node.left);
//       为什么不能中序，因为 左边翻转后， 走到 root（4）的位置，左右交换了，新的右边，是原来的左边，翻转两次，所以失败
        beforeInvertIterate(node.right);
    }

    /**
     * 填充每个节点的下一个 右侧节点
     * @param node1
     * @param node2
     */
    private static void connectTwoNode(TreeNode node1, TreeNode node2) {
        if (node1 == null || node2 == null) {
            return;
        }

        // 将传⼊的两个节点连接
        node1.next = node2;
//        两个节点一起谦虚遍历

        // 连接相同⽗节点的两个⼦节点
        connectTwoNode(node1.left, node1.right);
        connectTwoNode(node2.left, node2.right);
        // 连接跨越⽗节点的两个⼦节点
        connectTwoNode(node1.right, node2.left);
    }

    /**
     * 二叉树 转成 链表
     * @param root
     */
    private static void flatten(TreeNode root) {
        // base case
        if (root == null) return;

        flatten(root.left);
        flatten(root.right);
        /**** 后序遍历位置 ****/
//        另外注意递归框架是后序遍历，因为我们要先拉平左右⼦树才能进⾏后续操作。
        // 1、左右⼦树已经被拉平成⼀条链表
        TreeNode left = root.left;
        TreeNode right = root.right;

        // 2、将左⼦树作为右⼦树
        root.left = null;
        root.right = left;
        // 3、将原先的右⼦树接到当前右⼦树的末端
        TreeNode p = root;
        while (p.right != null) {
            p = p.right;
        }
        p.right = right;
    }
    /**
     * 数组转 二叉树，最大的是 根，根左边的是左子树，右边的是右子树
     *  [3,2,1,6,0,5]
     *             6
     *           3   5
     *            2 0
     *             1
     */
    private static void constructMaximumBinaryTree(){
        int[] array={3,2,1,6,0,5};
        constructMaximumBinaryTreeFx(array,0,array.length-1);
    }

    private static TreeNode constructMaximumBinaryTreeFx(int[] nums,int left,int right) {
        if (left>right){
            return null;
        }
        int index=left;
        int maxValue=0;
        // 找到数组中的最⼤值
        for (int i = left; i < right; i++) {
            if (nums[i]>maxValue){
                maxValue=nums[i];
                index=i;
            }
        }
        TreeNode root = new TreeNode(maxValue);
        // 递归调⽤构造左右⼦树
        root.left = constructMaximumBinaryTreeFx(nums,left,index-1);
        root.right = constructMaximumBinaryTreeFx(nums,index+1,right);
        return root;
    }
    /**
     * 根据 前序和 中序 构造源数组
     * 遍历后的数组结构如下图
     * 前序 【中  左  右】
     * 中序 【左  中  右】
     * 后序 【左  右  中】
     */
    private static void buildTree(){
//        前序
        int[] preOrder={3,9,20,15,7};
//        中序
        int[] inOrder={9,3,15,20,7};
//        后序
        int[] postOrder={9,15,7,20,3};
//        根据前中 生成原始
        TreeNode root=buildTreeFxPreIn(preOrder,0,preOrder.length-1
                    ,inOrder,0,inOrder.length-1);
        System.out.println();
    }

    /*
        根据前中 生成原始
     */
    private static TreeNode buildTreeFxPreIn(int[] preOrder,int startPre,int endPre,
                                        int[] inOrder,int startIn,int endIn){
        if (startPre>endPre){
            return null;
        }
        TreeNode root=new TreeNode(preOrder[startPre]);
        int index=startIn;
        for (int i = startIn; i <=endIn; i++) {
            if (inOrder[i]==root.value){
                index=i;
                break;
            }
        }
        // 左⼦树的节点个数
        int leftSize=index-startIn;

        root.left=buildTreeFxPreIn(preOrder,startPre+1,startPre+leftSize
                            ,inOrder,startIn,index-1);
        root.right=buildTreeFxPreIn(preOrder,startPre+leftSize+1,endPre
                ,inOrder,index+1,endIn);
        return root;
    }

    /*
        根据前中 后 生成原始
     */
    private static TreeNode buildTreeFxInPost(int[] postOrder,int startPost,int endPost,
                                        int[] inOrder,int startIn,int endIn){
        if (startPost>endPost){
            return null;
        }
        TreeNode root=new TreeNode(postOrder[endPost]);
        int index=startIn;
        for (int i = startIn; i <=endIn; i++) {
            if (inOrder[i]==root.value){
                index=i;
                break;
            }
        }
        // 左⼦树的节点个数
        int leftSize=index-startIn;

        root.left=buildTreeFxInPost(postOrder,startPost,startPost+leftSize-1
                ,inOrder,startIn,index-1);
        root.right=buildTreeFxInPost(postOrder,startPost+leftSize,endPost-1
                ,inOrder,index+1,endIn);
        return root;
    }

    /**
     * 记录重复子树
     * 后序便利的时候，记录当前节点的 string样子
     */
    private static void findDuplicateSubtrees(){
        HashMap<String,Integer> keyNums=new HashMap<>();
        TreeNode root=findDuplicateSubtreesInit();
        findDuplicateSubtreesFx(keyNums,root);
        System.out.println();
    }

    private static TreeNode findDuplicateSubtreesInit(){
        TreeNode rootNode=new TreeNode(1);
        TreeNode node2=new TreeNode(2);
        TreeNode node3=new TreeNode(3);
        rootNode.left=node2;
        rootNode.right=node3;

        TreeNode node4=new TreeNode(4);
        node2.left=node4;


        TreeNode node22=new TreeNode(2);
        node3.left=node22;
        TreeNode node42=new TreeNode(4);
        node3.right=node42;

        TreeNode node43=new TreeNode(4);
        node22.left=node43;
        return rootNode;
    }
    private static String findDuplicateSubtreesFx(HashMap<String,Integer> keyNums,TreeNode root){

        if (root==null){
            return "#";
        }
        String left=findDuplicateSubtreesFx(keyNums,root.left);
        String right=findDuplicateSubtreesFx(keyNums,root.right);

        String rootString=left+right+root.value;
        if (keyNums.containsKey(rootString)){
            keyNums.put(rootString, keyNums.get(rootString)+1);
        }else {
            keyNums.put(rootString,1);
        }
        return rootString;
    }




}
