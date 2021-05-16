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
import java.util.ArrayList;
import java.util.List;

public class CommentDao extends AbstractDao<Comment> {
    private static final String SELECT_ALL_COMMENTS =
            "SELECT c.id, c.text, " +
                    "c.author_id, u.name, u.age, " +
                    "c.message_id, m.text msg_text, m.author_id msg_author_id, um.name msg_name, um.age msg_age " +
                    "FROM comments c " +
                    "JOIN usr u ON c.author_id = u.id " +
                    "JOIN messages m ON c.message_id = m.id " +
                    "JOIN usr um ON m.author_id = um.id ";
    private static final String SELECT_COMMENT = SELECT_ALL_COMMENTS + " WHERE c.id = ? ";
    private static final String INSERT_COMMENT = "INSERT INTO comments (text, author_id, message_id) VALUES (?, ?, ?) ";
    private static final String UPDATE_COMMENT = "UPDATE comments SET text = ?, author_id = ?, message_id = ? WHERE id = ? ";
    private static final String DELETE_COMMENT = "DELETE FROM comments WHERE id = ? ";
    private static final String DELETE_COMMENTS = "DELETE FROM comments WHERE id IN ";

    public CommentDao(ConnectionPool connectionPool) {
        super(connectionPool);
    }

    protected List<Comment> parseResultSet(ResultSet rs) {
        List<Comment> comments = new ArrayList<>();
        try {
            while (rs.next()) {
                Comment comment = new Comment();
                comment.setId(rs.getLong("id"));
                comment.setText(rs.getString("text"));

                User author = new User();
                author.setId(rs.getLong("author_id"));
                author.setName(rs.getString("name"));
                author.setAge(rs.getInt("age"));
                comment.setAuthor(author);

                Message message = new Message();
                message.setId(rs.getLong("message_id"));
                message.setText(rs.getString("msg_text"));
                User msgAuthor = new User();
                msgAuthor.setId(rs.getLong("msg_author_id"));
                msgAuthor.setName(rs.getString("msg_name"));
                msgAuthor.setAge(rs.getInt("msg_age"));
                message.setAuthor(msgAuthor);
                comment.setMessage(message);

                comments.add(comment);
            }
        } catch (SQLException e) {
            try {
                throw new ParseResultSetException("Comment", e);
            } catch (ParseResultSetException err) {
                err.printStackTrace();
            }
        }

        return comments;
    }

    @Override
    protected String getSelectSql() {
        return SELECT_COMMENT;
    }

    @Override
    protected String getSelectAllSql() {
        return SELECT_ALL_COMMENTS;
    }

    @Override
    protected void setEntityId(long id, Comment comment) {
        comment.setId(id);
    }

    @Override
    protected void prepareStatementForInsert(PreparedStatement ps, Comment comment) throws StatementPerformingException {
        try {
            ps.setString(1, comment.getText());
            ps.setLong(2, comment.getAuthor().getId());
            ps.setLong(3, comment.getMessage().getId());
        } catch (SQLException e) {
            throw new StatementPerformingException(INSERT_COMMENT, e);
        }
    }

    @Override
    protected String getInsertSql() {
        return INSERT_COMMENT;
    }

    @Override
    protected void prepareStatementForUpdate(PreparedStatement ps, Comment newT) throws StatementPerformingException {
        try {
            ps.setString(1, newT.getText());
            ps.setLong(2, newT.getAuthor().getId());
            ps.setLong(3, newT.getMessage().getId());
            ps.setLong(4, newT.getId());
        } catch (SQLException e) {
            throw new StatementPerformingException(UPDATE_COMMENT, e);
        }
    }

    @Override
    protected String getUpdateSql() {
        return UPDATE_COMMENT;
    }

    @Override
    protected String getDeleteListSql() {
        return DELETE_COMMENTS;
    }

    @Override
    protected long getEntityId(Comment comment) {
        return comment.getId();
    }

    @Override
    protected String getDeleteSql() {
        return DELETE_COMMENT;
    }
}
