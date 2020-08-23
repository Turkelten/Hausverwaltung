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
 * Servlet implementation class HandleInseratServlet
 */
@WebServlet("/HandleInseratServlet")
public class HandleInseratServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public HandleInseratServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		// TODO Auto-generated method stub
		
		String id = (String) request.getParameter("inseratid");
		String function = (String) request.getParameter("button");
		HttpSession session = request.getSession();
		
		//Userid holen
				
		String username = (String) session.getAttribute("username");
		String destPage = "userprofil.jsp";
		
		User user = null;
		try 
		{
			user = Controller.GetUser(username);
		} 
		catch (SQLException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//kein User eingeloggt
		if(user == null)
		{
			RequestDispatcher dispatcher = request.getRequestDispatcher("fehler.jsp");
			dispatcher.forward(request, response);
			
		}
		
		if(function.equals("delete")) 
		{
			//Inserat Löschen
			try 
			{			
				Controller.DeleteInserat(Integer.parseInt(id));
				
			} 
			catch (SQLException e) 
			{
				destPage = "fehler.jsp";
				
			}
			
			destPage = "userprofil.jsp";
		}
		
		if(function.contentEquals("change")) 
		{
					
			Inserat inserat = null;
			try {
				//einzelnes Inserat auslesen
				inserat = Controller.ReadInserat(Integer.parseInt(id));
			} catch (NumberFormatException e1) {
				// TODO Auto-generated catch block
				destPage = "fehler.jsp";
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				destPage = "fehler.jsp";
			}
			
			session.setAttribute("inseratChange", inserat);
			
			//Aufruf Inserat ändern
			destPage = "userInseratAendern.jsp";

		}
		
		RequestDispatcher dispatcher = request.getRequestDispatcher(destPage);
		dispatcher.forward(request, response);
	}
}