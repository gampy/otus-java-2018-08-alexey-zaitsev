package ru.otus.l08;

import ru.otus.l08.GUI.ATMDepartment;

public class Main {

    public static void main(String[] args) {
        Department.addATMs(ATMMain.newInstance(100), ATMMain.newInstance(101), ATMMain.newInstance(102), ATMMain.newInstance(103));

        new ATMDepartment().setVisible(true);
    }
}
