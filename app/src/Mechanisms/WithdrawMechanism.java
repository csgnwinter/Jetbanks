package Mechanisms;

import Default.Account;

public interface WithdrawMechanism {
    public boolean withdrawAmountValid(double amount, Account acc);

    public boolean withdrawAmount(Account accc);
}
