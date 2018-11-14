package ru.otus.l08;

import java.util.EnumMap;

//That is a "Strategy" pattern realization! :)
public interface AmountSelection {

    EnumMap<Banknote, Integer> select(Amount target, ATMimpl.NoteCellsBlock subblock, boolean checkNoteAvailability);

    }
