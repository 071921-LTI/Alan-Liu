package Alan.dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import Alan.db.ConnectDatabase;
import Alan.entity.Customers;
import org.apache.logging.log4j.LogManager; 
import org.apache.logging.log4j.Logger;
public final class CustomerDao {
	Connection conn = null;
	PreparedStatement ps = null;
	ResultSet rs = null;
	private static Logger log = LogManager.getRootLogger();
	
	public ArrayList<Customers> CustomerLogin(String CName) throws SQLException {
		ArrayList<Customers> CustomerInfo = new ArrayList<Customers>();
		conn = ConnectDatabase.connect();
		String sql = "SELECT CID,CPASSWORD FROM Customers WHERE CNAME=?";
				try
				{
					ps = conn.prepareStatement(sql);
					ps.setString(1,CName);
					rs = ps.executeQuery();
					while (rs.next())
					{
						String cPassWord = rs.getString("cpassword");
						int EId = rs.getInt("cid");
						Customers customer = new Customers(EId,cPassWord); 
						CustomerInfo.add(customer);						
					}
				} catch (SQLException e1)
				{
					log.error("Failed to find The Customer Name " + CName);
					e1.printStackTrace();
				}
				finally {
					conn.close();
				}
	 return CustomerInfo;
	}
	
	public boolean AddCustomer(Customers CName) throws SQLException {
		boolean bool = false;
		conn = ConnectDatabase.connect();
		String sql = "INSERT INTO Customers(CNAME,CPASSWORD) VALUES(?,?)";
				try
				{
					ps = conn.prepareStatement(sql);
					ps.setString(1,CName.getCname());
					ps.setString(2, CName.getCpassword());
					int rs = ps.executeUpdate();
					if (rs >0) {
						bool = true;
					}
				} catch (SQLException e1)
				{
					log.error("Failed to add Customer with Name " + CName);
					e1.printStackTrace();
				}
				finally {
					conn.close();
				}
	 return bool;
	}
}
