package _leetcode._CONTEST._weekly._217;

/**
 * @Description: 1672. 最富有客户的资产总量
 * 给你一个 m x n 的整数网格 accounts ，其中 accounts[i][j] 是第 i​​​​​​​​​​​​ 位客户在第 j 家银行托管的资产数量。返回最富有客户所拥有的 资产总量 。
 * 客户的 资产总量 就是他们在各家银行托管的资产数量之和。最富有客户就是 资产总量 最大的客户。
 * @Author: matreeix
 * @Date: 2020/11/30
 */

public class Solution1 {
    public int maximumWealth(int[][] accounts) {
        int res = 0;
        for (int[] account : accounts) {
            int tmp = 0;
            for (int n : account) {
                tmp += n;
            }
            res = Math.max(tmp, res);
        }
        return res;
    }
}