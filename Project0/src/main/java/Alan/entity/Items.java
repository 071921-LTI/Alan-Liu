package Alan.entity;

public class Items {
	private int id;
	private String name;
	private double price;
	private int quantity;
	
	public Items(int id, String name, double price, int quantity) {
		this.id = id;
		this.name = name;
		this.price = price;
		this.quantity = quantity;
	}

	public Items(String name, double price, int quantity) {
		this.name = name;
		this.price = price;
		this.quantity = quantity;
	}

	public Items(int id, double price) {
		this.id = id;
		this.price = price;
	}


	public Items(int id, int quantity) {
		this.id = id;
		this.quantity = quantity;
	}

		public int getId() {
			return id;
		}
	
		public void setId(int id) {
			this.id = id;
		}
	
		public String getName() {
			return name;
		}
	
		public void setName(String name) {
			this.name = name;
		}
	
		public double getPrice() {
			return price;
		}
	
		public void setPrice(double price) {
			this.price = price;
		}
	
		public int getQuantity() {
			return quantity;
		}
	
		public void setQuantity(int quantity) {
			this.quantity = quantity;
		}
	
	
	
	
	
}
