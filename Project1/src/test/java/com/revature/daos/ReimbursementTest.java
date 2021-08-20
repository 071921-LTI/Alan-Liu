package com.revature.daos;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.revature.daos.ReimbursementDao;
import com.revature.models.Reimbursement;
import com.revature.models.ReimbursementStatus;
import com.revature.models.ReimbursementType;
import com.revature.models.User;
import com.revature.models.UserRole;
import com.revature.services.ReimbursementService;
import com.revature.services.ReimbursementServiceImpl;

@ExtendWith(MockitoExtension.class)
public class ReimbursementTest {
//	ReimbursementType rt = new ReimbursementType(1, "LODGING");
//	ReimbursementStatus rs = new ReimbursementStatus(1, "pending");
//	UserRole empl = new UserRole(2, "employee");
	User user = new User(1,"newUser", "password", "first", "last", "newuser@email.com", 2);
	Reimbursement r = new Reimbursement(500, Timestamp.valueOf(LocalDateTime.now()),"hello",1,1,1);
	
	@Mock
	ReimbursementDao rd;
	
	@InjectMocks
	ReimbursementService rs = new ReimbursementServiceImpl();
	
	@Test
	public void addReimbTrue() throws SQLException {
		Mockito.when(rd.addReimbursement(r)).thenReturn(r.getAuthorId());
		assertEquals(rs.add(r), 1);	
	}
	
	@Test
	public void addReimbFalse() throws SQLException {
		Mockito.when(rd.addReimbursement(r)).thenReturn(-1);
		assertEquals(rs.add(r), -1);
	}
	
	@Test
	public void getReimbByStatus() throws SQLException {
		List<Reimbursement> reimbs = new ArrayList<>();
		reimbs.add(r);
		Mockito.when(rd.getReimbursementByPending()).thenReturn(reimbs);
		assertEquals(reimbs, rs.getReimbursementByPending());
	}
	
	@Test
	public void getReimbByUser() throws SQLException {
		List<Reimbursement> reimbs = new ArrayList<>();
		reimbs.add(r);
		Mockito.when(rd.getReimbursementByUser(1)).thenReturn(reimbs);
		assertEquals(reimbs, rs.getByUser(1));
	}
	
	@Test
	public void getAll() throws SQLException {
		List<Reimbursement> reimbs = new ArrayList<>();
		reimbs.add(r);
		Mockito.when(rd.getAllReimbursements()).thenReturn(reimbs);
		assertEquals(reimbs, rs.getAllReimb());
	}
	
	@Test
	public void getReimb() throws SQLException {
		Mockito.when(rd.getReimbursementById(1)).thenReturn(r);
		assertEquals(r, rs.getReimbursementById(1));
	}
	
}
