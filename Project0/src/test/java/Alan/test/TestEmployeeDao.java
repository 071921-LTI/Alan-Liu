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
public class TestEmployeeDao {
	@Mock
	private EmployeeDao Edao;
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
	public void TestEmployeeLogins() throws SQLException {
		ArrayList<Employee> Expected = new ArrayList<>();
		Employee emp = new Employee(1,"Alan","7462");
		Expected.add(emp);
		
		ArrayList<Employee> Actual = new ArrayList<>();
		Actual.add(emp);
		Mockito.when(Edao.EmployeeLogin("Alan")).thenReturn(Actual);
		Actual = Edao.EmployeeLogin("Alan");
		
		assertEquals(Expected, Actual);
	}
	
	@Test
	public void TestAddEmployee() throws SQLException {
		boolean expected = true;
		boolean actual = false;
		
		Employee employee = new Employee (1, "Alan", "7462");
		Mockito.when(Edao.AddEmployee(employee)).thenReturn(true);
		actual = Edao.AddEmployee(employee);
		assertEquals(expected, actual);
	}
	
	@Test
	public void TestDeleteEmployee() throws SQLException {
		boolean expected = true;
		boolean actual = false;
		
		Employee employee = new Employee (1, "Alan", "7462");
		Edao.AddEmployee(employee);
		Mockito.when(Edao.FireEmployee(employee.getEname())).thenReturn(true);
		actual = Edao.FireEmployee(employee.getEname());
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