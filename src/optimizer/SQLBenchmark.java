package optimizer;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jaicore.basic.SQLAdapter;

public class SQLBenchmark {
	
	private final SQLAdapter adapterSrc;
	
	public SQLBenchmark(SQLAdapter adapterSrc) {
		super();
		this.adapterSrc = adapterSrc;
	}

	private class SQLCreateTableCommand { private String sql;

	public SQLCreateTableCommand(String sql) {
		super();
		this.sql = sql;
	}  }
	
	class SQLInsertDataCommand {
		String table;
		Map<String,String> values;
		public SQLInsertDataCommand(String table, Map<String, String> values) {
			super();
			this.table = table;
			this.values = values;
		}
	}
	
	private final Map<String,SQLCreateTableCommand> trajectoryCreate = new HashMap<>();
	private final Map<String,List<SQLInsertDataCommand>> trajectoryInsert = new HashMap<>();
	
	public void createTrajectoryForCopyingDatabase(int maxInserts) throws SQLException {
		
		int numInserts = 0;
		
		/* insert commands to create tables */
		ResultSet tableRS = adapterSrc.getResultsOfQuery("SHOW TABLES");
		while (tableRS.next()) {
			String tableName = tableRS.getString(1);
			ResultSet tableCreation = adapterSrc.getResultsOfQuery("SHOW CREATE TABLE " + tableName);
			tableCreation.next();
			String command = tableCreation.getString(2).replaceAll(" COLLATE utf8_bin", "").replaceAll("int\\(.*\\)", "INTEGER").replaceAll("ENGINE=(.*)", "").replaceAll("json", "text").replaceAll("`", "");
			trajectoryCreate.put(tableName, new SQLCreateTableCommand(command));
			System.out.println(trajectoryCreate.get(tableName));
			
			/* now add all entries of this table to the respective map */
			List<SQLInsertDataCommand> inserts = new ArrayList<>();
			trajectoryInsert.put(tableName, inserts);
			ResultSet rs = adapterSrc.getResultsOfQuery("SELECT * FROM " + tableName + " LIMIT " + maxInserts);
			int n = rs.getMetaData().getColumnCount();
			while (numInserts < maxInserts && rs.next()) {
				Map<String,String> values = new HashMap<>();
				for (int i = 0; i < n; i++) {
					values.put(rs.getMetaData().getColumnName(i + 1), rs.getString(i + 1));
				}
				inserts.add(new SQLInsertDataCommand(tableName, values));
				numInserts ++;
			}
		}
	}
	
	public void executeTrajectory(SQLAdapter adapterTarget) throws SQLException {
		
		/* now execute these commands on the target system */
		for (String tableName : trajectoryCreate.keySet()) {
			adapterTarget.update("DROP TABLE IF EXISTS " + tableName, new ArrayList<>());
			String sql = trajectoryCreate.get(tableName).sql;
			if (adapterTarget.getDriver().equals("postgresql")) {
				sql = sql.replaceAll("INT(.*)AUTO_INCREMENT", "SERIAL").replaceAll(" double ", " real ");
			}
			adapterTarget.update(sql, new ArrayList<>());
		}
		
		/* now send a queries in a parallelized fashion */
		for (String tableName : trajectoryInsert.keySet()) {
			trajectoryInsert.get(tableName).parallelStream().forEach(sql -> {
				try {
					adapterTarget.insert(tableName, sql.values);
				} catch (SQLException e) {
					System.out.println(sql.values);
					e.printStackTrace();
				}
			});
		}
	}
}
