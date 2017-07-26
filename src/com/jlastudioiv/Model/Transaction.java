package com.jlastudioiv.Model;

import com.jlastudioiv.Helpers.DatabaseManager;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class Transaction {
    private int id;
    private Statement statement;
    private String transactionName;
    private int balanceChange;

    public Transaction(Statement statement, String transactionName, int balanceChange) {
        this.statement = statement;
        this.transactionName = transactionName;
        this.balanceChange = balanceChange;
    }

    public Transaction(int id, Statement statement, String transactionName, int balanceChange) {
        this(statement, transactionName, balanceChange);
        this.id = id;
    }

    public void save() throws SQLException {
        String formattedSql = String.format("INSERT INTO atm (transactionName, balanceChange) VALUES ('%s', %s)", transactionName, balanceChange);
        statement.executeUpdate(formattedSql);
    }

    public static List<Transaction> findAll(DatabaseManager dbm) throws SQLException {
        ResultSet rs = dbm.findAll("atm");
        List<Transaction> tempCollection = new ArrayList<>();
        Statement tempStatement = dbm.getStatement();
        while (rs.next()) {
            String transactionName = rs.getString("transactionName");
            int balanceChange = rs.getInt("balanceChange");

            Transaction tempTransaction = new Transaction(rs.getInt("id"), tempStatement, transactionName, balanceChange);
            tempCollection.add(tempTransaction);
        }
        return tempCollection;
    }

    public String getTransactionName() {
        return transactionName;
    }

    public int getBalanceChange() {
        return balanceChange;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "transactionName='" + transactionName + '\'' +
                ", balanceChange=" + balanceChange +
                '}';
    }
}
