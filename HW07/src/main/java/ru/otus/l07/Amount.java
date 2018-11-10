package ru.otus.l07;

import java.util.EnumMap;

public class Amount {

    private Currency currency;
    private Integer sum;
    private Integer declinedSum;                                         //a subtotal that hasn't been accepted or delivered by an ATM
    private EnumMap<Banknote, Integer> notesSelection;

    public Amount(Currency currency, Integer sum) {
        this.currency = currency;
        this.sum = sum;
        this.declinedSum = 0;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public Integer getSum() {
        return sum;
    }

    public void setSum(Integer sum) {
        this.sum = (sum > 0) ? sum : 0;
    }

    public Integer getNotesSum() {
      //  return this.getNotesSelection().values().stream().mapToInt(Integer::intValue).sum();
       return this.getNotesSelection()
                .entrySet()
                .stream()
                .map(m -> m.getKey().getNominal() * m.getValue())
                .mapToInt(Integer::intValue)
                .sum();
    }

    public Integer getResidue() {
        return this.getSum() - this.getNotesSum();
    }

    public Integer getDeclinedSum() {
        return this.declinedSum;
    }

    public void setDeclinedSum(Integer residue) {
        this.declinedSum = (residue > 0) ? residue : 0;
    }

    public void addDeclinedSum(Integer residue) {
        this.declinedSum = (residue > 0) ? this.declinedSum + residue : this.declinedSum;
    }

    public EnumMap<Banknote, Integer> getNotesSelection() {
        return notesSelection;
    }

    public void setNotesSelection(EnumMap<Banknote, Integer> notesSelection) {
        this.notesSelection = notesSelection;
    }
}
