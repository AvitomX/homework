package ru.tfs.jdbc;

import org.junit.*;
import ru.tfs.jdbc.dal.CommentDao;
import ru.tfs.jdbc.dal.MessageDao;
import ru.tfs.jdbc.dal.UserDao;
import ru.tfs.jdbc.entities.Comment;
import ru.tfs.jdbc.entities.Message;
import ru.tfs.jdbc.entities.User;
import ru.tfs.jdbc.exceptions.connectionPoolExceptions.CloseConnectionException;
import ru.tfs.jdbc.exceptions.connectionPoolExceptions.ConnectionException;
import ru.tfs.jdbc.exceptions.connectionPoolExceptions.CreateConnectionException;
import ru.tfs.jdbc.exceptions.connectionPoolExceptions.ValidConnectionException;
import ru.tfs.jdbc.exceptions.daoExceptions.BadResultException;
import ru.tfs.jdbc.exceptions.daoExceptions.StatementPerformingException;
import ru.tfs.jdbc.utils.ConnectionPool;
import ru.tfs.jdbc.utils.ConnectionPoolImpl;

import java.sql.SQLException;

public class JdbcTest {
    static ConnectionPool pool;

    private static UserDao userDao;
    private static MessageDao messageDao;
    private static CommentDao commentDao;

    private User alex;
    private User max;

    private Message msg1;
    private Message msg2;
    private Message msg3;

    private Comment cmt1;
    private Comment cmt2;
    private Comment cmt3;

    @BeforeClass
    public static void init() throws CreateConnectionException {
        pool = ConnectionPoolImpl
                .create("jdbc:postgresql://localhost:5432/db_jdbc",
                        "postgres",
                        "qwerty");

        userDao = new UserDao(pool);
        messageDao = new MessageDao(pool);
        commentDao = new CommentDao(pool);
    }

    @AfterClass
    public static void end() throws CloseConnectionException {
        pool.shutdown();
    }

    @Before
    public void clearData() throws ConnectionException, StatementPerformingException {
        try {
            pool.getConnection()
                    .prepareStatement("truncate table db_jdbc.public.usr CASCADE;")
                    .execute();

            alex = new User("Alex", 20);
            max = new User("Max", 45);
            userDao.save(alex);
            userDao.save(max);

            msg1 = new Message("first msg", alex);
            msg2 = new Message("second msg", max);
            msg3 = new Message("third msg", alex);
            messageDao.save(msg1);
            messageDao.save(msg2);
            messageDao.save(msg3);

            cmt1 = new Comment( "cmt1 for msg1", msg1, max);
            cmt2 = new Comment("cmt2 for msg2", msg2, alex);
            cmt3 = new Comment("cmt3 for msg1", msg1, max);
            commentDao.save(cmt1);
            commentDao.save(cmt2);
            commentDao.save(cmt3);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getTest() throws BadResultException, StatementPerformingException, ConnectionException {
        User[] userArr = new User[]{alex, max};
        User user = userDao.get(alex.getId()).get();
        Assert.assertTrue(alex.equals(user));
        User[] usersFromDB = userDao.getAll().stream().toArray(User[]::new);
        Assert.assertArrayEquals(userArr, usersFromDB);

        Message[] messages = new Message[]{msg1, msg2, msg3};
        Message message = messageDao.get(msg1.getId()).get();
        Assert.assertTrue(msg1.equals(message));
        Message[] messageFromDB = messageDao.getAll().stream().toArray(Message[]::new);
        Assert.assertArrayEquals(messages, messageFromDB);

        Comment[] comments = new Comment[]{cmt1, cmt2, cmt3};
        Comment comment = commentDao.get(cmt1.getId()).get();
        Assert.assertTrue(cmt1.equals(comment));
        Comment[] commentFromDB = commentDao.getAll().stream().toArray(Comment[]::new);
        Assert.assertArrayEquals(comments, commentFromDB);
    }

    @Test
    public void deleteUserTest() throws BadResultException, StatementPerformingException, ConnectionException {
        userDao.delete(max);
        User user = userDao.get(max.getId()).orElse(null);
        Assert.assertTrue(user == null);
    }

    @Test
    public void deleteMessageTest() throws BadResultException, StatementPerformingException, ConnectionException {
        messageDao.delete(msg3);
        Message message = messageDao.get(msg3.getId()).orElse(null);
        Assert.assertTrue(message == null);
    }

    @Test
    public void deleteCommentTest() throws ConnectionException, BadResultException, StatementPerformingException {
        commentDao.delete(cmt2);
        Comment comment = commentDao.get(cmt2.getId()).orElse(null);
        Assert.assertTrue(comment == null);
    }

    @Test
    public void updateTest() throws ConnectionException, BadResultException, StatementPerformingException {
        alex.setAge(99);
        userDao.update(alex);
        User user = userDao.get(alex.getId()).get();
        Assert.assertTrue(user.getAge() == 99);

        msg1.setText("new message text");
        messageDao.update(msg1);
        Message message = messageDao.get(msg1.getId()).get();
        Assert.assertTrue("new message text".equals(message.getText()));

        cmt1.setText("new comment text");
        commentDao.update(cmt1);
        Comment comment = commentDao.get(cmt1.getId()).get();
        Assert.assertTrue("new comment text".equals(comment.getText()));
    }

    @Test
    public void saveTest() {
        Assert.assertTrue(alex.getId() != 0);
        Assert.assertTrue(max.getId() != 0);

        Assert.assertTrue(msg1.getId() != 0);
        Assert.assertTrue(msg2.getId() != 0);
        Assert.assertTrue(msg3.getId() != 0);

        Assert.assertTrue(cmt1.getId() != 0);
        Assert.assertTrue(cmt2.getId() != 0);
        Assert.assertTrue(cmt3.getId() != 0);
    }
}
