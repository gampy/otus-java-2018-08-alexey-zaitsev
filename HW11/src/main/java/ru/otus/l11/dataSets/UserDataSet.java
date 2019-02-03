package ru.otus.l11.dataSets;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Entity
@Table(name = "user")
public class UserDataSet extends DataSet implements Serializable {
    private String name;
    private int age;

    @OneToOne(cascade = CascadeType.ALL)
//    @JoinColumn(name = "user_id", nullable = false) // default is address_id
    private AddressDataSet mainAddress = new AddressDataSet();


//    @OneToMany(cascade = CascadeType.ALL)
//    @JoinColumn(name = "user_id", nullable = false) // default is address_id
//    private List<AddressDataSet> addresses = new ArrayList<>();

    @OneToMany(
            mappedBy = "user",
            cascade = CascadeType.ALL
    )
    private List<PhoneDataSet> phones = new ArrayList<>();

    //Important for Hibernate
    public UserDataSet() {
    }

    public UserDataSet(long id, String name, int age) {
        this.setId(id);
        this.name = name;
        this.age = age;
    }

    public UserDataSet(String name, int age) {
        this.name = name;
        this.age = age;
    }


    public UserDataSet(long id, String name, int age, List<AddressDataSet> addresses, PhoneDataSet... phones) {
        this.setId(id);
        this.name = name;
        this.age = age;
//        this.addresses.addAll(addresses);
        this.mainAddress = addresses.get(0);
        List<PhoneDataSet> userPhones = Arrays.asList(phones);
        this.setPhones(userPhones);
        userPhones.forEach(phone -> phone.setUser(this));
    }

    public UserDataSet(String name, int age, List<AddressDataSet> addresses, PhoneDataSet... phones) {
        this(-1, name, age, addresses, phones);
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public AddressDataSet getMainAddresses() {
        return mainAddress;
    }

//    public List<AddressDataSet> getAddresses() {
//        return addresses;
//    }

    public List<PhoneDataSet> getPhones() {
        return phones;
    }

    public void setPhones(List<PhoneDataSet> phones) {
        this.phones.addAll(phones);
    }

    @Override
    public String toString() {
        return "UserDataSet{" +
                "name='" + name + '\'' +
                ", addresses=" + age +
                ", addresses=" + mainAddress +
                ", phones=" + phones +
                '}';
    }
}
