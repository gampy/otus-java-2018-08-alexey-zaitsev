package ru.otus.l04.MyJUnit;

import java.lang.annotation.*;

@Target({ElementType.ANNOTATION_TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented

public @interface Test {
    int valueInt1() default 0;
    int valueInt2() default 0;
}
