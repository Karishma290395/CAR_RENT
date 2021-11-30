package car.rentingapplication;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Scanner;

public class CarIssueDao {

	static final String DB_URL = "jdbc:mysql://localhost:3306/world";
	static final String USER = "root";
	static final String PASS = "123456";

	public HashMap<String, Integer> RentalCars() {

		final String QUERY = "SELECT car_pid, car_name, car_rent_per_day FROM m_cars where car_count > 0";
		HashMap<String, Integer> carMap = new HashMap<>();
		int count = 1;

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection(DB_URL, USER, PASS);
			PreparedStatement stmt = con.prepareStatement(QUERY);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				String carName = rs.getString("car_name");
				int carRent = rs.getInt("car_rent_per_day");

				System.out.println(
						"Car Number : " + count++ + " , Car Name : " + carName + " , Car Rent : " + carRent + "/Day");
				carMap.put(carName, carRent);

			}

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return carMap;
	}

	public void insertCarTransaction(CarUserData objCarUserData) {
		final String QUERY = "INSERT INTO t_booking_car(user_id, no_of_day_taken, car_from_date, car_to_date,advance_paid,  total_charges, balance_amount,car_name) VALUE(?,?,?,?,?,?,?,?)";
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection(DB_URL, USER, PASS);
			PreparedStatement stmt = con.prepareStatement(QUERY);
			int count = 1;
			stmt.setInt(count++, objCarUserData.getUserId());
			stmt.setInt(count++, objCarUserData.getNoOfDayTaken());
			stmt.setDate(count++, new java.sql.Date(objCarUserData.getFromDate().getTime()));
			stmt.setDate(count++, new java.sql.Date(objCarUserData.getToDate().getTime()));
			stmt.setDouble(count++, objCarUserData.getAdvanceAmount());
			stmt.setDouble(count++, objCarUserData.getTotalAmount());
			stmt.setDouble(count++, objCarUserData.getBalanceAmount());
			stmt.setString(count++, objCarUserData.getCarName());
			stmt.execute();

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public void insertNumberOfCars(CarUserData objCarUserData) {
		final String QUERY = "SELECT car_count FROM m_cars WHERE car_name = ?";
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection(DB_URL, USER, PASS);
			PreparedStatement stmt = con.prepareStatement(QUERY);
			int count = 1;
			stmt.setString(count, objCarUserData.getCarName());

			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				int newCarCount = rs.getInt("car_count") - 1;
				updateCarCount(newCarCount, objCarUserData, con);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	private void updateCarCount(int newCarCount, CarUserData objCarUserData, Connection con) {
		final String QUERY = "UPDATE m_cars set car_count=? where car_name=?";
		try {
			int count = 1;
			PreparedStatement stmt = con.prepareStatement(QUERY);
			stmt.setInt(count++, newCarCount);
			stmt.setString(count++, objCarUserData.getCarName());
			stmt.execute();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void IsCarOnUserName(CarUser objCarUser,  CarUserData objCarUserData, Scanner doubleSc) {
		final String QUERY = "select car_name,balance_amount,trans_pid  from t_booking_car where user_id =?";
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection(DB_URL, USER, PASS);
			PreparedStatement stmt = con.prepareStatement(QUERY);
			int count = 1;
			stmt.setInt(count++, objCarUser.getUserpid());
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				objCarUserData.setCarName(rs.getString("car_name"));
				objCarUserData.setBalanceAmount(rs.getDouble("balance_amount"));
				objCarUserData.setTransPid(rs.getInt("trans_pid"));
				returnCars(doubleSc, objCarUserData);
				deleteCarEntry(con,objCarUserData);
				 
			} else {
				System.out.println("Sorry. No cars on your name. Please login with correct account ");
			}

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public void returnCars(Scanner doubleSc, CarUserData objCarUserData) {
		String issuedCar = objCarUserData.getCarName();
		if(issuedCar != null && !issuedCar.equals("")&& !issuedCar.equals(null)) {
		System.out.println("Car on your Name : " + issuedCar);
		while(objCarUserData.getBalanceAmount()!=0) {
			System.out.println("Your balance amount is : "+objCarUserData.getBalanceAmount()+"\nPlease pay the remaining amount ");
			double remainingPayment = doubleSc.nextDouble();
			double balanceAmount = objCarUserData.getBalanceAmount()-remainingPayment;
			objCarUserData.setBalanceAmount(balanceAmount);
			}
		}
		 System.out.println("Thank You! Car Succesfully returned. See You Soon");
	}

	public void deleteCarEntry(Connection con, CarUserData objCarUserData) {

		final String QUERY = "Delete from t_booking_car where trans_pid=?";
		int count = 1;
		try {
			PreparedStatement stmt = con.prepareStatement(QUERY);
			stmt.setInt(count++, objCarUserData.getTransPid());
			stmt.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}