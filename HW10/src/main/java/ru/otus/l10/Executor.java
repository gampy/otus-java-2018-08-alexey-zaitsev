package ru.otus.l10;

import ru.otus.l10.reflect.Structure;

import java.lang.ref.SoftReference;
import java.lang.reflect.InvocationTargetException;
import java.sql.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Executor<T extends DataSet> {
    private final Connection connection;
    private static Set<SoftReference<Structure>> cache = new HashSet<>();


    @FunctionalInterface
    interface ExecuteHandler {
        void handle(PreparedStatement statement) throws SQLException;
    }

    @FunctionalInterface
    interface ResultHandler<T> {
        T handle(ResultSet resultSet) throws SQLException;
    }


    public Executor(Connection connection) {
        this.connection = connection;
    }

    private Structure initStructure(Class clazz) {
        for (SoftReference<Structure> st : cache) {
            try {
                if (st.get().getBaseClass().equals(clazz)) {
                    return st.get();
                }
            } catch (NullPointerException e) {
                continue;
            }
        }

        Structure st = null;
        try {
            st = new Structure(clazz);
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        cache.add(new SoftReference<>(st));
        return st;
    }

    private void preparePlaceHolders(T obj, List<Structure<T>.FieldNode> nodes, PreparedStatement statement) throws SQLException, IllegalAccessException {
        int phPos = 1;
        for (Structure.FieldNode node : nodes) {
            switch (node.getType()) {
                case OBJECT:
                    statement.setObject(phPos++, node.getFieldValue(obj));
                    break;
                case STRING:
                    statement.setString(phPos++, (String) node.getFieldValue(obj));
                    break;
                case CHAR:
                    statement.setString(phPos++, String.valueOf((char) node.getFieldValue(obj)));
                    break;
                case LONG:
                    statement.setLong(phPos++, (long) node.getFieldValue(obj));
                    break;
                case INT:
                    statement.setInt(phPos++, (int) node.getFieldValue(obj));
                    break;
                case SHORT:
                    statement.setShort(phPos++, (short) node.getFieldValue(obj));
                    break;
                case BYTE:
                    statement.setByte(phPos++, (byte) node.getFieldValue(obj));
                    break;
                case BOOLEAN:
                    statement.setBoolean(phPos++, (boolean) node.getFieldValue(obj));
                    break;
                case FLOAT:
                    statement.setFloat(phPos++, (float) node.getFieldValue(obj));
                    break;
                case DOUBLE:
                    statement.setDouble(phPos++, (double) node.getFieldValue(obj));
                    break;
                default:
                    throw new IllegalArgumentException("Unknown type to put into a table");
            }
        }
    }

    private <BT> BT getResultSetValue(Structure<T>.FieldNode node, ResultSet result) throws SQLException {
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


    private void prepareTables(Structure<T> st) {
        StringBuilder CREATE_TABLE = new StringBuilder("create table if not exists %s (id bigint(20) NOT NULL auto_increment PRIMARY KEY");
        st.getFieldNodes().forEach(node -> {
                    String columnName = node.getField().getName();
                    String columnType = node.getType().getSQLType();
                    CREATE_TABLE.append(", " + columnName + " " + columnType);
                });
        CREATE_TABLE.append(")");

//        System.out.println(String.format(CREATE_TABLE.toString(), st.getTableName(st.getBaseClass())));

        execUpdate(String.format(CREATE_TABLE.toString(), st.getTableName(st.getBaseClass())), statement -> {} );
    }

    public void save(T dataSetChild) {
        Structure<T> st = initStructure(dataSetChild.getClass());
        prepareTables(st);

        StringBuilder INSERT = new StringBuilder("insert into %s ");
        INSERT.append(st.getFieldNodes().stream().map(node -> node.getField().getName()).collect(Collectors.joining(", " ,"(", ")")));
        INSERT.append(st.getFieldNodes().stream().map(node -> "?").collect(Collectors.joining(", " ," values(", ")")));

//        System.out.println(String.format(INSERT.toString(), st.getTableName(st.getBaseClass())));

        execUpdate(
                    String.format(INSERT.toString(), st.getTableName(st.getBaseClass())),
                    statement -> {
                        try {
                            preparePlaceHolders(dataSetChild, st.getFieldNodes(), statement);
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        }
                    }
        );

    }

    public T load(long id, Class<T> clazz) throws SQLException {
        StringBuilder SELECT = new StringBuilder("select * from %s where id = ?");
        Structure<T> st = initStructure(clazz);

        return execQuery(   String.format(SELECT.toString(), st.getTableName(clazz)),
                            statement -> statement.setLong(1, id),
                            result -> {
                                T obj = null;
                                if (result != null) {
                                    result.next();
                                    try {
                                        if (!st.getDefaultConstructor().isAccessible()) {
                                            st.getDefaultConstructor().setAccessible(true);
                                        }
                                        obj = st.getDefaultConstructor().newInstance(st.getDefaultConstructorParameters().toArray());

                                        obj.setId(id);
                                        for (Structure<T>.FieldNode node : st.getFieldNodes()) {
                                            node.setValue(obj, getResultSetValue(node, result));
                                        }

                                    } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
                                        e.printStackTrace();
                                    }
                                }
                                return obj;
                            });
    }

    private void execUpdate(String query, ExecuteHandler prepare) {
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            prepare.handle(statement);
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private <T> T execQuery(String query, ExecuteHandler prepare, ResultHandler<T> handleResult) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            prepare.handle(statement);
            statement.execute();
            ResultSet result = statement.getResultSet();
            return handleResult.handle(result);
        }
    }

    Connection getConnection() {
        return connection;
    }
}

