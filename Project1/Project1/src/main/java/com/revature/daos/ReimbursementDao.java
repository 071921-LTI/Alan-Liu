package com.revature.daos;

import java.sql.SQLException;
import java.util.List;

import com.revature.models.Reimbursement;

public interface ReimbursementDao {
	
	Reimbursement getReimbursementById(int id) throws SQLException;
	List<Reimbursement> getAllReimbursements() throws SQLException;
	List<Reimbursement> getReimbursementByUser(int id) throws SQLException;
	int addReimbursement(Reimbursement r) throws SQLException;
	boolean approveOrDeny(String choice, int id, int resolverId) throws SQLException;
}
