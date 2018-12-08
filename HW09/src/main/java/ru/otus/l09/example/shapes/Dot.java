package ru.otus.l09.example.shapes;

import ru.otus.l09.example.visitor.Visitor;

public class Dot implements Shape {
    private String id;
    private int x;
    private int y;

    public Dot() {
    }

    public Dot(String id, int x, int y) {
        this.id = id;
        this.x = x;
        this.y = y;
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
        return visitor.visitDot(this);
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
}