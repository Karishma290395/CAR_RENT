package car.rentingapplication;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CarUserDao {

	static final String DB_URL = "jdbc:mysql://localhost:3306/world";
	static final String USER = "root";
	static final String PASS = "123456";

	public boolean registerUser(CarUser carUserObj) {

		final String QUERY = "INSERT INTO carUsers(user_name, user_address, user_contact, user_password) values(?,?,?,?)";
		boolean isSuccesfullyRegister = false;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection(DB_URL, USER, PASS);
			PreparedStatement stmt = con.prepareStatement(QUERY);
			int count = 1;
			stmt.setString(count++, carUserObj.getUserName());
			stmt.setString(count++, carUserObj.getUserAddress());
			stmt.setString(count++, carUserObj.getUserContact());
			stmt.setString(count++, carUserObj.getUserPassword());
			stmt.execute();
			System.out.println("Congratulations!!! You are succesfully registered");
			isSuccesfullyRegister = true;
			carUserObj.setUserpid(getUserPid(con, carUserObj));

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return isSuccesfullyRegister;
	}

	private int getUserPid(Connection con,CarUser carUserObj) {

		final String QUERY = "select user_pid from carUsers where user_name=? and user_address=? and user_contact=? and user_password=?";
		int userPid=0;
		
		try {
		PreparedStatement stmt = con.prepareStatement(QUERY);
		int count = 1;
		stmt.setString(count++, carUserObj.getUserName());
		stmt.setString(count++, carUserObj.getUserAddress());
		stmt.setString(count++, carUserObj.getUserContact());
		stmt.setString(count++, carUserObj.getUserPassword());
		ResultSet rs = stmt.executeQuery();
		
		if(rs.next()){
			userPid= rs.getInt("user_pid");
		}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return userPid;
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
