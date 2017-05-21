package dao;

import java.util.Hashtable;
import java.util.Vector;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import components.User;

public class TableUsers extends IDataBase<User> {

	public boolean insert(User user) {
		Statement statement = null;
		try {
			statement = ConnectDB.getConnection().createStatement();
			statement.executeUpdate("INSERT INTO users (login, password, workbench_ID) VALUES ('" + user.getLogin() + "', '"
					+ user.getPassword() + "', '" + user.getWorkbench() + "')");
		} catch (SQLException e) {
			if (e.getErrorCode() == 335544665) {
				System.out.println("ѕользователь с таким логином уже существует");
			} else {
				e.printStackTrace();
			}
			return false;
		} finally {
			try {
				statement.close();
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}
		}
		return true;
	}

	public Vector<User> select(Hashtable<String, String> selectsHt) {
		Statement statement = null;
		ResultSet resultSet = null;
		String select = makeSelectString(selectsHt, "*");
		Vector<User> users = new Vector<User>();
		try {
			statement = ConnectDB.getConnection().createStatement();
			resultSet = statement.executeQuery(select);
			while (resultSet.next()) {
				User user = new User();
				user.setLogin(resultSet.getString("LOGIN"));
				user.setPassword(resultSet.getString("PASSWORD"));
				user.setUserId(resultSet.getInt("USER_ID"));
				user.setWorkbench(resultSet.getInt("WORKBENCH_ID"));
				users.addElement(user);
			}
		} catch (SQLException e) {
			if (e.getErrorCode() == 335544569) {
				System.out.println("“акого пол€ не существует, повторите выбор");
			} else {
				e.printStackTrace();
			}
			return null;
		} finally {
			try {
				if (resultSet != null)
					resultSet.close();
				statement.close();
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		}
		return users;
	}

	@SuppressWarnings("unchecked")
	public <TY> Vector<TY> select(Hashtable<String, String> selectsHt, String requiredField) {
		Statement statement = null;
		ResultSet resultSet = null;
		String select = makeSelectString(selectsHt, requiredField);
		Vector<TY> objects = new Vector<TY>();
		try {
			statement = ConnectDB.getConnection().createStatement();
			resultSet = statement.executeQuery(select);
			while (resultSet.next()) {
				TY object;
				if (requiredField.toUpperCase().equals("LOGIN"))
					object = (TY) resultSet.getString("LOGIN");
				else if (requiredField.toUpperCase().equals("PASSWORD"))
					object = (TY) (Integer) resultSet.getInt("PASSWORD");
				else if (requiredField.toUpperCase().equals("USER_ID"))
					object = (TY) (Integer) resultSet.getInt("USER_ID");
				else if (requiredField.toUpperCase().equals("WORKBENCH_ID"))
					object = (TY) resultSet.getString("WORKBENCH_ID");
				else {
					System.out.println("“акого пол€ не существует");
					return null;
				}
				objects.addElement(object);
			}
		} catch (SQLException e) {
			if (e.getErrorCode() == 335544569) {
				System.out.println("“акого пол€ не существует, повторите выбор");
			} else {
				e.printStackTrace();
			}
			return null;
		} finally {
			try {
				if (resultSet != null)
					resultSet.close();
				statement.close();
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		}
		return objects;
	}

}
