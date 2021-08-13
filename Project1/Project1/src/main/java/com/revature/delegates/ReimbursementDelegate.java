package com.revature.delegates;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.exceptions.UserNotFoundException;
import com.revature.models.Reimbursement;
import com.revature.models.User;
import com.revature.services.ReimbursementService;
import com.revature.services.ReimbursementServiceImpl;

public class ReimbursementDelegate implements Delegatable {
	ReimbursementService res = new ReimbursementServiceImpl();
	@Override
	public void process(HttpServletRequest rq, HttpServletResponse rs) throws ServletException, IOException, SQLException {
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
	public void handleGet(HttpServletRequest rq, HttpServletResponse rs)
			throws ServletException, IOException, SQLException {
		System.out.println("In handleGet");

		
		String pathNext = (String) rq.getAttribute("pathNext");
		
		if (pathNext != null) {
			try {
				Reimbursement r = res.getReimbursementById(Integer.valueOf(pathNext));
				try (PrintWriter pw = rs.getWriter()) {
					pw.write(new ObjectMapper().writeValueAsString(r));
				}
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			List<Reimbursement> r = res.getAllReimb();
			try (PrintWriter pw = rs.getWriter()) {
				pw.write(new ObjectMapper().writeValueAsString(r));
			}
		}
	}

	@Override
	public void handlePut(HttpServletRequest rq, HttpServletResponse rs) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void handlePost(HttpServletRequest rq, HttpServletResponse rs)
			throws ServletException, IOException, SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void handleDelete(HttpServletRequest rq, HttpServletResponse rs) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
	}

}
