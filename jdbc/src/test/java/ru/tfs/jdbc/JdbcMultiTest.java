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
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class JdbcMultiTest {
    private Random random = new Random();

    private static ConnectionPool pool;

    private static UserDao userDao;
    private static MessageDao messageDao;
    private static CommentDao commentDao;

    private List<User> users;
    private List<Message> messages;
    private List<Comment> comments;

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
    public void setUp() throws ConnectionException, StatementPerformingException {
        try {
            pool.getConnection()
                    .prepareStatement("truncate table db_jdbc.public.usr CASCADE;")
                    .execute();

            users = new ArrayList<>();
            for (int i = 0; i < 10; i++) {
                users.add(new User("User " + i, 18 + i));
            }
            userDao.save(users);

            messages = new ArrayList<>();
            for (int i = 0; i < 10; i++) {
                messages.add(new Message("msg " + i, users.get(random.nextInt(users.size()))));
            }
            messageDao.save(messages);

            comments = new ArrayList<>();
            for (int i = 0; i < 10; i++) {
                comments.add(new Comment(
                        "cmt " + i,
                        messages.get(random.nextInt(messages.size())),
                        users.get(random.nextInt(users.size()))
                ));
            }
            commentDao.save(comments);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void multiSaveTest() {
        for (User user : users) {
            Assert.assertTrue(user.getId() != 0);
        }

        for (Message message : messages) {
            Assert.assertTrue(message.getId() != 0);
        }

        for (Comment comment : comments) {
            Assert.assertTrue(comment.getId() != 0);
        }
    }

    @Test
    public void multiUpdateTest() throws ConnectionException, StatementPerformingException, BadResultException {
        users.forEach(user -> {
            user.setName("Update username");
            user.setAge(100);
        });
        Assert.assertTrue(userDao.update(users));
        for (User user : users) {
            User userFromDB = userDao.get(user.getId()).get();
            String name = userFromDB.getName();
            int age = userFromDB.getAge();
            Assert.assertTrue("Update username".equals(name));
            Assert.assertTrue(age == 100);
        }

        messages.forEach(message -> message.setText("Update msg"));
        Assert.assertTrue(messageDao.update(messages));
        for (Message message : messages) {
            String msgText = messageDao.get(message.getId()).get().getText();
            Assert.assertTrue("Update msg".equals(msgText));
        }

        comments.forEach(comment -> comment.setText("Update cmt"));
        Assert.assertTrue(commentDao.update(comments));
        for (Comment comment : comments) {
            String cmtText = commentDao.get(comment.getId()).get().getText();
            Assert.assertTrue("Update cmt".equals(cmtText));
        }
    }

    @Test
    public void multiUserDeleteTest() throws ConnectionException, BadResultException, StatementPerformingException {
        Assert.assertTrue(userDao.delete(users));
        for (User user : users) {
            Assert.assertFalse(userDao.get(user.getId()).isPresent());
        }
    }

    @Test
    public void multiMessageDeleteTest() throws ConnectionException, BadResultException, StatementPerformingException {
        Assert.assertTrue(messageDao.delete(messages));
        for (Message message : messages) {
            Assert.assertFalse(messageDao.get(message.getId()).isPresent());
        }
    }

    @Test
    public void multiCommentDeleteTest() throws ConnectionException, BadResultException, StatementPerformingException {
        Assert.assertTrue(commentDao.delete(comments));
        for (Comment comment : comments) {
            Assert.assertFalse(commentDao.get(comment.getId()).isPresent());
        }
    }

}
