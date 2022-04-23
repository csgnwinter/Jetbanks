package Default;

import java.io.*;
import java.util.ArrayList;

public class Bank {
    /*-----------------------------------------------Declaring Constants and Variables-----------------------------------------------*/
    // Constant values for the Account csv
    final int ACC_NUM_COL = 0;
    final int PIN_HASH_COL = 1;
    final int USER_NAME_COL = 2;
    final int BAL_COL = 3;
    final int TRANS_LIMIT_COL = 4;
    final int WITHDRAW_LIMIT_COL = 5;

    // Constant values for the Transactions csv
    final int ACCOUNT_NO_COL = 0;
    final int DATE_COL = 1;
    final int TRANSACTION_DETAILS_COL = 2;
    final int WITHDRAW_AMT_COL = 5;
    final int DEPOSIT_AMT_COL = 6;
    final int BALANCE_AMT_COL = 7;

    // Array list to store the accounts and transactions for the csv
    private ArrayList<Account> accList = new ArrayList<>();
    private ArrayList<Transaction> transactionList = new ArrayList<>();

    /*-----------------------------------------------Getter/Setter-----------------------------------------------*/

    // Getter and Setter for transactionList
    public ArrayList<Transaction> getTransactionList() {
        return transactionList;
    }

    public void setTransactionList(ArrayList<Transaction> transactionList) {
        this.transactionList = transactionList;
    }

    // Getter and Setter for accList
    public ArrayList<Account> getAccountList() {
        return accList;
    }

    public void setAccountList(ArrayList<Account> accList) {
        this.accList = accList;
    }

    /*-----------------------------------------------CSV Accessing Functions-----------------------------------------------*/
    // Reading in the Users CSV file
    public void readUserCSV() {
        try (BufferedReader br = new BufferedReader(new FileReader("JetBanks/src/Data/Users.csv"))) {
            String line;
            int rowCount = 0;
            // Read the csv file line by line
            while ((line = br.readLine()) != null) {
                // Skips the first fow (Column Header)
                if (rowCount > 0) {
                    // Splits each line and create an account object using the values and adds it to
                    // the array list
                    String[] values = line.split(",");
                    Account acc = new Account();
                    acc.setAccountNo(Long.parseLong(values[ACC_NUM_COL]));
                    acc.setPinHash(values[PIN_HASH_COL]);
                    acc.setUserName(values[USER_NAME_COL]);
                    acc.setBalance(Double.parseDouble(values[BAL_COL]));
                    acc.setTransferLimit(Double.parseDouble(values[TRANS_LIMIT_COL]));
                    acc.setWithdrawalLimit(Double.parseDouble(values[WITHDRAW_LIMIT_COL]));

                    accList.add(acc);
                }
                rowCount++;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    // Updating users CSV
    public void updateCSV(ArrayList<Account> accList) {
        try {
            String filepath = "JetBanks/src/Data/Users.csv";
            File inputFile = new File(filepath);
            // In order to update the CSV, we need to delete the old CSV file and create a
            // new one with the updated values
            inputFile.delete();

            // PrintWriter prints formatted representations of objects to a text-output
            // stream
            PrintWriter pw = new PrintWriter(new FileWriter(new File(filepath)));

            // Add each object in the Account arraylist to the CSV
            pw.println("Account Number,Hash,name,bal,tran limit,withdraw limit,join date");
            for (Account acc : accList) {
                String lineToAdd = acc.getAccountNo() + "," + acc.getPinHash() + "," + acc.getUserName() + ","
                        + acc.getBalance() + "," + acc.getTransferLimit() + "," + acc.getWithdrawalLimit();
                pw.println(lineToAdd);
                pw.flush();
            }
            pw.close();

        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    // Reading in Transaction CSV file
    public void readTransactionCSV() {
        try (BufferedReader br = new BufferedReader(new FileReader("JetBanks/src/Data/transactionLog.csv"))) {
            String line;
            int rowCount = 0;
            // Read the csv file line by line
            while ((line = br.readLine()) != null) {
                // Skips the first fow (Column Header)
                if (rowCount > 0) {
                    // Splits each line and create an account object using the values and adds it to
                    // the array list
                    String[] values = line.split(",");
                    Transaction trans = new Transaction();
                    trans.setAccountNo(Long.parseLong(values[ACCOUNT_NO_COL]));
                    trans.setDate(values[DATE_COL]);
                    trans.setTransactionDetail(values[TRANSACTION_DETAILS_COL]);
                    trans.setWithdraw_amt(Double.parseDouble(values[WITHDRAW_AMT_COL]));
                    trans.setDeposit_amt(Double.parseDouble(values[DEPOSIT_AMT_COL]));
                    trans.setBalance_amt(Double.parseDouble(values[BALANCE_AMT_COL]));

                    transactionList.add(trans);
                }
                rowCount++;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    // Updating Transaction CSV
    public void updateTransactionCSV(ArrayList<Transaction> transactionList) {
        try {
            String filepath = "JetBanks/src/Data/transactionLog.csv";
            File inputFile = new File(filepath);
            // In order to update the CSV, we need to delete the old CSV file and create a
            // new one with the updated values
            inputFile.delete();

            // PrintWriter prints formatted representations of objects to a text-output
            // stream
            PrintWriter pw = new PrintWriter(new FileWriter(new File(filepath)));

            // Add each object in the Transaction arraylist to the CSV
            pw.println("AccountNo,DATE,TRANSACTION_DETAILS,CHQ_NO,VALUE_DATE,WITHDRAWAL_AMT,DEPOSIT_AMT,BALANCE_AMT");
            for (Transaction trans : transactionList) {
                String lineToAdd = trans.getAccountNo() + "," + trans.getDate() + "," + trans.getTransactionDetail()
                        + ",,," +
                        trans.getWithdraw_amt() + "," + trans.getDeposit_amt() + "," + trans.getBalance_amt();
                pw.println(lineToAdd);
                pw.flush();
            }
            pw.close();

        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }
}