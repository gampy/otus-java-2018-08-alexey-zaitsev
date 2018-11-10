package ru.otus.l07;

import java.util.EnumMap;

//That is a "Strategy" pattern realization! :)
public interface AmountSelection {

    EnumMap<Banknote, Integer> select(Amount target, ATMimpl.NoteCellsBlock subblock, boolean checkNoteAvailability);

    }
