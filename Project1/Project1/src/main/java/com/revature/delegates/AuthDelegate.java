package com.revature.delegates;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.revature.exceptions.UserNotFoundException;
import com.revature.models.User;
import com.revature.services.AuthService;
import com.revature.services.AuthServiceImpl;

public class AuthDelegate implements Delegatable{
	AuthService as = new AuthServiceImpl();
	private static final long serialVersionUID = 1L;
	@Override
	public void process(HttpServletRequest rq, HttpServletResponse rs) throws ServletException, IOException, SQLException, UserNotFoundException {

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
	public void handleGet(HttpServletRequest rq, HttpServletResponse rs) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String pathNext = (String) rq.getAttribute("pathNext");
		switch(pathNext) {
		case "logout":
			rq.getSession().setAttribute("user", null);
			break;
		}
		
	}


	@Override
	public void handlePost(HttpServletRequest rq, HttpServletResponse rs) throws ServletException, IOException, SQLException, UserNotFoundException {
		

		String username = rq.getParameter("username");
		String password = rq.getParameter("password");

		try {
			User user = as.login(username, password);
			if (user != null) {
				HttpSession session = rq.getSession();
				session.setAttribute("user", username);

				String token = user.getId() + ":" + user.getRole();
				rs.setHeader("Authorization", token);
				rs.setStatus(200);
			} else {
				rs.sendError(404);
			}
		} catch (UserNotFoundException e) {
			rs.sendError(404);
		}

	}


	// used to handle the logic for logging into the system, checks database to make sure user exists
//	private void login(HttpServletRequest request, HttpServletResponse response) throws UserNotFoundException, SQLException {
//		String json;
//		try {
//			json = request.getReader().lines().reduce((acc, curr) -> acc +curr).get();
//			ObjectMapper om = new ObjectMapper();
//			User u = om.readValue(json, User.class);
//			User actualUser = as.login(u.getUsername(),u.getPassword());
//			ObjectWriter ow = om.writer().withDefaultPrettyPrinter();
//			String jsonUser = ow.writeValueAsString(actualUser);
//			
//			// if user is not in the database, send a 401 code
//			if(actualUser == null) {
//				response.setStatus(401);
//			}
//			
//			// sets JsessionID for tracking
//			// sets a response header with the user to make check which role they are
//			else {
//				request.getSession().setAttribute("user", actualUser.getId());
//				response.setHeader("user", jsonUser);
//			}
//		} 
//		catch (IOException e) {
//			e.printStackTrace();
//		}
//	}

	@Override
	public void handlePut(HttpServletRequest rq, HttpServletResponse rs) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void handleDelete(HttpServletRequest rq, HttpServletResponse rs) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
	}

}