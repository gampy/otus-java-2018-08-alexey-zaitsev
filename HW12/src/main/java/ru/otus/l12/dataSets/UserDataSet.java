package ru.otus.l12.dataSets;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "user")
public class UserDataSet extends DataSet implements Serializable {
    @Column(unique = true)
    private String login;
    private String password;

    //Important for Hibernate
    public UserDataSet() {
    }

    public UserDataSet(long id, String login, String password) {
        this.setId(id);
        this.login = login;
        this.password = password;
    }

    public UserDataSet(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public String getLogin() {
        return this.login;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
