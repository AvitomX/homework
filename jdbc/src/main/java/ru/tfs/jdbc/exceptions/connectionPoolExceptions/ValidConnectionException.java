package ru.tfs.jdbc.exceptions.connectionPoolExceptions;

/**
 * Класс исклчения, вызываемого при некорректной валидации Connection
 */
public class ValidConnectionException extends ConnectionException {
    public ValidConnectionException(Throwable err) {
        super("Connection timeout validation fail", err);
    }
}
