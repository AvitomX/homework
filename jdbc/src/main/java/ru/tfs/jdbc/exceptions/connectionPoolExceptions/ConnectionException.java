package ru.tfs.jdbc.exceptions.connectionPoolExceptions;

/**
 * Класс исклчения, возникающих при работе с Connection
 */
public class ConnectionException extends Exception {
    public ConnectionException(String message, Throwable cause) {
        super(message, cause);
    }
}
