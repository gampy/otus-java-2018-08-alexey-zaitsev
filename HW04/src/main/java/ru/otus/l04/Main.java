package ru.otus.l04;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) throws IOException, ClassNotFoundException, IllegalAccessException, InvocationTargetException, InstantiationException {
        List<Class> classes = ReflectionHelper.getClasses("ru.otus.l04");
        List<Method> methods = new ArrayList<>();

        for (Class cls: classes) {
            methods.addAll(ReflectionHelper.getMethods(cls));
        }

        for (Method method: methods) {
            ReflectionHelper.invokeAnnotatedMethod(method, Test.class);
        }

    }
}
