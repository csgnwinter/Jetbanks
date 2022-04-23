package Exceptions;

import Mechanisms.Screen;

// Exception for if the account does not exist
public class NoSuchAccException extends Exception {
    long accNo;

    // NoSuchAccException Constructor
    public NoSuchAccException(long accNo) {
        this.accNo = accNo;
    }

    // Printing error message
    public void printError() {
        System.out.println(Screen.COLOR_RED + "This account does not exist." + Screen.COLOR_RESET);
    }
}
