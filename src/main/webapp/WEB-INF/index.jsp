<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8" name="viewport" content="width=device-width, initial-scale=1.0">
	<title>QR Hunt | Index</title>
	<link rel="stylesheet" type="text/css" href="/css/bootstrap.css">
<!-- 	<link rel="stylesheet" type="text/css" href="/css/style.css"> -->
</head>
<body>
	<div class="container">
		<div class="jumbotron mt-3">
			<h1 class="text-center">Welcome to QR Hunt</h1>
			<h3 class="text-center">A scavenger hunt game using QR codes.</h3>
			<hr>
			<h4 class="text-center">Please log in or register below.</h4>
			<h4 class="text-center">Or <a href="/games/available">click here</a> to view available games.</h4>
		</div>
		<div class="row">
			<div class="col-lg-6 col-md-12 border py-3">
				<h2>Login</h2>
				<h3 class="text-danger">${msg}</h3>
				<form action="/users/login" method="post">
					<div class="form-group input-group-lg">
						<label>Alias:</label>
						<input class="form-control" type="text" name="alias">
					</div>
					<div class="form-group input-group-lg">
						<label>Password:</label>
						<input class="form-control" type="password" name="password">
					</div>
					<button class="btn btn-primary btn-lg btn-block" type="submit">Login</button>
				</form>
			</div>
			<div class="col-lg-6 col-md-12 border py-3">
				<h2>Register</h2>
				<f:form action="/users" method="post" modelAttribute="user">
					<div class="form-group input-group-lg">
						<f:label path="alias">Alias:</f:label>
						<f:errors class="text-danger" path="alias"/>
						<f:input class="form-control" type="text" path="alias"
							placeholder="1-63 characters, no spaces"/>
					</div>
					<div class="form-group input-group-lg">
						<f:label path="password">Password:</f:label>
						<f:errors class="text-danger" path="password"/>
						<f:input class="form-control" type="password" path="password"
							placeholder="At least 8 characters"/>
					</div>
					<div class="form-group input-group-lg">
						<f:label path="passwordConfirmation">Password Confirmation:</f:label>
						<f:errors class="text-danger" path="passwordConfirmation"/>
						<f:input class="form-control" type="password" path="passwordConfirmation"
							placeholder="Must match password"/>
					</div>
					<button class="btn btn-success btn-lg btn-block" type="submit">Register</button>
				</f:form>
			</div>
		</div>
	</div>	
</body>
</html>