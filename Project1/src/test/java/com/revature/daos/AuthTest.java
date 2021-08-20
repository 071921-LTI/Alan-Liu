package com.revature.daos;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.sql.SQLException;

import javax.persistence.NoResultException;

import org.hibernate.exception.ConstraintViolationException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.revature.daos.UserDao;
import com.revature.exceptions.UserNotFoundException;
import com.revature.models.User;
import com.revature.models.UserRole;
import com.revature.services.AuthService;
import com.revature.services.AuthServiceImpl;

@ExtendWith(MockitoExtension.class)
public class AuthTest {
	
	User user = new User("Alan", "pass", "first", "last", "newuser@email.com", 2);
	
	@Mock
	UserDao ud;
	
	@InjectMocks
	AuthService as = new AuthServiceImpl();
	
	@Test
	public void loginSuccess() throws UserNotFoundException, SQLException {
		Mockito.when(ud.getUserByUsername("Alan")).thenReturn(user);
		assertEquals(user, as.login("Alan", "pass"));
	}
	
	@Test
	public void loginFailure() throws UserNotFoundException, SQLException {
		Mockito.when(ud.getUserByUsername("Alan")).thenReturn(user);
		assertThrows(UserNotFoundException.class, () -> as.login("Alan", "wrong"));
	}

//	
	@Test
	public void registerSucess() throws UserNotFoundException, SQLException {
		User u = new User("newUser", "password", "first", "last", "newuser@email.com", 2);
		Mockito.when(ud.addUser(u)).thenReturn(u.getId());
		assertEquals(u.getId(), as.register(u));
	}
	
	@Test
	public void registerFailure() throws SQLException {
		User u = new User("newUser", "password", "first", "last", "newuser@email.com", 2);
		Mockito.when(ud.addUser(u)).thenReturn(u.getId());
		assertNotEquals(1, as.register(u));
	}
}
	
	