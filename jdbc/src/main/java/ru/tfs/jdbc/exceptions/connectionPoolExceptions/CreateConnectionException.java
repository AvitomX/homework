package ru.tfs.jdbc.exceptions.connectionPoolExceptions;

/**
 * Класс исклчения, вызываемого при ошибке создания Connection в пуле
 */
public class CreateConnectionException extends ConnectionException {
    public CreateConnectionException(Throwable err) {
        super("Connection creation fail", err);
    }
}
