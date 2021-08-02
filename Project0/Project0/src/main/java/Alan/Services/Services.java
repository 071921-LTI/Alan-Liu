package Alan.Services;

import java.util.Scanner;

import Alan.dao.CustomerDao;
import Alan.dao.EmployeeDao;
import Alan.dao.ItemDao;
import Alan.dao.SalesDao;
import Alan.entity.Customers;
import Alan.entity.Employee;
import Alan.entity.Items;
import Alan.entity.Sales;
import Alan.menu.MainMenu;

import java.sql.SQLException;
import java.util.ArrayList;
public class Services {

	public static void AddItem() throws SQLException {
		System.out.println("Please Enter Item Name:");
		Scanner sc = new Scanner(System.in);
		String item = sc.nextLine();
		System.out.println("Please Enter Item Price:");
		double itemPrice = sc.nextDouble();
		System.out.println("Please Enter Item Quantity");
		int ItemNumber = sc.nextInt();
		Items obj = new Items(item,itemPrice,ItemNumber);
		boolean bool = new ItemDao().addItems(obj);
		if (bool)
			System.out.println("Item Added!");
		else
			System.out.println("Fail to Add Item");
		MainMenu.EmployerOperations();
	}
	
	public static void DeleteItem() throws SQLException {
		System.out.println("Please Enter Item id");
		Scanner sc = new Scanner(System.in);
		int id = sc.nextInt();
		boolean bool = new ItemDao().DeleteItems(id);
		if (bool)
			System.out.println("Item Deleted!");
		else
			System.out.println("Fail to Delete Item");
		MainMenu.EmployerOperations();
		
	}
	
	
	public static void DisplayItems(Customers customer) throws SQLException {
		ArrayList<Items> lst = new ItemDao().DisplayItem();
		int length = lst.size();
		System.out.println("\tProduct ID\tProduct Name\t Product Price\t Product Quantity");
		for (int i = 0; i < length;i++) {
			Items item = lst.get(i);
			System.out.print("\t"+item.getId()+"\t\t"+item.getName()+"\t\t"+" $"+item.getPrice()+"\t\t "+item.getQuantity()+"\n");
		}
			MainMenu.CustomerOperations(customer);
	}
	
	public static void DisplayItems2() throws SQLException {
		ArrayList<Items> lst = new ItemDao().DisplayItem();
		int length = lst.size();
		System.out.println("\tProduct ID\tProduct Name\t Product Price\t Product Quantity");
		for (int i = 0; i < length;i++) {
			Items item = lst.get(i);
			System.out.print("\t"+item.getId()+"\t\t"+item.getName()+"\t\t"+" $"+item.getPrice()+"\t\t "+item.getQuantity()+"\n");
		}

		MainMenu.EmployerOperations();
	}
	
	
	
	public static void Purchase(Customers customer) throws SQLException {
		System.out.println("Please Enter Item ID");
		Scanner sc = new Scanner(System.in);
		int id = sc.nextInt();
		System.out.println("How many do you want to buy?");
		int n = sc.nextInt();
		boolean bool3 = false;
		Items item = new ItemDao().getItemById(id);
		int quantity = item.getQuantity();
		if (n>quantity) {
			System.out.println("Quantity Exceeded Inventory");
			Purchase(customer);
		}
		
		boolean bool = new ItemDao().UpdateQuantity(item.getId(),quantity-n);
		boolean bool2 = new SalesDao().AddSales(item.getId(),customer.getCid(),n);
		
		if ((bool&bool2)||bool3) {
			System.out.println("Purchased!");
			MainMenu.CustomerOperations(customer);
		}
		else {
			System.out.println("Fail to purchase");
			MainMenu.CustomerOperations(customer);
		}
	}
	
	public static void MyPurchase(Customers customer) throws SQLException {
		int id = customer.getCid();
		ArrayList<Sales> lst = new SalesDao().ViewMyPurchase(id);
		int length = lst.size();
		System.out.println("\t\tMy Purchase History\n");
		System.out.println("\t Product Name\t\t Product Quantity");
		for (int i = 0; i < length;i++) {
			Sales sale = lst.get(i);
			System.out.println("\t    "+sale.getsName()+"\t\t\t"+sale.getsNum());
			
		}
			MainMenu.CustomerOperations(customer);
	}
	
	public static void UpdateItem() throws SQLException {
		System.out.println("Please Enter Item ID");
		Scanner sc = new Scanner(System.in);
		int id = sc.nextInt();
		System.out.println("1:Change Item Price");
		System.out.println("2:Change Item Quantity");
		int n = sc.nextInt();
		switch(n) {
		case 1:
			System.out.println("Please Enter New Item Price");
			double price = sc.nextDouble();
			boolean bool = new ItemDao().UpdatePrice(id,price);
			if (bool) {
				System.out.println("Price Updated!");
				MainMenu.EmployerOperations();
			}
			else {
				System.out.println("Price Update Failed");
				MainMenu.EmployerOperations();
			}
		case 2:
			System.out.println("Please Enter New Item Quantity");
			int quantity = sc.nextInt();
			boolean bool2 = new ItemDao().UpdateQuantity(id,quantity);
			if (bool2) {
				System.out.println("Quantity Updated!");
				MainMenu.EmployerOperations();
			}
			else {
				System.out.println("Quantity Update Failed");
				MainMenu.EmployerOperations();
			}
		}
	}
	
	public static void AddEmployee() throws SQLException {
		System.out.println("Please Enter New Employee Name (FirstName_LastName):");
		Scanner sc = new Scanner(System.in);
		String name = sc.nextLine();
		System.out.println("Please Enter New Employee Password");
		String pass = sc.nextLine();
		Employee employee = new Employee(name,pass);
		boolean bool = new EmployeeDao().AddEmployee(employee);
		if(bool) {
			System.out.println("Employee Added!");
			MainMenu.ManagerOperations();
		}
		else {
			System.out.println("Add Employee Failed");
			MainMenu.ManagerOperations();
		}
		
	}
	
	public static void FireEmployee() throws SQLException {
		System.out.println("Please Enter Employee Name");
		Scanner sc = new Scanner(System.in);
		String name = sc.nextLine();
		boolean bool = new EmployeeDao().FireEmployee(name);
		if(bool) {
			System.out.println("Employee Fired!");
			MainMenu.ManagerOperations();
		}
		else {
			System.out.println("Failed to Fire Employee");
			MainMenu.ManagerOperations();
		}
	}
	
	public static void AddCustomer() throws SQLException {
		System.out.println("Please Enter Your Name (FirstName_LastName):");
		Scanner sc = new Scanner(System.in);
		String name = sc.nextLine();
		System.out.println("Please Enter Your Password");
		String pass = sc.nextLine();
		Customers customer = new Customers(name,pass);
		boolean bool = new CustomerDao().AddCustomer(customer);
		if(bool) {
			System.out.println("Registed!");
			MainMenu.mainMenu();
		}
		else {
			System.out.println("Failed to Register");
			MainMenu.mainMenu();
		}
	}
	
	public static void ViewPayments() throws SQLException{
		ArrayList<Sales> lst = new SalesDao().AllPayments();
		int length = lst.size();
		System.out.println("\tSales ID\tCustomer ID\t Product Name\t Product Quantity");
		for (int i = 0; i < length;i++) {
			Sales sale = lst.get(i);
			System.out.print("\t   "+sale.getsId()+"\t\t   "+sale.getcId()+"\t\t   "+sale.getsName()+"\t\t "+sale.getsNum()+"\n");
		}
			MainMenu.EmployerOperations();
	}
	
	public static void CalculatePayment() throws SQLException{
		ArrayList<Sales> lst = new SalesDao().WeeklyPayments();
		int length = lst.size();
		System.out.println("\tProduct Name\t Weekly Total");
		for (int i = 0; i < length;i++) {
			Sales sale = lst.get(i);
			System.out.print("\t" + sale.getIname()+"\t\t $"+sale.getSsum()+"\n");
		}
		MainMenu.ManagerOperations();
		}
}
