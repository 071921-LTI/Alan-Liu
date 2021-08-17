package com.revature.services;

import java.sql.SQLException;

import com.revature.exceptions.UserNotFoundException;
import com.revature.models.User;

public interface AuthService {

	User login(String username, String password) throws UserNotFoundException, SQLException;
	boolean authorize(String token) throws UserNotFoundException, SQLException;
	int register(User u) throws SQLException;

}
