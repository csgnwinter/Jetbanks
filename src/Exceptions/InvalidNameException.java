package Exceptions;

import Mechanisms.Screen;

// Exception for if the name entered by user when creating account is not valid
public class InvalidNameException extends Exception {

    // Printing error message
    public void printError() {
        System.out.println(Screen.COLOR_RED + "Your name can only contain letters and space." + Screen.COLOR_RESET);
    }
}
