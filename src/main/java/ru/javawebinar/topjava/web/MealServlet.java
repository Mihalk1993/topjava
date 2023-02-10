package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.repository.InMemoryMealCrud;
import ru.javawebinar.topjava.model.MealTo;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;
import static ru.javawebinar.topjava.util.MealsUtil.*;

public class MealServlet extends HttpServlet {
    private static final Logger log = getLogger(MealServlet.class);
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("forward to meals");

        request.setAttribute("pattern", DATE_TIME_FORMATTER);

        List<MealTo> mealsTo = filteredByStreams(new ArrayList<>(InMemoryMealCrud.getInstance().getAll()), LocalTime.MIN, LocalTime.MAX, CALORIES_PER_DAY);
        mealsTo.sort(Comparator.comparing(MealTo::getDateTime));
        request.setAttribute("mealsTo", mealsTo);

        request.getRequestDispatcher("/meals.jsp").forward(request, response);
    }
}
