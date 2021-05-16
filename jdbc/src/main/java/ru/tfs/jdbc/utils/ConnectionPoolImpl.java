package ru.tfs.jdbc.utils;

import ru.tfs.jdbc.exceptions.connectionPoolExceptions.CloseConnectionException;
import ru.tfs.jdbc.exceptions.connectionPoolExceptions.CreateConnectionException;
import ru.tfs.jdbc.exceptions.connectionPoolExceptions.EndOfConnectionsException;
import ru.tfs.jdbc.exceptions.connectionPoolExceptions.ValidConnectionException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class ConnectionPoolImpl implements ConnectionPool {
    private static final int MAX_POOL_SIZE = 10;
    private static final int MAX_RESPONSE_TIME = 1000 * 3;
    private static int INITIAL_POOL_SIZE = 3;

    private final String url;
    private final String user;
    private final String password;
    private final List<Connection> connectionPool;
    private final List<Connection> usedConnections = new CopyOnWriteArrayList<>();


    public static ConnectionPool create(String url, String user, String password) throws CreateConnectionException {
        List<Connection> pool = new CopyOnWriteArrayList<>(new ArrayList<>(INITIAL_POOL_SIZE));
        for (int i = 0; i < INITIAL_POOL_SIZE; i++) {
            pool.add(createConnection(url, user, password));
        }

        return new ConnectionPoolImpl(url, user, password, pool);
    }

    private static Connection createConnection(String url, String user, String password) throws CreateConnectionException {
        try {
            return DriverManager.getConnection(url, user, password);
        } catch (SQLException err) {
            throw new CreateConnectionException(err);
        }
    }

    private ConnectionPoolImpl(String url, String user, String password, List<Connection> connectionPool) {
        this.url = url;
        this.user = user;
        this.password = password;
        this.connectionPool = connectionPool;
    }

    private boolean releaseDeadConnections() throws ValidConnectionException, CreateConnectionException {
        boolean result = false;
        if (!usedConnections.isEmpty()) {
            for (int i = 0; i < usedConnections.size(); i++) {
                Connection con = usedConnections.get(i);
                try {
                    if (con.isClosed() || !con.isValid(MAX_RESPONSE_TIME)) {
                        usedConnections.remove(con);
                        connectionPool.add(createConnection(url, user, password));
                        result = true;
                    }
                } catch (SQLException e) {
                    throw new ValidConnectionException(e);
                }
            }
        }
        return result;
    }

    @Override
    public Connection getConnection() throws ValidConnectionException, CreateConnectionException {
        if (connectionPool.isEmpty()) {
            if (usedConnections.size() < MAX_POOL_SIZE) {
                connectionPool.add(createConnection(url, user, password));
            } else {
                if (!releaseDeadConnections()) {
                    throw new EndOfConnectionsException();
                }
            }
        }

        Connection connection = connectionPool.remove(connectionPool.size() - 1);

        try {
            if (!connection.isValid(MAX_RESPONSE_TIME)) {
                connection = createConnection(url, user, password);
            }
        } catch (SQLException err) {
            throw new ValidConnectionException(err);
        }
        usedConnections.add(connection);

        return connection;
    }

    @Override
    public boolean releaseConnection(Connection connection) {
        connectionPool.add(connection);

        return usedConnections.remove(connection);
    }

    @Override
    public void shutdown() throws CloseConnectionException {
        for (int i = 0; i < usedConnections.size(); i++) {
            releaseConnection(usedConnections.get(i));
        }
        for (Connection c : connectionPool) {
            try {
                c.close();
            } catch (SQLException err) {
                throw new CloseConnectionException(err);
            }
        }
        connectionPool.clear();
    }

    @Override
    public String getUrl() {
        return url;
    }

    @Override
    public String getUser() {
        return user;
    }

    @Override
    public String getPassword() {
        return password;
    }

}
