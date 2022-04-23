package Auditor;

import java.text.SimpleDateFormat;
import java.util.*;

import Mechanisms.Screen;

public class ExceptionChecker {
    String ACC_NUM_COL;
    String DATE_COL;
    String TRANSACTION_DETAILS_COL;
    String CHEQUE_NO_COL;
    String VALUE_DATE_COL;
    String WITHDRAW_AMT_COL;
    String DEPOSIT_AMT_COL;
    String BAL_AMT_COL;

    public ExceptionChecker(String[] values) {
        try {
            this.ACC_NUM_COL = values[0].substring(0, 12);
        } catch (IndexOutOfBoundsException ex) {
            this.ACC_NUM_COL = values[0];
        }
        this.DATE_COL = values[1];
        this.TRANSACTION_DETAILS_COL = values[2];
        this.CHEQUE_NO_COL = values[3];
        this.VALUE_DATE_COL = values[4];
        this.WITHDRAW_AMT_COL = values[5];
        this.DEPOSIT_AMT_COL = values[6];
        this.BAL_AMT_COL = values[7];
    }

    // check for date format
    public boolean isValidDate(final String date) {
        boolean valid = false;
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
        format1.setLenient(false);

        if (date.trim().equals("")) {
            return true;
        } else {
            try {
                // ensure date format is the same as the one declard above
                Date checkerDate = format1.parse("2014-12-31");
                Date date1 = format1.parse(date);
                if (date1.before(checkerDate) != true) {
                    valid = true;
                }
            } catch (Exception e) {
                valid = false;
            }
        }
        return valid;
    }

    // checking for account length and starting string ensuring it is one of JETBANK
    // accounts
    public boolean isValidAccountNo() {
        if (!this.ACC_NUM_COL.substring(0, 4).equals("4090") || this.ACC_NUM_COL.length() != 12) {
            return false;
        } else {
            return true;
        }
    }

    // checking if inputs are Numeric
    public static boolean isNumeric(String s) {
        if (s.isEmpty()) {
            return true;
        }
        try {
            Double tester = Double.parseDouble(s);
            return true;
        } catch (NumberFormatException ex) {
            return false;
        }
    }

    // ensuring all monetary value is in digits only
    public boolean isValidAmount() {
        if (isNumeric(this.WITHDRAW_AMT_COL) && isNumeric(this.DEPOSIT_AMT_COL) && isNumeric(this.BAL_AMT_COL)) {
            return true;
        } else {
            return false;
        }
    }

    // final check for all exceptions above and throw out all error transactions
    public String checkRow(int rowCount) {
        // check for date
        String lineToAdd = "";

        // String rowSpace = String.valueOf(rowCount);
        // if (String.valueOf(rowCount).length() < 20) {
        // for (int i = 0; rowSpace.length() < 20; i++) {
        // rowSpace = rowSpace + " ";
        // }
        // }

        if (isValidDate(this.DATE_COL) == false) {
            lineToAdd += "Transaction Number " + rowCount + "        Invalid Date                   :       Date:   "
                    + this.DATE_COL + "///";
        }

        if (isValidDate(this.VALUE_DATE_COL) == false) {
            lineToAdd += "Transaction Number " + rowCount + "        Invalid Date                   : Value Date:   "
                    + this.VALUE_DATE_COL + "///";
        }

        // check for valid Account No
        if (!isValidAccountNo()) {
            lineToAdd += "Transaction Number " + rowCount + "        Invalid Account Number         :               "
                    + this.ACC_NUM_COL + "///";
        }
        // check for valid Amount
        if (!isValidAmount()) {
            lineToAdd += "Transaction Number " + rowCount
                    + "       Invalid Withdrawal | Deposit | Balance :               " + this.WITHDRAW_AMT_COL + " | "
                    + this.DEPOSIT_AMT_COL + " | " + this.BAL_AMT_COL + "///";
        }
        return lineToAdd;

    }

    public String getACC_NUM_COL() {
        return ACC_NUM_COL;
    }

    public void setACC_NUM_COL(String ACC_NUM_COL) {
        this.ACC_NUM_COL = ACC_NUM_COL;
    }

    public String getDATE_COL() {
        return DATE_COL;
    }

    public void setDATE_COL(String DATE_COL) {
        this.DATE_COL = DATE_COL;
    }

    public String getTRANSACTION_DETAILS_COL() {
        return TRANSACTION_DETAILS_COL;
    }

    public void setTRANSACTION_DETAILS_COL(String TRANSACTION_DETAILS_COL) {
        this.TRANSACTION_DETAILS_COL = TRANSACTION_DETAILS_COL;
    }

    public String getCHEQUE_NO_COL() {
        return CHEQUE_NO_COL;
    }

    public void setCHEQUE_NO_COL(String CHEQUE_NO_COL) {
        this.CHEQUE_NO_COL = CHEQUE_NO_COL;
    }

    public String getVALUE_DATE_COL() {
        return VALUE_DATE_COL;
    }

    public void setVALUE_DATE_COL(String VALUE_DATE_COL) {
        this.VALUE_DATE_COL = VALUE_DATE_COL;
    }

    public String getWITHDRAW_AMT_COL() {
        return WITHDRAW_AMT_COL;
    }

    public void setWITHDRAW_AMT_COL(String WITHDRAW_AMT_COL) {
        this.WITHDRAW_AMT_COL = WITHDRAW_AMT_COL;
    }

    public String getDEPOSIT_AMT_COL() {
        return DEPOSIT_AMT_COL;
    }

    public void setDEPOSIT_AMT_COL(String DEPOSIT_AMT_COL) {
        this.DEPOSIT_AMT_COL = DEPOSIT_AMT_COL;
    }

    public String getBAL_AMT_COL() {
        return BAL_AMT_COL;
    }

    public void setBAL_AMT_COL(String BAL_AMT_COL) {
        this.BAL_AMT_COL = BAL_AMT_COL;
    }
}
