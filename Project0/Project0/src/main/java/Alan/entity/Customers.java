package Alan.entity;

import java.io.Serializable;

public class Customers implements Serializable {
	private int Cid;
	private String Cname;
	private String Cpassword;
	
	public Customers(int cid, String cname, String cpassword) {
		this.Cid = cid;
		this.Cname = cname;
		this.Cpassword = cpassword;
	}

	public Customers(int cid, String cpassword) {
		this.Cid = cid;
		this.Cpassword = cpassword;
	}

	public Customers(String cname, String cpassword) {
		this.Cname = cname;
		this.Cpassword = cpassword;
	}

	public int getCid() {
		return Cid;
	}

	public void setCid(int cid) {
		Cid = cid;
	}

	public String getCname() {
		return Cname;
	}

	public void setCname(String cname) {
		Cname = cname;
	}

	public String getCpassword() {
		return Cpassword;
	}

	public void setCpassword(String cpassword) {
		Cpassword = cpassword;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Cid;
		result = prime * result + ((Cname == null) ? 0 : Cname.hashCode());
		result = prime * result + ((Cpassword == null) ? 0 : Cpassword.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Customers other = (Customers) obj;
		if (Cid != other.Cid)
			return false;
		if (Cname == null) {
			if (other.Cname != null)
				return false;
		} else if (!Cname.equals(other.Cname))
			return false;
		if (Cpassword == null) {
			if (other.Cpassword != null)
				return false;
		} else if (!Cpassword.equals(other.Cpassword))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Customers [Cid=" + Cid + ", Cname=" + Cname + ", Cpassword=" + Cpassword + "]";
	}
	
	
	
	
	
	
}
