package ru.otus.l07;

public interface ATM {

    boolean acceptNotes(Amount amount);

    boolean deliverNotes(Amount amount);

    Integer getBalance(Currency currency);



}
