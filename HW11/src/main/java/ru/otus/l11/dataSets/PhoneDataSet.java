package ru.otus.l11.dataSets;

import ru.otus.l11.dataSets.DataSet;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "phone")
public class PhoneDataSet extends DataSet implements Serializable {

    @Column(name = "number")
    private String number;

    @ManyToOne(fetch = FetchType.LAZY)
    private UserDataSet user;

    public PhoneDataSet() {
    }

    public PhoneDataSet(String number) {
        this.number = number;
    }

    void setUser(UserDataSet user) {
        this.user = user;
    }

    public String getNumber() {
        return number;
    }

    @Override
    public String toString() {
        return "PhoneDataSet{" +
                "number='" + number + '\'' +
                '}';
    }
}