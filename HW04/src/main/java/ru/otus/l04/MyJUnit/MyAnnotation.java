package ru.otus.l04.MyJUnit;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class MyAnnotation {
    static private List<Method> methods = new ArrayList<>();
    static private List<Method> beforeListMethods = new ArrayList<>();
    static private List<Method> afterListMethods = new ArrayList<>();
    static private List<Method> testListMethods = new ArrayList<>();

    public static void startPackageTest(String packagename) throws ClassNotFoundException, IOException, IllegalAccessException, InvocationTargetException, InstantiationException {
        List<Class> classes = ReflectionHelper.getClasses(packagename);
        for (Class cls : classes) {
          startTest(cls);
        }
    }

    public static void startClassTest(String classname) throws ClassNotFoundException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Class cls = ReflectionHelper.getClass(classname);
        startTest(cls);
    }

    private static void startTest(Class cls) throws IllegalAccessException, InvocationTargetException, InstantiationException {
        System.out.println("\n>CLASS TO TEST: " + cls.getName());
        clear();
        methods.addAll(ReflectionHelper.getMethods(cls));
        collectAnnotations(methods);
        if (isTest()) {
            invokeAnnotatedMethods(cls.newInstance());
        }
    }

     private static void collectAnnotations(List<Method> methods) {

        for (Method method : methods) {
            if (method.getAnnotation(Before.class) != null) {
                beforeListMethods.add(method);
            }

            if (method.getAnnotation(After.class) != null) {
                afterListMethods.add(method);
            }

            if (method.getAnnotation(Test.class) != null) {
                testListMethods.add(method);
            }
        }
    }

    private static boolean isTest() {
        return testListMethods.isEmpty()? false: true;
    }

    private static void invokeAnnotatedMethods(Object cls) throws InvocationTargetException, IllegalAccessException {
        try {
            for (Method beforeMethod : beforeListMethods) {
                beforeMethod.setAccessible(true);
                beforeMethod.invoke(cls, new Object[beforeMethod.getParameterCount()]);
            }

            for (Method testMethod : testListMethods) {
                Object[] args = new Object[testMethod.getParameterCount()];

                switch(testMethod.getParameterCount()) {
                    case 0:
                            break;
                    case 1:
                            args[0] = testMethod.getAnnotation(Test.class).valueInt1();
                            break;
                    case 2:
                            args[0] = testMethod.getAnnotation(Test.class).valueInt1();
                            args[1] = testMethod.getAnnotation(Test.class).valueInt2();
                            break;
                }

                testMethod.setAccessible(true);

                testMethod.invoke(cls, args);
            }

            for (Method afterMethod : afterListMethods) {
                afterMethod.setAccessible(true);
                afterMethod.invoke(cls, new Object[afterMethod.getParameterCount()]);
            }

        } catch (Exception e) {
            System.out.println("Test failed: " + e.getCause());
        }
    }

    private static void clear() {
        methods.clear();
        beforeListMethods.clear();
        afterListMethods.clear();
        testListMethods.clear();
    }

    }

