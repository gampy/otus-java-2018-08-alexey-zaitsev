package ru.otus.l04;

import java.io.*;
import java.lang.annotation.Annotation;
import java.lang.reflect.*;
import java.net.URL;
import java.util.*;

public class ReflectionHelper {

    /**
     * Scans all classes accessible from the context class loader which belong to the given package and subpackages.
     *
     * @param packageName The base package
     * @return The classes
     * @throws ClassNotFoundException
     * @throws IOException
     */
    public static List<Class> getClasses(String packageName) throws ClassNotFoundException, IOException {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        assert classLoader != null;
        String path = packageName.replace('.', '/');
        Enumeration<URL> resources = classLoader.getResources(path);
        List<File> dirs = new ArrayList<>();
        while (resources.hasMoreElements()) {
            URL resource = resources.nextElement();
            dirs.add(new File(resource.getFile()));
        }
        List<Class> classes = new ArrayList<>();
        for (File directory : dirs) {
            classes.addAll(findClasses(directory, packageName));
        }
        return classes;
    }

    /**
     * Recursive method used to find all classes in a given directory and subdirs.
     *
     * @param directory   The base directory
     * @param packageName The package name for classes found inside the base directory
     * @return The classes
     * @throws ClassNotFoundException
     */
    private static List<Class> findClasses(File directory, String packageName) throws ClassNotFoundException {
        List<Class> classes = new ArrayList<>();
        if (!directory.exists()) {
            return classes;
        }
        File[] files = directory.listFiles();
        for (File file : files) {
            if (file.isDirectory()) {
                assert !file.getName().contains(".");
                classes.addAll(findClasses(file, packageName + "." + file.getName()));
            } else if (file.getName().endsWith(".class")) {
                classes.add(Class.forName(packageName + '.' + file.getName().substring(0, file.getName().length() - 6)));
            }
        }
        return classes;
    }

    /**
     * returns all the methods in a certain class
     *
     * @param cls The class
     * @return The methods
     * @throws IllegalArgumentException
     */

    public static List<Method> getMethods(Class cls) throws IllegalArgumentException {
        return Arrays.asList(cls.getDeclaredMethods());
    }

    /**
     * creates an array of args for @Test-tagged methods
     *
     * @param annotation @Test is expected
     * @return The array of args
     */

    private static Object[] getTestAnnotationValues(Annotation annotation) {
        if (annotation instanceof Test) {
            return new Object[] {((Test) annotation).valueInt1(), ((Test) annotation).valueInt2()};
        }

        return null;
    }


    /**
     * invoke an annotated method
     *
     * @param method The method to be invoked
     * @param annotationClass The class of the annotation to be checked
     * @throws IllegalAccessException, InstantiationException, InvocationTargetException
     */
    public static void invokeAnnotatedMethod(Method method, Class annotationClass) throws IllegalAccessException, InstantiationException, InvocationTargetException {
        Annotation[] annotations = method.getDeclaredAnnotations();
        for (Annotation annotation: annotations) {
            if (annotation.annotationType().equals(annotationClass)) {
                if (method.getParameterTypes().length > 0) {
                    method.invoke(method.getDeclaringClass().newInstance(), getTestAnnotationValues(annotation));
                } else {
                    method.invoke(method.getDeclaringClass().newInstance());
                }
            }
        }
    }

}