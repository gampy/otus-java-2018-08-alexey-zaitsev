package ru.otus.l09.example.shapes;

import ru.otus.l09.example.visitor.Visitor;

public interface Shape {
    void move(int x, int y);
    void draw();
    String accept(Visitor visitor);
}