package com.revature.services;

import java.sql.SQLException;

import com.revature.daos.UserDao;
import com.revature.daos.UserPostgres;
import com.revature.exceptions.UserNotFoundException;
import com.revature.models.User;

public class AuthServiceImpl implements AuthService {

	UserDao ud = new UserPostgres();

	@Override
	public User login(String username, String password) throws UserNotFoundException, SQLException {
		User user = ud.getUserByUsername(username);
		if (password.equals(user.getPassword())) {
			return user;
		} else {
			return null;
		}
	}
	
	public int register(User u){
		try {
			return ud.addUser(u);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return -1;
	}

	@Override
	public boolean authorize(String token) throws UserNotFoundException, SQLException {
	String[] stringArr = token.split(":");
	int id = Integer.parseInt(stringArr[0]);
	int role = Integer.parseInt(stringArr[1]);
		User user = ud.getUserById(id);
		if(user.getRole() != role) {
			return false;
		} else {
			return true;
		}
	}

}
