package ru.otus.l09.example.shapes;


import java.util.ArrayList;
import java.util.List;

public class CompoundShape implements Shape {
    public String id;
    public List<Shape> children = new ArrayList<>();

    public CompoundShape(String id) {
        this.id = id;
    }

    @Override
    public void move(int x, int y) {
        // move shape
    }

    @Override
    public void draw() {
        // draw shape
    }

    public String getId() {
        return id;
    }

    public void add(Shape shape) {
        children.add(shape);
    }
}