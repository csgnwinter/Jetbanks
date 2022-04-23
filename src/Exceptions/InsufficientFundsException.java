package Exceptions;

import Default.Account;
import Mechanisms.Screen;

// Exception for if the balance is not enough to perform transfer or withdrawal
public class InsufficientFundsException extends Exception {
    private double amount;
    private Account acc;

    // InsufficientFundsException Constructor
    public InsufficientFundsException(double amount, Account acc) {
        this.amount = amount;
        this.acc = acc;
    }

    // Getter for amount
    public double getAmount() {
        return amount;
    }

    // Printing error message
    public void printError() {
        System.out.println(
                Screen.COLOR_RED + "Sorry, but your account is short by: $" + Math.abs(getAmount() - acc.getBalance())
                        + Screen.COLOR_RESET);
    }
}