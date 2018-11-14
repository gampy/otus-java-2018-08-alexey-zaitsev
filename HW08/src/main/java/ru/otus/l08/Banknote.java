package ru.otus.l08;

public enum Banknote {

    R100(Currency.RUB, 100),
    R200(Currency.RUB, 200),
    R500(Currency.RUB, 500),
    R1000(Currency.RUB, 1000),
    R2000(Currency.RUB, 2000),
    R5000(Currency.RUB, 5000),

    D1(Currency.USD, 1),
    D2(Currency.USD, 2),
    D5(Currency.USD, 5),
    D10(Currency.USD, 10),
    D20(Currency.USD, 20),
    D50(Currency.USD, 50),
    D100(Currency.USD, 100),

    E5(Currency.EUR, 5),
    E10(Currency.EUR, 10),
    E20(Currency.EUR, 20),
    E50(Currency.EUR, 50),
    E100(Currency.EUR, 100),
    E200(Currency.EUR, 200),
    E500(Currency.EUR, 500),
    ;

    private Currency currency;
    private Integer nominal;

    Banknote(Currency currency, Integer nominal) {
        this.currency = currency;
        this.nominal = nominal;
    }

    Banknote get(Currency currency, Integer nominal) {
        for (Banknote n : Banknote.values() ) {
            if (
                    n.currency == currency &&
                    n.nominal == nominal
            ) return n;
        }
        return null;
    }

    public Currency getCurrency() {
        return currency;
    }

    public Integer getNominal() {
        return nominal;
    }
}
