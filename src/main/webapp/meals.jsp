<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<style>
    <%@include file="/WEB-INF/css/style.css" %>
</style>
<html lang="ru">
<head>
    <title>Meals</title>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<hr>
<h2>Meals</h2>
<table>
    <tr>
        <th>Date</th>
        <th>Description</th>
        <th>Calories</th>
        <th></th>
        <th></th>
    </tr>
    <c:set var="pattern" value="${DateTimeFormatter.ofPattern('yyyy-MM-dd HH:mm')}"/>
    <c:forEach var="meal" items="${requestScope.mealsTo}">
        <c:set var="exceed" value="${meal.excess ? 'exceeded' : 'notExceeded'}"/>
        <tr>
            <td class="${exceed}">${meal.dateTime.format(pattern)}</td>
            <td class="${exceed}">${meal.description}</td>
            <td class="${exceed}">${meal.calories}</td>
            <td>Update</td>
            <td>Delete</td>
        </tr>
    </c:forEach>
</table>
</body>
</html>