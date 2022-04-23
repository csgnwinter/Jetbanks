package Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import Default.ATM;
import Default.Account;

public class AtmTest {
    // Test objects
    ATM machine;
    ArrayList<Account> testAccList;
    Account testAccount1;
    Account testAccount2;

    // Backup for System.in and System.out
    private final PrintStream stdOut = System.out;
    private final InputStream stdIn = System.in;

    // ByteArrayOutputStream to test any print statements
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

    // Initialize the objects needed for the tests
    @Before
    public void setUp() {
        machine = new ATM();
        testAccList = new ArrayList<>();
        testAccount1 = new Account(Long.parseLong("101010101010"), "Test User 1", "123456", 1000, 1000);
        testAccount2 = new Account(Long.parseLong("123456781234"), "Test User 2", "654321", 1000, 1000);
        testAccList.add(testAccount1);
        testAccList.add(testAccount2);
        machine.setAccountList(testAccList);

        // Reassign the standard output stream to a new PrintStream with a
        // ByteArrayOutputStream
        System.setOut(new PrintStream(outputStreamCaptor));
    }

    // Test case for getInput method
    @Test
    public void getInput() {
        /*
         * TEST CASE 1 (Normal Case)
         * Input: 3
         * maxOption: 5
         * canCancel: false
         */
        String inputTestCase = "3";
        // Set the user's input to be "3" for this test
        ByteArrayInputStream in = new ByteArrayInputStream(inputTestCase.getBytes());
        System.setIn(in);
        // Assert that message can be successfully retrieved
        assertEquals(inputTestCase, machine.getInput("Test", 5, false));

        /*
         * TEST CASE 2 (Normal Case)
         * Input: 5000
         * maxOption: -1
         * canCancel: false
         */
        inputTestCase = "5000";
        // Set the user's input to be "5000" for this test
        in = new ByteArrayInputStream(inputTestCase.getBytes());
        System.setIn(in);
        // Assert that message can be successfully retrieved
        assertEquals(inputTestCase, machine.getInput("Test", -1, false));

        /*
         * TEST CASE 3 (Invalid Case)
         * Input: 6
         * maxOption: 5
         * canCancel: false
         */
        inputTestCase = "6";
        // Set the user's input to be "6" for this test
        in = new ByteArrayInputStream(inputTestCase.getBytes());
        System.setIn(in);
        // Assert that message will be "-1"
        assertEquals("-1", machine.getInput("Test", 5, false));

        /*
         * TEST CASE 4 (Invalid Case)
         * Input: hello
         * maxOption: -1
         * canCancel: false
         */
        inputTestCase = "hello";
        // Set the user's input to be "hello" for this test
        in = new ByteArrayInputStream(inputTestCase.getBytes());
        System.setIn(in);
        // Assert that message will be "-1"
        assertEquals("-1", machine.getInput("Test", -1, false));

        /*
         * TEST CASE 5 (Normal Case)
         * Input: c
         * maxOption: -1
         * canCancel: true
         */
        inputTestCase = "c";
        // Set the user's input to be "abc" for this test
        in = new ByteArrayInputStream(inputTestCase.getBytes());
        System.setIn(in);
        // Assert that message can be successfully retrieved
        assertEquals(inputTestCase, machine.getInput("Test", -1, true));
    }

    // Test case for getKeyboardInput method
    @Test
    public void getKeyboardInput() {
        // TEST CASE: "Alexander Ang" (Normal Case)
        String inputTestCase = "Alexander Ang";
        // Set the user's input to be "Alexander Ang" for this test
        ByteArrayInputStream in = new ByteArrayInputStream(inputTestCase.getBytes());
        System.setIn(in);
        // Assert that message can be successfully retrieved
        assertEquals(inputTestCase, machine.getKeyboardInput("Test"));

        // TEST CASE: "12345" (Invalid Case)
        inputTestCase = "12345";
        // Set the user's input to be "12345" for this test
        in = new ByteArrayInputStream(inputTestCase.getBytes());
        System.setIn(in);
        // Assert that message will be "-1"
        assertEquals("-1", machine.getKeyboardInput("Test"));

        // TEST CASE: "!@#$" (Invalid Case)
        inputTestCase = "!@#$";
        // Set the user's input to be "!@#$" for this test
        in = new ByteArrayInputStream(inputTestCase.getBytes());
        System.setIn(in);
        // Assert that message will be "-1"
        assertEquals("-1", machine.getKeyboardInput("Test"));
    }

    // Test case for printMessage method
    @Test
    public void printMessage() {
        // TEST CASE: "Hello, Good Morning!"
        String printTestCase = "Hello, Good Morning!";
        machine.printMessage(printTestCase);
        assertEquals(printTestCase, outputStreamCaptor.toString());
    }

    // Test case for withdrawAmount method
    @Test
    public void withdrawAmount() {
        /*
         * TEST CASE 1 (Normal Case)
         * Withdraw Amount: 500
         * balance: 1000
         * Withdrawal Limit: 1000
         */
        String withdrawTestCase = "500";
        double balance = 1000;
        testAccount1.setBalance(balance);
        testAccount1.setWithdrawalLimit(1000);
        // Set the user's input to be "500" for this test
        ByteArrayInputStream in = new ByteArrayInputStream(withdrawTestCase.getBytes());
        System.setIn(in);
        // Assert that deposit function is working
        assertTrue(machine.withdrawAmount(testAccount1));
        // Assert that the withdrawal function withdraws the correct amount of money
        assertEquals((balance - Double.parseDouble(withdrawTestCase)), testAccount1.getBalance(), 0);

        /*
         * TEST CASE 2 (Invalid Case)
         * Withdraw Amount: 800
         * balance: 500
         * Withdrawal Limit: 1000
         */
        withdrawTestCase = "800";
        balance = 500;
        testAccount1.setBalance(balance);
        testAccount1.setWithdrawalLimit(1000);
        // Set the user's input to be "800" for this test
        in = new ByteArrayInputStream(withdrawTestCase.getBytes());
        System.setIn(in);
        // Assert that the withdrawal function will fail amount to withdraw is more than
        // the remaining balance
        assertFalse(machine.withdrawAmount(testAccount1));

        /*
         * TEST CASE 3 (Invalid Case)
         * Withdraw Amount: 1500
         * balance: 2000
         * Withdrawal Limit: 1000
         */
        withdrawTestCase = "1500";
        balance = 2000;
        testAccount1.setBalance(balance);
        testAccount1.setWithdrawalLimit(1000);
        // Set the user's input to be "1500" for this test
        in = new ByteArrayInputStream(withdrawTestCase.getBytes());
        System.setIn(in);
        // Assert that the withdrawal function will fail when the amount to withdraw is
        // more than
        // the withdrawal limit
        assertFalse(machine.withdrawAmount(testAccount1));
    }

    // Test case for depositAmount method
    @Test
    public void depositAmount() {
        double balance;

        // TEST CASE: 100 (Normal Case)
        String depositTestCase = "100";
        balance = testAccount1.getBalance();
        // Set the user's input to be "100" for this test
        ByteArrayInputStream in = new ByteArrayInputStream(depositTestCase.getBytes());
        System.setIn(in);
        // Assert that deposit function is working
        assertTrue(machine.depositAmount(testAccount1));
        // Assert that the deposit function deposits the correct amount of money
        assertEquals((Double.parseDouble(depositTestCase) + balance), testAccount1.getBalance(), 0);

        // TEST CASE: -1 (Negative Case)
        depositTestCase = "-1";
        balance = testAccount1.getBalance();
        // Set the user's input to be "-1" for this test
        in = new ByteArrayInputStream(depositTestCase.getBytes());
        System.setIn(in);
        // Assert that deposit function will fail when input is negative
        assertFalse(machine.depositAmount(testAccount1));

        // TEST CASE: 0 (Invalid Case)
        depositTestCase = "0";
        balance = testAccount1.getBalance();
        // Set the user's input to be "0" for this test
        in = new ByteArrayInputStream(depositTestCase.getBytes());
        System.setIn(in);
        // Assert that deposit function will fail when input is 0
        assertFalse(machine.depositAmount(testAccount1));
    }

    // Test case for signInMech method
    @Test
    public void signInMech() {
        /*
         * TEST CASE 1 (Normal Case)
         * Account Number: 101010101010
         * PIN: 123456
         */
        Long testAccNum = Long.parseLong("101010101010");
        String testPin = "123456";
        Account acc = machine.signInMech(testAccNum, testPin);
        // Assert that the sign in method is working
        assertTrue(acc != null);

        /*
         * TEST CASE 2 (Invalid Case)
         * Account Number: 101010101010
         * PIN: 654321 (Incorrect Pin)
         */
        testAccNum = Long.parseLong("101010101010");
        testPin = "654321";
        acc = machine.signInMech(testAccNum, testPin);
        // Assert that the sign in fails when entering an incorrect pin
        assertTrue(acc == null);

        /*
         * TEST CASE 3 (Invalid Case)
         * Account Number: 123456 (Account Number not 12 digits)
         * PIN: 654321
         */
        testAccNum = Long.parseLong("123456");
        testPin = "654321";
        acc = machine.signInMech(testAccNum, testPin);
        // Assert that the sign in fails when account number is not 12 digits
        assertTrue(acc == null);
    }

    // Test case for transferAmount method
    @Test
    public void transferAmount() {

        /*
         * TEST CASE 1 (Normal Case)
         * Amount to transfer: 500
         * From: testAccount1
         * - Account Number: 101010101010
         * - Balance: 1000
         * - Transfer Limit: 1000
         * To: testAccount2
         * - Account Number: 123456781234
         * - Balance: 1000
         * - Transfer Limit: 1000
         */
        Long toAccountNum = testAccount2.getAccountNo();
        testAccount1.setBalance(1000);
        testAccount1.setTransferLimit(1000);
        testAccount2.setBalance(1000);
        testAccount2.setTransferLimit(1000);
        String transferTestCase = "500";
        Double fromAccBalance = testAccount1.getBalance();
        Double toAccBalance = testAccount2.getBalance();

        // Set the user's input to be "500" for this test
        ByteArrayInputStream in = new ByteArrayInputStream(transferTestCase.getBytes());
        System.setIn(in);

        // Assert that the transfer function is working
        assertTrue(machine.transferAmount(testAccount1, toAccountNum));
        // Assert that the correct amount of money is being taken out from the sender
        // account
        assertEquals(fromAccBalance - Double.parseDouble(transferTestCase), testAccount1.getBalance(), 0);
        // Assert that the correct amount of money is being deposited into the receiver
        // account
        assertEquals(toAccBalance + Double.parseDouble(transferTestCase), testAccount2.getBalance(), 0);

        /*
         * TEST CASE 2 (Invalid Case)
         * Amount to transfer: 500
         * From: testAccount1
         * - Account Number: 101010101010
         * - Balance: 300
         * - Transfer Limit: 1000
         * To: testAccount2
         * - Account Number: 123456781234
         * - Balance: 1000
         * - Transfer Limit: 1000
         */
        toAccountNum = testAccount2.getAccountNo();
        testAccount1.setBalance(300);
        testAccount1.setTransferLimit(1000);
        testAccount2.setBalance(1000);
        testAccount2.setTransferLimit(1000);
        transferTestCase = "500";

        // Set the user's input to be "500" for this test
        in = new ByteArrayInputStream(transferTestCase.getBytes());
        System.setIn(in);

        // Assert that the transfer function will fail when the balance is not enough
        assertFalse(machine.transferAmount(testAccount1, toAccountNum));

        /*
         * TEST CASE 3 (Invalid Case)
         * Amount to transfer: 1500
         * From: testAccount1
         * - Account Number: 101010101010
         * - Balance: 2000
         * - Transfer Limit: 1000
         * To: testAccount2
         * - Account Number: 123456781234
         * - Balance: 1000
         * - Transfer Limit: 1000
         */
        toAccountNum = testAccount2.getAccountNo();
        testAccount1.setBalance(2000);
        testAccount1.setTransferLimit(1000);
        testAccount2.setBalance(1000);
        testAccount2.setTransferLimit(1000);
        transferTestCase = "1500";

        // Set the user's input to be "1500" for this test
        in = new ByteArrayInputStream(transferTestCase.getBytes());
        System.setIn(in);

        // Assert that the transfer function will fail when amount to transfer is more
        // than the transfer limit
        assertFalse(machine.transferAmount(testAccount1, toAccountNum));

        /*
         * TEST CASE 4 (Invalid Case)
         * From: testAccount1
         * - Account Number: 101010101010
         * - Balance: 1000
         * - Transfer Limit: 1000
         * To: testAccount3
         * - Account Number: 123412341234 (Doesn't exist)
         */
        toAccountNum = Long.parseLong("123412341234");
        testAccount1.setBalance(1000);
        testAccount1.setTransferLimit(1000);

        // Assert that the transfer function will fail when the account to transfer to
        // does not exist
        assertFalse(machine.transferAmount(testAccount1, toAccountNum));

        /*
         * TEST CASE 5 (Invalid Case)
         * From: testAccount1
         * - Account Number: 101010101010
         * - Balance: 1000
         * - Transfer Limit: 1000
         * To: testAccount3
         * - Account Number: 12341234 (Not 12 digits)
         */
        toAccountNum = Long.parseLong("12341234");
        testAccount1.setBalance(1000);
        testAccount1.setTransferLimit(1000);

        // Assert that the transfer function will fail when the account number to
        // transfer to does not have 12 digits
        assertFalse(machine.transferAmount(testAccount1, toAccountNum));

        /*
         * TEST CASE 6 (Invalid Case)
         * From & To: testAccount1
         * - Account Number: 101010101010
         * - Balance: 1000
         * - Transfer Limit: 1000
         */
        toAccountNum = testAccount1.getAccountNo();
        testAccount1.setBalance(1000);
        testAccount1.setTransferLimit(1000);

        // Assert that the transfer function will fail when the account to transfer to
        // is the same as the account transferring
        assertFalse(machine.transferAmount(testAccount1, toAccountNum));

        /*
         * TEST CASE 3 (Invalid Case)
         * Amount to transfer: 1500
         * From: testAccount1
         * - Account Number: 101010101010
         * - Balance: 2000
         * - Transfer Limit: 1000
         * To: testAccount2
         * - Account Number: 123456781234
         * - Balance: 1000
         * - Transfer Limit: 1000
         */
        toAccountNum = testAccount2.getAccountNo();
        testAccount1.setBalance(2000);
        testAccount1.setTransferLimit(1000);
        testAccount2.setBalance(1000);
        testAccount2.setTransferLimit(1000);
        transferTestCase = "1500";

        // Set the user's input to be "1500" for this test
        in = new ByteArrayInputStream(transferTestCase.getBytes());
        System.setIn(in);

        // Assert that the transfer function will fail when amount to transfer is more
        // than the transfer limit
        assertFalse(machine.transferAmount(testAccount1, toAccountNum));
    }

    // Test case for withdrawLimit method
    @Test
    public void withdrawLimit() {
        // TEST CASE: 500 (Normal Case)
        String withdrawLimitTestCase = "500";
        // Set the user's input to be "500" for this test
        ByteArrayInputStream in = new ByteArrayInputStream(withdrawLimitTestCase.getBytes());
        System.setIn(in);
        // Assert that the withdraw limit have been changed successfully
        assertTrue(machine.withdrawLimit(testAccount1));
        // Assert that the withdraw limit is changed to the correct amount
        assertEquals(testAccount1.getWithdrawalLimit(), Double.parseDouble(withdrawLimitTestCase), 0);

        // TEST CASE: 80 (Invalid Case)
        withdrawLimitTestCase = "80";
        // Set the user's input to be "80" for this test
        in = new ByteArrayInputStream(withdrawLimitTestCase.getBytes());
        System.setIn(in);
        // Assert that the withdraw limit will not be changed when its less than 100
        assertFalse(machine.withdrawLimit(testAccount1));
    }

    // Test case for transferLimit method
    @Test
    public void transferLimit() {
        // TEST CASE: 500 (Normal Case)
        String transLimitTestCase = "500";
        // Set the user's input to be "500" for this test
        ByteArrayInputStream in = new ByteArrayInputStream(transLimitTestCase.getBytes());
        System.setIn(in);
        // Assert that the transfer limit have been changed successfully
        assertTrue(machine.transferLimit(testAccount1));
        // Assert that the transfer limit is changed to the correct amount
        assertEquals(testAccount1.getTransferLimit(), Double.parseDouble(transLimitTestCase), 0);

        // TEST CASE: 80 (Invalid Case)
        transLimitTestCase = "80";
        // Set the user's input to be "80" for this test
        in = new ByteArrayInputStream(transLimitTestCase.getBytes());
        System.setIn(in);
        // Assert that the transfer limit will not be changed when its less than 100
        assertFalse(machine.transferLimit(testAccount1));
    }

    // Test case for changePin method
    @Test
    public void changePin() {
        /*
         * TEST CASE 1 (Normal Case)
         * Old PIN: 123456
         * New PIN 1: 123321
         * New PIN 2: 123321
         */
        String oldPinTestCase = "123456";
        String newPin1TestCase = "123321";
        String newPin2TestCase = "123321";
        testAccount1.setPinHash(testAccount1.generatePINHash(oldPinTestCase));
        // Assert that the pin can be changed successfully
        assertTrue(machine.changePin(newPin1TestCase, newPin2TestCase, testAccount1));
        // Assert that the new pin can be used to logged in
        assertTrue(machine.signInMech(testAccount1.getAccountNo(), newPin1TestCase) != null);

        /*
         * TEST CASE 2 (Invalid Case)
         * New PIN 1: 123
         * New PIN 2: 123
         */
        newPin1TestCase = "123";
        newPin2TestCase = "123";
        // Assert that the pin cannot be changed if the pins is not 6 digits
        assertFalse(machine.changePin(newPin1TestCase, newPin2TestCase, testAccount1));

        /*
         * TEST CASE 3 (Invalid Case)
         * New PIN 1: 123321
         * New PIN 2: 321123
         */
        newPin1TestCase = "123321";
        newPin2TestCase = "321123";
        // Assert that the pin cannot be changed if the pins does not match
        assertFalse(machine.changePin(newPin1TestCase, newPin2TestCase, testAccount1));

        /*
         * TEST CASE 4 (Invalid Case)
         * Old PIN: 123456
         * New PIN 1: 123456
         * New PIN 2: 123456
         */
        oldPinTestCase = "123456";
        newPin1TestCase = "123456";
        newPin2TestCase = "123456";
        testAccount1.setPinHash(testAccount1.generatePINHash(oldPinTestCase));
        // Assert that the pin cannot be changed if it is the same as the old pin
        assertFalse(machine.changePin(newPin1TestCase, newPin2TestCase, testAccount1));
    }

    @After
    public void tearDown() {
        // Restore the system.in and System.out
        System.setIn(stdIn);
        System.setOut(stdOut);
    }
}