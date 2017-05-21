package dao;

import java.util.Date;
import java.util.Hashtable;
import java.util.Vector;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import components.Workbench;

public class TableWorkbench extends IDataBase<Workbench> {

	public boolean insert(Workbench workbench) {
		Statement statement = null;
		try {
			statement = ConnectDB.getConnection().createStatement();
			statement.executeUpdate("INSERT INTO workbenches (workbench_id, note_id) VALUES ('"
					+ workbench.getWorkbenchId() + "', '" + workbench.getNoteId() + "')");
		} catch (SQLException e) {
			if (e.getErrorCode() == 335544665) {
				System.out.println("Запись с данным уникальным ключем уже существует");
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

	public Vector<Workbench> select(Hashtable<String, String> selectsHt) {
		Statement statement = null;
		ResultSet resultSet = null;
		String select = makeSelectString(selectsHt, "*");
		Vector<Workbench> workbenches = new Vector<Workbench>();
		try {
			statement = ConnectDB.getConnection().createStatement();
			resultSet = statement.executeQuery(select);
			while (resultSet.next()) {
				Workbench workbench = new Workbench();
				workbench.setNoteId(resultSet.getInt("NOTE_ID"));
				workbench.setWorkbenchId(resultSet.getInt("WORKBENCH_ID"));
				workbenches.addElement(workbench);
			}
		} catch (SQLException e) {
			if (e.getErrorCode() == 335544569) {
				System.out.println("Такого поля не существует, повторите выбор");
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
		return workbenches;
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
				if (requiredField.toUpperCase().equals("NOTE_ID"))
					object = (TY) (Integer) resultSet.getInt("NOTE_ID");
				else if (requiredField.toUpperCase().equals("WORKBENCH_ID"))
					object = (TY) (Date) resultSet.getDate("WORKBENCH_ID");
				else {
					System.out.println("Такого поля не существует");
					return null;
				}
				objects.addElement(object);
			}
		} catch (SQLException e) {
			if (e.getErrorCode() == 335544569) {
				System.out.println("Такого поля не существует, повторите выбор");
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
