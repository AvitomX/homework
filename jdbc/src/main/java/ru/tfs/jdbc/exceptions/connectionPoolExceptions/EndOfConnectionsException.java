package ru.tfs.jdbc.exceptions.connectionPoolExceptions;

/**
 * Класс исклчения, вызываемого при отсутствии свободных Connection в пуле
 */
public class EndOfConnectionsException extends RuntimeException {
    public EndOfConnectionsException() {
        super("Maximum pool size reached, no available connections!");
    }
}
