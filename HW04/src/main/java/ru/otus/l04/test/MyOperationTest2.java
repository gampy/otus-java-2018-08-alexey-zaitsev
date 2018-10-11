package ru.otus.l04.test;

import ru.otus.l04.MyJUnit.*;
import ru.otus.l04.example.MyOperation;

public class MyOperationTest2 {
    MyOperation op = new MyOperation();

    @Before
    public void testBefore() {
        System.out.println("\ntestBefore(): init the arrays");
        op.x = new Double[] {0.0, 2.1, 5.6};
        op.y = new Double[] {1.1, 3.3, 0.0};
    }


    @Test
    public void resultSubtraction() {
        Number[] result = op.arrayOperation(op.x, op.y, (xn, yn) -> xn.doubleValue() - yn.doubleValue());
        MyOperation.Output print = z -> System.out.println(z);
        System.out.println("\nSubtraction result:");
        op.arraySimpleOperation(result, print);
    }

    @Test
    public void resultDiv() {
        Number[] result = op.arrayOperation(op.x, op.y, (xn, yn) -> xn.doubleValue() / yn.doubleValue());
        MyOperation.Output print = z -> System.out.println(z);
        System.out.println("\nDivision result:");
        op.arraySimpleOperation(result, print);
    }


    @After
    public void testAfter() {
        System.out.println("\ntestAfter(): deallocate the arrays");
        op.x = null;
        op.y = null;
    }
}
