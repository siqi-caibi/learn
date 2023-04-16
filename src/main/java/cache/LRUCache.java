package cache;

import java.util.LinkedHashMap;

/**
 * Least Recently Used，
 * 删除最晚使用的
 *  缓存容量为 2
 *LRUCache cache=new LRUCache(2);
         * // 你可以把 cache 理解成⼀个队列
         * // 假设左边是队头，右边是队尾
         * // 最近使⽤的排在队头，久未使⽤的排在队尾
         * // 圆括号表示键值对 (key, val)
         *cache.put(1,1);
         * // cache = [(1, 1)]
         *cache.put(2,2);
         * // cache = [(2, 2), (1, 1)]
         *cache.get(1); // 返回 1
         * // cache = [(1, 1), (2, 2)]
         * // 解释：因为最近访问了键 1，所以提前⾄队头
         * // 返回键 1 对应的值 1
         *cache.put(3,3);
         * // cache = [(3, 3), (1, 1)]
         * // 解释：缓存容量已满，需要删除内容空出位置
         * // 优先删除久未使⽤的数据，也就是队尾的数据
         * // 然后把新的数据插⼊队头
         *cache.get(2); // 返回 -1 (未找到)
         * // cache = [(3, 3), (1, 1)]
         * // 解释：cache 中不存在键为 2 的数据
         *cache.put(1,4);
         * // cache = [(1, 4), (3, 3)]
         * // 解释：键 1 已存在，把原始值 1 覆盖为 4
         * // 不要忘了也要将键值对提前到队头
 *
 * 「为什么必须要⽤双向链表」的问题了，因为我们需要删除操作。删除⼀个节点不光要 得到该节点本身的指针，
 * 也需要操作其前驱节点的指针，⽽双向链表才能⽀持直接查找前驱，保证操作的时 间复杂度 O(1)。
 */
public class LRUCache {


}
