package ru.otus.l08;

import java.util.*;
import java.util.function.BiFunction;

public class ATMimpl implements ATM, EventListener {
    private int number;
    private NoteCellsBlock block;

    private String msg = "";

    ATMimpl(Set<NoteCell> cells) {
        block = new ATMimpl.NoteCellsBlock(cells);
    }

    ATMimpl(Set<NoteCell> cells, Integer number) {
        this.block = new ATMimpl.NoteCellsBlock(cells);
        this.number = number;
    }

    @Override
    public boolean acceptNotes(Amount amount) {
       return  block.acceptProcessing(amount, new AmountSelector(new AmountSelection_MaxNotes()), false);
    }

    @Override
    public boolean deliverNotes(Amount amount) {
       return block.deliverProcessing(amount, new AmountSelector(new AmountSelection_MaxNotes()), false);
    }

    @Override
    public Integer getBalance(Currency currency) {
        return block.getCells(currency)
                .stream()
                .map(m -> m.getAmount())
                .mapToInt(Integer::intValue)
                .sum();
    }

    public NoteCellsBlock getNoteCellsBlock() {
        return this.block;
    }


    public String getMessage() {
        return this.msg;
    }

    public Integer getNumber() {
        return this.number;
    }

    public void update(Object obj, int id) {
        if (obj.equals(Department.class))
            if (id == 200) {
                block.reset();
            }

    }

    public class NoteCellsBlock extends TreeSet<NoteCell>{

        private NoteCellsBlock(Set<NoteCell> cells) {
            this.addAll(cells);
        }

        public NoteCellsBlock() {
        }

        public NoteCell getCell(Banknote note) {
            for (NoteCell cell: this) {
                if (cell.getBanknote().equals(note)) {
                    return cell;
                }
            }
            return null;
        }

        public NoteCellsBlock getCells(Currency currency) {
            NoteCellsBlock curset = new NoteCellsBlock();
            for (NoteCell cell: this) {
                if (cell.getCurrency().equals(currency)) {
                    curset.add(cell);
                }
            }
            return curset;
        }

        /**
         *
         * @param amount - an amount to add
         * @param isCreditCardOperation - takes "false" when the initial banknotes placement occurs
         * @return "true" if an operation has completed successfully, "false" - if has not
         */

         public boolean acceptProcessing(Amount amount, AmountSelector algorithm, boolean isCreditCardOperation) {
            if (this.isEmpty()) throw new NullPointerException("Banknotes cells must be initialized first");

            amount.setNotesSelection(algorithm.select(amount, getCells(amount.getCurrency()), false));
            amount.getNotesSelection().forEach((k, v) -> amount.addDeclinedSum(getCell(k).add(v)));
            System.out.println(block.toString());

            Integer accepted = amount.getSum() - amount.getDeclinedSum() - amount.getResidue();
             if (accepted == 0) {
                 msg = "В приеме денег отказано. Возможные причины: 1. Банкомат полностью заполнен. 2. Банкомат не оборудован ячейками для приема запрошенного номинала";
                 return false;
             }

             if (isCreditCardOperation) {
                 InsertedCreditCard.toDebit(amount.getCurrency(), accepted);
             }
             msg = "Принято купюр на общую сумму " + accepted;

             return true;
         }

        /**
         *
         * @param amount - an amount to deliver
         * @param isCreditCardOperation - takes "false" when the initial banknotes adjustment occurs
         * @return "true" if an operation has completed successfully, "false" - if has not
         */
        public boolean deliverProcessing(Amount amount, AmountSelector algorithm, boolean isCreditCardOperation) {
            if (this.isEmpty()) throw new NullPointerException("Banknotes cells must be initialized first");

            if (isCreditCardOperation && InsertedCreditCard.getBalance(amount.getCurrency()) < amount.getSum()) {
                msg = "Недостаточно средств на бакновской карте.";
                return false;
            }

            amount.setNotesSelection(algorithm.select(amount, getCells(amount.getCurrency()), true));
            amount.getNotesSelection().forEach((k, v) -> amount.addDeclinedSum(getCell(k).withdraw(v)));
            System.out.println(block.toString());

            Integer delivered = amount.getSum() - amount.getDeclinedSum() - amount.getResidue();
            if (delivered == 0) {
                msg = "В выдаче денег отказано. Возможные причины: 1. Банкомат пуст. 2. Банкомат не оборудован ячейками для выдачи запрошенного номинала";
                return false;
            }

            if (isCreditCardOperation) {
                InsertedCreditCard.toCredit(amount.getCurrency(), delivered);
            }
            msg = "Выдано купюр на общую сумму " + delivered;

            return true;
        }

        public Banknote getMinNote(Currency currency, Integer upperLimit, boolean checkNoteAvailability) {
            return getBoundaryNote(currency, Math::min, upperLimit, checkNoteAvailability);
        }

        public Banknote getMaxNote(Currency currency, Integer lowerLimit, boolean checkNoteAvailability) {
            return getBoundaryNote(currency, Math::max, lowerLimit, checkNoteAvailability);
        }

        private Banknote getBoundaryNote(Currency currency, BiFunction<Integer, Integer, Integer> compare, Integer limit, boolean checkNoteAvailability) {
            Banknote boundary = null;
            NoteCellsBlock subblock = getCells(currency);
            for (NoteCell cell : subblock) {
                if (checkNoteAvailability)
                    if (cell.getNumber() == cell.MIN_NUMBER) continue;                                                              // there are no banknotes at the cell

                    if(compare.apply(cell.getNominal(), limit).equals(limit))                                                       // at limit range
                        if (boundary == null || compare.apply(cell.getNominal(), boundary.getNominal()).equals(cell.getNominal()))  // the next nominal is more/less then the current
                        {
                            boundary = cell.getBanknote();
                        }
            }
            return boundary;
        }

        private void reset () {
            for (NoteCell cell: this) {
                cell.reset();
            }
            System.out.println(block.toString());
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("ATM: #" + ATMimpl.this.getNumber()+"\r\n");
            if (this.isEmpty()) sb.append("empty");
            else {
                for (NoteCell cell : this) {
                    sb.append("[" + cell.getBanknote() + "] -> " + cell.getNumber() + "\n\r");
                }
            }
            return sb.toString();
        }
    }

}
