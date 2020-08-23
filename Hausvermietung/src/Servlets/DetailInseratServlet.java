package Servlets;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import Controller.Controller;
import Objects.Inserat;

/**
 * Servlet implementation class DeteilInseratServlet
 */
@WebServlet("/DetailInseratServlet")
public class DetailInseratServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DetailInseratServlet() {
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
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
		String id = (String) request.getParameter("inseratid");
		HttpSession session = request.getSession();
		String destPage = "userprofil.jsp";
		
		Inserat inserat = null;
		try {
			//einzelnes Inserat auslesen
			inserat = Controller.ReadInserat(Integer.parseInt(id));
			if(inserat != null) {
				session.setAttribute("Inserat", inserat);
				destPage = "inseratDetailAnsicht.jsp";
			}
			
		} catch (NumberFormatException e1) {
			
			// TODO Auto-generated catch block
			destPage = "fehler.jsp";
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			destPage = "fehler.jsp";
		}
		RequestDispatcher dispatcher = request.getRequestDispatcher(destPage);
		dispatcher.forward(request, response);
	}

}
