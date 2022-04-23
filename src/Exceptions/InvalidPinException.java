package Exceptions;

import Mechanisms.Screen;

// Exception for if the length of pin is not 6
public class InvalidPinException extends Exception {
    private int length;

    // InvalidPinException Constructor
    public InvalidPinException(int length) {
        this.length = length;
    }

    // Printing error message
    public void printError() {
        System.out
                .println(Screen.COLOR_RED + "PIN needs to be 6 digits. Current length: " + length + Screen.COLOR_RESET);
    }

}