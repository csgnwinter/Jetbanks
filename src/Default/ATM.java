package Default;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.Date;

import Exceptions.*;
import Mechanisms.*;

public class ATM extends Bank
        implements Screen, Keypad, SignInMechanism, AccountMechanism, TransferMechanism, DepositMechanism,
        WithdrawMechanism {

    // Global variables
    private Scanner inp;
    private SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");

    /*-----------------------------------------------Constructor for ATM-----------------------------------------------*/
    public ATM() {
        // Calls the parent(Bank.java)'s function to read the user and transaction data
        // from the CSV files
        super.readUserCSV();
        super.readTransactionCSV();
    }

    /*-----------------------------------------------Functions for user input-----------------------------------------------*/

    // Function to keep looping the getInput function untill a valid input is given
    public String inputLoop(String text, int maxOption, boolean canCancel) {
        String inputStr = "";
        while (inputStr.isEmpty()) {
            inputStr = getInput(text, maxOption, canCancel);
        }
        return inputStr;
    }

    // Function to get the user's input (Acts as a keypad)
    @Override
    public String getInput(String text, int maxOption, boolean canCancel) {
        String inputStr = "";
        try {
            // Prints the prompt message
            printMessage(text);
            // Get the user's input
            inp = new Scanner(System.in);
            inputStr = inp.nextLine();
            // Returns an empty string if the operation can be cancelled and the user inputs
            // a 'c'
            if (canCancel && inputStr.equals("c")) {
                return inputStr;
            }

            // Throw exception if user enters a string that contains 'd', 'f' or 'e', which would be considered a valid double
            if(inputStr.toLowerCase().contains("d") || inputStr.toLowerCase().contains("f") || inputStr.toLowerCase().contains("e")) {
                throw new NumberFormatException();
            }

            // Throw exception if user enters a string that is not double or is more than the max option
            if (Double.parseDouble(inputStr) <= 0 || (maxOption != -1 && Double.parseDouble(inputStr) > maxOption)) {
                throw new InvalidChoiceException(Double.parseDouble(inputStr));
            }

        } catch (NoSuchElementException nse) {
            inputStr = " ";
        } catch (InvalidChoiceException ice) {
            inputStr = "-1";
            ice.printError();
        } catch (NumberFormatException nfe) {
            inputStr = "-1";
            if (maxOption == -1) {
                printMessage(Screen.COLOR_RED + "Invalid input type, Please enter numbers.\n" + Screen.COLOR_RESET);
            } else {
                printMessage(Screen.COLOR_RED + "Please choose from the options 1-" + maxOption + ".\n"
                        + Screen.COLOR_RESET);
            }
        }
        return inputStr;
    }

    // Function to keep looping the getKeyboardInput function untill a valid input
    // is given
    public String keyboardInputLoop(String text) {
        String inputStr = "";
        while (inputStr.isEmpty()) {
            inputStr = getKeyboardInput(text);
        }
        return inputStr;
    }

    // Function to get keyboard input (For user to enter thier name when creating an
    // account)
    @Override
    public String getKeyboardInput(String text) {
        String inputStr = "";
        try {
            // Prints the prompt message
            printMessage(text);
            // Get the user's input
            inp = new Scanner(System.in);
            inputStr = inp.nextLine();
            // Throws exception if the entered name contains anything other than upper and
            // lower case letters and spaces
            if (!inputStr.matches("[a-zA-Z ]*")) {
                throw new InvalidNameException();
            }

        } catch (InvalidNameException ine) {
            ine.printError();
            inputStr = "-1";
        }
        return inputStr;
    }

    /*-----------------------------------------------Function for Screen Output-----------------------------------------------*/

    // Function to print messages
    @Override
    public void printMessage(String message) {
        System.out.print(message);
    }

    /*-----------------------------------------------Functions for Withdrawal Mechanism-----------------------------------------------*/

    // Function to withdraw amount from ATM
    @Override
    public boolean withdrawAmount(Account acc) {
        double amount;
        // Check if there is any error in the input, if there is throw appropriate
        // exceptions
        try {
            amount = Double
                    .parseDouble(getInput("How much would you like to withdraw? (or press 'c' to cancel): ", -1, true));

            if (amount == -1) {
                return false;
            }

            // If withdraw amount is not valid, throw InsufficientFundsException
            if (withdrawAmountValid(amount, acc) == false) {
                throw new InsufficientFundsException(amount, acc);
            }
            // If the withdraw amount if more than the account's withdrawal limit, throw
            // InvalidAmountException
            if (amount > acc.getWithdrawalLimit()) {
                throw new InvalidAmountException(amount);
            }
            // Withdraw the amount the user specified if no error is found
            acc.withdraw(amount);

            // Create a new transaction record and adds it to the transaction list
            String transaction_detail = "Withdrawn $" + String.valueOf(amount);
            String dateString = dateFormatter.format(new Date());
            Transaction trans = new Transaction(acc.getAccountNo(), dateString, transaction_detail, amount, 0,
                    acc.getBalance());
            getTransactionList().add(trans);

            this.printMessage(Screen.COLOR_GREEN + "$ " + amount + " has been withdrawn from your account.\n"
                    + Screen.COLOR_RESET);
            this.printMessage(Screen.COLOR_BLUE + "Updated balance is: $"
                    + acc.getBalance() + "\n" + Screen.COLOR_RESET);

            return true;
        }
        // Catch exceptions
        catch (NumberFormatException nfe) {
            return false;
        } catch (InsufficientFundsException ife) {
            ife.printError();
        } catch (InvalidAmountException iae) {
            iae.printErrorWithdrawal();
        }
        return false;
    }

    // Function to check if withdraw amount is valid
    @Override
    public boolean withdrawAmountValid(double amount, Account acc) {
        // if amount to be withdrawn is less than or equal to 0, OR
        // Amount to be withdrawn is more then current account balance
        if (acc.getBalance() < amount) {
            return false;
        }
        return true;
    }

    /*-----------------------------------------------Functions for Deposit Mechanism-----------------------------------------------*/

    // Function to deposit amount
    @Override
    public boolean depositAmount(Account acc) {
        double amount;
        // Check if there is any error in the input, if there is throw appropriate
        // exceptions
        try { // if input is not of type double or negative
            amount = Double
                    .parseDouble(getInput("How much would you like to deposit? (or press 'c' to cancel): ", -1, true));

            if (amount == -1) {
                return false;
            }
            
            if (!depositAmountValid(amount)) {
                throw new InvalidAmountException(amount); // exception thrown if depositAmountValid is False
            }
            acc.deposit(amount); // depositing amount into the account

            // Create a new transaction record and adds it to the transaction list
            String dateString = dateFormatter.format(new Date());
            String transaction_detail = "Deposited $" + String.valueOf(amount);
            Transaction trans = new Transaction(acc.getAccountNo(), dateString, transaction_detail, 0, amount,
                    acc.getBalance());
            getTransactionList().add(trans);
            this.printMessage(Screen.COLOR_GREEN + "$" + amount + " has been deposited into your account.\n"
                    + Screen.COLOR_RESET);
            this.printMessage(Screen.COLOR_BLUE + "Updated balance is: $"
                    + acc.getBalance() + "\n" + Screen.COLOR_RESET);
            return true;
        }
        // Catch exceptions
        catch (NumberFormatException nfe) {
            return false;
        } 
        catch (InvalidAmountException i) {
            i.printErrorDeposit();
        }
        return false;
    }

    // Function to check if deposit amount is valid
    @Override
    public boolean depositAmountValid(double amount) {
        // if amount to be deposit is less than or equal to 0
        if (amount <= 0) {
            return false;
        }
        return true;
    }

    /*-----------------------------------------------Functions for Transfer Mechanism-----------------------------------------------*/

    // Function to check if the account to be transferred to is valid
    @Override
    public boolean toAccountValid(int account) {
        // For each account in the account list
        // If the account number in the list is equivalent to the given account, return
        // true
        for (Account acc : this.getAccountList()) {
            if (acc.getAccountNo() == account) {
                return true;
            }
        }
        return false;
    }

    // Function to check if transfer amount is valid
    @Override
    public boolean transferAmountValid(double amount, Account acc) {
        // if amount to transfer is less than 0, OR
        // amount to transfer is less than the balance in current account
        if (acc.getBalance() < amount) {
            return false;
        }
        return true;
    }

    // Function to transfer amount from current account to another account
    @Override
    public boolean transferAmount(Account fromAccountObj, long toAccount) {
        try {
            // If account entered is not 12 digits (length of account)
            if (Long.toString(toAccount).length() != 12) {
                throw new InvalidAccNumException(Long.toString(toAccount).length());
            }
            // If account to transfer FROM is same as account to transfer TO
            else if (fromAccountObj.getAccountNo() == toAccount) {
                throw new InvalidChoiceException(toAccount);
            }

            Account toAccountObj = null;
            // iterating through account list to find account to transfer to
            for (Account acc : this.getAccountList()) {
                if (acc.getAccountNo() == toAccount) {
                    toAccountObj = acc; // assigning account found to toAccountObj
                }
            }

            // If account entered doesn't exist
            if (toAccountObj == null) {
                throw new NoSuchAccException(toAccount);
            }

            double amount;
            // Getting user input for amount transfer
            amount = Double.parseDouble(
                    getInput("Enter the amount you want to transfer (or press 'c' to cancel): ", -1, true));

            if (amount == -1) {
                return false;
            }

            // If amount is less than current account balance
            if (transferAmountValid(amount, fromAccountObj) == false) {
                throw new InsufficientFundsException(amount, fromAccountObj);
            }
            // If amount is more than the transfer limit
            if (amount > fromAccountObj.getTransferLimit()) {
                throw new InvalidAmountException(amount);
            }
            // else, withdraw from fromAccountObj(current account), deposit into
            // toAccountObj
            fromAccountObj.withdraw(amount);
            toAccountObj.deposit(amount);
            this.printMessage(Screen.COLOR_GREEN + "$" + amount + " from " + fromAccountObj.getAccountNo()
                    + " has been deposited into "
                    + toAccountObj.getAccountNo() + "\n" + Screen.COLOR_RESET);
            this.printMessage(Screen.COLOR_BLUE + "Your updated balance is: $"
                    + fromAccountObj.getBalance() + "\n" + Screen.COLOR_RESET);

            String dateString = dateFormatter.format(new Date());
            String transaction_detail = "Transferred $" + String.valueOf(amount) + " to "
                    + toAccountObj.getAccountNo();
            Transaction trans = new Transaction(fromAccountObj.getAccountNo(), dateString, transaction_detail,
                    amount, 0,
                    fromAccountObj.getBalance());

            getTransactionList().add(trans);
            return true;

        }
        // Catch Exceptions
        catch (NumberFormatException nfe) {
            return false;
        } catch (InsufficientFundsException ife) {
            ife.printError();
        } catch (InvalidAmountException iae) {
            iae.printErrorTransfer();
        } catch (NoSuchAccException i) {
            i.printError();
        } catch (InvalidAccNumException i) {
            i.printError();
        } catch (InvalidChoiceException i) {
            i.sameAccount();
        }
        return false;
    }

    /*-----------------------------------------------Functions for Withdrawal Limit-----------------------------------------------*/

    // Function to change the withdraw limit
    public boolean withdrawLimit(Account acc) {
        double amount;
        try {
            amount = Double.parseDouble(getInput(
                    "How much would you like to set your new withdrawal limit? (or press 'c' to cancel): ", -1, true));

            if (amount == -1) {
                return false;
            }
            
            // If the amount is less than 100, throw InvalidAmountException
            if (withdrawLimitValid(amount, acc) == false) {
                throw new InvalidAmountException(amount);
            }
            // Set account's new withdrawal limit
            acc.setWithdrawalLimit(amount);
            this.printMessage(Screen.COLOR_GREEN + "Withdrawal limit has been changed to: $" + amount + "\n" + Screen.COLOR_RESET);
            return true;
        }
        // Catch exceptions
        catch (NumberFormatException nfe) {
            return false;
        } catch (InvalidAmountException i) {
            i.printErrorSetWithdrawalLimit();
        }

        return false;
    }

    // Function to check if withdraw limit given is less than 100
    public boolean withdrawLimitValid(double amount, Account acc) {
        // If the amount is less than 100, return false
        // User must input more than or equals to 100
        if (amount < 100) {
            return false;
        }
        return true;
    }

    /*-----------------------------------------------Functions for Transfer Limit-----------------------------------------------*/

    // Function to set transfer limit
    public boolean transferLimit(Account acc) {
        double amount;

        try {
            amount = Double.parseDouble(getInput(
                    "What is your new transfer limit? (or press 'c' to cancel): ", -1, true));

            if (amount == -1) {
                return false;
            }

            // If transfer limit is less than 100
            if (transferLimitValid(amount, acc) == false) {
                throw new InvalidAmountException(amount);
            }
            // Setting new transfer limit
            acc.setTransferLimit(amount);
            this.printMessage(Screen.COLOR_GREEN + "Transfer limit has been changed to: $" + amount + "\n" + Screen.COLOR_RESET);
            return true;
        }
        // Catch Exceptions
        catch (NumberFormatException nfe) {
            return false;
        } catch (InvalidAmountException ife) {
            ife.printErrorSetTransferLimit();
        }

        return false;
    }

    // Function to check if transfer limit given is less than 100
    public boolean transferLimitValid(double amount, Account acc) {
        if (amount < 100) {
            return false;
        }
        return true;
    }
    /*-----------------------------------------------Functions for Sign In Mechanism-----------------------------------------------*/

    // Function to check if pin is valid
    @Override
    public boolean pinValid(String pin, Account acc) {
        if (acc.getPinHash().equals(acc.generatePINHash(pin))) {
            return true;
        }
        return false;
    }

    // Function to change pin
    @Override
    public boolean changePin(String newPin1, String newPin2, Account acc) {
        try {
            // Check that pin length is 6 digits
            if (newPin1.length() != 6) {
                throw new InvalidPinException(newPin1.length());
            }
            if (newPin2.length() != 6) {
                throw new InvalidPinException(newPin2.length());
            }
            // Check that newPin1 (first entry) is equal to newPin2 (second entry)
            if (!newPin1.equals(newPin2)) {
                throw new PinMismatchException();
            }
            // Check if new pin is the same as old pin
            if (acc.generatePINHash(newPin1).equals(acc.getPinHash())) {
                throw new PinSameException();
            }
            // else, set new pin
            acc.setPinHash(acc.generatePINHash(newPin2));
            this.printMessage(Screen.COLOR_GREEN + "PIN has been successfully changed" + Screen.COLOR_RESET + "\n");
            return true;
        }
        // Catch exceptions
        catch (PinMismatchException pm) {
            pm.printError();
        } catch (InvalidPinException ip) {
            ip.printError();
        } catch (PinSameException ps) {
            ps.printError();
        }

        return false;
    }

    public Account signIn() {
        String accNum = getInput("Account Number: ", -1, false);
        if (accNum.equals("-1")) {
            return null;
        }
        String pin = getInput("Pin Number: ", -1, false);
        if (pin.equals("-1")) {
            return null;
        }
        return signInMech(Long.parseLong(accNum), pin);
    }

    // Function for sign in mechanism
    @Override
    public Account signInMech(Long accNum, String pin) {
        try {
            // Throw exception if account number is not of length 12
            if (String.valueOf(accNum).length() != 12) {
                throw new InvalidAccNumException(String.valueOf(accNum).length());
            }
            // Throw exception if pin is not of length 6
            if (pin.length() != 6) {
                throw new InvalidPinException(pin.length());
            }

            // Loop through the accounts list and see if any account number matches
            for (Account acc : super.getAccountList()) {
                if (acc.getAccountNo() == accNum) {
                    // Compare the pin and check if it is correct
                    if (acc.generatePINHash(pin).equals(acc.getPinHash())) {
                        return acc;
                    } else {
                        printMessage(Screen.COLOR_RED + "Your PIN is incorrect.\n" +
                                Screen.COLOR_RESET);
                        return null;
                    }
                }
            }

            // if account number entered does not exist in csv
            printMessage(Screen.COLOR_RED + "Your Account Number is not found.\n" + Screen.COLOR_RESET);
            return null;

        }
        // Catch exceptions
        catch (InvalidAccNumException iane) {
            iane.printError();
        } catch (InvalidPinException ipe) {
            ipe.printError();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // Function for create account mechanism
    @Override
    public Account createAccountMech() {
        Account accToAdd = null;
        try {
            // Get user input and return null if input invalid
            String name = getKeyboardInput("Enter your Name: ");
            if(name.equals("-1")) {
                return null;
            }
            String pin = getInput("Enter your PIN: ", -1, false);
            if(pin.equals("-1")) {
                return null;
            } 
            // Throw exception if pin is not of length 6
            else if (pin.length() != 6) {
                throw new InvalidPinException(pin.length());
            }
            double withdrawLimit = Double.parseDouble(getInput("Enter the withdrawal limit: $", -1, false));
            if(withdrawLimit == -1) {
                return null;
            }
            double transferLimit = Double.parseDouble(getInput("Enter the transfer limit: $", -1, false));
            if(transferLimit == -1) {
                return null;
            }

            // Opens the Users csv
            String filepath = "JetBanks/src/Data/Users.csv";
            File inputFile = new File(filepath);
            // Deletes the Users csv
            inputFile.delete();

            PrintWriter pw = new PrintWriter(new FileWriter(new File(filepath)));
            ArrayList<Account> accountL = this.getAccountList();
            // Generate a new account number from the last account number in the csv
            long newAccNum = accountL.get(accountL.size() - 1).getAccountNo() + 1;

            // Create a new account object and adds it to the list
            accToAdd = new Account(newAccNum, name, pin, transferLimit, withdrawLimit);
            accountL.add(accToAdd);

            // Write the whole account list into the csv
            pw.println("Account Number,Hash,name,bal,tran limit,withdraw limit,join date");
            for (Account acc : accountL) {
                String lineToAdd = acc.getAccountNo() + "," + acc.getPinHash() + "," + acc.getUserName() + ","
                        + acc.getBalance() + "," + acc.getTransferLimit() + "," + acc.getWithdrawalLimit() + ",";
                pw.println(lineToAdd);
                pw.flush();
            }
            pw.close();
        }
        // Catch exceptions
        catch (InvalidPinException ipe) {
            ipe.printError();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return accToAdd;
    }
}
