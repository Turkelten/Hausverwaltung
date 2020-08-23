package Servlets;

import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import Controller.Controller;
import Objects.User;

/**
 * Servlet implementation class UserChangeServlet
 */
@WebServlet("/UserChangeServlet")
public class UserChangeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserChangeServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		
		//Userdaten auslesen
		User User = null;
        String username = (String) session.getAttribute("username");
              
        try
        {
            User = Controller.GetUser(username);
        } catch (SQLException throwables)
        {
        	//niht eingeloggt
            throwables.printStackTrace();
        }
		
        //Daten der Seite laden
		String UserName = request.getParameter("username");
		String FirstName = request.getParameter("firstname");
		String LastName = request.getParameter("lastname");

		
		try
		{
			if(Controller.UpdateUser(User.id, UserName, FirstName, LastName))
			{
				//Hat funktioniert
				System.out.println("Userdaten ge�ndert");
				response.sendRedirect("LogoutServlet");

			}
		} catch (SQLException throwables)
		{
			//Hat nicht funktioniert
			System.out.println("Userdaten nicht ge�ndert");
			response.sendRedirect("fehler.jsp");
		}

	}

}
