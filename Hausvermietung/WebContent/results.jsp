<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>	
<!DOCTYPE html>
<html>
<head>
	<meta name="viewport" content="width=device-width, initial-scale=1.0" charset="ISO-8859-1">
	<title>Hausvermietung</title>
	<link href="https://fonts.googleapis.com/css2?family=Roboto+Slab:wght@300&family=Roboto:wght@300&display=swap" rel="stylesheet">
	<link href="style.css" rel="stylesheet">
</head>
<body>
	<jsp:include page="navigationbar.jsp" />
	<div class="page">
		<jsp:include page="searchBar.jsp" />
		<div class="user-page">
		  	<div class="anzeige">
			      <h2>Suchergebnisse</h2><br/>
					  <div class="userinserate">
					  	<table>
					  		<tr>				  			
					  			<th>
					  				<label style="font-size: 2rem; padding-left: 10px">Beschreibung</label>					  				
					  			</th>
					  			<th>
					  				<label style="font-size: 2rem; padding-left: 10px">Auto Marke</label>					  				
					  			</th>
					  			<th>
					  				<label style="font-size: 2rem; padding-left: 10px"">Bezeichnung</label>
					  			</th>
					  			<th>
					  				<label style="font-size: 2rem; padding-left: 10px">Leistung</label>					  				
					  			</th>
					  			<th>
					  				<label style="font-size: 2rem; padding-left: 10px">Motortyp</label>					  				
					  			</th>
					  		</tr>			
 							<c:forEach var="current" items="${ SearchResult }" >
 								<tr>
 								
								<td>
									<a><c:out value="${ current.beschreibung }" /></a>
								</td>
								<td>
									<a><c:out value="${ current.marke }" /></a>
								</td>
								<td>
									<a><c:out value="${ current.bezeichnung }" /></a>
								</td>
								<td>
									<a><c:out value="${ current.PS }" /></a>
								</td>
								<td>
									<a><c:out value="${ current.verbrauchsstoff }" /></a>
								</td>
									<td>
										<form action="DetailInseratServlet" method="post">
											<input style="display: none" type="text" name="inseratid" value="${ current.id }">
											<button type="submit" name="button" value="detail">Details</button>							    	
									    </form>
								    </td>
								</tr>								
							</c:forEach>	
						 </table>		
					</div>
			</div>
		</div>
	</div>	
	
</body>
</html>