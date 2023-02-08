<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
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
<a href="editMeal?action=create">Add meal</a>
<table>
    <tr>
        <th>Date</th>
        <th>Description</th>
        <th>Calories</th>
        <th></th>
        <th></th>
    </tr>
    <c:set var="pattern" value="${requestScope.pattern}"/>
    <c:forEach var="meal" items="${requestScope.mealsTo}">
        <c:set var="exceed" value="${meal.excess ? 'exceeded' : 'notExceeded'}"/>
        <tr class="${exceed}">
            <td>${meal.dateTime.format(pattern)}</td>
            <td>${meal.description}</td>
            <td>${meal.calories}</td>
            <td class="simple"><a href="editMeal?action=update&mealId=<c:out value="${meal.id}"/>">Update</a></td>
            <td class="simple"><a href="editMeal?action=delete&mealId=<c:out value="${meal.id}"/>">Delete</a></td>
        </tr>
    </c:forEach>
</table>
</body>
</html>