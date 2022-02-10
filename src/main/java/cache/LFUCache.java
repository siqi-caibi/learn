package cache;

import java.util.HashMap;
import java.util.LinkedHashSet;

/**
 * Least Frequently Used
 * LFU 算法相当于是把数据按照访问频次进⾏排序，这个需求恐怕没有那么简单，⽽且还有⼀种情况，
 * 如果 多个数据拥有相同的访问频次，我们就得删除最早插⼊的那个数据。
 * 也就是说 LFU 算法是淘汰访问频次最低 的数据，如果访问频次最低的数据有多条，需要淘汰最旧的数据。
 *
 * // 构造⼀个容量为 2 的 LFU 缓存
 * LFUCache cache = new LFUCache(2);
 * // 插⼊两对 (key, val)，对应的 freq 为 1
 * cache.put(1, 10);
 * cache.put(2, 20);
 * // 查询 key 为 1 对应的 val
 * // 返回 10，同时键 1 对应的 freq 变为 2
 * cache.get(1);
 * // 容量已满，淘汰 freq 最⼩的键 2
 * // 插⼊键值对 (3, 30)，对应的 freq 为 1
 * cache.put(3, 30);
 * // 键 2 已经被淘汰删除，返回 -1
 * cache.get(2);
 */
public class LFUCache {
    // key 到 val 的映射，我们后⽂称为 KV 表
    HashMap<Integer, Integer> keyToVal;
    // key 到 freq 的映射，我们后⽂称为 KF 表
    HashMap<Integer, Integer> keyToFreq;
    // freq 到 key 列表的映射，我们后⽂称为 FK 表
//   HashMap freq 对 key 是⼀对多的关系
//   Linked  便于快速查找并删除最旧的 key。
//  HashSet  因为如果频次为 freq 的某个 key 被访问，那么它的 频次就会变成 freq+1，就应该从 freq 对应的 key 列表中删除，加到 freq+1 对应的 key 的列表中。
    HashMap<Integer, LinkedHashSet<Integer>> freqToKeys;
    // 记录最⼩的频次
//    将 freq 最⼩的 key 删除，那你就得快速得到当前所有 key 最⼩的 freq 是多少。
//    想要时间复杂度 O(1) 的话，肯定不能遍历⼀遍去找，那就⽤⼀个变量 minFreq 来记录当前最⼩的 freq 吧。
    int minFreq;
    // 记录 LFU 缓存的最⼤容量
    int cap;

    public int get(int key) {
        // 增加 key 对应的 freq
        increaseFreq(key);
        return keyToVal.get(key);
    }
    public void put(int key, int val) {
        if (this.cap <= 0) return;

        /* 若 key 已存在，修改对应的 val 即可 */
        if (keyToVal.containsKey(key)) {
            keyToVal.put(key, val);
            // key 对应的 freq 加一
            increaseFreq(key);
            return;
        }

        /* key 不存在，需要插入 */
        /* 容量已满的话需要淘汰一个 freq 最小的 key */
        if (this.cap <= keyToVal.size()) {
            removeMinFreqKey();
        }

        /* 插入 key 和 val，对应的 freq 为 1 */
        // 插入 KV 表
        keyToVal.put(key, val);
        // 插入 KF 表
        keyToFreq.put(key, 1);
        // 插入 FK 表
        freqToKeys.putIfAbsent(1, new LinkedHashSet<>());
        freqToKeys.get(1).add(key);
        // 插入新 key 后最小的 freq 肯定是 1
        this.minFreq = 1;
    }
    private void removeMinFreqKey() {
        // freq 最小的 key 列表
        LinkedHashSet<Integer> keyList = freqToKeys.get(this.minFreq);
        // 其中最先被插入的那个 key 就是该被淘汰的 key
        int deletedKey = keyList.iterator().next();
        /* 更新 FK 表 */
        keyList.remove(deletedKey);
        if (keyList.isEmpty()) {
            freqToKeys.remove(this.minFreq);
            // 问：这里需要更新 minFreq 的值吗？
//            实际上没办法快速计算minFreq，只能线性遍历FK表或者KF表来计算，这样肯定不能保证 O(1) 的时间复杂度。
//但是，其实这里没必要更新minFreq变量，因为你想想removeMinFreqKey这个函数是在什么时候调用？
// 在put方法中插入新key时可能调用。而你回头看put的代码，插入新key时一定会把minFreq更新成 1，所以说即便这里minFreq变了，我们也不需要管它。
        }
        /* 更新 KV 表 */
        keyToVal.remove(deletedKey);
        /* 更新 KF 表 */
        keyToFreq.remove(deletedKey);
    }

    private void increaseFreq(int key) {
        int freq = keyToFreq.get(key);
        /* 更新 KF 表 */
        keyToFreq.put(key, freq + 1);
        /* 更新 FK 表 */
        // 将 key 从 freq 对应的列表中删除
        freqToKeys.get(freq).remove(key);
        // 将 key 加入 freq + 1 对应的列表中
        freqToKeys.putIfAbsent(freq + 1, new LinkedHashSet<>());
        freqToKeys.get(freq + 1).add(key);
        // 如果 freq 对应的列表空了，移除这个 freq
        if (freqToKeys.get(freq).isEmpty()) {
            freqToKeys.remove(freq);
            // 如果这个 freq 恰好是 minFreq，更新 minFreq
            if (freq == this.minFreq) {
                this.minFreq++;
            }
        }
    }

}
