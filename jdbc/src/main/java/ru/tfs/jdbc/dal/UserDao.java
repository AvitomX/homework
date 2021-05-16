package ru.tfs.jdbc.dal;

import ru.tfs.jdbc.entities.User;
import ru.tfs.jdbc.exceptions.daoExceptions.ParseResultSetException;
import ru.tfs.jdbc.exceptions.daoExceptions.StatementPerformingException;
import ru.tfs.jdbc.utils.ConnectionPool;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDao extends AbstractDao<User> {
    private static final String SELECT_ALL_USERS = "SELECT id, name, age FROM usr ";
    private static final String SELECT_USER = SELECT_ALL_USERS + " WHERE id = ? ";
    private static final String INSERT_USER = "INSERT INTO usr (name, age) VALUES (?, ?) ";
    private static final String UPDATE_USER = "UPDATE usr SET name = ?, age = ? WHERE id = ? ";
    private static final String DELETE_USER = "DELETE FROM usr WHERE id = ? ";
    private static final String DELETE_USERS = "DELETE FROM usr WHERE id IN ";


    public UserDao(ConnectionPool connectionPool) {
        super(connectionPool);
    }

    protected List<User> parseResultSet(ResultSet rs) {
        List<User> users = new ArrayList<>();
        try {
            while (rs.next()) {
                User user = new User();
                user.setId(rs.getLong("id"));
                user.setName(rs.getString("name"));
                user.setAge(rs.getInt("age"));
                users.add(user);
            }
        } catch (SQLException e) {
            try {
                throw new ParseResultSetException("User", e);
            } catch (ParseResultSetException err) {
                err.printStackTrace();
            }
        }

        return users;
    }

    @Override
    protected String getSelectSql() {
        return SELECT_USER;
    }

    @Override
    protected String getSelectAllSql() {
        return SELECT_ALL_USERS;
    }

    @Override
    protected void setEntityId(long id, User user) {
        user.setId(id);
    }

    @Override
    protected void prepareStatementForInsert(PreparedStatement ps, User user) throws StatementPerformingException {
        try {
            ps.setString(1, user.getName());
            ps.setInt(2, user.getAge());
        } catch (SQLException e) {
            throw new StatementPerformingException(INSERT_USER, e);
        }
    }

    @Override
    protected String getInsertSql() {
        return INSERT_USER;
    }

    @Override
    protected void prepareStatementForUpdate(PreparedStatement ps, User newT) throws StatementPerformingException {
        try {
            ps.setString(1, newT.getName());
            ps.setInt(2, newT.getAge());
            ps.setLong(3, newT.getId());
        } catch (SQLException e) {
            throw new StatementPerformingException(UPDATE_USER, e);
        }
    }

    @Override
    protected String getUpdateSql() {
        return UPDATE_USER;
    }

    @Override
    protected String getDeleteListSql() {
        return DELETE_USERS;
    }

    @Override
    protected long getEntityId(User user) {
        return user.getId();
    }

    @Override
    protected String getDeleteSql() {
        return DELETE_USER;
    }
}
