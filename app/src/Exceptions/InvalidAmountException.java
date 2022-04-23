package Exceptions;

import Mechanisms.Screen;

// Exception for if the amount for deposit/withdraw/transfer is invalid
public class InvalidAmountException extends Exception {
    double amount;

    // InvalidAmountException Constructor
    public InvalidAmountException(double amount) {
        this.amount = amount;
    }

    // Printing error for invalid deposit
    public void printErrorDeposit() {
        System.out.println(Screen.COLOR_RED + "The amount " + this.amount + " is an invalid amount to deposit."
                + Screen.COLOR_RESET);
    }

    

    // Printing error for invalid withdrawal amount
    public void printErrorWithdrawal() {
        System.out.println(
                Screen.COLOR_RED + "The amount " + this.amount + " is more than your current withdrawal limit."
                        + Screen.COLOR_RESET);
    }

    // Printing error for invalid SET withdrawal Limit
    public void printErrorSetWithdrawalLimit() {
        System.out.println(
                Screen.COLOR_RED + "The amount " + this.amount + " is an invalid amount to be set as withdrawal limit."
                        + Screen.COLOR_RESET);
    }

    // Printing error for invalid transfer amount
    public void printErrorTransfer() {
        System.out.println(
                Screen.COLOR_RED + "The amount " + this.amount + " is more than your current transfer limit."
                        + Screen.COLOR_RESET);
    }

    // Printing error for invalid SET transfer Limit
    public void printErrorSetTransferLimit() {
        System.out.println(
                Screen.COLOR_RED + "The amount " + this.amount + " is an invalid amount to be set as transfer limit."
                        + Screen.COLOR_RESET);
    }
}
