package ru.otus.l08;

public interface ATM {

    boolean acceptNotes(Amount amount);

    boolean deliverNotes(Amount amount);

    Integer getBalance(Currency currency);



}
