package ru.otus.l07;

import ru.otus.l07.GUI.ATMScreen;

import java.util.Set;

public class Main {

    public static void main(String[] args) {

        ATMimpl instance = new ATMimpl(Set.of(
                new NoteCell(Banknote.R500), new NoteCell(Banknote.R1000), new NoteCell(Banknote.R2000), new NoteCell(Banknote.R5000),
                new NoteCell(Banknote.D10), new NoteCell(Banknote.D20), new NoteCell(Banknote.D50), new NoteCell(Banknote.D100),
                new NoteCell(Banknote.E50), new NoteCell(Banknote.E100), new NoteCell(Banknote.E200), new NoteCell(Banknote.E500)
            )
        );

        instance.getNoteCellsBlock().acceptProcessing(new Amount(Currency.RUB ,86000), new AmountSelector(new AmountSelection_EqualNotesNum()), false);

        new ATMScreen(instance).setVisible(true);
    }
}
