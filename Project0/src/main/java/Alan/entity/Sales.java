package Alan.entity;

public final class Sales {
	private int sId;
	private int cId;
	private int sNum;
	private String sName;
	
	public Sales(int sId, int cId, int sNum, String sName) {
		this.sId = sId;
		this.cId = cId;
		this.sNum = sNum;
		this.sName = sName;
	}
	public Sales(String sName,int sNum) {
		this.sNum = sNum;
		this.sName = sName;
	}
	public int getsId() {
		return sId;
	}
	public void setsId(int sId) {
		this.sId = sId;
	}
	public int getcId() {
		return cId;
	}
	public void setcId(int cId) {
		this.cId = cId;
	}
	public int getsNum() {
		return sNum;
	}
	public void setsNum(int sNum) {
		this.sNum = sNum;
	}
	public String getsName() {
		return sName;
	}
	public void setsName(String sName) {
		this.sName = sName;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + cId;
		result = prime * result + sId;
		result = prime * result + ((sName == null) ? 0 : sName.hashCode());
		result = prime * result + sNum;
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
		Sales other = (Sales) obj;
		if (cId != other.cId)
			return false;
		if (sId != other.sId)
			return false;
		if (sName == null) {
			if (other.sName != null)
				return false;
		} else if (!sName.equals(other.sName))
			return false;
		if (sNum != other.sNum)
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "Sales [sId=" + sId + ", cId=" + cId + ", sNum=" + sNum + ", sName=" + sName + "]";
	} 
	
	
}
