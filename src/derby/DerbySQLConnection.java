package derby;

import java.sql.SQLException;
import java.util.Properties;

import jaicore.basic.SQLAdapter;

public class DerbySQLConnection {

	public static void main(String[] args) throws SQLException {
		
		Properties properties = new Properties();
		
		SQLAdapter adapter = new SQLAdapter("derby", "localhost", "derbyDB", "derbyDB", "derbyDB;create=true", properties);
		
		adapter.getResultsOfQuery("SELECT * FROM users");
		
	}
}
