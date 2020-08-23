package Servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.mail.MessagingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import Controller.Controller;
import Objects.User;

/**
 * Servlet implementation class PasswordChangeServlet
 */
@WebServlet("/PasswordChangeServlet")
public class PasswordChangeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PasswordChangeServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//TODO TJERK
		
		HttpSession session = request.getSession(false);
		String userName = (String) session.getAttribute("username");
		
		User user = null;
		
		try {
			user = Controller.GetUser(userName);
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		String currentPassword = request.getParameter("passwordold");
		
		String new1 = request.getParameter("passwordnew1");
		String new2 = request.getParameter("passwordnew2");
		
		if(!new1.equals(new2))
		{
			//Neues PW stimmt nicht überein
			System.out.println("Neue Passwörter stimmen nicht überein");
			response.sendRedirect("fehler.jsp");	
			return;
		}
		int userid = 0;
		try
		{
			if(Controller.ChangePassword(user.id, currentPassword, new1 ))
			{
				System.out.println("hat geklappt");
				response.sendRedirect("userprofil.jsp");
			}
			else
			{
				System.out.println("hat nicht geklappt");
				response.sendRedirect("userPasswortAendern.jsp");

			}

		} catch (SQLException throwables)
		{
			//Irgend ne Query hat nicht hingehauen
			throwables.printStackTrace();

		}

	}

}
