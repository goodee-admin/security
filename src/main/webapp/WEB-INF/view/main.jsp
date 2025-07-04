<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h1>Main</h1>
	
	<hr>
	
	<!-- 로그인이 안 되어있다면 -->
	<c:if test="${loginUsername == 'anonymousUser'}">
		<a href="/addUser">회원가입</a>
		<a href="/login">로그인</a>
	</c:if>
	
	<!-- 로그인이 되어있다면 -->
	<c:if test="${loginUsername != 'anonymousUser'}">
		${loginUsername}(${loginRole})님 반갑습니다.
		<a href="/logout">로그아웃</a> <!-- 로그아웃 설정은 SecurityConfig.class -->
	</c:if>
</body>
</html>