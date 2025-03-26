<%@ page
	import="com.example.hello_servlet.repository.MemberRepository"%>
<%@ page import="com.example.hello_servlet.model.Member"%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%
    //request, response 사용 가능
    MemberRepository memberRepository = MemberRepository.getInstance();
    String userName = request.getParameter("userName");
    int age = Integer.parseInt(request.getParameter("age"));
    Member member = new Member(userName, age);
    System.out.println("member = " + member);
    memberRepository.save(member);
%>
<html>
<head>
<meta charset="UTF-8">
</head>
<body>
	성공
	<ul>
		<li>id=<%=member.getId()%></li>
		<li>userName=<%=member.getName()%></li>
		<li>age=<%=member.getAge()%></li>
	</ul>
</body>
</html>