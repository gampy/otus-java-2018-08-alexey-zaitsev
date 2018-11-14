package ru.otus.l08;

import java.util.HashMap;
import java.util.Map;

public class InsertedCreditCard {
    static final String accountNo = "1234567890";
    static final int minAmount = 0;
    static final private Map<Currency, Integer> balance = new HashMap<>(Map.of(
                                                                        Currency.RUB, 000,
                                                                        Currency.USD, 000,
                                                                        Currency.EUR, 000
                                                                        ));
    private InsertedCreditCard () { }

    public static boolean toDebit(Currency currency, Integer amount) {
        Integer balanceAmount = balance.getOrDefault(currency, 0);
        balance.put(currency, balanceAmount + amount);
        return true;
    }

    public static boolean toCredit(Currency currency, Integer amount) {
        if (!balance.containsKey(currency)) return false;
        Integer balanceAmount = balance.get(currency);

        if (balanceAmount < amount) return false;

        balance.put(currency, balanceAmount - amount);
        return true;
    }

    public static Integer getBalance(Currency currency) {

        return balance.get(currency);
    }


}
