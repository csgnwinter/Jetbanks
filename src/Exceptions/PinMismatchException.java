package Exceptions;

import Mechanisms.Screen;

// Exception for if the pins does not match when changing pin
public class PinMismatchException extends Exception {

    // Printing error message
    public void printError() {
        System.out.println(Screen.COLOR_RED + "The pins entered do not match" + Screen.COLOR_RESET);
    }

}
