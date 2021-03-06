package ru.otus.l09.example;

import ru.otus.l09.example.shapes.*;

import java.util.Arrays;
import java.util.List;

public class Demo {
    String[] arr = new String[]{"a", "b", "c"};
    List<String> list = Arrays.asList("d", "e", "f");
    Dot dot;
    Circle circle;
    Rectangle rectangle;
    CompoundShape cs;
    CompoundShape ccs;
    transient int transientTest = 0;
    Boolean toDraw = false;

    public Demo() {
        dot = new Dot("1", 10, 55);
        circle = new Circle("2", 23, 15, 10);
        rectangle = new Rectangle("3", 10, 17, 20, 30);
        Dot nullDot = null;

        cs = new CompoundShape("4");
        cs.add(dot);
        cs.add(circle);

        ccs = new CompoundShape("5");
        ccs.add(cs);
        ccs.add(rectangle);

    }

}