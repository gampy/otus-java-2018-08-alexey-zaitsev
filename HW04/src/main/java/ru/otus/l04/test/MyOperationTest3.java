package ru.otus.l04.test;

import ru.otus.l04.MyJUnit.*;

public class MyOperationTest3 {

    @Test(valueInt1 = 21, valueInt2 = 7)
    public void testMethod31(int a, int b) throws ArithmeticException {
        System.out.println("a = " + a + ", b = " + b + ", a/b = " + a/b);
    }

    @Test(valueInt1 = 21)
    public void testMethod32(int a, int b) {
        System.out.println("a = " + a + ", b = " + b + ", a/b = " + a/b);
    }

    @Before
    @After
    public void testBeforeAfter() {
        System.out.println("\ntestBeforeAfter(): before and after each method invocation");
    }

}
