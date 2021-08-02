package Alan.dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import Alan.entity.Items;
import Alan.db.ConnectDatabase;
public final class ItemDao {
	Connection conn = null;
	PreparedStatement ps = null;
	ResultSet rs = null;
	
	public Items getItemById(int id) throws SQLException {
		String sql = "select * from Items where iid = ?";
		conn = ConnectDatabase.connect();
		Items item = null;
		try{
			ps = conn.prepareStatement(sql);
			ps.setInt(1, id); 
			ResultSet rs = ps.executeQuery();
		
			if(rs.next()) {
				int itemId = rs.getInt("iid");
				String itemName = rs.getString("iname");
				int itemPrice = rs.getInt("price");
				int quantity = rs.getInt("quantity");
				item= new Items(itemId, itemName, itemPrice, quantity);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			conn.close();
		}
		return item;
	}
	
	public boolean addItems(Items items) throws SQLException
	{
		boolean bool = false;
		conn = ConnectDatabase.connect();
		String sql = "INSERT INTO Items(Iname,price,quantity) VALUES(?,?,?)";
			
			try
			{
				ps = conn.prepareStatement(sql);
				ps.setString(1,items.getName());
				ps.setDouble(2,items.getPrice());
				ps.setInt(3,items.getQuantity());
				
				int rs = ps.executeUpdate();
				if (rs > 0)
				{
					bool = true;
				}
			} catch (SQLException e)
			{
				e.printStackTrace();
			}
			finally {
				conn.close();
			}
	 return bool;
	}
	
	public boolean DeleteItems(int id) throws SQLException
	{
		boolean bool = false;
		conn = ConnectDatabase.connect();
		String sql = "Delete From Items Where iid =?";
			
			try
			{
				ps = conn.prepareStatement(sql);
				ps.setInt(1,id);
				
				int rs = ps.executeUpdate();
				if (rs > 0)
				{
					bool = true;
				}
			} catch (SQLException e)
			{
				e.printStackTrace();
			}
			finally {
				conn.close();
			}
	 return bool;
	}
	
	public ArrayList<Items> DisplayItem() throws SQLException {
		conn = ConnectDatabase.connect();
		String sql = "Select * From Items";
		ArrayList<Items> lst = new ArrayList<Items>();
		try
		{
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next()) {
				int iid = rs.getInt(1);
				String name = rs.getString(2);
				double price = rs.getDouble("price");
				int quantity = rs.getInt(4);
				Items item = new Items(iid,name,price,quantity);
				lst.add(item);
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
	
	
	public boolean UpdatePrice(int id,double price) throws SQLException {
		
		boolean bool = false;
		conn = ConnectDatabase.connect();
		String sql = "Update Items Set price=? Where iid =?";
		try
		{
			ps = conn.prepareStatement(sql);
			ps.setDouble(1,price);
			ps.setInt(2,id);
			
			int res = ps.executeUpdate();
			if (res > 0)
				bool = true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			conn.close();
		}
		return bool;
		
	}
	
public boolean UpdateQuantity(int id,int n) throws SQLException {
		
		boolean bool = false;
		conn = ConnectDatabase.connect();

		String sql = "Update Items Set Quantity=? Where iid =?";
		try
		{
			ps = conn.prepareStatement(sql);
			ps.setInt(1,n);
			ps.setInt(2,id);
			
			int res = ps.executeUpdate();
			if (res > 0)
				bool = true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			conn.close();
		}
		return bool;
		
	}
}

