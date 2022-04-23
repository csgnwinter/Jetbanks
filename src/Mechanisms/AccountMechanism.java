package Mechanisms;

import Default.Account;

public interface AccountMechanism {
    public Account createAccountMech();

    public boolean pinValid(String pin, Account acc);

    public boolean changePin(String pin1, String pin2, Account acc);
}
