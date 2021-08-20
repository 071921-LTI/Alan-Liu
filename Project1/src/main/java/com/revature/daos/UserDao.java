package com.revature.daos;

import java.sql.SQLException;
import java.util.List;

import com.revature.exceptions.UserNotFoundException;
import com.revature.models.User;

public interface UserDao {

	// if no user is found
	User getUserById(int id) throws UserNotFoundException, SQLException;
	// if no user is found
	User getUserByUsername(String username) throws UserNotFoundException, SQLException;
	List<User> getUsers() throws SQLException;
	// Should return the id generated
	int addUser(User user) throws SQLException;
	boolean updateUserEmail(int id,String email) throws SQLException;
	boolean updateUserPassword(int id, String password) throws SQLException;
	
}
