package util.DBConnetions;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class PostgreSQL_util {

	static Connection con = null;

	public static Connection getConnection() {
		String path = "localhost:";
		String port = "5432/";
		String dbName = "postgres";
		String password = "1q2w3e4r";
		if (con != null)
			return con;
		else {
			try {
				Class.forName("org.postgresql.Driver");
				con = DriverManager.getConnection("jdbc:postgresql://" + path + port + dbName, dbName, password);
				System.out.println("PostgreSQL db connection opened");
			} catch (Exception e) {
				e.printStackTrace();
			}
			return con;
		}
	}
	
	public static void closeConnection() throws SQLException {
		System.out.println("Postgres tabase connection closed");
		con.close();
		
	}
}
