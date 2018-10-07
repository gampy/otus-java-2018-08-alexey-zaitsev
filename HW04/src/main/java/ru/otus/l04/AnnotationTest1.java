package ru.otus.l04;

public class AnnotationTest1 {

    @After
    public void testAfter() {
        System.out.println("testAfter(): after each method invocation");
    }


    @Test
    public void testMethod11() {
        System.out.println("Test method 1-1");
    }

    @Test
    void testMethod12() {
        String s = "123.";
        System.out.println(Integer.parseInt(s));
    }

    void testMethod13() {
        System.out.println("Test method 1-3");
    }

    @Before
    public void testBefore() {
        System.out.println("testBefore(): before each method invocation");
    }

}
