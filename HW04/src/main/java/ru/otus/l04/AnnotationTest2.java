package ru.otus.l04;

public class AnnotationTest2 {


    @Test(valueInt1 = 12, valueInt2 = 7)
    public void testMethod21(int a, int b) throws ArithmeticException {
        System.out.println("a = " + a + ", b = " + b + ", a/b = " + (a/(float)b));
    }

    @Test(valueInt1 = 12)
    public void testMethod22(int a, int b) throws ArithmeticException {
        System.out.println("a = " + a + ", b = " + b + ", a/b = " + (a/(float)b));
    }

}
