package ru.otus.l08.GUI;

import ru.otus.l08.Department;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class ATMDepartment extends JFrame {

    private ArrayList<ATMScreen> fList = new ArrayList<>();

    private JPanel contolPanel = new JPanel();
    private JButton buttonReset = new JButton("Сбросить");


    public ATMDepartment() throws HeadlessException {
        super("ATM Deparment");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        initComponents();
        pack();
    }

    private void initComponents() {
        this.setLayout(new GridLayout(3, 2, 5, 5));
        for (int i = 0; i < Department.getATMs().size(); i++) {
            ATMScreen screen = new ATMScreen(Department.getATM(i));
            fList.add(screen);
            this.add(screen);
        }

        this.add(contolPanel);
        contolPanel.setLayout(new BorderLayout());
        contolPanel.add(buttonReset, BorderLayout.NORTH);


        buttonReset.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Department.reset();
                reset();
            }
        });
    }

    private void reset() {
        for (ATMScreen s : fList) {
            s.initDefaultValues();
        }
    }

}
