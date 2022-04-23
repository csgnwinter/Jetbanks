package Default;

import Mechanisms.Screen;
import Socket.ChatClient;

public class JetBanks {
    public static void main(String[] args) {
        ATM machine = new ATM();
        // While isRunning is true, continue to loop
        // Else break out of the loop
        boolean isRunning = true;
        AsciiArt art = new AsciiArt();
        art.welcomeMsg();
        while (isRunning) {
            // Bank's main menu user interface
            machine.printMessage("------------------------------------------------------\n");
            machine.printMessage("                Welcome to Jet Banks!                 \n");
            machine.printMessage("              What would you like to do?              \n");
            machine.printMessage("              1. Sign in                              \n");
            machine.printMessage("              2. Create Account                       \n");
            machine.printMessage("              3. Request for live assistance          \n");
            machine.printMessage("              4. Exit                                 \n");
            machine.printMessage("------------------------------------------------------\n");

            // Store's user's choice into the choice variable
            // If user enters 3, exit
            int choice = Integer.parseInt(machine.getInput("Enter option: ", 4, false));

            // If choice is 1, sign the account in with the signIn() function
            if (choice == 1) {
                Account signedInAccount = machine.signIn();

                // If signedInAccount is not null, enter the bank user interface
                while (signedInAccount != null) {
                    String name = signedInAccount.getUserName();
                    // Prints out all the options within the bank's interface
                    machine.printMessage("------------------------------------------------------\n");
                    machine.printMessage("                  Welcome " + name + "                \n");
                    machine.printMessage("              1. Check Balance                        \n");
                    machine.printMessage("              2. Withdraw                             \n");
                    machine.printMessage("              3. Deposit                              \n");
                    machine.printMessage("              4. Transfer                             \n");
                    machine.printMessage("              5. Settings                             \n");
                    machine.printMessage("              6. Log out                              \n");
                    machine.printMessage("------------------------------------------------------\n");

                    // Store's user's choice into the choice variable
                    // If user enters 6, Log out
                    choice = Integer.parseInt(machine.getInput("Enter option: ", 6, false));

                    // If choice is 1, display the balance of the signed in user
                    if (choice == 1) {
                        machine.printMessage(Screen.COLOR_BLUE + "Balance is: $" + signedInAccount.getBalance() + "\n"
                                + Screen.COLOR_RESET);
                    }
                    // If choice is 2, use machine mechanism to withdraw
                    else if (choice == 2) {
                        // Update both the CSV if withdraw is successful
                        if (machine.withdrawAmount(signedInAccount)) {
                            machine.updateCSV(machine.getAccountList());
                            machine.updateTransactionCSV(machine.getTransactionList());
                        }
                    }
                    // If choice is 3, use machine mechanism to deposit
                    else if (choice == 3) {
                        // Update both the CSV if deposit is successful
                        if (machine.depositAmount(signedInAccount)) {
                            machine.updateCSV(machine.getAccountList());
                            machine.updateTransactionCSV(machine.getTransactionList());
                        }
                    }
                    // If choice is 4, use machine mechanism to do local transfer
                    else if (choice == 4) {
                        // Update both the CSV if transfer is successful
                        long account = Long
                                .parseLong(machine.getInput("Enter the account you want to transfer to: ", -1, false));

                        if (machine.transferAmount(signedInAccount, account)) {
                            machine.updateCSV(machine.getAccountList());
                            machine.updateTransactionCSV(machine.getTransactionList());
                        }

                    }
                    // If choice is 5, enter the user's Settings interface
                    else if (choice == 5) {
                        double setting = -1;
                        boolean settings = true;
                        while (settings) {
                            // Prints out all the options within the Settings menu
                            machine.printMessage("------------------------------------------------------\n");
                            machine.printMessage("                  Settings                            \n");
                            machine.printMessage("              1. Change Withdraw Limit                \n");
                            machine.printMessage("              2. Change Transfer Limit                \n");
                            machine.printMessage("              3. Change PIN                           \n");
                            machine.printMessage("              4. Go Back                              \n");
                            machine.printMessage("------------------------------------------------------\n");

                            setting = Double.parseDouble(machine.getInput("Enter option: ", 4, false));

                            // If setting is 1, Change the Withdraw Limit
                            if (setting == 1) {
                                // get the current withdrawal limit
                                machine.printMessage(
                                        Screen.COLOR_BLUE + "Current withdrawal limit is: $"
                                                + signedInAccount.getWithdrawalLimit() + "\n" + Screen.COLOR_RESET);

                                // Update the Users CSV if withdraw limit is successfully changed
                                if (machine.withdrawLimit(signedInAccount)) {
                                    machine.updateCSV(machine.getAccountList());
                                }
                            }
                            // If setting is 2, Change the Transfer Limit
                            else if (setting == 2) {
                                // get current transfer limit
                                machine.printMessage(
                                        Screen.COLOR_BLUE + "Current transfer limit is: $"
                                                + signedInAccount.getTransferLimit() + "\n" + Screen.COLOR_RESET);

                                // Update the Users CSV if the tansfer limit is successfully changed
                                if (machine.transferLimit(signedInAccount)) {
                                    machine.updateCSV(machine.getAccountList());
                                }
                            }
                            // If setting is 3, Change the pin
                            else if (setting == 3) {
                                // Getting user input to enter current pin
                                String currPin = machine.getInput("What is your current pin?: ", -1, true);
                                // Cancels current operation if user typed 'c'
                                if (currPin.equals("c")) {
                                    settings = false;
                                }
                                // If pin entered is invalid
                                if (machine.pinValid(currPin, signedInAccount) == false) {
                                    machine.printMessage(Screen.COLOR_RED
                                            + "Incorrect PIN, unable to change PIN.\n" + Screen.COLOR_RESET);
                                }
                                // Else, change pin and update Users CSV
                                else {
                                    String newPin1 = machine.getInput("Enter your new pin: ", -1, false);
                                    String newPin2 = machine.getInput("Confirm your new pin: ", -1, false);

                                    if (machine.changePin(newPin1, newPin2, signedInAccount)) {
                                        machine.updateCSV(machine.getAccountList());
                                    }
                                }
                            }
                            // If setting is 4, Return to previous screen
                            else if (setting == 4) {
                                settings = false;
                            } else {
                                settings = true;
                            }
                        }
                    }
                    // If choice is 6, Log out
                    else if (choice == 6) {
                        signedInAccount = null;
                    }
                }

            }
            // If choice is 2, create a new account in with the createAccountMech() function
            else if (choice == 2) {
                // create account
                Account newAccount = machine.createAccountMech();
                if (newAccount != null) {
                    machine.printMessage(Screen.COLOR_GREEN + "Account created successfully. Your Account Number is: "
                            + newAccount.getAccountNo() + "\n" + Screen.COLOR_RESET);
                }
            }
            // If choice is 3, hook up to the server for live chat
            else if (choice == 3) {

                ChatClient.runClient();
            }
            // If choice is 4, exit
            else if (choice == 4) {
                isRunning = false;
            }
        }
    }
}
