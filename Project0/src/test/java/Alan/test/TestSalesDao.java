package Alan.test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.h2.tools.RunScript;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import Alan.db.*;
import Alan.dao.*;
import Alan.entity.*;
@ExtendWith(MockitoExtension.class)
public class TestSalesDao {
	@Mock
	private SalesDao Sdao;
	private static MockedStatic<ConnectDatabase> mockedConnectionUtil;
	private static Connection connection;
	
	@BeforeAll
	public static void init() throws SQLException {
		/*
		 * Mocking the ConnectionUtil class for the getConnectionFromEnv method to return
		 * a connection to the H2 while the mock is "open".
		 */
		mockedConnectionUtil = Mockito.mockStatic(ConnectDatabase.class);
		mockedConnectionUtil.when(ConnectDatabase::connect).then(I -> getH2Connection());
	}
	
	@AfterAll
	public static void end() {
		/*
		 * Closing resource, mocked behavior ends.
		 */
		mockedConnectionUtil.close();
	}
	
	@BeforeEach
	public void setUp() {
		try (Connection c = ConnectDatabase.connect()) {
			RunScript.execute(c, new FileReader("src/test/resources/setup.sql"));
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	@AfterEach
	public void tearDown() {
		try (Connection c = ConnectDatabase.connect()) {
			RunScript.execute(c, new FileReader("src/test/resources/teardown.sql"));
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	@Test
	public void TestAddSales() throws SQLException {
			
			boolean expected = true;
			Items item = new Items (1, "Apple", 2.5, 10);
			Mockito.when(Sdao.AddSales(item.getId(),1,item.getQuantity())).thenReturn(true);
			boolean actualResult = Sdao.AddSales(item.getId(),1,item.getQuantity());
			assertEquals(expected, actualResult);
		}
	
	@Test
	public void TestViewPurchase() throws SQLException {
		ArrayList<Sales> expected = new ArrayList<Sales>();
		Sales sale1 = new Sales(1,1,10,"Apple");
		Sales sale2 = new Sales(1,1,20,"Banana");
		expected.add(sale1);
		expected.add(sale2);

		ArrayList<Sales> actual = new ArrayList<Sales>();
		actual.add(sale1);
		actual.add(sale2);
		Mockito.when(Sdao.ViewMyPurchase(1)).thenReturn(actual);
		actual = Sdao.ViewMyPurchase(1);
		assertEquals(expected, actual);
	}
	
	@Test
	public void TestAllPayments() throws SQLException {
		ArrayList<Sales> expected = new ArrayList<Sales>();
		Sales sale1 = new Sales(1,1,10,"Apple");
		Sales sale2 = new Sales(1,1,20,"Banana");
		expected.add(sale1);
		expected.add(sale2);

		ArrayList<Sales> actual = new ArrayList<Sales>();
		actual.add(sale1);
		actual.add(sale2);
		Mockito.when(Sdao.AllPayments()).thenReturn(actual);
		actual = Sdao.AllPayments();
		assertEquals(expected, actual);
	}
	
	
	
	public static Connection getH2Connection() {
		try {
			if (connection == null || connection.isClosed()) {
				connection = DriverManager.getConnection("jdbc:h2:~/test");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return connection;
	}
}