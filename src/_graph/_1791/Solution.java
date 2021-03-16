package _graph._1791;

/**
 * @Description: 1791. 找出星型图的中心节点
 * 有一个无向的 星型 图，由 n 个编号从 1 到 n 的节点组成。星型图有一个 中心 节点，并且恰有 n - 1 条边将中心节点与其他每个节点连接起来。
 * 给你一个二维整数数组 edges ，其中 edges[i] = [ui, vi] 表示在节点 ui 和 vi 之间存在一条边。请你找出并返回 edges 所表示星型图的中心节点。
 * @Date: 2021/3/16
 */

public class Solution {

    public int findCenter(int[][] e) {
//        return edges[0][0] == edges[1][0] ? edges[0][0] : (edges[0][1] == edges[1][0] ? edges[0][1] : edges[1][1]);
        return e[0][0] == e[1][0] || e[0][0] == e[1][1] ? e[0][0] : e[0][1];
    }

}
