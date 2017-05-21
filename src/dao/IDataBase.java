package dao;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;



public abstract class IDataBase<T> {

	public abstract boolean insert(T object);

	public abstract Vector<T> select(Hashtable<String, String> selects);

	public abstract <TY> Vector<TY> select(Hashtable<String, String> selects, String requiredField);

	public boolean update(String field, String value, Hashtable<String, String> newValues) {
		Statement statement = null;
		String update = makeUpdateString(field, value, newValues);
		if (update == null)
			return false;
		try {
			try {
				statement = ConnectDB.getConnection().createStatement();
				statement.executeUpdate(update);
			} catch (SQLException e) {
				if (e.getErrorCode() == 335544665) {
					System.out.println("Запись с таким уникальным ключем уже существует");
				} else {
					e.printStackTrace();
				}
				return false;
			}
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

	public <T1> boolean delete(String field, T1 value) {

		String clName = getClassName();
		if (clName == null)
			return false;

		Statement statement = null;
		try {
			statement = ConnectDB.getConnection().createStatement();
			statement.executeUpdate("DELETE FROM " + clName + " WHERE " + field + " = '" + value + "'");
		} catch (SQLException e) {
			if (e.getErrorCode() == 335544569) {
				System.out.println("В таблице такого поля не существует");
			} else if(e.getErrorCode() == 335544363){
				System.out.println("Нет такого значения");
			} else{
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

	public String makeSelectString(Hashtable<String, String> selectsHt, String field) {
		String clName = getClassName();
		if (clName == null)
			return null;

		if (selectsHt == null || selectsHt.size() == 0) {
			return "SELECT " + field + " FROM " + clName;
		}
		String select = new String("SELECT " + field + " FROM " + clName + " WHERE ");
		Enumeration<String> fields = selectsHt.keys();
		Enumeration<String> values = selectsHt.elements();
		while (fields.hasMoreElements() && values.hasMoreElements()) {
			select += fields.nextElement();
			select += " = '";
			select += values.nextElement();
			select += "'";
			if (fields.hasMoreElements() && values.hasMoreElements())
				select += " AND ";
		}
		return select;
	}

	public String makeUpdateString(String field, String value, Hashtable<String, String> newValues) {
		String clName = getClassName();
		if (clName == null)
			return null;

		if (newValues == null || newValues.size() == 0) {
			return null;
		}
		String update = new String("UPDATE " + clName + " SET ");
		Enumeration<String> fields = newValues.keys();
		Enumeration<String> values = newValues.elements();
		while (fields.hasMoreElements() && values.hasMoreElements()) {
			update += fields.nextElement();
			update += " = '";
			update += values.nextElement();
			update += "'";
			if (fields.hasMoreElements() && values.hasMoreElements())
				update += ", ";
			else {
				update += " WHERE " + field + " = '" + value + "'";
			}
		}
		return update;
	}

	private String getClassName() {
		if (this.getClass().getName().equals("dao.TableUsers"))
			return "users";
		else if (this.getClass().getName().equals("dao.TableNotes"))
			return "notes";
		else if (this.getClass().getName().equals("dao.TableWorkbench"))
			return "workbenches";
		else
			return null;
	}
}
