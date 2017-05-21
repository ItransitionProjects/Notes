package components;

public class Note {

	private int noteId;
	private String posX;
	private String posY;
	private String color;
	private String heigth;
	private String width;
	private String text;

	public Note(int noteId, String posX, String posY, String color, String heigth, String width, String text) {
		super();
		this.noteId = noteId;
		this.posX = posX;
		this.posY = posY;
		this.color = color;
		this.heigth = heigth;
		this.width = width;
		this.text = text;
	}

	public Note() {
		super();
	}

	public int getNoteId() {
		return noteId;
	}

	public void setNoteId(int noteId) {
		this.noteId = noteId;
	}

	public String getPosX() {
		return posX;
	}

	public void setPosX(String posX) {
		this.posX = posX;
	}

	public String getPosY() {
		return posY;
	}

	public void setPosY(String posY) {
		this.posY = posY;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getHeigth() {
		return heigth;
	}

	public void setHeigth(String heigth) {
		this.heigth = heigth;
	}

	public String getWidth() {
		return width;
	}

	public void setWidth(String width) {
		this.width = width;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String toString() {
		return "Note [noteId=" + noteId + ", posX=" + posX + ", posY=" + posY + ", color=" + color + ", heigth="
				+ heigth + ", width=" + width + ", text=" + text + "]";
	}

}
