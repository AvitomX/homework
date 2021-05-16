package ru.tfs.jdbc.utils;

import ru.tfs.jdbc.exceptions.connectionPoolExceptions.CloseConnectionException;
import ru.tfs.jdbc.exceptions.connectionPoolExceptions.CreateConnectionException;
import ru.tfs.jdbc.exceptions.connectionPoolExceptions.ValidConnectionException;

import java.sql.Connection;

public interface ConnectionPool {

    Connection getConnection() throws ValidConnectionException, CreateConnectionException;

    boolean releaseConnection(Connection connection);

    String getUrl();

    String getUser();

    String getPassword();

    void shutdown() throws CloseConnectionException;
}
