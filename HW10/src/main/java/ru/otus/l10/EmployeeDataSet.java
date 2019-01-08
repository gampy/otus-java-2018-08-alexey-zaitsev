package ru.otus.l10;

public class EmployeeDataSet extends DataSet {
    private final String name;
    private final int age;
    private final char gender;

    public EmployeeDataSet(long id, String name, int age, char gender) {
        super(id);
        this.name = name;
        this.age = age;
        this.gender = gender;
    }

    public EmployeeDataSet(String name, int age, char gender) {
        this.name = name;
        this.age = age;
        this.gender = gender;
    }
    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getAge() {
        return age;
    }

    public char getGender() {
        return gender;
    }

    @Override
    public String toString() {
        return "\"employee\": {" +
                "\n\"id\": " + this.getId() + "," +
                "\n\"name\": \"" + this.getName() + "\"" + "," +
                "\n\"age\": " + this.getAge()  + "," +
                "\n\"gender\": \"" + this.getGender()  + "\"" +
                "\n}";
    }

}
