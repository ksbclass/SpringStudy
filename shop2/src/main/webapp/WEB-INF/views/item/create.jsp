<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%><c:catch></c:catch>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!--파일업로드 , 유효성검증 -->
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>상품등록</title>
</head>
<body>
<%--  enctype="multipart/form-date : POST방식 --%>
<form:form modelAttribute="item" action="create"  enctype="multipart/form-data">
<h2>상품등록</h2>
<table>
<tr><td>상품명</td>
<td><form:input path="name"/></td>
<td><font color="red"><form:errors path="name"/></font></td></tr>

<tr><td>상품가격</td>
	<td><form:input path="price"/></td>
	<td><font color="red"><form:errors path="price"/></font></td></tr>
	
<tr><td>상품이미지</td>	
<td colspan="2"><input type="file" name="picture"></td></tr>

<tr><td>상품설명</td>
	<td><form:textarea path="description" cols="20" rows="5"/></td>
	<td><font color="red"><form:errors path="description"/></font></td></tr>
	
<tr><td colspan="3"><input type="submit" value="상품등록">	
	<input type="button" value="상품목록" onclick="location.href='list'"></td></tr>
</table>
</form:form>
</body>
</html>