package util.DBConnetions;

import java.sql.*;
//--------------------------------------
//MySQL connection
//--------------------------------------
public class MySQL_Util {
	static Connection con = null;
	
	public static Connection getConnection() {
		String dbName = "soccerdata";
		String username = "user1";
		String password = "1q2w3e4r";
		if (con != null)
			return con;
		else {
			try {
				Class.forName("com.mysql.jdbc.Driver");
				con = DriverManager.getConnection("jdbc:mysql://localhost:3306/" + dbName, username, "");
				System.out.println("MySQL opened");
			} catch (Exception e) {
				e.printStackTrace();
			}
			return con;
		}
	}
	
}                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                               
