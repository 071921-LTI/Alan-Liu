package com.revature.delegates;

import java.io.IOException;
import org.apache.logging.log4j.LogManager; 
import org.apache.logging.log4j.Logger;
import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.revature.exceptions.UserNotFoundException;
import com.revature.models.Reimbursement;
import com.revature.models.User;
import com.revature.services.AuthService;
import com.revature.services.AuthServiceImpl;
import com.revature.services.UserService;
import com.revature.services.UserServiceImpl;

public class UserDelegate implements Delegatable {

	UserService us = new UserServiceImpl();
	AuthService as = new AuthServiceImpl();
	private static Logger log = LogManager.getRootLogger();

	@Override
	public void process(HttpServletRequest rq, HttpServletResponse rs) throws ServletException, IOException, SQLException {
		// Retrieve GET, POST, PUT, DELETE...
		String method = rq.getMethod();

		switch (method) {
		case "GET":
			handleGet(rq, rs);
			break;
		case "POST":
			handlePost(rq, rs);
			break;
		case "PUT":
			handlePut(rq, rs);
			break;
		case "DELETE":
			handleDelete(rq, rs);
			break;
		default:
			rs.sendError(405);
		}

	}

	@Override
	public void handleGet(HttpServletRequest rq, HttpServletResponse rs) throws ServletException, IOException, SQLException {

		String actualURL = rq.getRequestURI().substring(rq.getContextPath().length());
		switch(actualURL) {
		case "/users":
		int userID = Integer.parseInt(rq.getParameter("userId"));
		try {
			User user = us.getUserById(userID);
			try (PrintWriter pw = rs.getWriter()) {
				pw.write(new ObjectMapper().writeValueAsString(user));
			}
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UserNotFoundException e) {
			rs.sendError(404);
			log.error("Failed to find The User " + userID);
		}
			break;
		case "/users/All":
			String json;
			List<User> u = us.getUsers();
			try {
				ObjectMapper om = new ObjectMapper();
				ObjectWriter ow = om.writer().withDefaultPrettyPrinter();
				json = ow.writeValueAsString(u);
				PrintWriter writer = rs.getWriter();
				writer.write(json);
				rs.setStatus(200);
			}
			catch(IOException e) {
				rs.setStatus(401);
				log.error("Failed to Get All Users" +u);
				e.printStackTrace();
			}
			break;
		}
	}


	@Override
	public void handlePost(HttpServletRequest rq, HttpServletResponse rs) throws ServletException, IOException, SQLException {

		String actualURL = rq.getRequestURI().substring(rq.getContextPath().length());
		switch(actualURL) {
		case "/users/register":
			InputStream request = rq.getInputStream();
			User user = new ObjectMapper().readValue(request, User.class);
			
			if (us.addUser(user) == -1) {
				rs.sendError(400, "Unable to add user.");
				log.error("Failed to Register User " + user);
			} else {
				HttpSession session = rq.getSession();
				session.setAttribute("user", user.getUsername());
				String token = user.getId() + ":" + user.getRole();
				rs.setHeader("Authorization", token);
				rs.setStatus(201);
			}
			break;
		case "/users/update/email":
			int userID = Integer.parseInt(rq.getParameter("userId"));
			String email = rq.getParameter("Email");

			try {
				if (us.updateUserEmail(userID, email)) {
					rs.setStatus(200);
				}
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (UserNotFoundException e) {
				rs.sendError(404);
				log.error("Failed to Update User Email " + userID);
			}
			break;
		case "/users/update/password":
			int ID = Integer.parseInt(rq.getParameter("userId"));
			String password = rq.getParameter("password");
			try {
				if (us.updateUserPassword(ID, password)) {
					rs.setStatus(200);
				}
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (UserNotFoundException e) {
				rs.sendError(404);
				log.error("Failed to Update User Password " + ID);
			}
			break;
		}

	}

	@Override
	public void handlePut(HttpServletRequest rq, HttpServletResponse rs) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void handleDelete(HttpServletRequest rq, HttpServletResponse rs) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
	}

}