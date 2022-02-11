package exhaustively;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

/**
 * BFS 出现的常⻅场景好吧，问题的本质就是让你在⼀幅「图」中找到从起点
 * start 到终点 target 的最近距离，这个例⼦听起来很枯燥，
 * 但是 BFS 算法问题其实都是在⼲这个事⼉，把 枯燥的本质搞清楚了，再去欣赏各种问题的包装才能胸有成⽵嘛。
 *
 * 1、为什么 BFS 可以找到最短距离，DFS 不⾏吗？
 * ⾸先，你看 BFS 的逻辑，depth 每增加⼀次，队列中的所有节点都向前迈⼀步，这保证了第⼀次到达终点的 时候，⾛的步数是最少的。
 * DFS 不能找最短路径吗？其实也是可以的，但是时间复杂度相对⾼很多。
 * 你想啊，DFS 实际上是靠递归的堆 栈记录⾛过的路径，你要找到最短路径，肯定得把⼆叉树中所有树杈都探索完才能对⽐出最短的路径有多⻓ 对不对？
 * ⽽ BFS 借助队列做到⼀次⼀步「⻬头并进」，是可以在不遍历完整棵树的条件下找到最短距离的。
 * 形象点说，DFS 是线，BFS 是⾯；DFS 是单打独⽃，BFS 是集体⾏动。这个应该⽐较容易理解吧。
 * 2、既然 BFS 那么好，为啥 DFS 还要存在？ BFS 可以找到最短距离，但是空间复杂度⾼，⽽ DFS 的空间复杂度较低。
 * 还是拿刚才我们处理⼆叉树问题的例⼦，假设给你的这个⼆叉树是满⼆叉树，节点数为 N，对于 DFS 算法来 说，
 * 空间复杂度⽆⾮就是递归堆栈，最坏情况下顶多就是树的⾼度，也就是 O(logN)。 但是你想想 BFS 算法，队列中每次都会储存着⼆叉树⼀层的节点，
 * 这样的话最坏情况下空间复杂度应该是树 的最底层节点的数量，也就是 N/2，⽤ Big O 表示的话也就是 O(N)。
 * 由此观之，BFS 还是有代价的，⼀般来说在找最短路径的时候使⽤ BFS，其他时候还是 DFS 使⽤得多⼀些 （主要是递归代码好写）。
 */
public class BreadthFirstSearch {
    public static void main(String[] args) {
        openLock();
    }

    /**
     * 你有一个带有四个圆形拨轮的转盘锁。每个拨轮都有10个数字： '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' 。每个拨轮可以自由旋转：例如把 '9' 变为 '0'，'0' 变为 '9' 。每次旋转都只能旋转一个拨轮的一位数字。
     *
     * 锁的初始数字为 '0000' ，一个代表四个拨轮的数字的字符串。
     *
     * 列表 deadends 包含了一组死亡数字，一旦拨轮的数字和列表里的任何一个元素相同，这个锁将会被永久锁定，无法再被旋转。
     *
     * 字符串 target 代表可以解锁的数字，你需要给出解锁需要的最小旋转次数，如果无论如何不能解锁，返回 -1 。
     *
     * 示例 1:
     *
     * 输入：deadends = ["0201","0101","0102","1212","2002"], target = "0202"
     * 输出：6
     * 解释：
     * 可能的移动序列为 "0000" -> "1000" -> "1100" -> "1200" -> "1201" -> "1202" -> "0202"。
     * 注意 "0000" -> "0001" -> "0002" -> "0102" -> "0202" 这样的序列是不能解锁的，
     * 因为当拨动到 "0102" 时这个锁就会被锁定。
     *
     * 穷举密码组合 深度优先也能做，但是占用空间大，并且这里求最小步长，从上到下走一次就行，深度会把 最远的也走完
     * 1、会⾛回头路。⽐如说我们从 "0000" 拨到 "1000"，但是等从队列拿出 "1000" 时，还会拨出⼀个
     * "0000"，这样的话会产⽣死循环。
     * 2、没有终⽌条件，按照题⽬要求，我们找到 target 就应该结束并返回拨动的次数。 3
     * 、没有对 deadends 的处理，按道理这些「死亡密码」是不能出现的，也就是说你遇到这些密码的时候需 要跳过
     */
    private static void openLock(){
        String[] deadends = {"0201","0101","0102","1212","2002"};
        String target = "0202";

        Set<String> visited=new HashSet<>();
        Queue<String> queue=new LinkedList<>();
        queue.offer("0000");
        int step=0;
        while (!queue.isEmpty()){
            int size= queue.size();

            queueFor:
            for (int i = 0; i < size; i++) {
                String cur= queue.poll();
                if (target.equals(cur)){
                    throw new RuntimeException("step ="+step);
                }
                for (String deadend : deadends) {
                    if (deadend.equals(cur)){
                        continue queueFor;
                    }
                }
                for (int j = 0; j < 4; j++) {
                    String up=plusOne(cur,j);
                    if (!visited.contains(up)){
                        queue.offer(up);
                        visited.add(up);
                    }

                    String down=minusOne(cur,j);
                    if (!visited.contains(down)){
                        queue.offer(down);
                        visited.add(down);
                    }
                }

            }
            step++;
        }
        throw new RuntimeException("没匹配到 step ="+step);
    }

    private static String  plusOne(String s,int j){
        char[] ch = s.toCharArray();
        if (ch[j] == '9')
            ch[j] = '0';
        else
            ch[j] += 1;
        return new String(ch);
    }

    private static String  minusOne(String s, int j) {
        char[] ch = s.toCharArray();
        if (ch[j] == '0')
            ch[j] = '9';
        else
            ch[j] -= 1;
        return new String(ch);
    }

}
