package Auditor;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LogAuditor {

    public static void main(String[] args) {
        List<String> allErrors = new ArrayList<>();
        List<String> allTransactions = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("JetBanks/src/Data/bank_sample_01.csv"))) {
            String line;
            int rowCount = 0;
            while ((line = br.readLine()) != null) {
                if (rowCount > 0) {
                    String[] values = line.split(",(?=(?:[^\\\"]*\\\"[^\\\"]*\\\")*[^\\\"]*$)");
                    ExceptionChecker checkLine = new ExceptionChecker(values);
                    String valuesString = (Arrays.toString(values));
                    allTransactions.add(valuesString);
                    if (!checkLine.checkRow(rowCount).isEmpty()) {
                        String[] temp = checkLine.checkRow(rowCount).split("///");
                        allErrors.add(temp[0]);
                    }
                }
                rowCount++;
            }

            Consolidate consol = new Consolidate(allTransactions, allErrors);
            consol.printConsolidatedErrors();
            consol.getAccountCheckInputs();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}