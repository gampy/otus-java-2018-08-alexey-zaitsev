package ru.otus.l07;

import java.util.EnumMap;

public class AmountSelector implements AmountSelection{

    private AmountSelection instance;

    public AmountSelector(AmountSelection selection) {
        this.instance = selection;
    }

    public void setAmountSelection(AmountSelection selection) {
        this.instance = selection;
    }

    @Override
    public EnumMap<Banknote, Integer> select(Amount target, ATMimpl.NoteCellsBlock subblock, boolean checkNoteAvailability) {
        return instance.select(target, subblock, checkNoteAvailability);
    }
}
