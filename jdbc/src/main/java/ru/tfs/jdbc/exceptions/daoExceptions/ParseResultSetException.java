package ru.tfs.jdbc.exceptions.daoExceptions;

/**
 * Класс исклчения, вызываемого при ошибке анализа (парсинга) ResultSet
 */
public class ParseResultSetException extends Exception {
    public ParseResultSetException(String resultSetName, Throwable err) {
        super(resultSetName + " parsing fail", err);
    }
}
