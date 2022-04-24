# Jetbanks
Implementation of an ATM CLI application based on OOP concepts. Implements features such as Account Creation, User Authentication, Deposit, Withdrawal, Transfer, Checking Balance, Adjusting Account Settings. Project scope was limited to be focused on OOP concepts mainly, file management to be done in csv format and the assumption of user's laptop to act as the ATM terminal.

# 1. Features
## *Account Management*
Accounts can be created where users are able to enter their information and password, as well as their account settings for withdrawal and transfer limits. All accounts are stored in a csv file, Users.csv, with all their information. It is assumed that this csv file is encrypted and protected already, due to project limitations. All account information is also kept up to date real time as users make their various transactions.



https://user-images.githubusercontent.com/93068145/164951812-8f5268ed-dcbe-4de6-bb4a-237e1e182a6c.mov



## *Account Security*
Accounts are protected by hashing users' passwords with a hashing function and then storing the hashed password in User.csv and users are required to log into their accounts before being able to proceed with any transactions.


https://user-images.githubusercontent.com/93068145/164951846-069e2d8e-05c9-4132-a048-1ba60a82cc2a.mov


## *Check Balance*
Users are able to check their account's current balance.


https://user-images.githubusercontent.com/93068145/164951819-e115d14a-6d9a-4182-bebd-c96b100f0186.mov


## *Deposit*
Users are able to simulate depositting money into their accounts. Error checking is implemented for any invalid inputs such as non monetary values or invalid monetary values such as negative numbers. Once error checking passed, users' account information is updated with the appropriate balance.


https://user-images.githubusercontent.com/93068145/164951825-09b9cfdf-35f6-4dd7-9f5f-2c4fb1cca59c.mov


## *Withdrawal*
Users are able to simulate withdrawing money from their accounts. Error checking is also implemented for any invalid inputs or withdrawing more than account's withdrawal limits or whether account has sufficient funds. Once error checking passed, users' account information is updated with the appropriate balance. 


https://user-images.githubusercontent.com/93068145/164951826-5761036a-213b-47cd-a44d-014837843fe8.mov


## *Transfer*
Users are able to transfer funds from their account to any other account in the Bank's database. Error checking is also implemented here to check for invalid input or against account's transfer limits or whether account has sufficient funds. Once error checking passed, users' account information is updated as well as the recipient's account information.


https://user-images.githubusercontent.com/93068145/164951828-bb4ae2cd-aaff-4119-9005-0633a9db0df8.mov


## *Settings*
Users are able to change their withdrawal limits, transfer limits and account PINs. 


https://user-images.githubusercontent.com/93068145/164951829-d5b062ea-4988-4d6d-b81a-43dca546ab41.mov



https://user-images.githubusercontent.com/93068145/164951832-7570c131-d53c-4194-8c8a-b275c0c23ab1.mov


## *Live Chat Support*
ATM application supports the use of live chat implemented on socket programming to simulate a live chat support for users if they have any enquiries to make.

## *User Experience*
User experience is taken into account and various features are implemented such as the welcome message upon loading the bank app. Error messages are coded in red font color and succes messages are code in green font colors to give users an accurate mental mode. Other colors are also implemented to improve users' experience.

<img width="504" alt="Screenshot 2022-04-24 at 9 31 21 AM" src="https://user-images.githubusercontent.com/93068145/164951865-c028aecd-3dbf-49c2-b27c-0cf9c6ff29a7.png">

<img width="412" alt="Screenshot 2022-04-24 at 9 31 43 AM" src="https://user-images.githubusercontent.com/93068145/164951874-3118b4f6-1a7f-47dd-b75b-c4e09a195911.png">

# 2. Instructions
Open and run JetBanks.java in Default folder under app -> src 
