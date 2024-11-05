package com.github.lkaushik.bankmanagement.Models;

import java.util.Random;

public class AccountNumberGenerator {
    static DatabaseDriver databaseDriver = new DatabaseDriver();

    private static String generateRandomAccountNumber() {
        Random random = new Random();
        StringBuilder accountNumber = new StringBuilder();

        for (int i = 0; i < 16; i++) {
            int digit = random.nextInt(10);
            accountNumber.append(digit);

            // Add a space after every 4 digits, except for the last group
            if ((i + 1) % 4 == 0) {
                accountNumber.append(" ");
            }
        }

        return accountNumber.toString();
    }

    public static String generateUniqueSavingsAccountNumber(String checkingAccountNumber) {
        String savingsAccountNumber;
        do {
            savingsAccountNumber = generateAccountNumber();
        } while (savingsAccountNumber.equals(checkingAccountNumber));
        return savingsAccountNumber;
    }

    public static String generateAccountNumber() {
        String accountNumber;
        do{
            accountNumber = generateRandomAccountNumber();
        } while (databaseDriver.isAccountNumberExists(accountNumber));

        return  accountNumber;
    }
}
