package ru.tfs.jdbc.exceptions.connectionPoolExceptions;

/**
 * Класс исклчения, вызываемого при ошибке закрытия Connection в пуле
 */
public class CloseConnectionException extends ConnectionException {
    public CloseConnectionException(Throwable err) {
        super("Connection closing fail", err);
    }
}
