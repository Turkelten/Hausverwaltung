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
		<jsp:include page="sidebarUserProfil.jsp" />
			<div class="user-page">
				  <div class="userform">
					<form method="post" action="InseratServlet">
						<h2>Autoverkaufen</h2>
						<input type="text" placeholder="Beschreibung" name="beschreibung"/>
						<label>Marke:</label><br/>
						<select name="marke" id="marke">
						  <option value="vw">VW</option>
						  <option value="bmw">BMW</option>
						  <option value="mercedes">Mercedes</option>
						  <option value="audi">Audi</option>
						</select><br/><br/><br/>
						<input type="text" placeholder="Bezeichnung" name="bezeichnung"/>
						<label>Motorart:</label><br/>
						<select name="motor" id="motor">
						  <option value="benzin">Benzin</option>
						  <option value="diesel">Diesel</option>
						  <option value="elektro">Elektro</option>
						</select><br/><br/><br/>
						<input type="number" min="0" step="10" placeholder="Leistung(PS)" name="leistung"> <br/>
						<input type="number" min="0" step="0.5" placeholder="Verbrauch" name="verbrauch"/>
						<label>Größe:</label><br/>
						<select name="groesse" id="groesse">
						  <option value="3">3-Türer</option>
						  <option value="5">5-Türer</option>
						</select><br/><br/><br/>	
						<input type="number" min="0" step="500" placeholder="KM-Stand" name="kmStand"> <br/>
						<input type="text" placeholder="Ausstattung" name="ausstattung"/> <br/>
						<button type="submit" value="create" name="button">Auto einstellen</button>
					</form>						
				</div>
			</div>
	</div>
	
</body>
</html>