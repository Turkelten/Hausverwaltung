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
					<form class="login-form" method="post" action="UserChangeServlet">
						<h2>Profildaten</h2>
						<label style="float: left">Vorname</label>
				      	<input type="text" value=${ user.firstName } name="firstname"/>
				      	<label style="float: left">Nachname</label>
				      	<input type="text" value=${ user.lastName } name="lastname"/>
				      	<label style="float: left">Username</label>
				      	<input type="text" value=${ username } name="username"/>
				      	<button type="submit">Ändern</button>				      
			  		</form>					
				</div>
			</div>
	</div>

</body>
</html>