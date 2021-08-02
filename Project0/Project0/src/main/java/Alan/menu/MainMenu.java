package Alan.menu;
import java.util.Scanner;

import Alan.dao.EmployeeDao;
import Alan.dao.SalesDao;

import java.sql.SQLException;
import java.util.ArrayList;

import Alan.entity.Customers;
import Alan.entity.Employee;
import Alan.dao.CustomerDao;
import Alan.Services.Services;

public class MainMenu {
	public static void main(String[] args) throws SQLException {
		MainMenu.mainMenu();
	}

	public static void mainMenu() throws SQLException{
		System.out.println("Please Enter The Option Below:");
		System.out.println("Enter 1 for Customer");
		System.out.println("Enter 2 for Employee");
		System.out.println("Enter 3 for Manager");
		System.out.println("Enter 4 to Exit");
		Scanner sc = new Scanner(System.in);
		int n = sc.nextInt();
		while (n != 4) {
			switch(n) {
			case 1:
				System.out.println("");
				System.out.println("Enter 1 to Login");
				System.out.println("Enter 2 to Register");
				System.out.println("Enter other to Return to MainMenu");
				int x = sc.nextInt();
				//Customer Login
				if (x == 1) {
					System.out.println("");
					System.out.println("Please Enter the Username:");
					sc.nextLine();
					String username = sc.nextLine();
					System.out.println("Please Enter the Password:");
					String password = sc.nextLine();
					ArrayList<Customers> CustomerInfo = new CustomerDao().CustomerLogin(username);
					Customers customer = CustomerInfo.get(0);
					if (password.equals(customer.getCpassword())) {
						System.out.println("Logged in!");
						CustomerOperations(customer);
					}
					else
						System.out.println("Wrong Username or Password");
					
				}
				//Customer Register
				else if(x==2) {
					Services.AddCustomer();
				}
				else {
					mainMenu();
				}
				break;
			case 2:
				//Employee Login
				System.out.println("");
				System.out.println("Please Enter Username:");
				sc.nextLine();
				String username = sc.nextLine();
				System.out.println("Please Enter Password:");
				String password = sc.nextLine();
				ArrayList<Employee> EmployeeInfo = new EmployeeDao().EmployeeLogin(username);
				Employee Employee = EmployeeInfo.get(0);
				if (password.equals(Employee.getEpassword())){
					System.out.println("Logged in!");
					EmployerOperations();
				}
				else
					System.out.println("Wrong Username or Password");
				
				break;
			case 3:
				System.out.println("");
				System.out.println("Please Enter Username:");
				sc.nextLine();
				String username2 = sc.nextLine();
				System.out.println("Please Enter Password:");
				String password2 = sc.nextLine();
				if (username2.equals("Admin") & password2.equals("admin")){
					System.out.println("Logged in!");
					ManagerOperations();
				}
				else
					System.out.println("Wrong Username or Password");
				
				break;

			case 4:
				System.exit(1);
				break;
			}
		}
		sc.close();
	}
	
	
	public static void CustomerOperations(Customers customer) throws SQLException {
		System.out.println("");
		System.out.println("1:Display Items in the shop");
		System.out.println("2:Purchase an Item");
		System.out.println("3:My Purchase History");
		Scanner sc = new Scanner(System.in);
		int n = sc.nextInt();
		switch(n) {
		case 1:
			Services.DisplayItems(customer);
			break;
		case 2:
			Services.Purchase(customer);
			break;
		case 3:
			Services.MyPurchase(customer);
			break;
		}
		sc.close();
	}
	
	public static void EmployerOperations() throws SQLException {
		System.out.println("");
		System.out.println("1:Add Item to the shop");
		System.out.println("2:Delete an Item");
		System.out.println("3:Update Item");
		System.out.println("4:Display Items");
		System.out.println("5:Vew All Payments");
		Scanner sc = new Scanner(System.in);
		int n = sc.nextInt();
		switch(n) {
			case 1:
				Services.AddItem();
				break;
			case 2:
				Services.DeleteItem();
				break;
			case 3:
				Services.UpdateItem();
				break;
			case 4:
				Services.DisplayItems2();
				break;
			case 5:
				Services.ViewPayments();
		}
		sc.close();
	}
	public static void ManagerOperations() throws SQLException {
		System.out.println("");
		System.out.println("1:Register an Employee Acount");
		System.out.println("2:Fire an Employee");
		System.out.println("3:Calculate weekly Payment");
		Scanner sc = new Scanner(System.in);
		int n = sc.nextInt();
		switch(n) {
		case 1:
			Services.AddEmployee();
			break;
		case 2:
			Services.FireEmployee();
			break;
		case 3:
			Services.CalculatePayment();
		}
		sc.close();
	}
		
		
		
}
