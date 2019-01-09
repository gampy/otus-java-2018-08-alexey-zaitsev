package ru.otus.l10.myORM;

import ru.otus.l10.DataSet;
import ru.otus.l10.cache.CacheEngine;
import ru.otus.l10.cache.CacheEngineImpl;
import ru.otus.l10.connection.ConnectionHelper;
import ru.otus.l10.reflect.ClassMetaData;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

public class DBService<T extends DataSet> {
    private final Connection connection;
    private static CacheEngine<Class, ClassMetaData> cache = new CacheEngineImpl<>(100, 600_000, 0, false);

    public DBService() {
        connection = ConnectionHelper.getConnection();
    }

    private ClassMetaData initStructure(Class clazz) {

        ClassMetaData cmd = cache.get(clazz);

        if (cmd == null) {
            try {
                cmd = new ClassMetaData(clazz);
            } catch (InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }
            cache.put(clazz, cmd);
        }
        return cmd;
    }

    private void preparePlaceHolders(T obj, List<ClassMetaData<T>.FieldNode> nodes, PreparedStatement statement) throws SQLException, IllegalAccessException {
        int phPos = 1;
        for (ClassMetaData.FieldNode node : nodes) {
            statement.setObject(phPos++, node.getFieldValue(obj));
        }
    }

    private <BT> BT getResultSetValue(ClassMetaData<T>.FieldNode node, ResultSet result) throws SQLException {
        switch (node.getType()) {
            case OBJECT:
                return (BT) result.getObject(node.getField().getName());
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

    private void prepareTables(ClassMetaData<T> cmd) {
        StringBuilder CREATE_TABLE = new StringBuilder("create table if not exists %s (id bigint(20) NOT NULL auto_increment PRIMARY KEY");
        cmd.getFieldNodes().forEach(node -> {
            String columnName = node.getField().getName();
            String columnType = node.getType().getSQLType();
            CREATE_TABLE.append(", " + columnName + " " + columnType);
        });
        CREATE_TABLE.append(")");

//        System.out.println(String.format(CREATE_TABLE.toString(), st.getTableName(st.getBaseClass())));

        Executor exec = new Executor(getConnection());
        exec.execUpdate(String.format(CREATE_TABLE.toString(), cmd.getTableName(cmd.getBaseClass())), statement -> {} );
    }

    public void save(T dataSetChild) {
        ClassMetaData<T> cmd = initStructure(dataSetChild.getClass());
        prepareTables(cmd);

        StringBuilder INSERT = new StringBuilder("insert into %s ");
        INSERT.append(cmd.getFieldNodes().stream().map(node -> node.getField().getName()).collect(Collectors.joining(", " ,"(", ")")));
        INSERT.append(cmd.getFieldNodes().stream().map(node -> "?").collect(Collectors.joining(", " ," values(", ")")));

//        System.out.println(String.format(INSERT.toString(), cmd.getTableName(cmd.getBaseClass())));

        Executor exec = new Executor(getConnection());
        exec.execUpdate(
                String.format(INSERT.toString(), cmd.getTableName(cmd.getBaseClass())),
                statement -> {
                    try {
                        preparePlaceHolders(dataSetChild, cmd.getFieldNodes(), statement);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
        );
    }

    public T load(long id, Class<T> clazz) throws SQLException {
        StringBuilder SELECT = new StringBuilder("select * from %s where id = ?");
        ClassMetaData<T> cmd = initStructure(clazz);

        Executor exec = new Executor(getConnection());
        return exec.execQuery(   String.format(SELECT.toString(), cmd.getTableName(clazz)),
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
    public Connection getConnection() {
        return connection;
    }
}
