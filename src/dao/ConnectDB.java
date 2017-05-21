package dao;

import java.sql.Connection;
import java.sql.DriverManager;

import java.sql.SQLException;

public abstract class ConnectDB {

	private static Connection connection = null;

	public static boolean connectToDB() {
		try {
			if (connection == null) {
				Class.forName("org.firebirdsql.jdbc.FBDriver");
				connection = DriverManager.getConnection(
						"jdbc:firebirdsql:local:d:/valik/YandexDisk/iTransition/lab5/Notes.GDB",
						"SYSDBA", "masterkey");
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public static Connection getConnection() {
		return connection;
	}

	public static boolean disconnectFromDB() {
		try {
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
}