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
<h2><%=request.getAttribute("action") == "create" ? "Add meal" : request.getAttribute("action") == "update" ? "Update Meal" : "???" %>
</h2>
<c:set var="pattern" value="${requestScope.pattern}"/>
<jsp:useBean id="meal" scope="request" type="ru.javawebinar.topjava.model.Meal"/>
<form method="POST" action='editMeal' name="AddEditMeal">
    <input type="hidden" readonly="readonly" name="mealId" value="${meal.id}"/> <br/>
    DateTime : <input type="datetime-local" name="localDateTime" value="${meal.dateTime.format(pattern)}"/> <br/>
    Description : <input type="text" name="description" value="${meal.description}"/> <br/>
    Calories : <input type="number" name="calories" value="${meal.calories}"/> <br/>
    <input type="submit" value="Save"/>
    <button onclick="window.history.back()" type="button">Cancel</button>
</form>
</body>
</html>
