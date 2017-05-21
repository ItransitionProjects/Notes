package components;

public class Workbench {
	private int workbenchId;
	private int noteId;

	public Workbench(int workbenchId, int noteId) {
		super();
		this.workbenchId = workbenchId;
		this.noteId = noteId;
	}

	public Workbench() {
		super();
	}

	public int getWorkbenchId() {
		return workbenchId;
	}

	public void setWorkbenchId(int workbenchId) {
		this.workbenchId = workbenchId;
	}

	public int getNoteId() {
		return noteId;
	}

	public void setNoteId(int noteId) {
		this.noteId = noteId;
	}

	public String toString() {
		return "Workbench [workbenchId=" + workbenchId + ", noteId=" + noteId + "]";
	}

}
