package ru.otus.l04;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class MyAnnotation {
    static private List<Method> methods = new ArrayList<>();
    static private List<Method> beforeListMethods = new ArrayList<>();
    static private List<Method> afterListMethods = new ArrayList<>();
    static private List<Method> testListMethods = new ArrayList<>();

    static void startPackageTest(String packagename) throws ClassNotFoundException, IOException {
        List<Class> classes = ReflectionHelper.getClasses(packagename);

        for (Class cls : classes) {
            System.out.println("\n***CLASS TO TEST: " + cls.getName());
            clear();
            methods.addAll(ReflectionHelper.getMethods(cls));
            startTest();
        }


    }

    static void startClassTest(String classname) throws ClassNotFoundException {
        Class cls = ReflectionHelper.getClass(classname);
        System.out.println("\n***CLASS TO TEST: " + cls.getName());
        clear();
        methods.addAll(ReflectionHelper.getMethods(cls));
        startTest();
    }

    static private void startTest() {

        for (Method method : methods) {
            List<Annotation> annotations = ReflectionHelper.getAnnotations(method);

            if (isBefore(annotations)) {
                beforeListMethods.add(method);
            }
            if (isAfter(annotations)) {
                afterListMethods.add(method);
            }
            if (isTest(annotations)) {
                testListMethods.add(method);
            }
        }

        for (Method testMethod : testListMethods) {
            System.out.println("\n*METHOD TO TEST: " + testMethod.getName());
            invokeTestMethod(testMethod, testMethod.getAnnotation(Test.class));
        }
    }

    private static void invokeTestMethod(Method testMethod, Annotation annotation) {
        try {
            for (Method beforeMethod : beforeListMethods) {
                beforeMethod.invoke(beforeMethod.getDeclaringClass().newInstance());
                Thread.sleep(500);
            }


            if (testMethod.getParameterTypes().length > 0) {
                testMethod.invoke(testMethod.getDeclaringClass().newInstance(), ReflectionHelper.getTestAnnotationValues(annotation));

            } else {
                testMethod.invoke(testMethod.getDeclaringClass().newInstance());
            }
            Thread.sleep(500);

            for (Method afterMethod : afterListMethods) {
                afterMethod.invoke(afterMethod.getDeclaringClass().newInstance());
                Thread.sleep(500);
            }


        } catch (Exception e) {
            System.err.println("Test failed: " + testMethod.getName() + " " + e.getCause());
        }
    }

    private static boolean isAfter(List<Annotation> annotations) {
        return (ReflectionHelper.getAnnotationClasses(annotations).contains(After.class))? true: false;
    }

    private static boolean isBefore(List<Annotation> annotations) {
        return (ReflectionHelper.getAnnotationClasses(annotations).contains(Before.class))? true: false;
    }

    private static boolean isTest(List<Annotation> annotations) {
        return (ReflectionHelper.getAnnotationClasses(annotations).contains(Test.class))? true: false;
    }

    private static void clear() {
        methods.clear();
        beforeListMethods.clear();
        afterListMethods.clear();
        testListMethods.clear();
    }

    }

