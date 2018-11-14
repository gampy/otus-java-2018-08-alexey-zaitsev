package ru.otus.l08;

import java.util.EnumMap;

public class AmountSelection_MaxNotes implements AmountSelection {

    @Override
    public EnumMap<Banknote, Integer> select(Amount amount, ATMimpl.NoteCellsBlock subblock, boolean checkNoteAvailability) {
        EnumMap<Banknote, Integer> notesSelection = new EnumMap(Banknote.class);
        Integer target = amount.getSum();
        Banknote key;
        Integer value;

        do {
            key = subblock.getMaxNote(amount.getCurrency(), target, checkNoteAvailability);
            if (key == null) break;                                                                                            // no sought nominal
            Integer nominal = key.getNominal();
            value = target / nominal;

            notesSelection.put(key, value);
            target = target % nominal;
        } while (key != null);
        return notesSelection;
    }
}
