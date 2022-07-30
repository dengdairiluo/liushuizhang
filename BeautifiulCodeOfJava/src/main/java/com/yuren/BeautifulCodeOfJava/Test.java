package com.yuren.BeautifulCodeOfJava;

/**
 * Created with Intellij IDEA.
 * Description:
 *
 * @author lujiang
 * @date 2022-07-30 22:17
 */
public class Test {
    public void demo() {
        int[] a = new int[10];
        a[3] = 92;
        System.out.println(a[3]);
    }

    public static void main(String[] args) {
        Test test = new Test();
        test.demo();
    }
}
