package ru.otus.l04;

public class AnnotationTest2 {

    @Test(valueInt1 = 33, valueInt2 = 7)
    public void testMethod21(int a, int b) throws ArithmeticException {
        System.out.println("a = " + a + ", b = " + b + ", a/b = " + a/b);

    }

    @Test(valueInt1 = 33)
    public void testMethod22(int a, int b) {
        System.out.println("a = " + a + ", b = " + b + ", a/b = " + a/b);
    }

    @Before
    @After
    public void testBeforeAfter() {
        System.out.println("testBeforeAfter(): before and after each method invocation");
    }

}
