package ru.otus.l04;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

public class Main {

    public static void main(String[] args) throws IOException, ClassNotFoundException, IllegalAccessException, InvocationTargetException, InstantiationException {

        MyAnnotation.startClassTest("ru.otus.l04.AnnotationTest1");
        MyAnnotation.startClassTest("ru.otus.l04.AnnotationTest2");
        MyAnnotation.startPackageTest("ru.otus.l04");
    }
}
