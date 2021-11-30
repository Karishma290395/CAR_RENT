package car.rentingapplication;

public class CarUser{
	private int userpid;
	private String userName;
	private String userAddress;
	private String userContact;
	private String userPassword;
	public int getUserpid() {
		return userpid;
	}
	public void setUserpid(int userpid) {
		this.userpid = userpid;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserAddress() {
		return userAddress;
	}
	public void setUserAddress(String userAddress) {
		this.userAddress = userAddress;
	}
	public String getUserContact() {
		return userContact;
	}
	public void setUserContact(String userContact) {
		this.userContact = userContact;
	}
	public String getUserPassword() {
		return userPassword;
	}
	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}
	
	 
	public String toString() {
		return "userpid :"+this.userpid+" userName : "+this.userName+" userAddress : "+this.userAddress+" userContact : "+this.userContact+" userPassword : "+this.userPassword;
	}
}
