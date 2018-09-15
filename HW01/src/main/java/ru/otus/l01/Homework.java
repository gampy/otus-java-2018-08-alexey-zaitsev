package ru.otus.l01;

import java.math.BigInteger;
import java.util.*;
import com.google.common.math.BigIntegerMath;

public class Homework {

    private static BigInteger fact(int n) {
        return BigIntegerMath.factorial(n);
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int n = 0;
        do {
            try {
                System.out.print("Введите целое число > 0: ");
                n = in.nextInt();
                BigInteger f = fact(n);
                System.out.println(n + "! = " + f);
                System.exit(1);
            } catch (java.util.InputMismatchException e) {
                System.out.println("\"" + in.next() + "\" - не верный формат числа");
            } catch (IllegalArgumentException e) {
                System.out.println("\"" + n + "\" - не верный диапазон числа");
            }
        } while (true);
    }

}


/*
import org.apache.commons.math3.util.CombinatoricsUtils;
import org.apache.commons.math3.exception.*;


public class Homework {

    private static long fact(int n) {
        return CombinatoricsUtils.factorial(n);
    }


    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int n = 0;
        do {
            try {
                    System.out.print("Введите целое число 0 < x < 20: ");
                    n = in.nextInt();
                    long f = fact(n);
                    if (f == com.google.common.math.BigIntegerMath.factorial(n).longValue()) {
                        System.out.println(n + "! = " + f);
                        System.exit(1);
                    } else System.exit(0);
                } catch (java.util.InputMismatchException e) {
                    System.out.println("\"" + in.next() +"\" - не верный формат числа");
                } catch (NotPositiveException | MathArithmeticException e) {
                System.out.println("\"" + n +"\" - не верный диапазон числа");
                }
        } while (true);
    }
*/

