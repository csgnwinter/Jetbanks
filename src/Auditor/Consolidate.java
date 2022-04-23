package Auditor;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.*;

import Mechanisms.Screen;

public class Consolidate {
    List<String> allErrors;
    List<String> allTransaction;

    public Consolidate(List<String> allTransaction, List<String> allErrors) {
        this.allErrors = allErrors;
        this.allTransaction = allTransaction;

    }

    public void printConsolidatedErrors() {
        Map<String, ArrayList<Integer>> multiValueMap = new HashMap<String, ArrayList<Integer>>();

        // for each error, consolidate the error entries and put them into a
        // multiValueMap
        for (int i = 0; i < this.allErrors.size(); i++) {
            // for each line of error split lines to attain the accountNo, error message
            // separately

            String[] splitError = this.allErrors.get(i).split(" ", 11);
            int temp = Integer.parseInt(splitError[2]);

            // if error message do not exist as a key in the Map, create it as a new key
            if (multiValueMap.isEmpty() || !multiValueMap.containsKey(splitError[10])) {
                multiValueMap.put(splitError[10], new ArrayList<Integer>());
            }

            // add account number as a value in the error key
            multiValueMap.get(splitError[10]).add(temp);
        }

        List<String> consolidatedAllErrors = new ArrayList<>();

        // iterate through the Map and chain the value
        for (String key : multiValueMap.keySet()) {
            String input;
            int minNo = -1;
            int maxNo = -1;
            for (Integer value : multiValueMap.get(key)) {
                if (minNo == -1) {
                    minNo = value;
                    maxNo = value;
                }
                if (maxNo + 1 == value) {
                    maxNo = value;
                }
            }
            String spacingBal = String.valueOf(minNo);
            if (String.valueOf(minNo).length() < 42) {
                for (int x = 0; spacingBal.length() < 42; x++) {
                    spacingBal = spacingBal + " ";
                }
            }

            String minNoBal = String.valueOf(minNo);
            if (String.valueOf(minNo).length() < 16) {
                for (int x = 0; minNoBal.length() < 16; x++) {
                    minNoBal = minNoBal + " ";
                }
            }

            String maxNoBal = String.valueOf(minNo);
            if (String.valueOf(maxNo).length() < 16) {
                for (int x = 0; maxNoBal.length() < 16; x++) {
                    maxNoBal = maxNoBal + " ";
                }
            }

            input = Screen.COLOR_YELLOW;
            // translate combined version of errors and add string into consolidatedAllError
            if (minNo == maxNo) {
                input += "Transaction         " + spacingBal + "             :      " + key;
            } else {
                input += "Transaction         " + minNoBal + " to      " + maxNoBal + "              :      " + key;
            }
            input += Screen.COLOR_RESET;
            consolidatedAllErrors.add(input);
        }
        for (String s : consolidatedAllErrors) {
            System.out.println(s);
        }
    }

    // to translate individual account discrepancies into a csv
    public void updateCSV(ArrayList<String> accountTransactions, String accountNo) {
        try {
            String filepath = "JetBanks/src/Data/IndividualTransactions/" + accountNo + ".csv";

            File inputFile = new File(filepath);
            // In order to update the CSV, we need to delete the old CSV file and create a
            // new one with the updated values
            inputFile.delete();

            // PrintWriter prints formatted representations of objects to a text-output
            // stream
            PrintWriter pw = new PrintWriter(new FileWriter(new File(filepath)));

            pw.println(
                    "Account No,DATE,TRANSACTION DETAILS,CHQ.NO.,VALUE DATE,WITHDRAWAL AMT,DEPOSIT AMT,BALANCE AMT,DISCREPANCIES");
            for (String s : accountTransactions) {
                pw.println(s);
            }
            pw.close();
            System.out.println(Screen.COLOR_BLUE + "CSV file has been printed to " + filepath + Screen.COLOR_RESET);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    // calculate discrepancies
    public void checkBalance(long accountToCheck) {
        String accountToCheckString = String.valueOf(accountToCheck);
        int lengthOfAccountToCheck = accountToCheckString.length();
        ArrayList<String> accountTransactions = new ArrayList<>();
        int rowCounter = 0;
        double prevBalance = 0;
        for (String s : this.allTransaction) {
            s = s.replace("[", "");
            s = s.replace("]", "");
            if (s.substring(0, (lengthOfAccountToCheck)).equals(accountToCheckString)) {
                String[] values = s.split(",(?=(?:[^\\\"]*\\\"[^\\\"]*\\\")*[^\\\"]*$)");
                double withdrawalAmt = 0.00;
                double depositAmt = 0.00;
                double balanceAmt = 0.00;
                try {
                    withdrawalAmt = Double.parseDouble(values[5]);
                } catch (NumberFormatException ex) {
                }
                try {
                    depositAmt = Double.parseDouble(String.valueOf(values[6]));
                } catch (NumberFormatException ex) {
                }
                try {
                    balanceAmt = Double.parseDouble(String.valueOf(values[7]));
                } catch (NumberFormatException ex) {
                }

                // calculation of discrepancies to be flagged out
                double discrepancies = prevBalance - balanceAmt + depositAmt - withdrawalAmt;
                if (rowCounter == 0) {
                    discrepancies = 0;
                }
                rowCounter++;
                String valuesString = values[0] + "," + values[1] + "," + values[2] + "," + values[3] + "," + values[4]
                        + "," + values[5] + "," + values[6] + "," + values[7] + "," + discrepancies;
                prevBalance = balanceAmt;
                accountTransactions.add(valuesString);
            }
        }
        if (accountTransactions.isEmpty()) {
            System.out.println(Screen.COLOR_RED + "Invalid Account Number, Please choose again." + Screen.COLOR_RESET);
            getAccountCheckInputs();
        } else {
            updateCSV(accountTransactions, accountToCheckString);
            getAccountCheckInputs();
        }
    }

    // print total discrepancies for all Accounts
    public void checkAllAccBalance() {
        Map<String, ArrayList<Double>> multiValueMap = new HashMap<String, ArrayList<Double>>();
        for (String s : this.allTransaction) {
            s = s.replace("[", "");
            s = s.replace("]", "");
            String[] values = s.split(",(?=(?:[^\\\"]*\\\"[^\\\"]*\\\")*[^\\\"]*$)");

            double withdrawalAmt = 0.00;
            double depositAmt = 0.00;
            double balanceAmt = 0.00;
            try {
                withdrawalAmt = Double.parseDouble(values[5]);
            } catch (NumberFormatException ex) {
            }
            try {
                depositAmt = Double.parseDouble(values[6]);
            } catch (NumberFormatException ex) {
            }
            try {
                balanceAmt = Double.parseDouble(values[7]);
            } catch (NumberFormatException ex) {
            }

            if (!multiValueMap.containsKey(values[0])) {
                multiValueMap.put(values[0], new ArrayList<Double>());
                Double discrepancies = 0.00;
                multiValueMap.get(values[0]).add(balanceAmt);
                multiValueMap.get(values[0]).add(discrepancies);
            } else {

                // discrepancies calculation
                double prevBalance = Double.parseDouble(String.valueOf((multiValueMap.get(values[0])).get(0)));
                double discrepancies = prevBalance - balanceAmt + depositAmt - withdrawalAmt;
                double prevDiscrepancies = Double.parseDouble(String.valueOf(multiValueMap.get(values[0]).get(1)));
                Double accumDiscrepancies = discrepancies + prevDiscrepancies;
                multiValueMap.get(values[0]).set(0, balanceAmt);
                multiValueMap.get(values[0]).set(1, accumDiscrepancies);
            }

        }
        List<String> consolidatedDiscrepancies = new ArrayList<>();
        for (Map.Entry<String, ArrayList<Double>> entry : multiValueMap.entrySet()) {
            String key = entry.getKey();
            ArrayList<Double> value = entry.getValue();

            // string to balance and ensure uniformity when printing
            String spacingOne = String.valueOf(key);
            if (String.valueOf(key).length() < 20) {
                for (int i = 0; spacingOne.length() < 20; i++) {
                    spacingOne = spacingOne + " ";
                }
            }
            String spacingTwo = String.valueOf(String.format("%.2f", value.get(0)));
            if (String.valueOf(String.format("%.2f", value.get(0))).length() < 20) {
                for (int i = 0; spacingTwo.length() < 20; i++) {
                    spacingTwo = spacingTwo + " ";
                }
            }

            String input = Screen.COLOR_YELLOW + "Account Number       : " + spacingOne + "       Balance        : "
                    + spacingTwo
                    + "       Discrepancies       : " + String.format("%.10f", value.get(1)) + Screen.COLOR_RESET;
            consolidatedDiscrepancies.add(input);

        }
        for (String s : consolidatedDiscrepancies) {
            System.out.println(s);
        }
        getAccountCheckInputs();
    }

    // choices for user to check balances of all ior individual account
    public void getAccountCheckInputs() {
        Scanner scan = new Scanner(System.in);

        System.out.println("\n\n" +
                "Option 0 for all Balance Discrepancies\n" +
                "Option 1 for individual Balance Discrepancies\n" +
                "Option 2 to Exit\n" +
                "Enter option:");
        long checkChoice = (scan.nextLong());
        long accountToCheck = 0;
        if (checkChoice == 0) {
            checkAllAccBalance();
        } else if (checkChoice == 1) {
            System.out.println("Enter account to check:");
            accountToCheck = scan.nextLong();
            checkBalance(accountToCheck);

        } else if (checkChoice == 2) {

        } else {
            System.out.println(Screen.COLOR_RED + "Invalid Choice, Please choose again." + Screen.COLOR_RESET);
            getAccountCheckInputs();
        }

    }

}
