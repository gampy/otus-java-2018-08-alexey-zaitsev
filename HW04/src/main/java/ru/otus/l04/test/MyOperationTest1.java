package ru.otus.l04.test;

import ru.otus.l04.MyJUnit.*;
import ru.otus.l04.example.MyOperation;

public class MyOperationTest1 {
    MyOperation op = new MyOperation();

    @Before
    public void testBefore() {
        System.out.println("\ntestBefore(): init the arrays");
        op.x = new Integer[] {1, 0, 3};
        op.y = new Integer[] {0, 3, -1};
    }


    @Test
    public void resultSum(Integer a, Integer b) {
        System.out.println("\nSum result:");
        Number[] resultSum = op.arrayOperation(op.x, op.y, (xn, yn) -> xn.intValue() + yn.intValue());
        MyOperation.Output print = z -> System.out.println(z);
        op.arraySimpleOperation(resultSum, print);
    }

    @Test
    public void resultDiv(Number a, Number b) {
        System.out.println("\nDivision result:");
        Number[] resultDiv = op.arrayOperation(op.x, op.y, (xn, yn) -> xn.intValue() / yn.intValue());
        MyOperation.Output print = z -> System.out.println(z);
        op.arraySimpleOperation(resultDiv, print);
    }


    @After
    public void testAfter() {
        System.out.println("\ntestAfter(): deallocate the arrays");
        op.x = null;
        op.y = null;
    }


}
