<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<html>
<head>
<title>new-form-jsp</title>
</head>
<body>
	<form action="/jsp/members/new-save.jsp" method="post">
		userName: <input type="text" name="userName" /> 
		age: <input type="text" name="age" />
		<button type="submit">전송</button>
	</form>
</body>
</html>