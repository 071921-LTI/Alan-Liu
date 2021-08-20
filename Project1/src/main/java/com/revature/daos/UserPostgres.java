package com.revature.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.revature.exceptions.UserNotFoundException;
import com.revature.models.User;
import com.revature.util.ConnectionUtil;

public class UserPostgres implements UserDao{
	Connection conn = null;
	PreparedStatement ps = null;
	ResultSet rs = null;
	
	private User getUserFromResultSet(ResultSet rs) throws SQLException{
		
		int id = rs.getInt("ers_users_id");
		String username = rs.getString("ers_username");
		String password = rs.getString("ers_password");
		String fname = rs.getString("user_first_name");
		String lname = rs.getString("user_last_name");
		String email = rs.getString("user_email");
		int roleId = rs.getInt("user_role_id");
	
		return new User(id,username,password,fname,lname,email,roleId);
	}

    @Override
    public User getUserById(int id) throws UserNotFoundException, SQLException {
    	User u = null;
    	try {
			conn = ConnectionUtil.getConnection();
			String sql = "select * from ers_users where ers_users_id = ?";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			
			if(rs.next()) {
				u = getUserFromResultSet(rs);
				return u;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			conn.close();
		}
		return u;
    }
    
    @Override
    public User getUserByUsername(String username) throws UserNotFoundException, SQLException {
    	User user = null;
		
		try {
			conn = ConnectionUtil.getConnection();
			String sql = "select * from ers_users where ers_username = ?;";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, username);
			ResultSet rs = ps.executeQuery();
			
			if(rs.next()) {
				
				user = getUserFromResultSet(rs);
				return user;
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			conn.close();
    	}
		throw new UserNotFoundException();
    }
    
    @Override
    public List<User> getUsers() throws SQLException {
    	
    	List<User> users = new ArrayList<>();
		
		try {
			conn = ConnectionUtil.getConnection();
			String sql = "select * from ers_users;";
			Statement s = conn.createStatement();
			ResultSet rs = s.executeQuery(sql);
			
			while(rs.next()) {
				User user = getUserFromResultSet(rs);
				users.add(user);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			conn.close();
		}
		return users;
    }
    
    @Override
    public int addUser(User user) throws SQLException {
		int id = -1;
		String sql = "INSERT INTO ers_users (ers_username,ers_password,user_first_name,user_last_name,user_email,user_role_id)"
				+ "VALUES (?,?,?,?,?,?)";

		try {
			conn = ConnectionUtil.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, user.getUsername());
			ps.setString(2, user.getPassword());
			ps.setString(3, user.getFirstName());
			ps.setString(4, user.getLastName());
			ps.setString(5, user.getEmail());
			ps.setInt(6, user.getRole());
			ps.execute();

			ResultSet rs = ps.getGeneratedKeys();

			if (rs.next()) {
				id = rs.getInt("ers_users_id");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			conn.close();
		}
		return id;
    }

	@Override
	public boolean updateUserEmail(int id, String email) throws SQLException {
		boolean bool = false;
		String sql = "Update ers_users Set user_email = ? Where ers_users_id = ?";
		try {
			conn = ConnectionUtil.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setString(1, email);
			ps.setInt(2, id);

			int res = ps.executeUpdate();
			if (res > 0)
				bool = true;
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			conn.close();
		}
		
		return bool;
	}

	@Override
	public boolean updateUserPassword(int id, String password) throws SQLException {
		boolean bool = false;
		String sql = "Update ers_users Set ers_password = ? Where ers_users_id = ?";
		try {
			conn = ConnectionUtil.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setString(1, password);
			ps.setInt(2, id);

			int res = ps.executeUpdate();
			if (res > 0)
				bool = true;
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			conn.close();
		}
		
		return bool;
	}


}
