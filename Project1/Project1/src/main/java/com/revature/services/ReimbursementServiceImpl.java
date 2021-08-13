package com.revature.services;

import java.sql.SQLException;
import java.util.List;

import com.revature.daos.ReimbursementDao;
import com.revature.daos.ReimbursementPostgres;
import com.revature.models.Reimbursement;

public class ReimbursementServiceImpl implements ReimbursementService{
	
	private ReimbursementDao rd = new ReimbursementPostgres();
	
	@Override
	public Reimbursement getReimbursementById(int id) throws SQLException {
		return rd.getReimbursementById(id);
	}
	
	@Override
	public int add(Reimbursement r) throws SQLException {
		return rd.addReimbursement(r);
	}

	@Override
	public boolean statusChange(Reimbursement r, String choice, int resolverId) throws SQLException {
		return rd.approveOrDeny(choice, r.getId(), resolverId);
	}

	@Override
	public List<Reimbursement> getAllReimb() throws SQLException {
		return rd.getAllReimbursements();
	}

	@Override
	public List<Reimbursement> getByUser(int id) throws SQLException {
		return rd.getReimbursementByUser(id);
	}
	
}
