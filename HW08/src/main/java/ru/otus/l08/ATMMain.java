package ru.otus.l08;

import ru.otus.l08.GUI.ATMScreen;

import java.util.Random;
import java.util.Set;

class ATMMain {

    public static ATMimpl newInstance(Integer number) {

        ATMimpl instance = new ATMimpl(Set.of(
                new NoteCell(Banknote.R500), new NoteCell(Banknote.R1000), new NoteCell(Banknote.R2000), new NoteCell(Banknote.R5000),
                new NoteCell(Banknote.D10), new NoteCell(Banknote.D20), new NoteCell(Banknote.D50), new NoteCell(Banknote.D100),
                new NoteCell(Banknote.E50), new NoteCell(Banknote.E100), new NoteCell(Banknote.E200), new NoteCell(Banknote.E500)
            ),
                number
        );

        instance.getNoteCellsBlock().acceptProcessing(new Amount(Currency.RUB, new Random().nextInt(100_000)), new AmountSelector(new AmountSelection_EqualNotesNum()), false);
        instance.getNoteCellsBlock().acceptProcessing(new Amount(Currency.USD, new Random().nextInt(10_000)), new AmountSelector(new AmountSelection_EqualNotesNum()), false);
        instance.getNoteCellsBlock().acceptProcessing(new Amount(Currency.EUR, new Random().nextInt(10_000)), new AmountSelector(new AmountSelection_EqualNotesNum()), false);
       // new ATMScreen(instance).setVisible(true);

        return instance;
    }

}
