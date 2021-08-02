package Alan.dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import Alan.db.ConnectDatabase;
import Alan.entity.Employee;
public final class EmployeeDao {
	Connection conn = null;
	PreparedStatement ps = null;
	ResultSet rs = null;
	
	public ArrayList<Employee> EmployeeLogin(String EName) throws SQLException {
		ArrayList<Employee> EmployeeInfo = new ArrayList<Employee>();
		conn = ConnectDatabase.connect();
		String sql = "SELECT EID,EPASSWORD FROM Employee WHERE ENAME=?";
				try
				{
					ps = conn.prepareStatement(sql);
					ps.setString(1,EName);
					rs = ps.executeQuery();
					while (rs.next())
					{
						String sPassWord = rs.getString("epassword");
						int EId = rs.getInt("eid");
						Employee employee = new Employee(EId,sPassWord); 
						EmployeeInfo.add(employee);						
					}
				} catch (SQLException e1)
				{
					e1.printStackTrace();
				}
				finally {
					conn.close();
				}
	 return EmployeeInfo;
	}
	
	public boolean AddEmployee(Employee EName) throws SQLException {
		boolean bool = false;
		conn = ConnectDatabase.connect();
		String sql = "INSERT INTO Employee(ENAME,EPASSWORD) VALUES(?,?)";
				try
				{
					ps = conn.prepareStatement(sql);
					ps.setString(1,EName.getEname());
					ps.setString(2, EName.getEpassword());
					int rs = ps.executeUpdate();
					if (rs >0) {
						bool = true;
					}
				} catch (SQLException e1)
				{
					e1.printStackTrace();
				}
				finally {
					conn.close();
				}
	 return bool;
	}
	
	public boolean FireEmployee(String name) throws SQLException {
		boolean bool = false;
		conn = ConnectDatabase.connect();
		String sql = "Delete From Employee Where Ename = ?";
				try
				{
					ps = conn.prepareStatement(sql);
					ps.setString(1,name);
					int rs = ps.executeUpdate();
					if (rs >0) {
						bool = true;
					}
				} catch (SQLException e1)
				{
					e1.printStackTrace();
				}
				finally {
					conn.close();
				}
	 return bool;
	}

}
