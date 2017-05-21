<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" type="text/css" href="resources/css/style.css" media="all" />
<script src="resources/js/script.js"></script>
<title>Notes Authorization</title>
</head>
<body id='body'>
	<br>
	<br>
	<br>
	<center>
		<section class="container">
		<div class="login">
			<h1>Enter your login and password</h1>
			<form method="post" action="${pageContext.request.contextPath}/NotesServlet">
				<p>
					<input type="text" name="login" value="" placeholder="Username">
				</p>
				<p>
					<input type="password" name="password" value=""
						placeholder="Password">
				</p>
				<p class="submit">
					<input type="submit" name="commit" value="Login">
				</p>
			</form>
		</div>
		</section>
	</center>
</body>

</html>