package main.java.tools;

import java.util.Hashtable;
import java.util.List;
import java.util.Vector;

import components.Note;
import components.User;
import components.Workbench;
import dao.IDataBase;
import dao.TableNotes;
import dao.TableUsers;
import dao.TableWorkbench;

public class Tools {

	private static Tools instance = null;

	public synchronized static Tools getInstance() {
		if (instance == null)
			instance = new Tools();
		return instance;
	}

	public User authorize(String login, int password) {
		IDataBase<User> u = new TableUsers();
		Hashtable<String, String> ht = new Hashtable<String, String>();
		ht.put("login", login);
		ht.put("password", ((Integer) password).toString());
		User user = null;
		try {
			user = u.select(ht).firstElement();
		} catch (Exception e) {
			return null;
		}
		return user;
	}

	public List<Note> getNotesByUser(User user) {
		Vector<Integer> notesId = getNotesIdByWorkbench(user.getWorkbench());
		return getNotesByIds(notesId);
	}

	private List<Note> getNotesByIds(Vector<Integer> notesId) {
		IDataBase<Note> n = new TableNotes();
		Hashtable<String, String> ht = new Hashtable<String, String>();
		List<Note> notes = new Vector<Note>();
		for (Integer noteId : notesId){
			ht.put("note_id", noteId.toString());
			notes.add(n.select(ht).firstElement());
			ht.clear();
		}
		return notes;
	}

	private Vector<Integer> getNotesIdByWorkbench(Integer workbench) {
		IDataBase<Workbench> w = new TableWorkbench();
		Hashtable<String, String> ht = new Hashtable<String, String>();
		ht.put("workbench_id", workbench.toString());
		return  w.select(ht, "note_id");

	}

	public int saveToDB(Note note, int workbenchId) {
		IDataBase<Note> n = new TableNotes();
		if(note.getNoteId() == 0){
			n.insert(note);
			IDataBase<Workbench> w = new TableWorkbench();
			Hashtable<String, String> ht = new Hashtable<String, String>();
			ht.put("pos_x", note.getPosX());
			ht.put("pos_y", note.getPosY());
			ht.put("color", note.getColor());
			ht.put("heigth", note.getHeigth());
			ht.put("width", note.getWidth());
			ht.put("text", note.getText());
			Note note1 = n.select(ht).firstElement();
			w.insert(new Workbench(workbenchId,note1.getNoteId()));
			return note1.getNoteId();
		}
		else{
			Hashtable<String, String> ht = new Hashtable<String, String>();
			ht.put("pos_x", note.getPosX());
			ht.put("pos_y", note.getPosY());
			ht.put("color", note.getColor());
			ht.put("heigth", note.getHeigth());
			ht.put("width", note.getWidth());
			ht.put("text", note.getText());
			n.update("note_id", ((Integer)note.getNoteId()).toString(), ht);
			return note.getNoteId();
		}
	}

	public void delete(String noteId) {
		IDataBase<Note> n = new TableNotes();
		n.delete("note_Id", noteId);
		IDataBase<Workbench> w = new TableWorkbench();
		w.delete("note_Id", noteId);
	}

}
