package com.revature.services;

import java.sql.SQLException;
import java.util.List;

import com.revature.exceptions.UserNotFoundException;
import com.revature.models.User;

public interface UserService {

	// Should return true if a user was successfully added
	int addUser(User user) throws SQLException;
	List<User> getUsers() throws SQLException;
	User getUserById(int id) throws UserNotFoundException, SQLException;
	User getUserByUsername(String username) throws UserNotFoundException, SQLException;
	boolean updateUserEmail(int id, String email) throws UserNotFoundException, SQLException;
	boolean updateUserPassword(int id, String password) throws SQLException, UserNotFoundException;
}
