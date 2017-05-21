package main.java;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.fasterxml.jackson.databind.ObjectMapper;

import components.Note;
import components.User;
import dao.ConnectDB;
import main.java.tools.Tools;

@WebServlet("/NotesServlet")
public class NotesServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public NotesServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.getRequestDispatcher("//loginPage.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		if (request.getParameter("commit") != null) {
			String login = request.getParameter("login");
			String password = request.getParameter("password");
			User user = Tools.getInstance().authorize(login, password.hashCode());
			if (user == null) {
				request.getRequestDispatcher("//wrongUser.jsp").forward(request, response);
			} else {
				List<Note> notes = Tools.getInstance().getNotesByUser(user);
				request.setAttribute("notes", notes);
				session.setAttribute("login", user.getLogin());
				session.setAttribute("workbench", user.getWorkbench());
				request.getRequestDispatcher("//mainPage.jsp").forward(request, response);
			}
		}
		else if (request.getParameter("note") != null) {
			String noteStr = request.getParameter("note");
			ObjectMapper mapper = new ObjectMapper();
			Note note = mapper.readValue(noteStr, Note.class);
			int note_id = Tools.getInstance().saveToDB(note, Integer.parseInt(
					session.getAttribute("workbench").toString()));
			response.getWriter().write(note_id);
		}
		else if (request.getParameter("delete") != null) {
			Tools.getInstance().delete((String)request.getParameter("noteId"));
			String noteStr = request.getParameter("note");
			ObjectMapper mapper = new ObjectMapper();
			Note note = mapper.readValue(noteStr, Note.class);
			int note_id = Tools.getInstance().saveToDB(note, Integer.parseInt(
					session.getAttribute("workbench").toString()));
			response.getWriter().write(note_id);

		} else {
			request.getRequestDispatcher("//loginPage.jsp").forward(request, response);
		}
	}

	public void init(ServletConfig config) throws ServletException {
		ConnectDB.connectToDB();
	}
}
