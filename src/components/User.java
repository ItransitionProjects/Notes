package components;

public class User {

	private int userId;
	private String login;
	private String password;
	private int workbench;

	public User() {
		super();
	}

	public User(int userId, String login, String password, int workbench) {
		super();
		this.workbench = workbench;
		this.userId = userId;
		this.login = login;
		this.password = password;
	}

	public int getWorkbench() {
		return workbench;
	}

	public void setWorkbench(int workbench) {
		this.workbench = workbench;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String toString() {
		return "User [rolesId=" + workbench + ", userId=" + userId + ", login=" + login + ", password=" + password
				+ "]";
	}

}
