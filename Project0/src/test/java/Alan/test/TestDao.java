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
public class TestDao {
	@Mock
	private ItemDao Idao;
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
	public void getByIdExists() throws SQLException {
	
		Items expected = new Items(1, "Apple", 2.5, 10);
		Items actualResult = new Items (1, "Apple", 2.5, 10);
		Mockito.when(Idao.getItemById(1)).thenReturn(actualResult);
		actualResult = Idao.getItemById(1);
		assertEquals(expected, actualResult);
	}
	
	@Test
	public void getNonExistantItemByIdTest() throws SQLException {
		Items expected = new Items(1, "Apple", 2.5, 10);
		Items actualResult = new Items (2, "Banana", 3.2, 20);
		Mockito.when(Idao.getItemById(1)).thenReturn(actualResult);
		actualResult = Idao.getItemById(1);
		assertNotEquals(expected, actualResult);
	}
	
	@Test
	public void TestAddItems() throws SQLException {
		
		boolean expected = true;
		Items item = new Items (1, "Apple", 2.5, 10);
		Mockito.when(Idao.addItems(item)).thenReturn(true);
		boolean actualResult = Idao.addItems(item);
		assertEquals(expected, actualResult);
	}
	
	@Test
	public void TestDeleteItems() throws SQLException {
		
		boolean expected = true;
		Items item = new Items (1, "Apple", 2.5, 10);
		Idao.addItems(item);
		Mockito.when(Idao.DeleteItems(1)).thenReturn(true);
		boolean actualResult = Idao.DeleteItems(1);
		assertEquals(expected, actualResult);
	}
	
	@Test
	public void TestDisplayItems() throws SQLException {
		Items item1 = new Items(1, "Apple", 2.5, 10);
		Items item2 = new Items (2, "Banana", 3.2, 20);
		ArrayList<Items> ExpectedItems = new ArrayList<>();
		ExpectedItems.add(item1);
		ExpectedItems.add(item2);
		
		ArrayList<Items> AcualItems = new ArrayList<>();
		AcualItems.add(item1);
		AcualItems.add(item2);
		Mockito.when(Idao.DisplayItem()).thenReturn(AcualItems);
		AcualItems = Idao.DisplayItem();
		assertEquals(ExpectedItems, AcualItems);
	}
	
	@Test
	public void TestUpdateItemsPrice() throws SQLException {
		
		boolean expected = true;
		boolean actual = false;
		Items item = new Items (1, "Apple", 2.5, 10);
		Idao.addItems(item);
		Mockito.when(Idao.UpdatePrice(1,3.2)).thenReturn(true);
		actual = Idao.UpdatePrice(1,3.2);
		assertEquals(expected, actual);
	}
	
	@Test
	public void TestUpdateItemsQuantity() throws SQLException {
		
		boolean expected = true;
		boolean actual = false;
		Items item = new Items (1, "Apple", 2.5, 10);
		Idao.addItems(item);
		Mockito.when(Idao.UpdateQuantity(1,20)).thenReturn(true);
		actual = Idao.UpdateQuantity(1,20);
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
