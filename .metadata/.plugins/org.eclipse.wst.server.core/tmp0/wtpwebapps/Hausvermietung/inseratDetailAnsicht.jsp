<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
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
		  		<div class="anzeige">
			  	<h2>${ Inserat.beschreibung }</h2>
			  		<form method="post" <%-- %>action="KontaktUserServlet"--%> >
							<label>Marke:</label><br/>
							<label>${ Inserat.marke }</label><br/>
							<label>Bezeichnung:</label><br/>
							<label>${ Inserat.bezeichnung }</label><br/>
							<label>Motorart:</label><br/>
							<label>${ Inserat.verbrauchsstoff }</label><br/>
							<label>Leisung:</label><br/>
							<label>${ Inserat.PS }</label><br/>
							<label>Verbrauch:</label><br/>
							<label>${ Inserat.verbrauch }</label><br/>
							<label>Kilometerstand:</label><br/>
							<label>${ Inserat.kilometerstand }</label><br/>
							<label>Ausstattung:</label><br/>
							<label>${ Inserat.ausstattung }</label><br/>
							<input style="display: none" type="text" value="${ inseratChange.id }" name="inseratid"/> <br/>
							<a href="nichtimplementiert.jsp">Verkäufer kontaktieren</a>
						</form>
					</div>
		  	</div>
		</div>
	</div>	
	
</body>
</html>