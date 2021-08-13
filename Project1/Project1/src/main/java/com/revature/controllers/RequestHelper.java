package com.revature.controllers;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.revature.delegates.AuthDelegate;
import com.revature.delegates.ReimbursementDelegate;
import com.revature.delegates.UserDelegate;
import com.revature.exceptions.UserNotFoundException;

public class RequestHelper {
	private UserDelegate ud = new UserDelegate();
	private AuthDelegate ad = new AuthDelegate();
	private ReimbursementDelegate rd = new ReimbursementDelegate();
	
	
	public void process(HttpServletRequest rq, HttpServletResponse rs) throws IOException, ServletException, SQLException, UserNotFoundException {
		/*
		 * Based on the request URL, we want to map the HttpServletRequest rq,
		 * HttpServletResponse rs to the appropriate delegate 
		 * 		- ie: 
		 * 			- if /users go to User Delegate 
		 * 				- call process 
		 * 			- if /pokemons go to Poke Delegate 
		 * 				- call process
		 */

		/*
		 *  Retrieving the path of the request to reroute request to appropriate handler
		 *  	- ie: path = "/users/1"
		 */
		String path = rq.getPathInfo();
		
		// if no path is found sent an error
		if (path != null) {
			
			/*
			 * removes the initial "/"
			 * 	- path = "users/1"
			 */
			path = path.substring(1);
			
			/*
			 * if a "/" character is present in path, split at that character and returns the first element
			 * 	- path = "users"
			 * 	- setting a request attribute to the second part of the url: 1
			 */
			if(path.indexOf("/") != -1) {
				String[] paths = path.split("/");
				path = paths[0];
				rq.setAttribute("pathNext", paths[1]);
			}
			
			switch(path) {
			case "users":
				ud.process(rq, rs);
				break;
			case "reimbursement":
				// Can add auth behavior here.
				rd.process(rq, rs);
				break;
			case "auth":
				ad.process(rq,rs);
				break;
			default:
				rs.sendError(400, "Path not supported:" + path);
			}
		} else {
			rs.sendError(400, "No path found.");
		}
	}
}
