package car.rentingapplication;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CarUsersLoginDao {

	static final String DB_URL = "jdbc:mysql://localhost:3306/world";
	static final String USER = "root";
	static final String PASS = "123456";
	static final String QUERY = " SELECT user_pid FROM carUsers where user_name=? and user_password=?";

	public boolean loginExtistingUser(String extUserName, String extUserPassword,CarUser objCarUser) {
		int userPid=0;
		boolean isUserLoggedIn = false;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection(DB_URL, USER, PASS);
			PreparedStatement stmt = con.prepareStatement(QUERY);

			int count = 1;
			stmt.setString(count++, extUserName);
			stmt.setString(count++, extUserPassword);

			ResultSet rs = stmt.executeQuery();
			

			if (rs.next()) {
				isUserLoggedIn= true;
				System.out.println("Congratulations!!! You are succesfully logged in");
				objCarUser.setUserpid(rs.getInt("user_pid"));
				
			} else {
				System.out.println("Incorrect User Name or Password");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return isUserLoggedIn;
	}

}
