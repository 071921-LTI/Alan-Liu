package com.revature.daos;
import java.sql.SQLException;
import java.util.List;

import javax.persistence.TypedQuery;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.revature.exceptions.UserNotFoundException;
import com.revature.models.Reimbursement;
import com.revature.models.User;
import com.revature.util.HibernateUtil;

public class UserHibernate implements UserDao{

	@Override
	public User getUserById(int id) throws UserNotFoundException, SQLException {
		User u = null;
		try(Session s = HibernateUtil.getSessionFactory().openSession()){
			u = s.get(User.class, id);
		}
		return u;
	}

	@Override
	public User getUserByUsername(String username) throws UserNotFoundException, SQLException {
		User user = null;
		try (Session s = HibernateUtil.getSessionFactory().openSession()) {
			String hql = ("from User where username = :user");
			TypedQuery<User> nq = s.createQuery(hql, User.class);
			nq.setParameter("user", username);
			user = nq.getSingleResult();
		}
		
		return user;
	}

	@Override
	public List<User> getUsers() throws SQLException {
		List<User> users = null;
		try (Session s = HibernateUtil.getSessionFactory().openSession()) {
			users = s.createQuery("FROM Reimbursement", User.class).getResultList();
		}
		return users;
	}

	@Override
	public int addUser(User user) throws SQLException {
		try (Session s = HibernateUtil.getSessionFactory().openSession()) {
			Transaction tx = s.beginTransaction();
			s.save(user);
			tx.commit();
		}
		return user.getId();
	}

	@Override
	public boolean updateUserEmail(int id, String email) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean updateUserPassword(int id, String password) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}
	
}
