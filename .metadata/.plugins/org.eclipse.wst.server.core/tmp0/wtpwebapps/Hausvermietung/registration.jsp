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
		<div class="login-page">
		  	<div class="form">
				<form class="login-form" method="post" action="RegistrationServlet">
			      	<input type="text" placeholder="firstname" name="firstname"/>
			      	<input type="text" placeholder="lastname" name="lastname"/>
			      	<input type="password" placeholder="password" name="password"/>
			      	<input type="text" placeholder="email address" name="username"/>
			      	<button type="submit">create</button>
			      	<p class="message">Already registered? <a href="login.jsp">Sign In</a></p>
			  	</form>
	  		</div>
  		</div>
  	</div>
</body>
</html>