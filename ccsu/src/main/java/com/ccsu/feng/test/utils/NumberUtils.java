package com.ccsu.feng.test.utils;

import java.util.Stack;

/**
 * @author admin
 * @create 2020-03-20-18:11
 */
public class NumberUtils {



    /**
     * 截取第一处的中文数字字符串
     *
     * @param str
     * @return
     */
    public static String getNumberStr(String str) {
        StringBuilder stringBuilder = new StringBuilder();
        boolean isFirst = true;
        String chineseNumStr = "零一二三四五六七八九十百千万亿";
        for (int i = 0; i < str.length(); i++) {
            String tempStr = str.substring(i, i + 1);
            if (chineseNumStr.contains(tempStr)) {
                stringBuilder.append(tempStr);
                if (isFirst) {
                    isFirst = false;
                }
            } else {
                if (!isFirst) {
                    break;
                }
            }
        }
        return stringBuilder.toString();
    }

    /**
     * 将中文数字转换为 阿拉伯数字
     *
     * @param chineseNumber 中文数字字符串
     * @return 转换后的阿拉伯数字
     * @author Tuzi294
     */
    public static long chineseNumber2Int(String chineseNumber) {
        String aval = "零一二三四五六七八九";
        String bval = "十百千万亿";
        int[] bnum = {10, 100, 1000, 10000, 100000000};
        long num = 0;
        char[] arr = chineseNumber.toCharArray();
        int len = arr.length;
        Stack<Integer> stack = new Stack<Integer>();
        for (int i = 0; i < len; i++) {
            char s = arr[i];
            //跳过零
            if (s == '零') continue;
            //用下标找到对应数字
            int index = bval.indexOf(s);
            //如果不在bval中，即当前字符为数字，直接入栈
            if (index == -1) {
                stack.push(aval.indexOf(s));
            } else { //当前字符为单位。
                int tempsum = 0;
                int val = bnum[index];
                //如果栈为空则直接入栈
                if (stack.isEmpty()) {
                    stack.push(val);
                    continue;
                }
                //如果栈中有比val小的元素则出栈，累加，乘N，再入栈
                while (!stack.isEmpty() && stack.peek() < val) {
                    tempsum += stack.pop();
                }
                //判断是否经过乘法处理
                if (tempsum == 0) {
                    stack.push(val);
                } else {
                    stack.push(tempsum * val);
                }
            }
        }
        //计算最终的和
        while (!stack.isEmpty()) {
            num += stack.pop();
        }
        return num;

    }

    /**
     * 将汉字转数字
     *
     * @param str
     * @return
     */
    public static long getIntTitle(String str) {
        String numberStr = getNumberStr(str);
        return chineseNumber2Int(numberStr);
    }
}
