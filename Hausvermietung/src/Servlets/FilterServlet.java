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

/**
 * Servlet implementation class FilterServlet
 */
@WebServlet("/FilterServlet")
public class FilterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FilterServlet() {
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
		// TODO Auto-generated method stub
		HttpSession session = request.getSession();		
		
		String beschreibung = request.getParameter("beschreibung");
		String marke = request.getParameter("marke");
		String PS = request.getParameter("leistung");
		String verbrauch = request.getParameter("verbrauch");
		String groesse = request.getParameter("groesse");
		String kilometerstand = request.getParameter("kmStand");
		String Verbrauchsstoff = request.getParameter("motor");
		String Bezeichnung = request.getParameter("bezeichnung");
		
//    	return String.format("select * from inserate where beschreibung like {1} and marke like {4} and PS >= %s and verbrauch <= %s and groesse = %s and kilometerstand <= %s and verbrauchsstoff like {5} and bezeichnung like {2}"
//    			+ " and ausstattung like {3};",marke, PS, verbrauch, groesse, kilometerstand, verbrauchsstoff)
//    			.replace("{1}", "%" + beschreibung + "%")
//    			.replace("{2}", "%" + bezeichnung + "%;")
//				.replace("{4}", marke)
//				.replace("{5}", verbrauchsstoff );
    	
		
		//Anpassung für Backend
		if(Verbrauchsstoff == "")
		{
			Verbrauchsstoff = "%";
			
		}
		else
		{
			Verbrauchsstoff = "%" + Verbrauchsstoff + "%";
		}
		
		if(marke.equals(""))
		{
			marke = "%";
		}
		else
		{
			marke = "%" + marke + "%";
		}
		
		if(PS.equals(""))
		{
			PS = "0";
		}
		
		if(verbrauch.equals(""))
		{
			verbrauch = "999";
		}
		
		if(groesse  == "")
		{
			groesse = "3";
		}
		
		if(kilometerstand.equals(""))
		{
			kilometerstand = "999999999";
		}
		
		if(beschreibung.equals(""))
		{
			beschreibung = "%";
		}
		else
		{
			beschreibung = "%" + beschreibung + "%";
		}
		
		if(Bezeichnung.equals(""))
		{
			Bezeichnung = "%";
		}
		else
		{
			Bezeichnung = "%" + Bezeichnung + "%";
		}
		
		//Inserat erstellen
		Inserat searchInserat = null;
			
		String destPage = "index.jsp";
		
		//Inserat in DB inserten
		try
		{
			int sPS = Integer.parseInt(PS);
			float sVerbrauch = Float.parseFloat(verbrauch);
			int sGroesse = Integer.parseInt(groesse);
			int sKilometer = Integer.parseInt(kilometerstand);
			
			ArrayList<Inserat> filteredInserate = Controller.SearchInserate(beschreibung, marke, sPS, sVerbrauch, sGroesse, sKilometer, Verbrauchsstoff, Bezeichnung);
			
			session.setAttribute("SearchResult", filteredInserate);
			
			destPage = "results.jsp";
			
		}catch (Exception e) {
			destPage = "fehler.jsp";
			
			// TODO: handle exception
		}

		RequestDispatcher dispatcher = request.getRequestDispatcher(destPage);
		dispatcher.forward(request, response);
	}

}
