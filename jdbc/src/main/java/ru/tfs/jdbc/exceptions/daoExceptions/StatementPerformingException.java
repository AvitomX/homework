package ru.tfs.jdbc.exceptions.daoExceptions;

/**
 * Класс исклчения, вызываемого при ошибке выполнения Statement
 */
public class StatementPerformingException extends Exception {
    public StatementPerformingException(String location, Throwable err) {
        super("Statement performing exception in " + location, err);
    }
}
