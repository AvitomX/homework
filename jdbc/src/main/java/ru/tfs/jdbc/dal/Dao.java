package ru.tfs.jdbc.dal;

import ru.tfs.jdbc.exceptions.connectionPoolExceptions.CreateConnectionException;
import ru.tfs.jdbc.exceptions.connectionPoolExceptions.ValidConnectionException;
import ru.tfs.jdbc.exceptions.daoExceptions.BadResultException;
import ru.tfs.jdbc.exceptions.daoExceptions.StatementPerformingException;

import java.util.List;
import java.util.Optional;

public interface Dao<T> {
//    Optional<T> get(long id);

    List<T> getAll() throws ValidConnectionException, CreateConnectionException, StatementPerformingException;

    boolean save(T t) throws ValidConnectionException, CreateConnectionException, StatementPerformingException;

    boolean update(T newT) throws ValidConnectionException, CreateConnectionException, StatementPerformingException;

    boolean delete(T t) throws ValidConnectionException, CreateConnectionException, StatementPerformingException;

    Optional<T> get(Long id) throws ValidConnectionException, CreateConnectionException, BadResultException, StatementPerformingException;
}
