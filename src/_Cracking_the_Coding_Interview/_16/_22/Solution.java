package _Cracking_the_Coding_Interview._16._22;

import java.util.*;

/**
 * @Description: 面试题 16.22. 兰顿蚂蚁
 * 一只蚂蚁坐在由白色和黑色方格构成的无限网格上。开始时，网格全白，蚂蚁面向右侧。每行走一步，蚂蚁执行以下操作。
 * (1) 如果在白色方格上，则翻转方格的颜色，向右(顺时针)转 90 度，并向前移动一个单位。
 * (2) 如果在黑色方格上，则翻转方格的颜色，向左(逆时针方向)转 90 度，并向前移动一个单位。
 * <p>
 * 编写程序来模拟蚂蚁执行的前 K 个动作，并返回最终的网格。
 * 网格由数组表示，每个元素是一个字符串，代表网格中的一行，黑色方格由 'X' 表示，
 * 白色方格由 '_' 表示，蚂蚁所在的位置由 'L', 'U', 'R', 'D' 表示，分别表示蚂蚁 左、上、右、下 的朝向。
 * 只需要返回能够包含蚂蚁走过的所有方格的最小矩形。
 * @Author: matreeix
 * @Date: 2020/12/4
 */

public class Solution {
    public static int[][] printKMoves(int K) {
        int minX = 5000, maxX = 5000, minY = 5000, maxY = 5000;
        int[][] arr = new int[10000][10000];
        int x = 5000, y = 5000;
        char dir = 'R';
        while (K > 0) {
            if (arr[x][y] == 0) {
                arr[x][y] = 1;
                x += dir == 'R' ? 1 : (dir == 'L' ? -1 : 0);
                y += dir == 'U' ? 1 : (dir == 'D' ? -1 : 0);
                dir = dir == 'R' ? 'D' : dir == 'D' ? 'L' : dir == 'L' ? 'U' : 'R';
            } else {
                arr[x][y] = 0;
                x += dir == 'R' ? 1 : (dir == 'L' ? -1 : 0);
                y += dir == 'U' ? 1 : (dir == 'D' ? -1 : 0);
                dir = dir == 'R' ? 'U' : dir == 'D' ? 'R' : dir == 'L' ? 'D' : 'L';
            }
            System.out.println("x:" + x + ",y:" + y + ",dir:" + dir);
            K--;
            minX = Math.min(minX, x);
            minY = Math.min(minY, y);
            maxX = Math.max(maxX, x);
            maxY = Math.max(maxY, y);
        }
        arr[x][y] = dir == 'R' ? 2 : dir == 'D' ? 3 : dir == 'L' ? 4 : 5;
        int[][] res = new int[maxX - minX + 1][maxY - minY + 1];
        for (int i = 0; i < res.length; i++) {
            for (int j = 0; j < res[0].length; j++) {
                res[i][j] = arr[i + minX][j + minY];
            }
        }
        return res;
    }

    private class Position {
        // 横坐标 x 纵坐标 y
        int x, y;
        public Position(int x, int y) {
            this.x = x;
            this.y = y;
        }
        @Override
        public boolean equals(Object obj) {
            if (obj == this) return true;
            if (!(obj instanceof Position)) return false;
            Position o = (Position) obj;
            return x == o.x && y == o.y;
        }
        // 改写哈希算法，使两个 Position 对象可以比较坐标而不是内存地址
        @Override
        public int hashCode() {
            int result = x;
            result = 31 * result + y;
            return result;
        }
    }

    public List<String> printKMoves2(int K) {
        char[] direction = {'L', 'U', 'R', 'D'};
        // 用“向量”记录方向，顺序与上一行方向的字符顺序保持一致，每个元素的后一个元素都是可以90°向右变换得到的
        int[][] offset = {{-1, 0}, {0, -1}, {1, 0}, {0, 1}};
        // 蚂蚁的位置
        Position antPos = new Position(0, 0);
        // 蚂蚁方向的向量序号
        int antDir = 2;
        // 用集合存储所有黑块的坐标，一开始想再定义一个路径的坐标集合，发现可以直接用黑块+蚂蚁位置也能过
        Set<Position> blackSet = new HashSet<>();
        while (K > 0) {
            // 新的坐标对象用于放入集合
            Position t = new Position(antPos.x, antPos.y);
            // 如果黑块集合能存入，说明脚下的块不在集合中，也就意味着是白色，方向序号循环自增1
            if (blackSet.add(t)) antDir = (antDir + 1) % 4;
            else {
                // 否则说明脚下的块已经在集合中，也就意味着是黑色，方向序号循环自增3，相当于自减1，但是Math.floorMod取模可能消耗大？用+3替代
                antDir = (antDir + 3) % 4;
                // 别忘了删除，即将黑块变白
                blackSet.remove(t);
            }
            // 蚂蚁移动位置
            antPos.x += offset[antDir][0];
            antPos.y += offset[antDir][1];
            K--;
        }
        // 计算边界，即输出网格的行数和列数
        int left = antPos.x, top = antPos.y, right = antPos.x, bottom = antPos.y;
        for (Position pos : blackSet) {
            left = pos.x < left ? pos.x : left;
            top = pos.y < top ? pos.y : top;
            right = pos.x > right ? pos.x : right;
            bottom = pos.y > bottom ? pos.y : bottom;
        }
        char[][] grid = new char[bottom - top + 1][right - left + 1];
        // 填充白块
        for (char[] row : grid)
            Arrays.fill(row, '_');
        // 替换黑块
        for (Position pos : blackSet)
            grid[pos.y - top][pos.x - left] = 'X';
        // 替换蚂蚁
        grid[antPos.y - top][antPos.x - left] = direction[antDir];
        // 利用网格生成字符串列表
        List<String> result = new ArrayList<>();
        for (char[] row : grid)
            result.add(String.valueOf(row));
        return result;
    }

    public static void main(String[] args) {

        int[][] res = printKMoves(5);
        for (int[] num : res)
            System.out.println(Arrays.toString(num));
    }
}
