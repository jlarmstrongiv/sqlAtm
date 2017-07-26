package com.jlastudioiv.Helpers;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseManager {
    Statement statement;

    public DatabaseManager(Connection connection) throws SQLException {
        this.statement = connection.createStatement();
    }

    public void createAtmTable() throws SQLException {
        statement.executeUpdate("CREATE TABLE atm (id INTEGER PRIMARY KEY, transactionName STRING, balanceChange INTEGER)");
    }

    public void deleteAtmTable() throws SQLException {
        statement.executeUpdate("DROP TABLE IF EXISTS atm)");
    }

    public Statement getStatement() {
        return statement;
    }

    public ResultSet findAll(String tableName) throws SQLException {
        String formattedSql = String.format("SELECT * FROM %s", tableName);
        ResultSet rs = statement.executeQuery(formattedSql);
        return rs;
    }
}
