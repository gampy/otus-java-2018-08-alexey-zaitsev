package ru.otus.l10.reflect;

import ru.otus.l10.DataSet;
import ru.otus.l10.EmployeeDataSet;
import ru.otus.l10.UserDataSet;
import ru.otus.l10.cache.CachedElementImpl;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.*;

public class ClassMetaData<T extends DataSet> extends CachedElementImpl {

    private Class<T> baseClass;
    private Constructor<T> defaultConstructor;
    private List<Object> defaultConstructorParameters = new LinkedList<>();
    private List<FieldNode> fieldNodes = new LinkedList<>();

    private static final Map<Class<?>, Object> primitivesDefaultValues = new HashMap<>();
    private static final Map<Class<?>, FieldValueType> typesMapping = new HashMap<>();
    private static final Map<Class<? extends DataSet>, String> tables = new HashMap<>();

    public ClassMetaData(Class<T> c) throws InstantiationException, IllegalAccessException {
        if (c == null) {
            throw new NullPointerException("The object can't point to null");
        }

        registerPrimitivesDefaultValues();
        registerTypesMapping();
        registerTableNames();

        baseClass = c;
        setDefaultConstructor(c);
        createFieldNodes(c);
    }

    private static void registerPrimitivesDefaultValues() {
        addDefaultValues(int.class, 0);
        addDefaultValues(long.class, (long) 0);
        addDefaultValues(double.class, 0.0);
        addDefaultValues(float.class, (float) 0.0);
        addDefaultValues(byte.class, (byte)0);
        addDefaultValues(short.class, (short)0);
        addDefaultValues(char.class, Character.MIN_VALUE);
        addDefaultValues(boolean.class, false);
    }

    private static <BT> void addDefaultValues(Class<BT> k, BT v) {
        primitivesDefaultValues.putIfAbsent(k, v);
    }

    private static void registerTypesMapping() {
        addTypes(int.class, FieldValueType.INT);
        addTypes(long.class, FieldValueType.LONG);
        addTypes(double.class, FieldValueType.DOUBLE);
        addTypes(float.class, FieldValueType.FLOAT);
        addTypes(byte.class, FieldValueType.BYTE);
        addTypes(short.class, FieldValueType.SHORT);
        addTypes(char.class, FieldValueType.CHAR);
        addTypes(boolean.class, FieldValueType.BOOLEAN);

        addTypes(Integer.class, FieldValueType.INT);
        addTypes(Long.class, FieldValueType.LONG);
        addTypes(Double.class, FieldValueType.DOUBLE);
        addTypes(Float.class, FieldValueType.FLOAT);
        addTypes(Byte.class, FieldValueType.BYTE);
        addTypes(Short.class, FieldValueType.SHORT);
        addTypes(Character.class, FieldValueType.CHAR);
        addTypes(String.class, FieldValueType.STRING);
        addTypes(Boolean.class, FieldValueType.BOOLEAN);

        addTypes(Object.class, FieldValueType.OBJECT);
    }

    private static void addTypes(Class k, FieldValueType v) {
        typesMapping.putIfAbsent(k, v);
    }

    private static void registerTableNames() {
        addTableNames(UserDataSet.class, "user");
    }

    private static void addTableNames(Class c, String tableName) {
        tables.putIfAbsent(c, tableName);
    }


    public String getTableName(Class<T> c) {
        return tables.getOrDefault(c, c.getSimpleName());
    }

    public Class<T> getBaseClass() {
        return baseClass;
    }

    public List<FieldNode> getFieldNodes() {
        return fieldNodes;
    }

    private void setDefaultConstructor(Class<T> c) throws IllegalAccessException, InstantiationException {
        if (c == null) {
            throw new NullPointerException("The default constructor has not been found");
        }

        try {
            defaultConstructor = c.getDeclaredConstructor(long.class);
            defaultConstructorParameters.add(0);
        } catch (NoSuchMethodException e) {
            defaultConstructor = (Constructor<T>) c.getDeclaredConstructors()[0];

            for (Class<?> pType : defaultConstructor.getParameterTypes()) {
                defaultConstructorParameters.add((pType.isPrimitive()) ? primitivesDefaultValues.get(pType) : null);
            }
        }
    }

    public Constructor<T> getDefaultConstructor() {
        return defaultConstructor;
    }

    public List<Object> getDefaultConstructorParameters() {
        return defaultConstructorParameters;
    }

    private void createFieldNodes(Class<T> c) {
        Field[] fields = c.getDeclaredFields();
        for (Field field : fields) {
            if (!field.isAccessible()) {
                field.setAccessible(true);
            }

            fieldNodes.add(new FieldNode(field));
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        if (!fieldNodes.isEmpty()) {
            for (FieldNode fieldNode : fieldNodes) {
                try {
                    sb.append("Field: " + fieldNode.field.getName() + "\r\n")
                            .append("Value Type: " + fieldNode.getType()  + "\r\n")
                            .append("\n\r");
                } catch (NullPointerException e) {
                    sb.append("\n\r");
                    continue;
                }
            }
        }
        return sb.toString();
    }

    public class FieldNode {
        private Field field;
        private FieldValueType fieldValueType;

        FieldNode(Field field) {
            this.field = field;
            this.fieldValueType = setType(field.getType());
        }

        private FieldValueType setType(Class<?> c) {
            FieldValueType type = typesMapping.get(c);
            if (type != null) return type;
            else {
                if (c.isAssignableFrom(String.class)) return FieldValueType.STRING;
                if (c.isAssignableFrom(Character.class)) return FieldValueType.STRING;
            }
            return FieldValueType.OBJECT;
        }

        public Field getField() {
            return field;
        }

        public Object getFieldValue(Object obj) throws IllegalAccessException {
            if (!field.isAccessible()) {
                field.setAccessible(true);
            }
            return field.get(obj);
        }

        public FieldValueType getType() {
            return fieldValueType;
        }

        public <BT> void setValue(Object obj, BT value) throws IllegalAccessException {
            switch (this.getType()) {
                case OBJECT:
                    field.set(obj, value);
                    break;
                case STRING:
                    field.set(obj, value);
                    break;
                case CHAR:
                    field.setChar(obj, (char) value);
                    break;
                case LONG:
                    field.setLong(obj, (long) value);
                    break;
                case INT:
                    field.setInt(obj, (int) value);
                    break;
                case SHORT:
                    field.setShort(obj, (short) value);
                    break;
                case BYTE:
                    field.setByte(obj, (byte) value);
                    break;
                case BOOLEAN:
                    field.setBoolean(obj, (boolean) value);
                    break;
                case FLOAT:
                    field.setFloat(obj, (float) value);
                    break;
                case DOUBLE:
                    field.setDouble(obj, (double) value);
                    break;
                default:
                    throw new IllegalArgumentException("Unknown type to assign");
            }
        }
    }

    public enum FieldValueType {
        OBJECT("blob"),
        STRING("varchar"),
        CHAR("varchar(1)"),
        LONG("bigint"),
        INT("int"),
        SHORT("smallint"),
        BYTE("tinyint"),
        BOOLEAN("boolean"),
        FLOAT("real"),
        DOUBLE("double"),
        ;

        private String sqlName;

        FieldValueType(String name) {
            this.sqlName = name;
        }

        public String getSQLType() {
            return sqlName;
        }
    }
}
