package com.jlastudioiv;

import com.jlastudioiv.Helpers.DatabaseManager;
import com.jlastudioiv.Model.Transaction;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws ClassNotFoundException {
	    Class.forName("org.sqlite.JDBC");

	    try (Connection connection = DriverManager.getConnection("jdbc:sqlite:stats.db")) {
            DatabaseManager dbm = new DatabaseManager(connection);
//            dbm.createAtmTable();
            welcomeScreen(dbm);
        } catch (SQLException ex) {
            System.out.println("Something went wrong with your DB connection.");
            ex.printStackTrace();
        }
    }
    public static void welcomeScreen(DatabaseManager dbm) throws SQLException {
        System.out.println("————————————————————————————————————————————————");
        System.out.println("Welcome to ATM 3000. What would you like to do?");
        System.out.println("1) Check running total of account balance");
        System.out.println("2) Deposit Amount");
        System.out.println("3) Withdraw Amount");
        System.out.println("————————————————————————————————————————————————");

        Scanner scanner = new Scanner( System.in );
        int choice = scanner.nextInt();

        switch (choice) {
            case 1:
                System.out.println("Now showing running total of account balance");
                List<Transaction> results = Transaction.findAll(dbm);
                int totalBalance = 0;
                for (Transaction transaction : results) {
                    System.out.println(transaction);
                    totalBalance = totalBalance + transaction.getBalanceChange();
                }
                System.out.println("Total{balance=" + totalBalance + "}");
                break;
            case 2:
                System.out.println("What is the name of your deposit?");
                String initialDepositName = scanner.next();
                System.out.println("How much are you depositing?");
                int initialDeposit = scanner.nextInt();
                new Transaction(dbm.getStatement(), initialDepositName, initialDeposit).save();
                break;
            case 3:
                System.out.println("What is the name of your withdraw?");
                String initialWithdrawName = scanner.next();
                System.out.println("How much are you withdrawing?");
                int initialWithdraw = scanner.nextInt();
                initialWithdraw = -initialWithdraw;
                new Transaction(dbm.getStatement(), initialWithdrawName, initialWithdraw).save();
                break;
        }
        welcomeScreen(dbm);
    }
}
