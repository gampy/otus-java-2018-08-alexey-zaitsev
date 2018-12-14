package ru.otus.l09;

import javax.json.*;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Iterator;
import java.util.List;


public class MyGson {

    public String toJson(Object src) {
        FPath path = new FPath();
        path.createPath(src);
//        System.out.println(path.toString());

        if (path.getRootNode() == null) {
            return null;
        }

        if (path.getRootNode().getType() == FPath.ValueType.STRING) {
            return writeToString((Json.createValue((String) path.getRootNode().getFieldObject())));
        }

        if (path.getRootNode().getType() == FPath.ValueType.NUMBER) {
            return writeToString((Json.createValue((Integer) path.getRootNode().getFieldObject())));                     //Only Integers are supported!
        }

        if (path.getRootNode().getType() == FPath.ValueType.BOOLEAN) {
            return writeToString((boolean)path.getRootNode().getFieldObject()? JsonValue.TRUE: JsonValue.FALSE);
        }

        JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
        buildJson(path.getRootChildren(), objectBuilder);

        return writeToString(objectBuilder.build());
    }

    private String writeToString(JsonValue jsonValue) {
        Writer writer = new StringWriter();
        try (JsonWriter jsonWriter = Json.createWriter(writer)) {
            jsonWriter.write(jsonValue);
        }
        return writer.toString();
    }

    private JsonObjectBuilder buildJson(List<FPath.Node> nodes, JsonObjectBuilder objectBuilder) {

        for (Iterator<FPath.Node> iter = nodes.iterator(); iter.hasNext(); ) {
            FPath.Node node = iter.next();

            if (node.isTransient()) {
                continue;
            }

            if (node.isFieldObjectNull()) {
                addObjectNull(node, objectBuilder);
                continue;
            }

            if (node.isIterable()) {
                createArrayElement(node, objectBuilder);
                continue;
            }

            switch (node.getType()) {
                case OBJECT:
                    createObjectElement(node, objectBuilder);
                    break;
                case STRING:
                    addObjectString(node, objectBuilder);
                    break;
                case NUMBER:
                    addObjectNumber(node, objectBuilder);
                    break;
                case BOOLEAN:
                    addObjectBoolean(node, objectBuilder);
                    break;
                default:
                    break;
            }
        }
        return objectBuilder;
    }

    private JsonObjectBuilder createArrayElement(FPath.Node node, JsonObjectBuilder objectBuilder) {
        return objectBuilder.add(
                node.getField().getName(),
                addArrayValues(node.getArrayNodes())
        );
    }

    private JsonArrayBuilder addArrayValues(List<FPath.Node> nodes) {
        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();

        for (Iterator<FPath.Node> iter = nodes.iterator(); iter.hasNext(); ) {
            FPath.Node node = iter.next();

            if (node.isIterable()) {
                switch (node.getType()) {
                    case OBJECT:
                        arrayBuilder.add(buildJson(node.getChildrenNodes(), Json.createObjectBuilder()));
                        break;
                    case STRING:
                        arrayBuilder.add((String) node.getFieldObject());
                        break;
                    case NUMBER:
                        arrayBuilder.add((Integer) node.getFieldObject());
                        break;
                    case BOOLEAN:
                        arrayBuilder.add((Boolean) node.getFieldObject());
                        break;
                    default:
                        break;
                }
            } else {
                throw new IllegalArgumentException("The node " + node + " is not an array element!");
            }
        }
        return arrayBuilder;
    }


    private JsonObjectBuilder createObjectElement(FPath.Node node, JsonObjectBuilder objectBuilder) {
        return objectBuilder.add(
            node.getField().getName(),
            buildJson(node.getChildrenNodes(), Json.createObjectBuilder())
        );
    }

    private JsonObjectBuilder addObjectString(FPath.Node node, JsonObjectBuilder objectBuilder) {
       return objectBuilder.add(
               node.getField().getName(),
               (String) node.getFieldObject()
        );
    }

    private JsonObjectBuilder addObjectNumber(FPath.Node node, JsonObjectBuilder objectBuilder) {
        return objectBuilder.add(
                node.getField().getName(),
                (Integer) node.getFieldObject()                                                         //Only Integers are supported!
        );
    }

    private JsonObjectBuilder addObjectBoolean(FPath.Node node, JsonObjectBuilder objectBuilder) {
        return objectBuilder.add(
                node.getField().getName(),
                (Boolean) node.getFieldObject()
        );
    }

    private JsonObjectBuilder addObjectNull(FPath.Node node, JsonObjectBuilder objectBuilder) {
        return objectBuilder.add(
                node.getField().getName(),
                JsonValue.NULL
        );
    }
}
