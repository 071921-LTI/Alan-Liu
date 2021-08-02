package Alan.entity;

import java.io.Serializable;

public class Employee implements Serializable{
	private int Eid;
	private String Ename;
	private String Epassword;
	
	public Employee(int eid, String epassword) {
		this.Eid = eid;
		this.Epassword = epassword;
	}

	public Employee(int eid, String ename, String epassword) {
		this.Eid = eid;
		this.Ename = ename;
		this.Epassword = epassword;
	}


	public Employee(String ename, String epassword) {
		this.Ename = ename;
		this.Epassword = epassword;
	}

	public int getEid() {
		return Eid;
	}

	public void setEid(int eid) {
		Eid = eid;
	}

	public String getEname() {
		return Ename;
	}

	public void setEname(String ename) {
		Ename = ename;
	}

	public String getEpassword() {
		return Epassword;
	}

	public void setEpassword(String epassword) {
		Epassword = epassword;
	}
	
	@Override
	public String toString() {
		return "Employee [Eid=" + Eid + ", Ename=" + Ename + ", Epassword=" + Epassword + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Eid;
		result = prime * result + ((Ename == null) ? 0 : Ename.hashCode());
		result = prime * result + ((Epassword == null) ? 0 : Epassword.hashCode());
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
		Employee other = (Employee) obj;
		if (Eid != other.Eid)
			return false;
		if (Ename == null) {
			if (other.Ename != null)
				return false;
		} else if (!Ename.equals(other.Ename))
			return false;
		if (Epassword == null) {
			if (other.Epassword != null)
				return false;
		} else if (!Epassword.equals(other.Epassword))
			return false;
		return true;
	}
	
}
