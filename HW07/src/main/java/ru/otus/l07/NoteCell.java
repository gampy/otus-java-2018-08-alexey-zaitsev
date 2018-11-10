package ru.otus.l07;

import java.util.function.Function;
import java.util.function.Supplier;

public class NoteCell implements Comparable<NoteCell> {
    final int MIN_NUMBER = 0;
    final int MAX_NUMBER = 100;

    private Banknote banknote;
    private int number;

    NoteCell (Banknote banknote) {
        this.banknote = banknote;
    }


    int add() {
       return add(1);
    }

    /**
     *
     * @param number requests the number of banknotes to add to a NoteCell
     * @return a sum  which hasn't been accepted
     */
    int add(int number) {
       return action(number, () -> this.number + number, this::isFull, MAX_NUMBER) * getNominal();
    }

    int withdraw() {
        return withdraw(1);
    }

    /**
     *
     * @param number requests the number of banknotes to withdraw from a NoteCell
     * @return a sum which hasn't been delivered
     */
    int withdraw(int number) {
        return action(number, () -> this.number - number, this::isEmpty, MIN_NUMBER) * getNominal();
    }

    /**
     *
     * @param number requests the number of banknotes to add/withdraw to/from a NoteCell
     * @param calc target banknotes number to add/ withdraw
     * @param check Emptiness/ Fullness check function
     * @param boundary MIN_NUMBER/ MAX_NUMBER
     * @return a number of banknotes which haven't been accepted/ delivered
     */
    private int action(int number, Supplier<Integer> calc, Function<Integer, Boolean> check, int boundary) {
        if (number < 0 ) throw new IllegalArgumentException("The number must be > " + MIN_NUMBER);
        int preNumber = calc.get();
        if (!check.apply(preNumber)) {
            this.number = preNumber;
            return 0;
        } else {
            this.number = boundary;
            return Math.abs(preNumber - boundary);
        }
    }

    public Banknote getBanknote() {
        return banknote;
    }

    public Currency getCurrency() {
        return banknote.getCurrency();
    }

    public Integer getNominal() {
        return banknote.getNominal();
    }

    public int getNumber() {
        return this.number;
    }

    public int getAmount() {
        return this.number * this.banknote.getNominal();
    }

    private boolean isFull (int value) {
        return (value > MAX_NUMBER) ? true : false;
    }

    private boolean isEmpty (int value) {
        return (value < MIN_NUMBER) ? true : false;
    }

    @Override
    public int compareTo(NoteCell o) {
        return Integer.compare(this.getBanknote().ordinal(), o.getBanknote().ordinal());
    }
}
