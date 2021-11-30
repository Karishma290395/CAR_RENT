package car.rentingapplication;

import java.util.Date;

public class CarUserData {
	private String carName;
	private int carRent;
	private String userName;
	private Date fromDate;
	private Date toDate;
	private double advanceAmount;
	private double totalAmount;
	private double balanceAmount;
	private int noOfDayTaken;
	private int userId;
	private int carCount;
	private int transPid;

	public int getCarCount() {
		return carCount;
	}

	public void setCarCount(int carCount) {
		this.carCount = carCount;
	}

	public String getCarName() {
		return carName;
	}

	public void setCarName(String carName) {
		this.carName = carName;
	}

	public int getCarRent() {
		return carRent;
	}

	public void setCarRent(int carRent) {
		this.carRent = carRent;
	}


	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public String getUserName() {
		return userName;
	}

	public double getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(double totalAmount) {
		this.totalAmount = totalAmount;
	}

	public Date getFromDate() {
		return fromDate;
	}

	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}

	public Date getToDate() {
		return toDate;
	}

	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}

	public double getBalanceAmount() {
		return balanceAmount;
	}

	public void setBalanceAmount(double balanceAmount) {
		this.balanceAmount = balanceAmount;
	}
	
	public double getAdvanceAmount() {
		return advanceAmount;
	}
	public void setAdvanceAmount(double advanceAmount) {
		this.advanceAmount = advanceAmount;
	}
	
	public int getNoOfDayTaken() {
		return noOfDayTaken;
	}
	public void setNoOfDayTaken(int noOfDayTaken) {
		this.noOfDayTaken = noOfDayTaken;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getTransPid() {
		return transPid;
	}

	public void setTransPid(int transPid) {
		this.transPid = transPid;
	}
}