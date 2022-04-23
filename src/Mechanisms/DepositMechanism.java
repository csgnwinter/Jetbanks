package Mechanisms;

import Default.Account;

public interface DepositMechanism {
    public boolean depositAmountValid(double amount);

    public boolean depositAmount(Account acc);
}
