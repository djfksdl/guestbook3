<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>delete Form</title>
</head>
<body>
	<form>
	<input type="text" name="no" value="<%=request.getParameter("no")%>">
		<table>
			<tr>
				<td>비밀번호</td>
				<td><input type="password" name="pw"></td>
				<td><button type="submit">삭제</button></td>
				<input type="text" name="action" value="delete" >
			</tr>
		</table>
	</form>
	
	<br><br>
	<a href="">메인으로 돌아가기</a>
</body>
</html>