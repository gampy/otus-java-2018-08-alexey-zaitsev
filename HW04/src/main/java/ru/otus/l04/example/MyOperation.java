package ru.otus.l04.example;

import java.lang.reflect.Array;

public class MyOperation<T extends Number> {
    public T x[], y[];

    public interface Operation<T extends Number> {
        T operation(T x, T y);
    }

    public interface Output<T extends Number> {
        void output(T z);
    }

     public T[] arrayOperation(T[] x, T[] y, Operation operation) {
        T[] result = (T[]) Array.newInstance(Number.class, x.length);
        for (int n = 0; n < x.length; n++) {
            result[n] = (T) operation.operation(x[n], y[n]);
        }
        return result;
    }

    public void arraySimpleOperation(T[] x, Output output) {
        for (int n = 0; n < x.length; n++) {
            output.output(x[n]);
        }
    }
}



