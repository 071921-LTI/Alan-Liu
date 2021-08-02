package Alan.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
 
public class ConnectDatabase {
    public static Connection connect() {
    	Connection conn = null;
        try {
        	conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/FruitShop", "postgres", "746233077");
            }
 
        catch (SQLException e) {
            System.out.println("Connection failure.");
            e.printStackTrace();
        }
        return conn;
    }
}