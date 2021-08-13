package com.revature.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.revature.models.Reimbursement;
import com.revature.util.ConnectionUtil;

public class ReimbursementPostgres implements ReimbursementDao {
	
	Connection conn = null;
	PreparedStatement ps = null;
	ResultSet rs = null;
	
	private Reimbursement getReimbursementFromResultSet(ResultSet rs) throws SQLException {
		
		// gets all fields from the table
		int id = rs.getInt("reimb_id");
		double amount = rs.getDouble("reimb_amount");
		Timestamp submitted = rs.getTimestamp("reimb_submitted");
		Timestamp resolved = rs.getTimestamp("reimb_resolved");
		String description = rs.getString("reimb_description");
		int author = rs.getInt("reimb_author");
		int resolver = rs.getInt("reimb_resolver");
		int statusId = rs.getInt("reimb_status_id");
		int typeId = rs.getInt("reimb_type_id");
		
		return new Reimbursement(id, amount, submitted, resolved, description, author, resolver, statusId, typeId);
	}

	
	@Override
	public Reimbursement getReimbursementById(int id) throws SQLException {
		String sql = "SELECT * FROM ers_reimbursement WHERE reimb_id = ?";
		try{
			conn = ConnectionUtil.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setInt(1, id);
			rs = ps.executeQuery();
			
			if(rs.next()) {
				return getReimbursementFromResultSet(rs);
			}	
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}finally {
			conn.close();
		}
		return null;
	}
	
	@Override
	public List<Reimbursement> getReimbursementByUser(int id) throws SQLException {
		String sql = "SELECT * FROM ers_reimbursement WHERE reimb_author = ?";
		List<Reimbursement> Reimbursements = new ArrayList<>();
		try {
			conn = ConnectionUtil.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setInt(1, id);
			rs = ps.executeQuery();
			
			while (rs.next()) {
				Reimbursement r= getReimbursementFromResultSet(rs);
				Reimbursements.add(r);
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
		}finally {
			conn.close();
		}
		return Reimbursements;
	}

	@Override
	public List<Reimbursement> getAllReimbursements() throws SQLException {
		String sql = "SELECT * FROM ers_reimbursement";
		List<Reimbursement> reimbursements = new ArrayList<>();
		try {
			conn = ConnectionUtil.getConnection();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			
			while (rs.next()) {
				Reimbursement r = getReimbursementFromResultSet(rs);
				reimbursements.add(r);
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
		}finally {
			conn.close();
		}
		return reimbursements;
	}



	@Override
	public int addReimbursement(Reimbursement r) throws SQLException {
		int res = -1;
		try{
			conn = ConnectionUtil.getConnection();
			PreparedStatement ps = conn.prepareStatement("INSERT INTO ers_reimbursement (reimb_amount, reimb_submitted, reimb_resolved, reimb_description,reimb_author,reimb_status_id,reimb_type_id)"
					+ "VALUES (?,?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
			ps.setDouble(1, r.getAmount());
			ps.setTimestamp(2, r.getSubmitted());
			ps.setTimestamp(3, r.getResolved());
			ps.setString(4, r.getDescription());
			ps.setInt(5, r.getAuthorId());
			ps.setInt(6, r.getStatus());
			ps.setInt(7, r.getType());
			
			ps.executeQuery();
			
			// key used to return the new ID of the reimbursement after adding
			rs = ps.getGeneratedKeys();
			if(rs.next()) {
				System.out.println("successfully added the reimbursement");
				res = rs.getInt("reimb_id");
			}
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}finally {
			conn.close();
		}
		return res;
	}


	@Override
	public boolean approveOrDeny(String choice, int id, int resolverId) throws SQLException {
		try {
			conn = ConnectionUtil.getConnection();
			PreparedStatement ps = conn.prepareStatement("UPDATE ers_reimbursement SET reimb_status_id = ?, reimb_resolved = ?, reimb_resolver = ? WHERE reimb_id = ? AND reimb_author != " + resolverId);
			
			if(choice.equals("approve")) {
				ps.setInt(1, 2); 
				ps.setTimestamp(2, new Timestamp(System.currentTimeMillis()));
				ps.setInt(3, resolverId);
				ps.setInt(4, id);
			}
			else if(choice.equals("deny")) {
				ps.setInt(1, 3);
				ps.setTimestamp(2, new Timestamp(System.currentTimeMillis()));
				ps.setInt(3, resolverId);
				ps.setInt(4, id);
			}
			else {
				return false;
			}
			ps.executeQuery();
			return true;
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}finally {
			conn.close();
		}
		return false;
	}
}
