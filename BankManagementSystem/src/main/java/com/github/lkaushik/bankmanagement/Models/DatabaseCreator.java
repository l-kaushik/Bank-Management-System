package com.github.lkaushik.bankmanagement.Models;

import java.sql.Array;
import java.util.Arrays;

public class DatabaseCreator {
    private static final String AdminTableQuery = """
        CREATE TABLE "Admins" (
            "ID"    INTEGER NOT NULL,
            "Username"      TEXT NOT NULL,
            "Password"      TEXT NOT NULL,
            PRIMARY KEY("ID" AUTOINCREMENT)
            )
        """;

    private static final String SavingsAccountTableQuery = """
            CREATE TABLE "SavingsAccounts" (
                    "ID"    INTEGER NOT NULL,
                    "Owner" TEXT NOT NULL,
                    "AccountNumber" TEXT NOT NULL,
                    "WithdrawalLimit"       REAL NOT NULL,
                    "Balance"       REAL NOT NULL,
                    PRIMARY KEY("ID" AUTOINCREMENT)
            )
            """;

    private static final String CheckingAccountsTableQuery = """
            CREATE TABLE "CheckingAccounts" (
                    "ID"    INTEGER NOT NULL,
                    "Owner" TEXT NOT NULL,
                    "AccountNumber" TEXT NOT NULL,
                    "TransactionLimit"      REAL NOT NULL,
                    "Balance"       REAL NOT NULL,
                    PRIMARY KEY("ID" AUTOINCREMENT)
            )
            """;

    private static final String ClientsTableQuery = """
            CREATE TABLE "Clients" (
                    "ID"    INTEGER NOT NULL,
                    "FirstName"     TEXT NOT NULL,
                    "LastName"      TEXT NOT NULL,
                    "PayeeAddress"  TEXT NOT NULL,
                    "Password"      TEXT NOT NULL,
                    "Date"  TEXT NOT NULL,
                    PRIMARY KEY("ID" AUTOINCREMENT)
            )
            """;

    private static final String TransactionsTableQuery = """
            CREATE TABLE "Transactions" (
                    "ID"    INTEGER NOT NULL,
                    "Sender"        TEXT NOT NULL,
                    "Receiver"      TEXT NOT NULL,
                    "Amount"        REAL NOT NULL,
                    "Date"  TEXT NOT NULL,
                    "Message"       TEXT,
                    PRIMARY KEY("ID" AUTOINCREMENT)
            )
            """;

    public static final String[] tableCreationQueries = {
            AdminTableQuery,
            ClientsTableQuery,
            SavingsAccountTableQuery,
            CheckingAccountsTableQuery,
            TransactionsTableQuery
    };
}
