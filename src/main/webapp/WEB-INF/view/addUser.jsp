<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h1>회원가입</h1>
	<hr>
	<form action="/addUserAction" method="post">
    	username: <input type="text" name="username">
    	<br>
    	password: <input type="password" name="password">
		<br>
		<button>회원가입</button>
	</form>
</body>
</html>