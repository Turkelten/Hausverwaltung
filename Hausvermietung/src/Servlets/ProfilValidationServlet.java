package Servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import Objects.User;

/**
 * Servlet implementation class ProfilValidationServlet
 */
@WebServlet("/ProfilValidationServlet")
public class ProfilValidationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ProfilValidationServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession(false);
		
		if(session == null)
		{
			System.out.println("session is null");
		}
		
		String username = (String)session.getAttribute("username");
		
		System.out.println(username);
        if(username!=null){
	        System.out.println("Eingeloggt");
	        response.sendRedirect("userprofil.jsp");
        }  
        else{  
        	System.out.println("Nicht eingeloggt");  
        	RequestDispatcher rd = getServletContext().getRequestDispatcher("/login.jsp");			
			rd.include(request, response);
        } 
	
    
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub

    
	}

}