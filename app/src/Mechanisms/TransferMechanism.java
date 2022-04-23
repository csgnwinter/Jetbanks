package Mechanisms;

import Default.Account;

public interface TransferMechanism {
    public boolean toAccountValid(int account);

    public boolean transferAmountValid(double amount, Account acc);

    public boolean transferAmount(Account fromAccount, long toAccount);
}
