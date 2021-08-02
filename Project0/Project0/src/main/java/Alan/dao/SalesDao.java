package Alan.dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import Alan.db.ConnectDatabase;
import Alan.entity.Items;
import Alan.entity.Sales;

public class SalesDao {
	Connection        conn  = null;
	PreparedStatement ps = null;
	ResultSet 		  rs    = null;
	
	public boolean AddSales(int iid,int cid, int n) throws SQLException
	{
		boolean bool = false;
		conn = ConnectDatabase.connect();
		String sql = "INSERT INTO Sales(IID,CID,SNUM) VALUES(?,?,?)";
		
		try
		{
			ps = conn.prepareStatement(sql);
			ps.setInt(1,iid);
			ps.setInt(2,cid);
			ps.setInt(3,n);
			
			int rs = ps.executeUpdate();
			if (rs > 0)
			{
				bool = true;
			}
		} catch (SQLException e)
		{
			e.printStackTrace();
		}finally
		{
			conn.close();
		}
		return bool;
	}
	
	public ArrayList<Sales> ViewMyPurchase(int id) throws SQLException{
		
		conn = ConnectDatabase.connect();
		ArrayList<Sales> lst = new ArrayList<Sales>();
		String sql = "Select i.Iname , s.snum\r\n"
				+ "From Items i,Sales s,Customers c\r\n"
				+ "Where c.cid = s.cid AND s.iid = i.iid And c.cid = ?" ;
		
		try
		{
			ps = conn.prepareStatement(sql);
			ps.setInt(1,id);
			rs = ps.executeQuery();
			while (rs.next()) {
				String iname = rs.getString(1);
				int n = rs.getInt(2);
				Sales sale = new Sales(iname,n);
				lst.add(sale);
			}
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		finally {
			conn.close();
		}
		return lst;
	}
	public ArrayList<Sales> AllPayments() throws SQLException{
		conn = ConnectDatabase.connect();
		String sql = "Select s.sid, c.cid,i.iname,s.snum,s.sdate\r\n"
				+ "From Sales s,Items i,Customers c\r\n"
				+ "Where c.cid = s.cid AND s.iid = i.iid;";
		ArrayList<Sales> lst = new ArrayList<Sales>();
		try
		{
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next()) {
				int sid = rs.getInt(1);
				int cid = rs.getInt(2);
				String iname = rs.getString(3);
				int quantity = rs.getInt(4);
				Sales sale = new Sales(sid,cid,quantity,iname);
				lst.add(sale);
			}
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		finally {
			conn.close();
		}
		return lst;
	}
	
	public ArrayList<Sales> WeeklyPayments() throws SQLException
	{
		ArrayList<Sales> lst = new ArrayList<Sales>(); 
		conn = ConnectDatabase.connect();

		String sql = "Select i.iname, sum(i.price*s.snum)\r\n"
				+ "From sales s,items i\r\n"
				+ "Where s.iid = i.iid And TRUNC(DATE_PART('day', CURRENT_DATE - sdate::timestamp)) < 7\r\n"
				+ "group by i.iname"; 
		try
		{
			ps = conn.prepareStatement(sql);
			rs 	  = ps.executeQuery();
			while (rs.next())
			{
				String iName = rs.getString(1);
				double Price = rs.getDouble(2);;
				
				Sales sale = new Sales(iName,Price);
				lst.add(sale);						
			}
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}finally {
			conn.close();
		}
		return lst;

	}
}
