<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<style>
    <%@include file="/WEB-INF/css/style.css" %>
</style>
<html lang="ru">
<head>
    <title>Edit meal</title>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<hr>
<h2>Edit meal</h2>
<jsp:useBean id="meal" scope="request" type="ru.javawebinar.topjava.model.Meal"/>
<form method="POST" action='editMeal' name="AddEditMeal">
    Meal ID : <input type="hidden" readonly="readonly" name="mealId" value="<c:out value="${meal.id}" />"/> <br/>
    DateTime : <input type="datetime-local" name="localDateTime" value="<c:out value="${meal.dateTime}" />"/> <br/>
    Description : <input type="text" name="description" value="<c:out value="${meal.description}" />"/> <br/>
    Calories : <input type="number" name="calories" value="<c:out value="${meal.calories}" />"/> <br/>
    <input type="submit" value="Save"/>
    <button onclick="window.history.back()" type="button">Cancel</button>
</form>
</body>
</html>
