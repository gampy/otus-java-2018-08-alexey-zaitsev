package ru.otus.l08;

import java.util.EnumMap;

public class AmountSelection_EqualNotesNum implements AmountSelection {
    @Override
    public EnumMap<Banknote, Integer> select(Amount amount, ATMimpl.NoteCellsBlock subblock, boolean checkNoteAvailability) {
        EnumMap<Banknote, Integer> notesSelection = new EnumMap(Banknote.class);
        Banknote key;
        Integer value;

        Integer base = 0;
        for (NoteCell cell: subblock) {
            base += cell.getNominal();
        }

        for (NoteCell cell: subblock) {
            key = cell.getBanknote();
            value = amount.getSum()/base;
            notesSelection.put(key, value);
        }
        return notesSelection;
    }

}
