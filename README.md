# Jetbanks
Implementation of an ATM CLI application based on OOP concepts. Implements features such as Account Creation, User Authentication, Deposit, Withdrawal, Transfer, Checking Balance, Adjusting Account Settings. Project scope was limited to be focused on OOP concepts mainly, file management to be done in csv format and the assumption of user's laptop to act as the ATM terminal.

# 1. Features
## *Account Management*
Accounts can be created where users are able to enter their information and password, as well as their account settings for withdrawal and transfer limits. All accounts are stored in a csv file, Users.csv, with all their information. It is assumed that this csv file is encrypted and protected already, due to project limitations. All account information is also kept up to date real time as users make their various transactions.

## *Account Security*
Accounts are protected by hashing users' passwords with a hashing function and then storing the hashed password in User.csv 

## *Check Balance*
Users are able to check their account's current balance.

## *Deposit*
Users are able to simulate depositting money into their accounts. Error checking is implemented for any invalid inputs such as non monetary values or invalid monetary values such as negative numbers. Once error checking passed, users' account information is updated with the appropriate balance.

## *Withdrawal*
Users are able to simulate withdrawing money from their accounts. Error checking is also implemented for any invalid inputs or withdrawing more than account's withdrawal limits or whether account has sufficient funds. Once error checking passed, users' account information is updated with the appropriate balance. 

## *Transfer*
Users are able to transfer funds from their account to any other account in the Bank's database. Error checking is also implemented here to check for invalid input or against account's transfer limits or whether account has sufficient funds. Once error checking passed, users' account information is updated as well as the recipient's account information.

## *Settings*
Users are able to change their withdrawal limits, transfer limits and account PINs. 

## *Live Chat Support*
ATM application supports the use of live chat implemented on socket programming to simulate a live chat support for users if they have any enquiries to make.

## *User Experience*
User experience is taken into account and various features are implemented such as the welcome message upon loading the bank app. Error messages are coded in red font color and succes messages are code in green font colors to give users an accurate mental mode. Other colors are also implemented to improve users' experience.

# 2. Instructions
Open and run JetBanks.java in Default folder under app -> src 
