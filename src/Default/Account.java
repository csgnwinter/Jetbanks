package Default;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Account {
    private long accountNo;
    private String userName;
    private String pinHash;
    private double balance;
    private double transferLimit;
    private double withdrawalLimit;

    // Default class contructor
    public Account() {
    }

    /*-----------------------------------------------Class contructor for new accounts-----------------------------------------------*/
    public Account(long accountNo, String userName, String pinNum, double transferLimit,
            double withdrawalLimit) {
        this.accountNo = accountNo;
        this.userName = userName;
        this.balance = 0;
        this.transferLimit = transferLimit;
        this.withdrawalLimit = withdrawalLimit;

        this.pinHash = generatePINHash(pinNum);
    }

    /*-----------------------------------------------Getter and Setter for AccountNo-----------------------------------------------*/
    public long getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(long accountNo) {
        this.accountNo = accountNo;
    }

    /*-----------------------------------------------Getter and Setter for UserName-----------------------------------------------*/
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    /*-----------------------------------------------Getter and Setter for Balance-----------------------------------------------*/
    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    /*-----------------------------------------------Getter and Setter for PinHash-----------------------------------------------*/
    public String getPinHash() {
        return pinHash;
    }

    public void setPinHash(String pinHash) {
        this.pinHash = pinHash;
    }

    /*-----------------------------------------------Getter and Setter for TransferLimit-----------------------------------------------*/
    public double getTransferLimit() {
        return transferLimit;
    }

    public void setTransferLimit(double transferLimit) {
        this.transferLimit = transferLimit;
    }

    /*-----------------------------------------------Getter and Setter for WithdrawalLimit-----------------------------------------------*/
    public double getWithdrawalLimit() {
        return withdrawalLimit;
    }

    public void setWithdrawalLimit(double withdrawalLimit) {
        this.withdrawalLimit = withdrawalLimit;
    }

    /*-----------------------------------------------Other Functions-----------------------------------------------*/

    // Function to deposit
    public void deposit(double value) {
        this.balance += value;
    }

    // Function to withdraw
    public void withdraw(double value) {
        this.balance -= value;
    }

    // Function to generate Pin Hash
    public String generatePINHash(String pinNum) {
        String pinHash = null;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(pinNum.getBytes());
            byte[] bytes = md.digest();
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < bytes.length; i++) {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            pinHash = sb.toString();
        } catch (NoSuchAlgorithmException ex) {
            System.out.println("\nFailed to hash password.\n" + ex);
        }
        return pinHash;
    }
}
