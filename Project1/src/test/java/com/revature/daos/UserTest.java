package com.revature.daos;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.h2.tools.RunScript;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.revature.exceptions.UserNotFoundException;
import com.revature.models.User;
import com.revature.services.UserService;
import com.revature.services.UserServiceImpl;
import com.revature.util.ConnectionUtil;

@ExtendWith(MockitoExtension.class)
public class UserTest {

	private UserDao ud = new UserPostgres();
	private UserService us = new UserServiceImpl();
	private static MockedStatic<ConnectionUtil> mockedConnectionUtil;
	private static Connection connection;

	/*
	 * Used to create a connection to our H2/ in memory db instead of "production"
	 * database
	 */
	public static Connection getH2Connection() {
		try {
			if (connection == null || connection.isClosed()) {
				connection = DriverManager.getConnection("jdbc:h2:~/test");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return connection;
	}

	@BeforeAll
	public static void init() throws SQLException {
		/*
		 * Mocking the ConnectionUtil class for the getConnectionFromEnv method to
		 * return a connection to the H2 while the mock is "open".
		 */
		mockedConnectionUtil = Mockito.mockStatic(ConnectionUtil.class);
		mockedConnectionUtil.when(ConnectionUtil::getConnection).then(I -> getH2Connection());
	}

	@AfterAll
	public static void end() {
			/*
			 * Drops h2 tables after tests.
			 */
			try (Connection c = ConnectionUtil.getConnection()) {
				RunScript.execute(c, new FileReader("teardown.sql"));
			} catch (SQLException e) {
				e.printStackTrace();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			/*
			 * Closing resource, mocked behavior ends.
			 */
			mockedConnectionUtil.close();
	}

	@BeforeEach
	public void setUp() {
			/*
			 * Clear previous tables and recreates tables before each tests
			 */
			try (Connection c = ConnectionUtil.getConnection()) {
				RunScript.execute(c, new FileReader("setup.sql"));
			} catch (SQLException e) {
				e.printStackTrace();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
	}

	@Test
	public void connectionTest() {
		try {
			Connection con = ConnectionUtil.getConnection();
			assertNotNull(con);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void getByIdExists() throws UserNotFoundException, SQLException {
		User expected = new User("Alan", "pass", "first", "last", "newuser@email.com", 2);
		int i = ud.addUser(expected);
		assertEquals(ud.getUserById(1).getId(), i);
	}
	
	@Test
	public void addUserNotExist() throws SQLException {
		User user = new User("newUser", "password", "first", "last", "newuser@email.com", 1);
		user.setId(1);
		assertEquals(1, us.addUser(user));
	}
	
	@Test
	public void getByIdDoesNotExists() throws UserNotFoundException, SQLException {
		assertEquals(null, ud.getUserById(100));
	}
	
	@Test
	public void getByUsernameExists() throws UserNotFoundException, SQLException {
		User expected = new User("Alan", "pass", "first", "last", "newuser@email.com", 2);
		ud.addUser(expected);
		assertEquals(ud.getUserByUsername("Alan").getUsername(), expected.getUsername());
	}
	
	@Test
	public void getByUsernameDoesNotExists() {
		assertThrows(UserNotFoundException.class, ()->ud.getUserByUsername("Test"));
	}
	
	@Test
	public void addUserValid() throws SQLException {
		assertEquals(ud.addUser(new User("Alan", "pass", "first", "last", "newuser@email.com", 2)), 1);
	}
	
}
