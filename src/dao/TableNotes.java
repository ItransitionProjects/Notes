package dao;

import java.util.Date;
import java.util.Hashtable;
import java.util.Vector;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import components.Note;

public class TableNotes extends IDataBase<Note> {

	public boolean insert(Note notes) {
		Statement statement = null;
		try {
			statement = ConnectDB.getConnection().createStatement();
			statement.executeUpdate("INSERT INTO notes (note_Id, pos_x, pos_y, color, heigth, width, text) VALUES ('"
					+ notes.getNoteId() + "', '" + notes.getPosX() + "', '" + notes.getPosY() + "', '"
					+ notes.getColor() + "', '" + notes.getHeigth() + "', '" + notes.getWidth() + "', '"
					+ notes.getText() + "')");
		} catch (SQLException e) {
			if (e.getErrorCode() == 335544665) {
				System.out.println("Note with that ID already exists");
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

	public Vector<Note> select(Hashtable<String, String> selectsHt) {
		Statement statement = null;
		ResultSet resultSet = null;
		String select = makeSelectString(selectsHt, "*");
		Vector<Note> notes = new Vector<Note>();
		try {
			statement = ConnectDB.getConnection().createStatement();
			resultSet = statement.executeQuery(select);
			while (resultSet.next()) {
				Note note = new Note();
				note.setColor(resultSet.getString("COLOR"));
				note.setHeigth(resultSet.getString("HEIGTH"));
				note.setNoteId(resultSet.getInt("NOTE_ID"));
				note.setPosX(resultSet.getString("POS_X"));
				note.setPosY(resultSet.getString("POS_Y"));
				note.setText(resultSet.getString("TEXT"));
				note.setWidth(resultSet.getString("WIDTH"));
				notes.addElement(note);
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
		return notes;
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
				if (requiredField.toUpperCase().equals("COLOR"))
					object = (TY) (Integer) resultSet.getInt("COLOR");
				else if (requiredField.toUpperCase().equals("HEIGTH"))
					object = (TY) (Integer) resultSet.getInt("HEIGTH");
				else if (requiredField.toUpperCase().equals("NOTE_ID"))
					object = (TY) (Date) resultSet.getDate("NOTE_ID");
				else if (requiredField.toUpperCase().equals("POS_X"))
					object = (TY) (Date) resultSet.getDate("POS_X");
				else if (requiredField.toUpperCase().equals("POS_Y"))
					object = (TY) (Integer) resultSet.getInt("POS_Y");
				else if (requiredField.toUpperCase().equals("TEXT"))
					object = (TY) (Date) resultSet.getDate("TEXT");
				else if (requiredField.toUpperCase().equals("WIDTH"))
					object = (TY) (Integer) resultSet.getInt("WIDTH");
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
