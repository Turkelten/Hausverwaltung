package Servlets;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import Controller.Controller;
import Objects.Inserat;
import Objects.User;

/**
 * Servlet implementation class UserInserateServlet
 */
@WebServlet("/UserInserateServlet")
public class UserInserateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserInserateServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		HttpSession session = request.getSession();
		
		String username = (String) session.getAttribute("username");
		String destPage = "login.jsp";
		ArrayList<Inserat> inserate = new ArrayList<Inserat>();
		
		User user = null;
		try {
			user = Controller.GetUser(username);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(user == null)
		{
			RequestDispatcher dispatcher = request.getRequestDispatcher("fehler.jsp");
			dispatcher.forward(request, response);
			
		}
		
		try {
			inserate = Controller.ReadInserateForUser(user.id);
			session.setAttribute("inserate", inserate);
		} catch (SQLException throwables)
		{
			//Fehler beim Inserten
			destPage = "fehler.jsp";
			throwables.printStackTrace();
		}
		
		//Erfolgreich
		if(inserate == null) {
			//Auf Fehlerseite
			destPage = "fehler.jsp";
		}else {
			//Auf Erfolgsseite
			destPage = "userEigeneInserate.jsp";
		}
		
		


		RequestDispatcher dispatcher = request.getRequestDispatcher(destPage);
		dispatcher.forward(request, response);
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
