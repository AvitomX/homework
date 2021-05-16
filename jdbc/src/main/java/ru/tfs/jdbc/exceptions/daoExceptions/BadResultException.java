package ru.tfs.jdbc.exceptions.daoExceptions;

/**
 * Класс исклчения, вызываемого при получении некорректного результата из БД. Например, в случае получении нескольких
 * записей при обращении за одной.
 */
public class BadResultException extends Exception {
    public BadResultException(String sqlQuery) {
        super("Bad result. SQL query: " + sqlQuery);
    }
}
