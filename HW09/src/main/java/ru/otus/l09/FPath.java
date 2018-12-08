package ru.otus.l09;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.*;

public class FPath {
    private final Map<Class, Class> primitives = new HashMap<>();
    private final List<Node> nodesList = new LinkedList<>();
    private RootNode rootNode;

    FPath() {
        registerPrimitiveClasses();
    }

    private void registerPrimitiveClasses() {
        registerPrimitive(int.class, Number.class);
        registerPrimitive(long.class, Number.class);
        registerPrimitive(double.class, Number.class);
        registerPrimitive(float.class, Number.class);
        registerPrimitive(byte.class, Number.class);
        registerPrimitive(short.class, Number.class);
        registerPrimitive(char.class, String.class);
        registerPrimitive(boolean.class, Boolean.class);
    }

    private void registerPrimitive(Class<?> k, Class<?> v) {
        primitives.putIfAbsent(k, v);
    }

    public void createPath(Object obj) {
        if (obj == null) {
            rootNode = null;
            return;
        } else {
            rootNode = new RootNode(obj);

            if (rootNode.isArray) {
                throw new UnsupportedOperationException("The object to serialize is an array. The operation is not supported: only objects, primitives and nested arrays can be used in MyGson");
            }

            if (!rootNode.isFieldPrimitive()) {
                createPath(obj, null);
            }
        }
    }

    private void createPath(Object obj, Node parentNode) {

        Field[] fields = obj.getClass().getDeclaredFields();
        for (Field field : fields) {
            if (!field.isAccessible()) {
                field.setAccessible(true);
            }
            try {
                Node node = new Node(field, obj, field.get(obj), parentNode);

                if (node.isFieldObjectNull()) {
                    nodesList.add(node);
                    continue;
                }

                else if (node.isArray()) {
                    for (Object arrObj : (Collection<?>) node.getFieldObject()) {
                        Node arrNode = new Node(field, obj, arrObj, parentNode);
                        nodesList.add(arrNode);
                        if (arrNode.isFieldObjectNull()) {
                            continue;
                        }
                        createPath(arrObj, arrNode);
                    }
                    continue;
                }

                else if (node.isObject()) {
                    nodesList.add(node);
                    createPath(node.getFieldObject(), node);
                    continue;
                }

                else if (node.isFieldPrimitive()) {
                    nodesList.add(node);
                    continue;
                }
            } catch (IllegalAccessException e) {
                continue;
            }
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        try {
            sb.append("ROOT Object: " + rootNode.getFieldObject() + "\r\n")
                    .append("Value Type: " + rootNode.getType() + "\r\n")
                    .append("Root Node: " + rootNode + "\r\n")
                    .append("\n\r");
        } catch (NullPointerException e) {
            sb.append("null \n\r");
        }
        if (!nodesList.isEmpty()) {
            for (Node node : nodesList) {
                try {
                    sb.append("Field: " + node.field.getName() + "\r\n")
                            .append("Value Type: " + node.getType()  + "\r\n")
                            .append("Is Array: " + node.isArray()  + "\r\n")
                            .append("Is Transient: " + node.isTransient  + "\r\n")
                            .append("Field Object: " + node.getFieldObject() + "\r\n")
                            .append("Node Object: " + node.nodeObject + "\r\n")
                            .append("Node: " + node + "\r\n")
                            .append("Parent Object: " + node.getParentNode().getNodeObject() + "\r\n")
                            .append("Parent Node: " + node.getParentNode() + "\r\n")
                            .append("\n\r");
                } catch (NullPointerException e) {
                    sb.append("\n\r");
                    continue;
                }
            }
        }
        return sb.toString();
    }

    public RootNode getRootNode() {
        return rootNode;
    }

    public List<Node> getRootChildren() {
        List<Node> nodes = new LinkedList<>();
        for (Node n : nodesList) {
            if (n.getNodeObject() == getRootNode().getFieldObject()) {
                nodes.add(n);
            }
        }
        return nodes;
    }


    public List<Node> getNodes() {
        return nodesList;
    }

    class RootNode {
        private Object fieldObject;
        private ValueType valueType;
        private boolean isArray;

        private RootNode() {}

        RootNode(Object baseObject) {
            this.fieldObject = baseObject;                                      //The object itself for the root object
            this.valueType = setType(baseObject.getClass());
            this.isArray = setArray(baseObject.getClass());
        }

        boolean isFieldPrimitive() {
            return (this.valueType != ValueType.OBJECT);
        }

        protected ValueType setType(Class<?> c) {
            if (c.isEnum() || c.isAnnotation())
                throw new UnsupportedOperationException();
            if (c.isAssignableFrom(String.class) || primitives.get(c) == String.class)
                return ValueType.STRING;
            if (c.isAssignableFrom(Number.class) || primitives.get(c) == Number.class)
                return ValueType.NUMBER;
            if (c.isAssignableFrom(Boolean.class)|| primitives.get(c) == Boolean.class)
                return ValueType.BOOLEAN;

            return ValueType.OBJECT;
        }

        Object getFieldObject() {                                                   //The object itself for the root object
            return fieldObject;
        }

        ValueType getType() {
            return valueType;
        }

        protected boolean setArray(Class<?> c) {
            return Collection.class.isAssignableFrom(c) || c.isArray();
        }

        boolean isArray() {
            return isArray;
        }

        boolean isObject() {
            return (getType() == ValueType.OBJECT);
        }
    }

    class Node extends RootNode {
        private Field field;
//        private Object fieldObject;
//        private ValueType valueType = ValueType.NULL;
        private Object nodeObject;
        private boolean isTransient;
        private Node parentNode;

        Node(Field field, Object nodeObject, Object fieldObject, Node parentNode) {
            this.field = field;
            this.nodeObject = nodeObject;
            super.fieldObject = fieldObject;
            super.valueType = setType(field.getType());
            super.isArray = setArray(field.getType());
            this.isTransient = setTransient(field.getModifiers());
            this.parentNode = parentNode;
        }

        boolean isFieldObjectNull() {
            return (super.fieldObject == null);
        }

        Field getField() {
            return field;
        }

        Object getNodeObject() {
            return nodeObject;
        }

        protected boolean setTransient(int modifiers) {
            return Modifier.isTransient(modifiers);
        }

        boolean isTransient() {
            return isTransient;
        }

        Node getParentNode() {
            return parentNode;
        }

        /**
         *
         * @return Child nodes or the note itself unless it has child nodes
         */

        public List<Node> getChildrenNodes() {
            List<Node> nodes = new LinkedList<>();
            for (Node n : nodesList) {

                if (n.getParentNode() == this) {
                    nodes.add(n);
                }
            }
            return nodes.isEmpty()? List.of(this): nodes;
        }

        public List<Node> getArrayNodes() {
            if (isArray()) {
                List<Node> nodes = new LinkedList<>();
                for (Node n : nodesList) {
                    if (n.getNodeObject() == this.getNodeObject() && n.getField() == this.getField() && n.getParentNode() == this.getParentNode()) {
                        nodes.add(n);
                    }
                }
                return nodes;
            } else return null;
        }
    }

     enum ValueType {
 //       ARRAY,
        OBJECT,
        STRING,
        NUMBER,
        BOOLEAN,
//        NULL;

    }
}
