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

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.revature.exceptions.UserNotFoundException;
import com.revature.models.Reimbursement;
import com.revature.models.User;
import com.revature.services.ReimbursementService;
import com.revature.services.ReimbursementServiceImpl;
import com.revature.services.UserService;
import com.revature.services.UserServiceImpl;

public class ReimbursementDelegate implements Delegatable {
	ReimbursementService res = new ReimbursementServiceImpl();
	UserService us = new UserServiceImpl();
	private static Logger log = LogManager.getRootLogger();
	
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
	public void handleGet(HttpServletRequest rq, HttpServletResponse rs) throws ServletException, IOException, SQLException, UserNotFoundException {
		String actualURL = rq.getRequestURI().substring(rq.getContextPath().length());
		switch(actualURL) {
		case "/reimbursement/all":
			String json;
			List<Reimbursement> allReimb = res.getAllReimb();
			
			try {
				ObjectMapper om = new ObjectMapper();
				ObjectWriter ow = om.writer().withDefaultPrettyPrinter();
				json = ow.writeValueAsString(allReimb);
				PrintWriter writer = rs.getWriter();
				writer.write(json);
				rs.setStatus(200);
			}
			catch(IOException e) {
				rs.setStatus(401);
				log.error("Failed to find All Reimbursements " + allReimb);
				e.printStackTrace();
			}
			break;
			
		case "/reimbursement/user":
			String json1;
//			List<Reimbursement> userReimb = res.getByUser((Integer)rq.getSession().getAttribute("userId"));
//			String authToken = rq.getHeader("Authorization");
//			String token = rq.getHeader("Authorization");
//			String username = token.split(":")[0];
//			User user = us.getUserByUsername(username);
//			InputStream request = rq.getInputStream();
			// Converts the request body into a User.class object
//			User user = new ObjectMapper().readValue(request, User.class);
			
			int userID = Integer.parseInt(rq.getParameter("userId"));
			List<Reimbursement> userReimb = res.getByUser(userID);
			try {
				ObjectMapper om = new ObjectMapper();
				ObjectWriter ow = om.writer().withDefaultPrettyPrinter();
				json1 = ow.writeValueAsString(userReimb);
				PrintWriter writer = rs.getWriter();
				writer.write(json1);
				rs.setStatus(200);
			}
			catch(IOException e) {
				rs.setStatus(401);
				log.error("Failed to find the User Reimbursements " + userReimb);
				e.printStackTrace();
			}
			break;
		case "/reimbursement/pending":
			String json2;
			List<Reimbursement> Reimbs = res.getReimbursementByPending();
			
			try {
				ObjectMapper om = new ObjectMapper();
				ObjectWriter ow = om.writer().withDefaultPrettyPrinter();
				json2 = ow.writeValueAsString(Reimbs);
				PrintWriter writer = rs.getWriter();
				writer.write(json2);
				rs.setStatus(200);
			}
			catch(IOException e) {
				rs.setStatus(401);
				log.error("Failed to Get Pending Reimbursements " + Reimbs);
				e.printStackTrace();
			}
			break;
		case "/reimbursement/resolved":
			String json3;
			List<Reimbursement> r = res.getReimbursementByResolved();
			
			try {
				ObjectMapper om = new ObjectMapper();
				ObjectWriter ow = om.writer().withDefaultPrettyPrinter();
				json3 = ow.writeValueAsString(r);
				PrintWriter writer = rs.getWriter();
				writer.write(json3);
				rs.setStatus(200);
			}
			catch(IOException e) {
				rs.setStatus(401);
				log.error("Failed to Get Resolved Reimbursements " + r);
				e.printStackTrace();
			}
			break;
		}
	}


	@Override
	public void handlePost(HttpServletRequest rq, HttpServletResponse rs) throws ServletException, IOException, SQLException {
		
		String actualURL = rq.getRequestURI().substring(rq.getContextPath().length());
		switch(actualURL) {
		case "/reimbursement/add":
			String json;
			try {
				json = rq.getReader().lines().reduce((acc, curr) -> acc + curr).get();
				ObjectMapper om = new ObjectMapper();
				Reimbursement r = om.readValue(json, Reimbursement.class);
				int authorID = r.getAuthorId();
				r.setAmount(r.getAmount());
				r.setAuthorId(authorID);
				int newReimbursement = res.add(r);
				
				r.setId(newReimbursement);
				
				if(newReimbursement == 0) {
					rs.setStatus(401);
					log.error("Failed to Add Reimbursement " + r);
				}
				else {
					rs.setStatus(201);
				}
			}
			catch (IOException e) {
				rs.setStatus(401);
				e.printStackTrace();
			}
			break;
		case "/reimbursement/approve":
			String json1;
			try {
				json1 = rq.getReader().lines().reduce((acc, curr) -> acc + curr).get();			
				ObjectMapper om = new ObjectMapper();
				Reimbursement r = om.readValue(json1, Reimbursement.class);
				boolean success = res.statusChange(r, "approve", r.getResolverId());
				
				if(success == false) {
					rs.setStatus(401);
					log.error("Failed to Approve the Reimbursement " + r);
				}
				else {
					rs.setStatus(200);
				}
			}
			catch(IOException e) {
				e.printStackTrace();
			}
			break;
		case "/reimbursement/deny":
			String json2;
			try {
				json2 = rq.getReader().lines().reduce((acc, curr) -> acc + curr).get();
				ObjectMapper om = new ObjectMapper();
				Reimbursement r = om.readValue(json2, Reimbursement.class);
				boolean success = res.statusChange(r, "deny", r.getResolverId());
				
				if(success == false) {
					rs.setStatus(401);
					log.error("Failed to Deny the Reimbursements " + r);
				}
				else {
					rs.setStatus(200);
				}
			}
			catch(IOException e) {
				rs.setStatus(401);
				e.printStackTrace();
			}
			break;
		
		}
	}

	@Override
	public void handleDelete(HttpServletRequest rq, HttpServletResponse rs) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void handlePut(HttpServletRequest rq, HttpServletResponse rs) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
	}
}