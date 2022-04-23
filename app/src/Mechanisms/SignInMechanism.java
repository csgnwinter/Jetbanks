package Mechanisms;

import Default.Account;

public interface SignInMechanism {
    public Account signInMech(Long accNum, String pin);
}
