package ru.otus.l10;

public class UserDataSet extends DataSet {
    private final String name;
    private final int age;

    public UserDataSet(long id, String name, int age) {
        super(id);
        this.name = name;
        this.age = age;
    }

    public UserDataSet(String name, int age) {
        this.name = name;
        this.age = age;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getAge() {
        return age;
    }

    @Override
    public String toString() {
        return "\"user\": {" +
                "\n\"id\": " + this.getId() + "," +
                "\n\"name\": \"" + name + "\"" + "," +
                "\n\"age\": " + age  + "," +
                "\n}";
    }
}
