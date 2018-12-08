package ru.otus.l09.example.visitor;

import ru.otus.l09.example.shapes.Circle;
import ru.otus.l09.example.shapes.CompoundShape;
import ru.otus.l09.example.shapes.Dot;
import ru.otus.l09.example.shapes.Rectangle;

public interface Visitor {
    String visitDot(Dot dot);

    String visitCircle(Circle circle);

    String visitRectangle(Rectangle rectangle);

    String visitCompoundGraphic(CompoundShape cg);
}
