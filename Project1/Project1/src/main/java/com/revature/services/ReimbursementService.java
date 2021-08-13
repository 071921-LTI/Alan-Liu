package com.revature.services;

import java.sql.SQLException;
import java.util.List;

import com.revature.models.Reimbursement;

public interface ReimbursementService {
	
	public Reimbursement getReimbursementById(int id) throws SQLException;
	public int add(Reimbursement r) throws SQLException;
	public boolean statusChange(Reimbursement r, String choice, int resolverId) throws SQLException;
	public List<Reimbursement> getAllReimb() throws SQLException;
	public List<Reimbursement> getByUser(int id) throws SQLException;
}
