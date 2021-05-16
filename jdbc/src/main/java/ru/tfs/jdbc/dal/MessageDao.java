package ru.tfs.jdbc.dal;

import ru.tfs.jdbc.entities.Comment;
import ru.tfs.jdbc.entities.Message;
import ru.tfs.jdbc.entities.User;
import ru.tfs.jdbc.exceptions.daoExceptions.ParseResultSetException;
import ru.tfs.jdbc.exceptions.daoExceptions.StatementPerformingException;
import ru.tfs.jdbc.utils.ConnectionPool;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class MessageDao extends AbstractDao<Message> {
    private static final String SELECT_ALL_MESSAGES =
            "SELECT m.id, m.text msg_text, " +
                    "m.author_id author_id, u.name author_name, u.age author_age, " +
                    "c.id cmt_id, c.text cmt_text, " +
                    "c.author_id cmt_author_id, cu.name cmt_author_name, cu.age cmt_author_age " +
                    "FROM messages m " +
                    "JOIN usr u ON m.author_id = u.id " +
                    "LEFT JOIN comments c ON m.id = c.message_id " +
                    "LEFT JOIN usr cu ON c.author_id = cu.id ";

    private static final String SELECT_MESSAGE = SELECT_ALL_MESSAGES + " WHERE m.id = ? ";
    private static final String INSERT_MESSAGE = "INSERT INTO messages (text, author_id) VALUES (?, ?) ";
    private static final String UPDATE_MESSAGE = "UPDATE messages SET text = ?, author_id = ? WHERE id = ? ";
    private static final String DELETE_MESSAGE = "DELETE FROM messages WHERE id = ? ";
    private static final String DELETE_MESSAGES = "DELETE FROM messages WHERE id IN ";

    public MessageDao(ConnectionPool connectionPool) {
        super(connectionPool);
    }

    protected List<Message> parseResultSet(ResultSet rs) {
        Set<Message> messages = new HashSet<>();
        try {
            while (rs.next()) {
                Long id = rs.getLong("id");
                Message message = messages.stream()
                        .filter(mes -> mes.getId().equals(id))
                        .findFirst()
                        .orElse(new Message(
                                        id,
                                        rs.getString("msg_text"),
                                        new User(
                                                rs.getLong("author_id"),
                                                rs.getString("author_name"),
                                                rs.getInt("author_age")
                                        )
                                )
                        );

                Long commentId = rs.getLong("cmt_id");
                if (commentId != 0) {
                    Comment comment = new Comment();
                    comment.setId(commentId);
                    comment.setText(rs.getString("cmt_text"));
                    User commentAuthor = new User(
                            rs.getLong("cmt_author_id"),
                            rs.getString("cmt_author_name"),
                            rs.getInt("cmt_author_age")
                    );
                    comment.setAuthor(commentAuthor);
                    message.addComment(comment);
                }

                if (!messages.contains(message)) {
                    messages.add(message);
                }
            }
        } catch (SQLException e) {
            try {
                throw new ParseResultSetException("Message", e);
            } catch (ParseResultSetException err) {
                err.printStackTrace();
            }
        }

        return messages.stream().collect(Collectors.toList());
    }

    @Override
    protected String getSelectSql() {
        return SELECT_MESSAGE;
    }

    @Override
    protected String getSelectAllSql() {
        return SELECT_ALL_MESSAGES;
    }

    @Override
    protected void setEntityId(long id, Message message) {
        message.setId(id);
    }

    @Override
    protected void prepareStatementForInsert(PreparedStatement ps, Message message) throws StatementPerformingException {
        try {
            ps.setString(1, message.getText());
            ps.setLong(2, message.getAuthor().getId());
        } catch (SQLException e) {
            throw new StatementPerformingException(INSERT_MESSAGE, e);
        }

    }

    @Override
    protected String getInsertSql() {
        return INSERT_MESSAGE;
    }

    @Override
    protected void prepareStatementForUpdate(PreparedStatement ps, Message newT) throws StatementPerformingException {
        try {
            ps.setString(1, newT.getText());
            ps.setLong(2, newT.getAuthor().getId());
            ps.setLong(3, newT.getId());
        } catch (SQLException e) {
            throw new StatementPerformingException(UPDATE_MESSAGE, e);
        }
    }

    @Override
    protected String getUpdateSql() {
        return UPDATE_MESSAGE;
    }

    @Override
    protected String getDeleteListSql() {
        return DELETE_MESSAGES;
    }

    @Override
    protected long getEntityId(Message message) {
        return message.getId();
    }

    @Override
    protected String getDeleteSql() {
        return DELETE_MESSAGE;
    }
}
