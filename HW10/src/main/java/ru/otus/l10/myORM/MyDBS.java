package ru.otus.l10.myORM;

import ru.otus.l10.DataSet;
import ru.otus.l10.reflect.ClassMetaData;

public interface MyDBS<T extends DataSet> {
    void prepareTables(ClassMetaData<T> cmd);
}
