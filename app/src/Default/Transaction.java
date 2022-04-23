package Default;

public class Transaction {
    private long account_no;
    private String transaction_detail;
    private String date;
    private double withdraw_amt;
    private double deposit_amt;
    private double balance_amt;

    /*-----------------------------------------------Constructor for Transaction-----------------------------------------------*/

    // Default class contructor
    public Transaction() {
    }

    public Transaction(long account_no, String date, String transaction_detail, double withdraw_amt, double deposit_amt,
            double balance_amt) {

        this.account_no = account_no;
        this.transaction_detail = transaction_detail;
        this.date = date;
        this.withdraw_amt = withdraw_amt;
        this.deposit_amt = deposit_amt;
        this.balance_amt = balance_amt;
    }

    /*-----------------------------------------------Getter/Setter-----------------------------------------------*/

    // Getter and Setter for account_no
    public long getAccountNo() {
        return account_no;
    }

    public void setAccountNo(long account_no) {
        this.account_no = account_no;
    }

    // Getter and Setter for transaction_detail
    public String getTransactionDetail() {
        return transaction_detail;
    }

    public void setTransactionDetail(String transaction_detail) {
        this.transaction_detail = transaction_detail;
    }

    // Getter and Setter for date
    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    // Getter and Setter for withdraw_amt
    public double getWithdraw_amt() {
        return withdraw_amt;
    }

    public void setWithdraw_amt(double withdraw_amt) {
        this.withdraw_amt = withdraw_amt;
    }

    // Getter and Setter for deposit_amt
    public double getDeposit_amt() {
        return deposit_amt;
    }

    public void setDeposit_amt(double deposit_amt) {
        this.deposit_amt = deposit_amt;
    }

    // Getter and Setter for balance_amt
    public double getBalance_amt() {
        return balance_amt;
    }

    public void setBalance_amt(double balance_amt) {
        this.balance_amt = balance_amt;
    }

}
