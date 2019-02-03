package ru.otus.l11.ORM.myORM;

import ru.otus.l11.dataSets.DataSet;
import ru.otus.l11.ORM.DataSetDAO;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

public class MyDAO<T extends DataSet> implements DataSetDAO<T> {
    private Connection session;
    private ClassMetaData<T> cmd;

    public MyDAO(Connection session, ClassMetaData cmd) {
        this.session = session;
        this.cmd = cmd;
    }

    private void preparePlaceHolders(T obj, List<ClassMetaData<T>.FieldNode> nodes, PreparedStatement statement)  {
        int phPos = 1;
        for (ClassMetaData.FieldNode node : nodes) {
            try {
                statement.setObject(phPos++, node.getFieldValue(obj));
            } catch (SQLException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    private <BT> BT getResultSetValue(ClassMetaData<T>.FieldNode node, ResultSet result) throws SQLException {
        switch (node.getType()) {
            case OBJECT:
               // return (BT) result.getObject(node.getField().getName());
                byte[] buf = result.getBytes(node.getField().getName());
                ObjectInputStream objectIn = null;
                try {
                    if (buf != null) {
                        objectIn = new ObjectInputStream(new ByteArrayInputStream(buf));
                    }
                    return (BT) objectIn.readObject();
                } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
            case STRING:
                return (BT) String.valueOf(result.getString(node.getField().getName()));
            case CHAR:
                return (BT) Character.valueOf(result.getString(node.getField().getName()).charAt(0));
            case LONG:
                return (BT) Long.valueOf(result.getLong(node.getField().getName()));
            case INT:
                return (BT) Integer.valueOf(result.getInt(node.getField().getName()));
            case SHORT:
                return (BT) Short.valueOf(result.getShort(node.getField().getName()));
            case BYTE:
                return (BT) Byte.valueOf(result.getByte(node.getField().getName()));
            case BOOLEAN:
                return (BT) Boolean.valueOf(result.getBoolean(node.getField().getName()));
            case FLOAT:
                return (BT) Float.valueOf(result.getFloat(node.getField().getName()));
            case DOUBLE:
                return (BT) Double.valueOf(result.getDouble(node.getField().getName()));
            default:
                throw new IllegalArgumentException("Unknown type to assign");
        }
    }


    public T load(long id, Class<T> clazz) throws SQLException {
        StringBuilder selectQueryBuilder = new StringBuilder("select * from %s where id = ?");

        Executor exec = new Executor(session);
        return exec.execQuery(   String.format(selectQueryBuilder.toString(), cmd.getTableName(clazz)),
                statement -> statement.setLong(1, id),
                result -> {
                    T obj = null;
                    if (result != null) {
                        result.next();
                        try {
                            if (!cmd.getDefaultConstructor().isAccessible()) {
                                cmd.getDefaultConstructor().setAccessible(true);
                            }
                            obj = cmd.getDefaultConstructor().newInstance(cmd.getDefaultConstructorParameters().toArray());

                            obj.setId(id);
                            for (ClassMetaData<T>.FieldNode node : cmd.getFieldNodes()) {
                                node.setValue(obj, getResultSetValue(node, result));
                            }

                        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
                            e.printStackTrace();
                        }
                    }
                    return obj;
                });
    }

    public void save(T dataSetChild) {
        new MyDBS(session, cmd).prepareTables(cmd);

        StringBuilder insertQueryBuilder = new StringBuilder("insert into %s ");
        insertQueryBuilder.append(cmd.getFieldNodes().stream().map(node -> node.getField().getName()).collect(Collectors.joining(", " ,"(", ")")));
        insertQueryBuilder.append(cmd.getFieldNodes().stream().map(node -> "?").collect(Collectors.joining(", " ," values(", ")")));

//        System.out.println(String.format(INSERT.toString(), cmd.getTableName(cmd.getBaseClass())));

        Executor exec = new Executor(session);
        exec.execUpdate(
                String.format(insertQueryBuilder.toString(), cmd.getTableName(cmd.getBaseClass())),
                statement -> preparePlaceHolders(dataSetChild, cmd.getFieldNodes(), statement)
        );
    }
}
