<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8" name="viewport" content="width=device-width, initial-scale=1.0">
	<title>QR Hunt | New Game</title>
	<link rel="stylesheet" type="text/css" href="/css/bootstrap.css">
	<link rel="stylesheet" type="text/css" href="/css/style.css">
</head>
<body>
	<div class="bgimg bgimg-4"><div class="bgwhite">
		<div class="container">
			<div class="row">
				<div class="col-lg-8 col-md-12 mx-auto py-3">
					<a class="btn btn-primary btn-lg" href="/games">Back to Dashboard</a>
					<h1 class="mt-5">Create a New Game</h1>
					<f:form action="/games" method="post" modelAttribute="game">
						<div class="form-group input-group-lg">
							<f:label class="" path="title"><strong>Title:</strong></f:label>
							<f:errors class="text-white" path="title"/>
							<f:input class="form-control see_thru" type="text" path="title"
								placeholder="required"/>
						</div>
						<div class="form-group input-group-lg">
							<f:label class="" path="qrText"><strong>Text for endpoint QR code:</strong></f:label>
							<f:errors class="text-white" path="qrText"/>
							<f:input class="form-control see_thru" type="text" path="qrText"
								placeholder="required, limit 174 characters"/>
						</div>
						<div class="form-group input-group-lg">
							<f:label class="" path="winMessage"><strong>Message to winners:</strong></f:label>
							<f:errors class="text-white" path="winMessage"/>
							<f:input class="form-control see_thru" type="text" path="winMessage"
								placeholder="required"/>
						</div>
						<div class="form-group input-group-lg">
							<f:label class="" path="winnerLimit"><strong>Winner limit (0 for unlimited):</strong></f:label>
							<f:errors class="text-white" path="winnerLimit"/>
							<f:input class="form-control see_thru" type="number" path="winnerLimit"
								min="0" value="0"/>
						</div>
						<button class="btn btn-success btn-lg btn-block" type="submit">Create</button>
					</f:form>
				</div>
			</div>
		</div>
	</div></div>
</body>
</html>