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
           return  Json.createValue((String) path.getRootNode().getFieldObject()).toString();
        }

        if (path.getRootNode().getType() == FPath.ValueType.NUMBER) {
            return  Json.createValue((Integer) path.getRootNode().getFieldObject()).toString();                     //Only Integers are supported!
        }

        JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
        buildJson(path.getRootChildren(), objectBuilder);

        return writeToString(objectBuilder.build());
    }

    private String writeToString(JsonObject jsonStr) {
        Writer writer = new StringWriter();
        try (JsonWriter jsonWriter = Json.createWriter(writer)) {
            jsonWriter.writeObject(jsonStr);
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

            if (node.isArray()) {
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
                    return null;
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

            if (node.isArray()) {
                arrayBuilder.add(buildJson(node.getChildrenNodes(), Json.createObjectBuilder()));
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
