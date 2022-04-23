package Exceptions;

import Mechanisms.Screen;

// Exception for if the Account Number is not 12 digits
public class InvalidAccNumException extends Exception {
    private int length;

    // InvalidAccNumException Constructor
    public InvalidAccNumException(int length) {
        this.length = length;
    }

    // Printing error message
    public void printError() {
        System.out.println(Screen.COLOR_RED + "Account number needs to be 12 digits. Current length: " + length + "."
                + Screen.COLOR_RESET);
    }
}
