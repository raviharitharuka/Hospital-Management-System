<%@page import="models.Users"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Users Management</title>
<link rel="stylesheet" href="Views/bootstrap.min.css">
<script src="Components/jquery-3.2.1.min.js"></script>
<script src="Components/main.js"></script>
</head>
<body>
	<div class="container">
		<div class="row">
			<div class="col-6">
				<h1>Users Management</h1>
				<form id="formUsers" name="formUsers">
					Name: <input id="name" name="name" type="text"
						class="form-control form-control-sm"> <br> Email: <input
						id="email" name="email" type="email"
						class="form-control form-control-sm"> <br> Password:
					<input id="password" name="password" type="password"
						class="form-control form-control-sm"> <br> Role
					(Admin / Doctor / Patient): <input id="role" name="role"
						type="text" class="form-control form-control-sm"> <br>
					<br> <input id="btnSave" name="btnSave" type="button"
						value="Save" class="btn btn-primary" onClick="SaveRefresh();">
					<input type="hidden" id="hidUsersIDSave" name="hidUsersIDSave"
						value="">
				</form>
				<br>
				<div id="alertSuccess" class="alert alert-success"></div>
				<div id="alertError" class="alert alert-danger"></div>
				<br>
				<div id="divUsersGrid">
					<%
						Users usersObj = new Users();
					out.print(usersObj.readUsers());
					%>
				</div>
			</div>
		</div>
	</div>
	<br>
	<br>
</body>
</html>







