package ru.tfs.jdbc.dal;

import ru.tfs.jdbc.exceptions.connectionPoolExceptions.CreateConnectionException;
import ru.tfs.jdbc.exceptions.connectionPoolExceptions.ValidConnectionException;
import ru.tfs.jdbc.exceptions.daoExceptions.BadResultException;
import ru.tfs.jdbc.exceptions.daoExceptions.StatementPerformingException;
import ru.tfs.jdbc.utils.ConnectionPool;

import java.sql.*;
import java.util.List;
import java.util.Optional;
import java.util.StringJoiner;

public abstract class AbstractDao<T> implements Dao<T> {
    private static final int FETCH_SIZE = 10;
    private ConnectionPool connectionPool;

    public AbstractDao(ConnectionPool connectionPool) {
        this.connectionPool = connectionPool;
    }

    protected abstract List<T> parseResultSet(ResultSet rs);

    protected abstract String getSelectSql();

    protected abstract String getSelectAllSql();

    protected abstract String getInsertSql();

    protected abstract String getUpdateSql();

    protected abstract String getDeleteSql();

    protected abstract String getDeleteListSql();

    protected abstract void prepareStatementForInsert(PreparedStatement ps, T t) throws StatementPerformingException;

    protected abstract void prepareStatementForUpdate(PreparedStatement ps, T newT) throws StatementPerformingException;

    protected abstract void setEntityId(long id, T t);

    protected abstract long getEntityId(T t);


    @Override
    public Optional<T> get(Long id) throws ValidConnectionException, CreateConnectionException, BadResultException, StatementPerformingException {
        Connection connection = connectionPool.getConnection();
        try (PreparedStatement ps = connection.prepareStatement(getSelectSql())) {
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            List<T> list = parseResultSet(rs);
            if (!list.isEmpty()) {
                if (list.size() > 1) {
                    throw new BadResultException(getSelectSql());
                }
                return Optional.of(list.get(0));
            }
        } catch (SQLException e) {
            throw new StatementPerformingException(getSelectSql(), e);
        } finally {
            connectionPool.releaseConnection(connection);
        }

        return Optional.empty();
    }

    @Override
    public List<T> getAll() throws ValidConnectionException, CreateConnectionException, StatementPerformingException {
        Connection connection = connectionPool.getConnection();
        try (PreparedStatement ps = connection.prepareStatement(getSelectAllSql())) {
            ResultSet rs = ps.executeQuery();
            List<T> list = parseResultSet(rs);
            if (!list.isEmpty()) {
                return list;
            }
        } catch (SQLException e) {
            throw new StatementPerformingException(getSelectSql(), e);
        } finally {
            connectionPool.releaseConnection(connection);
        }
        return null;
    }

    @Override
    public boolean save(T t) throws ValidConnectionException, CreateConnectionException, StatementPerformingException {
        boolean result = false;
        Connection connection = connectionPool.getConnection();
        try (PreparedStatement ps = connection.prepareStatement(getInsertSql(), Statement.RETURN_GENERATED_KEYS)) {
            prepareStatementForInsert(ps, t);
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs != null && rs.next()) {
                setEntityId(rs.getLong(1), t);
                result = true;
            }
        } catch (SQLException e) {
            throw new StatementPerformingException(getInsertSql(), e);
        } finally {
            connectionPool.releaseConnection(connection);
        }

        return result;
    }

    public boolean save(List<T> list) throws ValidConnectionException, CreateConnectionException, StatementPerformingException {
        boolean result = false;
        Connection connection = connectionPool.getConnection();
        try (PreparedStatement ps = connection.prepareStatement(getInsertSql(), Statement.RETURN_GENERATED_KEYS)) {
            ps.setFetchSize(FETCH_SIZE);
            for (T t : list) {
                prepareStatementForInsert(ps, t);
                ps.addBatch();
            }
            ps.executeBatch();
            ResultSet rs = ps.getGeneratedKeys();
            int i = 0;
            if (rs != null) {
                while (rs.next()) {
                    setEntityId(rs.getLong(1), list.get(i));
                    i++;
                }
                result = true;
            }
        } catch (SQLException e) {
            throw new StatementPerformingException(getInsertSql(), e);
        } finally {
            connectionPool.releaseConnection(connection);
        }

        return result;
    }


    @Override
    public boolean update(T newT) throws ValidConnectionException, CreateConnectionException, StatementPerformingException {
        boolean result = false;
        Connection connection = connectionPool.getConnection();
        try (PreparedStatement ps = connection.prepareStatement(getUpdateSql())) {
            prepareStatementForUpdate(ps, newT);
            int res = ps.executeUpdate();
            if (res != 0) {
                result = true;
            }
        } catch (SQLException e) {
            throw new StatementPerformingException(getUpdateSql(), e);
        } finally {
            connectionPool.releaseConnection(connection);
        }

        return result;
    }

    public boolean update(List<T> list) throws ValidConnectionException, CreateConnectionException, StatementPerformingException {
        boolean result = false;
        Connection connection = connectionPool.getConnection();
        try (PreparedStatement ps = connection.prepareStatement(getUpdateSql())) {
            ps.setFetchSize(FETCH_SIZE);
            for (T t : list) {
                prepareStatementForUpdate(ps, t);
                ps.addBatch();
            }
            int[] resBatch = ps.executeBatch();
            for (int i = 0; i < resBatch.length; i++) {
                result = true;
                if (resBatch[i] == 0) {
                    result = false;
                    break;
                }
            }
        } catch (SQLException e) {
            throw new StatementPerformingException(getUpdateSql(), e);
        } finally {
            connectionPool.releaseConnection(connection);
        }

        return result;
    }


    @Override
    public boolean delete(T t) throws ValidConnectionException, CreateConnectionException, StatementPerformingException {
        boolean result = false;
        Connection connection = connectionPool.getConnection();
        try (PreparedStatement ps = connection.prepareStatement(getDeleteSql())) {
            ps.setLong(1, getEntityId(t));
            int res = ps.executeUpdate();
            if (res != 0) {
                result = true;
            }
        } catch (SQLException e) {
            throw new StatementPerformingException(getUpdateSql(), e);
        } finally {
            connectionPool.releaseConnection(connection);
        }

        return result;
    }

    public boolean delete(List<T> list) throws ValidConnectionException, CreateConnectionException, StatementPerformingException {
        boolean result = false;
        Connection connection = connectionPool.getConnection();

        String sql = getDeleteListSql().concat("(");
        StringJoiner joiner = new StringJoiner(",");
        for (int i = 0; i < list.size(); i++) {
            joiner.add(String.valueOf(getEntityId(list.get(i))));
        }

        try (PreparedStatement ps = connection.prepareStatement(sql.concat(joiner.toString()).concat(")"))) {
            int res = ps.executeUpdate();
            if (res != 0) {
                result = true;
            }
        } catch (SQLException e) {
            throw new StatementPerformingException(getUpdateSql(), e);
        } finally {
            connectionPool.releaseConnection(connection);
        }

        return result;
    }

}
