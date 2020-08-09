package _Cracking_the_Coding_Interview._01._09;

/**
 * @Description: 面试题 01.09. 字符串轮转
 * 字符串轮转。给定两个字符串s1和s2，请编写代码检查s2是否为s1旋转而成（比如，waterbottle是erbottlewat旋转后的字符串）。
 * @Author: Pythagodzilla
 * @Date: 2020/7/31
 */

public class Solution {
    public boolean isFlipedString(String s1, String s2) {
        if (s1.length() != s2.length()) return false;
        return (s2 + s2).contains(s1);
    }

}