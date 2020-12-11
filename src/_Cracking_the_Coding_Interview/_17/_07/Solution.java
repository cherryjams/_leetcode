package _Cracking_the_Coding_Interview._17._07;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @Description: 面试题 17.07. 婴儿名字
 * 每年，政府都会公布一万个最常见的婴儿名字和它们出现的频率，也就是同名婴儿的数量。有些名字有多种拼法，
 * 例如，John 和 Jon 本质上是相同的名字，但被当成了两个名字公布出来。给定两个列表，一个是名字及对应的频率，
 * 另一个是本质相同的名字对。设计一个算法打印出每个真实名字的实际频率。注意，如果 John 和 Jon 是相同的，并且 Jon 和 Johnny 相同，则 John 与 Johnny 也相同，即它们有传递和对称性。
 * 在结果列表中，选择字典序最小的名字作为真实名字。
 * @Author: matreeix
 * @Date: 2020/12/10
 */

public class Solution {
    public String[] trulyMostPopular(String[] names, String[] synonyms) {
        Map<String, Integer> map = new HashMap<>();
        Map<String, String> unionMap = new HashMap<>();
        for (String name : names) {     //统计频率
            int idx1 = name.indexOf('(');
            int idx2 = name.indexOf(')');
            int frequency = Integer.parseInt(name.substring(idx1 + 1, idx2));
            map.put(name.substring(0, idx1), frequency);
        }
        for (String pair : synonyms) {  //union同义词
            int idx = pair.indexOf(',');
            String name1 = pair.substring(1, idx);
            String name2 = pair.substring(idx + 1, pair.length() - 1);
            while (unionMap.containsKey(name1))//找name1祖宗
                name1 = unionMap.get(name1);

            while (unionMap.containsKey(name2))//找name2祖宗
                name2 = unionMap.get(name2);

            if (!name1.equals(name2)) {//祖宗不同，要合并
                int frequency = map.getOrDefault(name1, 0) + map.getOrDefault(name2, 0);//出现次数是两者之和
                String trulyName = name1.compareTo(name2) < 0 ? name1 : name2;
                String nickName = name1.compareTo(name2) < 0 ? name2 : name1;
                unionMap.put(nickName, trulyName);//小名作为大名的分支，即大名是小名的祖宗
                map.remove(nickName);//删除掉小名
                map.put(trulyName, frequency);//更新一下数据
            }
        }
        String[] res = new String[map.size()];
        int index = 0;
        for (String name : map.keySet()) {
            StringBuilder sb = new StringBuilder(name);
            sb.append('(');
            sb.append(map.get(name));
            sb.append(')');
            res[index++] = sb.toString();
        }
        return res;
    }

    public static void main(String[] args) {
        System.out.println("123(456(789)".lastIndexOf("("));
    }
}
