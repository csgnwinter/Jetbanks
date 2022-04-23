package Exceptions;

import Mechanisms.Screen;

// Exception for if the choice user entered is invalid
public class InvalidChoiceException extends Exception {
    private double choice;

    // InvalidChoiceException Constructor
    public InvalidChoiceException(double choice) {
        this.choice = choice;
    }

    // Printing error when choice is invalid
    public void printError() {
        System.out.println(Screen.COLOR_RED + "The choice '" + (int) choice + "' is invalid." + Screen.COLOR_RESET);
    }

    // Printing error when transferring to same account
    public void sameAccount() {
        System.out.println(Screen.COLOR_RED + "You cannot transfer to your own account" + Screen.COLOR_RESET);
    }

}