package ru.otus.l07.GUI;

import ru.otus.l07.*;
import ru.otus.l07.Currency;

import javax.swing.*;
import javax.swing.text.NumberFormatter;
import java.text.NumberFormat;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

public class ATMScreen extends JFrame{
    static private ATMimpl instance;
    static private Amount amount;

    private JPanel currencyPanel = new JPanel();
    private JPanel buttonsPanel = new JPanel();
    private JPanel amountsPanel = new JPanel();
    private JPanel statusPanel = new JPanel();

    private ButtonGroup radioButtons;
    private JRadioButton radioButtonRUB = new JRadioButton("RUB");
    private JRadioButton radioButtonUSD = new JRadioButton("USD");
    private JRadioButton radioButtonEUR = new JRadioButton("EUR");

    private JButton button1 = new JButton("1");
    private JButton button2 = new JButton("2");
    private JButton button3 = new JButton("3");
    private JButton button4 = new JButton("4");
    private LinkedHashSet<JButton> buttons = new LinkedHashSet<>();

    private JFormattedTextField numberFieldAmount = new JFormattedTextField(setNumberFormatter());
    private JButton buttonAccept = new JButton("Принять");
    private JButton buttonDeliver = new JButton("Выдать");

    private JLabel labelCardBalance = new JLabel("Остаток по валюте на карте:", SwingConstants.RIGHT);
    private JFormattedTextField numberFieldCardBalance = new JFormattedTextField(setNumberFormatter());;
    private JLabel labelATMBalance = new JLabel("в банкомате: ", SwingConstants.RIGHT);
    private JFormattedTextField numberFieldATMBalance = new JFormattedTextField(setNumberFormatter());

    private JTextField textFieldStatus = new JTextField("");

    public ATMScreen(ATMimpl instance) {
        super("ATM Interface");
        this.instance = instance;
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        initComponents();
        pack();
        initListeners();
        initDefaultValues();
        setButtonsProperties(Currency.RUB);
    }

    private void initComponents() {
        currencyPanel.setLayout(new FlowLayout());
        buttonsPanel.setLayout(new GridLayout(1,7,5,5));
        amountsPanel.setLayout(new GridLayout(1,4,5,5));
        statusPanel.setLayout(new BorderLayout());

        this.setLayout(new GridLayout(4,1,5,10));
        this.add(currencyPanel);
        this.add(buttonsPanel);
        this.add(amountsPanel);
        this.add(statusPanel);

        radioButtons = new ButtonGroup();
        radioButtons.add(radioButtonRUB);
        radioButtons.add(radioButtonUSD);
        radioButtons.add(radioButtonEUR);

        currencyPanel.add(radioButtonRUB);
        currencyPanel.add(radioButtonUSD);
        currencyPanel.add(radioButtonEUR);

        buttonsPanel.add(button1);
        buttonsPanel.add(button2);
        buttonsPanel.add(button3);
        buttonsPanel.add(button4);

        buttonsPanel.add(numberFieldAmount);
        buttonsPanel.add(buttonAccept);
        buttonsPanel.add(buttonDeliver);

        //buttons.addAll(Set.of(button1, button2, button3, button4)); ToDO: find a way to sort out a random sorting
        buttons.add(button1);
        buttons.add(button2);
        buttons.add(button3);
        buttons.add(button4);

        amountsPanel.add(labelCardBalance);
        amountsPanel.add(numberFieldCardBalance);
        amountsPanel.add(labelATMBalance);
        amountsPanel.add(numberFieldATMBalance);

        statusPanel.add(textFieldStatus);

        numberFieldCardBalance.setEditable(false);
        numberFieldATMBalance.setEditable(false);
        textFieldStatus.setEnabled(false);
    }

    private void initListeners() {
        radioButtonRUB.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                reloadValues(Currency.RUB);
            }
        });

        radioButtonUSD.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                reloadValues(Currency.USD);
            }
        });

        radioButtonEUR.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                reloadValues(Currency.EUR);
            }
        });

        for (JButton button: buttons) {
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    numberFieldAmount.setValue(Integer.parseInt(button.getText()));
                }
            });
        }

        buttonAccept.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                amount = getAmount(amount.getCurrency(), (Integer) numberFieldAmount.getValue());
                instance.acceptNotes(amount);
                showBalances(amount.getCurrency());
                showStatus();
            }
        });

        buttonDeliver.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                amount = getAmount(amount.getCurrency(), (Integer) numberFieldAmount.getValue());
                instance.deliverNotes(amount);
                showBalances(amount.getCurrency());
                showStatus();
            }
        });
    }

    private void initDefaultValues() {
        radioButtonRUB.setSelected(true);
        amount = getAmount(Currency.RUB, 0);
        numberFieldAmount.setValue(amount.getSum());
        showBalances(amount.getCurrency());
        showStatus();
    }

    private void reloadValues(Currency currency) {
        amount = getAmount(currency, 0);
        setButtonsProperties(amount.getCurrency());
        showBalances(amount.getCurrency());
        numberFieldAmount.setValue(amount.getSum());
        textFieldStatus.setText("");
    }


    private void setButtonsProperties(Currency currency) {

        Iterator<NoteCell> itr = instance.getNoteCellsBlock().getCells(currency).iterator();

        for (JButton button: buttons) {
           if (itr.hasNext()) {
                String value = itr.next().getNominal().toString();
                button.setText(value);
           }
        }
    }

    private AbstractButton getSelectedButton(ButtonGroup buttonGroup) {
        for (Enumeration<AbstractButton> buttons = buttonGroup.getElements(); buttons.hasMoreElements();) {
            AbstractButton button = buttons.nextElement();

            if (button.isSelected()) {
                return button;
            }
        }
        return null;
    }

    private Amount getAmount(Currency currency, Integer sum) {
        return new Amount(currency, sum);
    }

    private void showStatus() {
        textFieldStatus.setText(instance.getMessage());
    }

    private void showBalances(Currency currency) {
        numberFieldCardBalance.setValue(InsertedCreditCard.getBalance(currency));
        numberFieldATMBalance.setValue(instance.getBalance(currency));
    }

    private NumberFormatter setNumberFormatter () {
        NumberFormat format = NumberFormat.getInstance();
        NumberFormatter formatter = new NumberFormatter(format);
        formatter.setValueClass(Integer.class);
        formatter.setMinimum(0);
        formatter.setMaximum(Integer.MAX_VALUE);
        formatter.setAllowsInvalid(false);
        formatter.setCommitsOnValidEdit(true);
        return formatter;
    }

}
