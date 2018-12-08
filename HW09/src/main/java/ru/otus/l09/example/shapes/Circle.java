package ru.otus.l09.example.shapes;

import ru.otus.l09.example.visitor.Visitor;

public class Circle implements Shape {
    private String id;
    private int x;
    private int y;
    private int radius;

    public Circle(String id, int x, int y, int radius) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.radius = radius;
    }

    @Override
    public void move(int x, int y) {
        // move shape
    }

    @Override
    public void draw() {
        // draw shape
    }

    public String accept(Visitor visitor) {
        return visitor.visitCircle(this);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public String getId() {
        return id;
    }

    public int getRadius() {
        return radius;
    }
}