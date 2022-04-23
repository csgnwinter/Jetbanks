package Exceptions;

import Mechanisms.Screen;

// Exception for if the pin is the same as old pin when changing pin
public class PinSameException extends Exception {

    // Printing error message
    public void printError() {
        System.out.println(
                Screen.COLOR_RED + "The new pin entered is the same as old pin" + Screen.COLOR_RESET);
    }
}
