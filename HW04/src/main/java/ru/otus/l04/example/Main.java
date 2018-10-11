package ru.otus.l04.example;

import ru.otus.l04.MyJUnit.MyAnnotation;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

public class Main {

    public static void main(String[] args) throws IOException, ClassNotFoundException, IllegalAccessException, InvocationTargetException, InstantiationException {

        MyAnnotation.startClassTest("ru.otus.l04.test.MyOperationTest1");
        MyAnnotation.startClassTest("ru.otus.l04.test.MyOperationTest2");
        MyAnnotation.startClassTest("ru.otus.l04.test.MyOperationTest3");
        MyAnnotation.startPackageTest("ru.otus.l04.test");

    }
}
