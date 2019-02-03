package ru.otus.l11.ORM.myORM;

import ru.otus.l11.dataSets.DataSet;

import java.sql.Connection;

public class MyDBS<T extends DataSet> {
    private Connection session;
    private ClassMetaData cmd;

    public MyDBS(Connection session, ClassMetaData cmd) {
        this.session = session;
        this.cmd = cmd;
    }

    public void prepareTables(ClassMetaData<T> cmd) {
        StringBuilder createTableQueryBuilder = new StringBuilder("create table if not exists %s (id bigint(20) NOT NULL auto_increment PRIMARY KEY");
        cmd.getFieldNodes().forEach(node -> {
            String columnName = node.getField().getName();
            String columnType = node.getType().getSQLType();
            createTableQueryBuilder.append(", " + columnName + " " + columnType);
        });
        createTableQueryBuilder.append(")");

//        System.out.println(String.format(CREATE_TABLE.toString(), st.getTableName(st.getBaseClass())));

        Executor exec = new Executor(session);
        exec.execUpdate(String.format(createTableQueryBuilder.toString(), cmd.getTableName(cmd.getBaseClass())), statement -> {} );
    }
}
